/**
 * 
 */
package de.tuberlin.uebb.emodelica.builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import de.tuberlin.uebb.emodelica.model.project.IModelicaPackage;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;
import de.tuberlin.uebb.emodelica.model.project.impl.ProjectManager;
import de.tuberlin.uebb.emodelica.operations.AsyncBuildProcessOperation;
import de.tuberlin.uebb.emodelica.operations.SelectCPPFilesOperation;

/**
 * @author choeger
 * 
 */
public class MosilabBuilder extends IncrementalProjectBuilder {

	private final class ColorSetterRunnable implements Runnable {
		private final MessageConsoleStream out;
		private final Color color;
		public boolean done = false;
		
		private ColorSetterRunnable(MessageConsoleStream out, Color color) {
			this.out = out;
			this.color = color;
		}

		@Override
		public void run() {
			out.setColor(color);
			synchronized (this) {
				done = true;
				this.notifyAll();
			}
		}
	}

	public static final String BUILDER_ID = "de.tuberlin.uebb.emodelica.mosilacBuilder";
	private AsyncBuildProcessOperation buildOp;
	private IProgressMonitor monitor;
	private IMosilabProject mosilabProject;

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
		mosilabProject = (IMosilabProject)getProject().getAdapter(IModelicaResource.class);
		if (mosilabProject == null)
			return null;

		System.err.println("MOSILAB builder starting " + kind);

		if ((kind == AUTO_BUILD) || (kind == INCREMENTAL_BUILD)) {
			/* incremental build */
			List<IFile> affectedResources = collectIncrementalResources();
			buildSourceFiles(monitor, affectedResources);
		} else if (kind == FULL_BUILD) {
			/* full build */
			List<IFile> affectedResources = collectAllResources();
			buildSourceFiles(monitor, affectedResources);
		}

