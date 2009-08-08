/**
 * 
 */
package de.tuberlin.uebb.emodelica.launch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.experiments.impl.TextFileExperiment;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;

/**
 * @author choeger
 * 
 */
public class MosilabLaunchDelegate implements ILaunchConfigurationDelegate {

	private static final String MAIN_TARGET = "main";
	public static final String SOLVER_DEFINES_PREFIX_KEY = "SOLVER_DEFINES_PREFIX";
	public static final String MAIN_FILE_TEMPLATE_KEY = "MAIN_FILE_TEMPLATE";
	public static final String OUTPUT_PATH_KEY = "C++_SOURCES_PATH";
	public static final String SOLVER_NAME_KEY = "SOLVER_NAME";
	public static final String HEADER_NAME = "eModelicaExperimentDefinitions.h";
	public static final String PROJECT_KEY = "MOSILAB_PROJECT";
	public static final String CLASS_NAME_KEY = "ROOT_CLASS_NAME";
	public static final String OBSERVABLES_KEY = "OBSERVABLES";
	private IProject project;
	private IMosilabProject mosilabProject;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.
	 * eclipse.debug.core.ILaunchConfiguration, java.lang.String,
	 * org.eclipse.debug.core.ILaunch,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("launching experiment", configuration.getAttributes()
				.size() + 2);

		String sourcePath = configuration.getAttribute(OUTPUT_PATH_KEY, "");

		if (sourcePath.isEmpty()) {
			System.err
					.println("Could not launch. No path for C++ sources given.");
			return;
		}

		IFolder sourceFolder = ResourcesPlugin.getWorkspace().getRoot()
				.getFolder(new Path(sourcePath));
		if (!sourceFolder.exists()) {
			System.err
					.println("Could not launch. Path for C++ sources given does not exist.");
			return;
		}
		
		writeMainFile(configuration, monitor, sourceFolder);

		createDefinitionHeader(configuration, monitor, sourceFolder);

		runMakefile(configuration, monitor, sourceFolder);

