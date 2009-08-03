/**
 * 
 */
package de.tuberlin.uebb.emodelica.builder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;
import de.tuberlin.uebb.emodelica.model.project.impl.ProjectManager;

/**
 * @author choeger
 * 
 */
public class MosilabBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "de.tuberlin.uebb.emodelica.mosilacBuilder";

	/**
	 * 
	 */
	public MosilabBuilder() {
		// TODO Auto-generated constructor stub
	}

	private MessageConsole findConsole(String name) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++)
			if (name.equals(existing[i].getName()))
				return (MessageConsole) existing[i];
		// no console found, so create a new one
		MessageConsole myConsole = new MessageConsole(name, null);
		conMan.addConsoles(new IConsole[] { myConsole });
		return myConsole;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int,
	 * java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		monitor.beginTask("Running mosilac", 100);

		/* incremental build */
		System.err.println("MOSILAB builder starting");
		if ((kind == AUTO_BUILD) || (kind == INCREMENTAL_BUILD)) {
			System.err.println("arguments: " + args.keySet());

			monitor.subTask("collecting resources");

			List<IFile> affectedResources = collectResources();

			System.err.println("got " + affectedResources.size()
					+ " files to compile.");
			monitor.subTask("running mosilac");

			MessageConsole console = findConsole("mosilac console");
			MessageConsoleStream out = console.newMessageStream();

			for (IResource resource : affectedResources) {
				String sourceFile = getLocation(resource);
				if (runMosilac(sourceFile, console, out) == 0) {
					runMakefile(resource, console, out);
				}
			}
		}

		monitor.done();

		return null;
	}

	private int runMakefile(IResource modelicaSourceFile,
			MessageConsole console, MessageConsoleStream out) {
		IMosilabProject mosilabProject = ProjectManager.getDefault()
				.getMosilabProject(getProject());

		String srcPath = getLocation(getProject().getFolder(
				mosilabProject.getOutputFolder()));

		File makefile = new File(mosilabProject.getMOSILABEnvironment()
				.getLocation()
				+ File.separator + "lib" + File.separator + "makefile");

		File ccSourceFile = null;
		// TODO: adapt file the modelica resource directly, then let go this
		// stuff!
		for (IMosilabSource sourceFolder : mosilabProject.getSrcFolders())
			if (sourceFolder.getBasePath().getFullPath().isPrefixOf(
					modelicaSourceFile.getFullPath())) {
				IPath packagePart = modelicaSourceFile.getFullPath()
						.removeFirstSegments(
								sourceFolder.getBasePath().getFullPath()
										.segments().length);
				String sourceFileName = srcPath + File.separator;
				for (String seg : packagePart.removeLastSegments(1).segments())
					sourceFileName += seg + "__";
				sourceFileName += packagePart.lastSegment().replaceAll("\\.mo",
						"\\.cc");

				ccSourceFile = new File(sourceFileName);
				break;
			}

		if (makefile.exists() && ccSourceFile.exists()) {
			try {
				console.activate();

				ProcessBuilder processBuilder = new ProcessBuilder(mosilabProject.getMOSILABEnvironment().getLocation() + 
						File.separator + "bin" + File.separator + "mkSelector.sh");
				processBuilder.directory(new File(srcPath));
				
				processBuilder.environment().put("MOSILAB_ROOT",
						mosilabProject.getMOSILABEnvironment().getLocation());

				Process proc = processBuilder.start();

				followOutput(out, processBuilder, proc);
				out.write("\n mkSelector.sh returned with: " + proc.exitValue() + "\n");

				// TODO: fix source file to c++ file mapping
				processBuilder = new ProcessBuilder("make",
						"-f", makefile.getAbsolutePath(), ccSourceFile
								.getAbsolutePath().replaceAll("\\.cc", "\\.o"), srcPath + File.separator + "_selector.o");

				processBuilder.environment().put("MOSILAB_ROOT",
						mosilabProject.getMOSILABEnvironment().getLocation());

				proc = processBuilder.start();

				followOutput(out, processBuilder, proc);

				out.write("\n make returned with: " + proc.exitValue() + "\n");

				if (proc.exitValue() != 0) {
					return proc.exitValue();
				}

				return proc.exitValue();
			} catch (IOException e) {
				// TODO throw CoreException
				e.printStackTrace();
			}
		} else {
			if (ccSourceFile.exists())
				System.err.println("Makefile: " + makefile.toString()
						+ " not found!");
			else
				System.err.println("Sourcefile: " + ccSourceFile.toString()
						+ " not found!");
		}
		return -1;
	}

	/**
	 * @param out
	 * @param proc
	 * @throws IOException
	 */
	private void followOutput(MessageConsoleStream out,
			ProcessBuilder processBuilder, Process proc) throws IOException {
		for (String cmd : processBuilder.command()) {
			out.write(cmd);
			out.write(" ");
		}
		out.write("\n");

		int r = -1;

		while ((r = proc.getInputStream().read()) > -1)
			out.write(r);

		try {
			proc.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}

		while ((r = proc.getInputStream().read()) > -1)
			out.write(r);
	}

	/**
	 * @param resource
	 * @return
	 */
	private String getLocation(IResource resource) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(
				resource.getFullPath()).getLocation().toOSString();
	}

	/**
	 * @param sourceFile
	 * @param console
	 * @param out
	 */
	private int runMosilac(String sourceFile, MessageConsole console,
			MessageConsoleStream out) {
		// TODO: move to adapter -> IMosilabProject
		IMosilabProject mosilabProject = ProjectManager.getDefault()
				.getMosilabProject(getProject());
		String mosilac = mosilabProject.getMOSILABEnvironment()
				.compilerCommand();
		String outPath = getLocation(getProject().getFolder(
				mosilabProject.getOutputFolder()));
		File binary = new File(mosilac);

		if (binary.exists()) {
			try {
				console.activate();

				ProcessBuilder processBuilder = new ProcessBuilder(mosilac,
						"--prefix", outPath, sourceFile);

				StringBuilder pathBuilder = new StringBuilder();
				// TODO make MOSILAB_LOADPATH configurable in MOSILAB
				// preferences
				pathBuilder.append(mosilabProject.getMOSILABEnvironment()
						.getLocation());
				pathBuilder.append(IPath.SEPARATOR);
				pathBuilder.append("base-lib");
				for (IMosilabSource src : mosilabProject.getSrcFolders()) {
					pathBuilder.append(File.pathSeparator);
					pathBuilder.append(getLocation(src.getBasePath()));
				}

				processBuilder.environment().put("MOSILAB_LOADPATH",
						pathBuilder.toString());
				processBuilder.environment().put("MOSILAB_ROOT",
						mosilabProject.getMOSILABEnvironment().getLocation());

				Process proc = processBuilder.start();

				followOutput(out, processBuilder, proc);

				System.err
						.println("mosilac returned with: " + proc.exitValue());
				out.write("\n mosilac returned with: " + proc.exitValue()
						+ "\n");

				return proc.exitValue();
			} catch (IOException e) {
				// TODO throw CoreException
				e.printStackTrace();
			}
		} else
			System.err.println("binary " + binary.toString() + " not found!");
		return -1;
	}

	private List<IFile> collectResources() {
		// TODO change this to accept() usage before commit
		final List<IFile> affectedResources = new ArrayList<IFile>();

		try {
			this.getDelta(getProject()).accept(new IResourceDeltaVisitor() {
				@Override
				public boolean visit(IResourceDelta delta) throws CoreException {

					for (IResourceDelta resourceDelta : delta
							.getAffectedChildren(IResourceDelta.ADDED
									| IResourceDelta.CHANGED)) {
						IResource resource = resourceDelta.getResource();
						System.err.println("checking: " + resource);
						if (resource instanceof IFile) {
							// TODO: adapt source or at least make pattern
							// configurable
							if (((IFile) resource).getName().endsWith(".mo"))
								affectedResources.add((IFile) resource);
							return false;
						}
					}
					return true;
				}
			});
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return affectedResources;
	}
}