		monitor.done();

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IncrementalProjectBuilder#clean(org.eclipse
	 * .core.runtime.IProgressMonitor)
	 */
	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		super.clean(monitor);
		mosilabProject = (IMosilabProject) getProject().getAdapter(
				IModelicaResource.class);

		/* clean */
		IFolder outFolder = getProject().getFolder(
				mosilabProject.getOutputFolder());
		if (outFolder.exists()) {
			monitor.beginTask("Cleaning", outFolder.members().length);
			for (IResource resource : outFolder.members()) {
				monitor.subTask("deleting: " + resource);
				resource.delete(true, monitor);
				monitor.worked(1);
			}
		}

	}

	private List<IFile> collectAllResources() {
		List<IFile> sourceFiles = new ArrayList<IFile>();
		try {
			for (IMosilabSource src : mosilabProject.getSrcFolders()) {

				for (IModelicaPackage pkg : src.getPackages()) {

					for (IResource resource : pkg.getContainer().members())
						if (resource instanceof IFile)
							sourceFiles.add((IFile) resource);

				}

				for (IResource resource : src.getBasePath().members())
					if (resource instanceof IFile)
						sourceFiles.add((IFile) resource);
			}

		} catch (CoreException e) {

			e.printStackTrace();
		}
		return sourceFiles;
	}

	/**
	 * @param monitor
	 * @param sourceFiles
	 */
	private void buildSourceFiles(IProgressMonitor monitor,
			List<IFile> sourceFiles) {
		if (sourceFiles.isEmpty())
			return;

		System.err.println("got " + sourceFiles.size() + " files to compile.");
		monitor.beginTask("Running mosilac", sourceFiles.size() + 2);
		monitor.subTask("compiling");

		MessageConsole console = findConsole("mosilac console");
		MessageConsoleStream out = console.newMessageStream();
		MessageConsoleStream err = console.newMessageStream();
		
		buildOp = new AsyncBuildProcessOperation(mosilabProject);
		this.monitor = monitor;

		monitor.subTask("creating selector");
		runSelector(monitor);
		monitor.worked(1);

		for (IResource resource : sourceFiles) {
			String sourceFile = buildOp.getLocation(resource);
			try {
				if (runMosilac(sourceFile, console, out, err) == 0) {
					runMakefile(resource, console, out, err);
					monitor.worked(1);
				}
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				return;
			} catch (InterruptedException e) {
				// canceled by user
				System.err.println("canceled!");
				return;
			}
		}
		
		monitor.subTask("running make");
		runMakeLibFake(monitor);
		monitor.worked(1);
		
	}

	/**
	 * @param monitor
	 */
	private void runMakeLibFake(IProgressMonitor monitor) {
		IFolder outFolder = mosilabProject.getProject().getFolder(
				mosilabProject.getOutputFolder());
		buildOp
				.setCommands(new String[] { "make", "--file", "-", "-C",
						buildOp.getLocation(outFolder), "lib_from_java", "-I",
						mosilabProject.getMOSILABEnvironment().mosilabRoot(),
						"TARGET_LIB=" + mosilabProject.getProject().getName(),
						"P=.", });

		StringBuffer buffer = new StringBuffer();
		String newLine = System.getProperty("line.separator");
		buffer.append("include ostype.mak");
		buffer.append(newLine);
		buffer.append("include lib/makefile");
		buffer.append(newLine);
		buffer.append("lib_from_java:");
		buffer.append(newLine);
		buffer.append("	echo \"linking model library\"");
		buffer.append(newLine);
		buffer.append("ifeq ($(OSTYPE),msys)");
		buffer.append(newLine);
		buffer.append("	ar cru $(MODEL) $(DEST)");
		buffer.append(newLine);
		buffer.append("	ranlib $(MODEL)");
		buffer.append(newLine);
		buffer.append("else");
		buffer.append(newLine);
		buffer.append("	echo $(LINKCMD) -o $(MODEL) $(DEST) $(LINKLIB) >&2");
		buffer.append(newLine);
		buffer.append("	$(LINKCMD) -o $(MODEL) $(DEST) $(LINKLIB)");
		buffer.append(newLine);
		buffer.append("endif");
		buffer.append(newLine);

		try {
			buildOp.run(monitor);
			buildOp.getProc().getOutputStream().write(
					buffer.toString().getBytes());
			buildOp.getProc().getOutputStream().close();
			buildOp.waitForProcess();
			int i = 0;
			while ((i = buildOp.getErrorStream().read()) > 0)
				System.err.write(new byte[] { (byte) i });
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param monitor
	 */
	private void runSelector(IProgressMonitor monitor) {
		SelectCPPFilesOperation selector = new SelectCPPFilesOperation(
				this.mosilabProject);
		try {
			selector.run(monitor);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.err.println("selector done.");
	}

	private int runMakefile(IResource modelicaSourceFile,
			MessageConsole console, MessageConsoleStream out, MessageConsoleStream err)
			throws InvocationTargetException, InterruptedException {
		IMosilabProject mosilabProject = ProjectManager.getDefault()
				.getMosilabProject(getProject());

		String srcPath = buildOp.getLocation(getProject().getFolder(
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

				// buildOp.setCommands(new String[] {
				// mosilabProject.getMOSILABEnvironment().getLocation() +
				// File.separator + "bin" + File.separator + "mkSelector.sh"});
				//				
				// buildOp.getEnvironment().put("MOSILAB_ROOT",
				// mosilabProject.getMOSILABEnvironment().getLocation());
				//
				// buildOp.run(monitor);
				//				
				// followOutput(out, buildOp.getProcessBuilder(),
				// buildOp.getProc());
				//
				// out.write("\n mkSelector.sh returned with: " +
				// buildOp.getProc().exitValue() + "\n");

				// TODO: fix source file to c++ file mapping
				buildOp.setCommands(new String[] {
						"make",
						"-f",
						makefile.getAbsolutePath(),
						ccSourceFile.getAbsolutePath().replaceAll("\\.cc",
								"\\.o"),
						srcPath + File.separator + "_selector.o" });

				buildOp.run(monitor);
				buildOp.waitForProcess();

				followOutput(out, buildOp.getProcessBuilder(), buildOp.getProc());

				followError(err, buildOp.getProcessBuilder(),buildOp.getProc());
				
				out.write("\n make returned with: "
						+ buildOp.getProc().exitValue() + "\n");

				return buildOp.getProc().exitValue();
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
	private void followOutput(final MessageConsoleStream out,
			ProcessBuilder processBuilder, Process proc) throws IOException {
		final String newLine = System.getProperty("line.separator");

		out.setEncoding(System.getProperty("file.encoding"));

		for (String cmd : processBuilder.command()) {
			out.write(cmd);
			out.write(" ");
		}
		out.write(newLine.getBytes());

		try {
			proc.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}

		InputStreamReader reader = new InputStreamReader(proc.getInputStream());
		BufferedReader buffReader = new BufferedReader(reader);
		String line = buffReader.readLine();

		while (line != null) {
			out.write(line.getBytes());
			out.write(newLine.getBytes());
			line = buffReader.readLine();
		}
		
		out.flush();
	}

	private void followError(final MessageConsoleStream err,
			ProcessBuilder processBuilder, Process proc) throws IOException {
		final String newLine = System.getProperty("line.separator");

		err.setEncoding(System.getProperty("file.encoding"));

		if (proc.exitValue() != 0) {
			setConsoleColor(err, new Color(Display.getDefault(), 255, 0, 0));
			
			try {
				proc.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}

			InputStreamReader reader = new InputStreamReader(proc.getErrorStream());

			BufferedReader buffReader = new BufferedReader(reader);
			String line = buffReader.readLine();

			while (line != null) {
				err.write(line.getBytes());
				err.write(newLine.getBytes());
				line = buffReader.readLine();
			}
			
			err.flush();
		}
	}

	/**
	 * @param out
	 * @param color
	 *            TODO
	 */
	private void setConsoleColor(final MessageConsoleStream out, Color color) {
		ColorSetterRunnable colorSetter = new ColorSetterRunnable(out, color);

		Display.getDefault().asyncExec(colorSetter);
		try {
			synchronized (colorSetter) {
				if (!colorSetter.done)
					colorSetter.wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param sourceFile
	 * @param console
	 * @param out
	 * @param err 
	 * @throws InterruptedException
	 * @throws InvocationTargetException
	 */
	private int runMosilac(String sourceFile, MessageConsole console,
			MessageConsoleStream out, MessageConsoleStream err) throws InvocationTargetException,
			InterruptedException {
		// TODO: move to adapter -> IMosilabProject
		IMosilabProject mosilabProject = ProjectManager.getDefault()
				.getMosilabProject(getProject());
		String mosilac = mosilabProject.getMOSILABEnvironment()
				.compilerCommand();
		String outPath = buildOp.getLocation(getProject().getFolder(
				mosilabProject.getOutputFolder()));
		File binary = new File(mosilac);

		if (binary.exists()) {
			try {
				console.activate();

				buildOp.setCommands(new String[] { mosilac, "--prefix",
						outPath, sourceFile });

				StringBuilder pathBuilder = new StringBuilder();
				// TODO make MOSILAB_LOADPATH configurable in MOSILAB
				// preferences
				pathBuilder.append(mosilabProject.getMOSILABEnvironment()
						.getLocation());
				pathBuilder.append(IPath.SEPARATOR);
				pathBuilder.append("base-lib");
				for (IMosilabSource src : mosilabProject.getSrcFolders()) {
					pathBuilder.append(File.pathSeparator);
					pathBuilder.append(buildOp.getLocation(src.getBasePath()));
				}

				buildOp.getEnvironment().put("MOSILAB_LOADPATH",
						pathBuilder.toString());
				buildOp.getEnvironment().put("MOSILAB_ROOT",
						mosilabProject.getMOSILABEnvironment().getLocation());

				buildOp.run(monitor);
				buildOp.waitForProcess();

				followOutput(out, buildOp.getProcessBuilder(), buildOp
						.getProc());

				followError(err, buildOp.getProcessBuilder(),buildOp.getProc());
				
				System.err.println("mosilac returned with: "
						+ buildOp.getProc().exitValue());
				out.write("\n mosilac returned with: "
						+ buildOp.getProc().exitValue() + "\n");

				return buildOp.getProc().exitValue();
			} catch (IOException e) {
				// TODO throw CoreException
				e.printStackTrace();
			}
		} else
			System.err.println("binary " + binary.toString() + " not found!");
		return -1;
	}

	private List<IFile> collectIncrementalResources() {
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
							final IFile file = (IFile) resource;

							final IModelicaResource adapter = (IModelicaResource) file
									.getParent().getAdapter(
											IModelicaResource.class);
							if (adapter != null
									&& (adapter instanceof IMosilabSource || adapter instanceof IModelicaPackage))
								affectedResources.add(file);

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