		runExperiment(configuration, monitor, sourceFolder);
	}

	/**
	 * @param configuration
	 * @param monitor
	 * @return
	 * @throws CoreException
	 */
	private void writeMainFile(ILaunchConfiguration configuration,
			IProgressMonitor monitor, IFolder sourceFolder)
			throws CoreException {
		monitor.subTask("writing main.cpp");
		String mainFilePath = configuration.getAttribute(
				MAIN_FILE_TEMPLATE_KEY, "/experiments/ida_main.cpp");

		try {
			URL url = this.getClass().getResource(mainFilePath);
			if (url == null) {
				System.err
						.println("Could not launch. Main file template does not exist.");
				return;
			}

			URI uri = FileLocator.resolve(url).toURI();

			System.err.println("using main file template: " + uri);
			File mainFile = new File(uri);
			IFile outFile = sourceFolder.getFile("main.cpp");
			FileInputStream in = new FileInputStream(mainFile);
			if (outFile.exists())
				outFile.setContents(in, true, false, monitor);
			else
				outFile.create(in, true, monitor);

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param configuration
	 * @param monitor
	 * @param sourceFolder
	 * @throws CoreException
	 */
	private void createDefinitionHeader(ILaunchConfiguration configuration,
			IProgressMonitor monitor, IFolder sourceFolder)
			throws CoreException {
		monitor.subTask("writing header");
		String relevantValues = configuration.getAttribute(
				SOLVER_DEFINES_PREFIX_KEY, IDASolverTab.IDA_PREFIX);
		IFile definesFile = sourceFolder.getFile(HEADER_NAME);
		try {
			PipedInputStream in = new PipedInputStream();
			PipedOutputStream out = new PipedOutputStream(in);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					out));
			writer.write("/*");
			writer.newLine();
			writer.write(" * " + HEADER_NAME);
			writer.newLine();
			writer
					.write(" * eModelica Experiment definitions - automagically generated, do NOT edit!");
			writer.newLine();
			writer.write(" */");
			writer.newLine();
			writer.write("#ifndef EMODELICA_DEFINES");
			writer.newLine();
			writer.write("#define EMODELICA_DEFINES");
			writer.newLine();

			// root class
			String className = configuration.getAttribute(CLASS_NAME_KEY, "");
			writer.write("#define ROOT_OBJECT \"");
			writer.write(className);
			writer.write("\"");
			writer.newLine();

			// observables
			String observables = configuration.getAttribute(OBSERVABLES_KEY,
					"time ");
			writer.write("#define OBSERVABLES \"");
			writer.write(observables);
			writer.write("\"");
			writer.newLine();

			// solver special values
			for (Object key : configuration.getAttributes().keySet()) {
				if (key instanceof String) {
					if (((String) key).startsWith(relevantValues)) {
						writer.write("#define "
								+ ((String) key).replaceFirst(relevantValues,
										"") + " "
								+ configuration.getAttributes().get(key));
						writer.newLine();
					}
				}
				monitor.worked(1);
			}

			writer.write("#endif  //EMODELICA_DEFINES");
			writer.newLine();
			writer.close();
			out.close();

			if (definesFile.exists())
				definesFile.setContents(in, true, false, monitor);
			else
				definesFile.create(in, true, monitor);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	private int runExperiment(ILaunchConfiguration configuration,
			IProgressMonitor monitor, IFolder sourceFolder) {
		IFile bin = sourceFolder.getFile(MAIN_TARGET);

		if (bin.exists()) {
			monitor.subTask("running the experiment");
			try {
				MessageConsole console = findConsole("running experiment");
				MessageConsoleStream out = console.newMessageStream();
				console.activate();

				// TODO: fix source file to c++ file mapping
				ProcessBuilder processBuilder = new ProcessBuilder(
						getLocation(bin));

				for (String cmd : processBuilder.command()) {
					out.write(cmd);
					out.write(" ");
				}
				out.write("\n");

				Process proc = processBuilder.start();

				try {
					proc.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
					return -1;
				}

				BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
				String s = null;
				while((s = reader.readLine()) != null)
					System.err.println(s);
				
				out.write("\n experiment returned with: " + proc.exitValue()
						+ "\n");

				if (proc.exitValue() == 0) {
					final InputStream stream = proc.getInputStream();
					Display.getDefault().asyncExec(
							new Runnable() {

								@Override
								public void run() {
									TextFileExperiment exp = new TextFileExperiment("experiment",mosilabProject,
											stream);
									mosilabProject.getExperiments().add(exp);
									}
								}
					);
				}
				return proc.exitValue();
			} catch (IOException e) {
				// TODO throw CoreException
				e.printStackTrace();
			}
		}
		System.err.println("Experiment binary: " + bin.toString()
				+ " not found!");
		return -1;
	}

	private int runMakefile(ILaunchConfiguration configuration,
			IProgressMonitor monitor, IFolder sourceFolder) {
		try {
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(
					configuration.getAttribute(PROJECT_KEY, ""));
		} catch (CoreException e1) {
			e1.printStackTrace();
			return -1;
		}

		if (!project.exists())
			return -1;

		mosilabProject = EModelicaPlugin.getDefault().getProjectManager()
				.getMosilabProject(project);

		if (mosilabProject.getMOSILABEnvironment() == null)
			return -2;

		// TODO check for special environment setting
		File makefile = new File(mosilabProject.getMOSILABEnvironment()
				.getLocation()
				+ File.separator + "lib" + File.separator + "makefile");

		monitor.subTask("running make");

		if (makefile.exists()) {
			try {
				MessageConsole console = findConsole("running experiment");
				MessageConsoleStream out = console.newMessageStream();
				console.activate();

				// TODO: fix source file to c++ file mapping
				ProcessBuilder processBuilder = new ProcessBuilder("make",
						"-f", makefile.getAbsolutePath(), "-C",
						getLocation(sourceFolder), "lib", MAIN_TARGET, "P=.",
						"TARGET=main", "TARGET_LIB=" + project.getName());

				processBuilder.environment().put("MOSILAB_ROOT",
						mosilabProject.getMOSILABEnvironment().getLocation());

				for (String cmd : processBuilder.command()) {
					out.write(cmd);
					out.write(" ");
				}
				out.write("\n");

				Process proc = processBuilder.start();

				int r = -1;

				while ((r = proc.getInputStream().read()) > -1)
					out.write(r);

				try {
					proc.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
					return -1;
				}

				while ((r = proc.getInputStream().read()) > -1)
					out.write(r);

				System.err.println("make returned with: " + proc.exitValue());
				out.write("\n make returned with: " + proc.exitValue() + "\n");

				return proc.exitValue();
			} catch (IOException e) {
				// TODO throw CoreException
				e.printStackTrace();
			}
		} else {
			System.err.println("Makefile: " + makefile.toString()
					+ " not found!");
		}
		return -1;
	}

	/**
	 * @param resource
	 * @return
	 */
	private String getLocation(IResource resource) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(
				resource.getFullPath()).getLocation().toOSString();
	}
}
