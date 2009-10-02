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
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

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
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.experiments.IExperiment;
import de.tuberlin.uebb.emodelica.model.experiments.impl.ExperimentContainer;
import de.tuberlin.uebb.emodelica.model.experiments.impl.TextFileExperiment;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.operations.BuildProcessOperation;

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
	private BuildProcessOperation buildOp;
	private IProject project;
	private IMosilabProject mosilabProject;
	private IFolder sourceFolder;

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
	public void launch(final ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("launching experiment", configuration.getAttributes()
				.size() + 2);

		initLaunch(configuration);

		WorkspaceModifyOperation writeStuffOp = new WorkspaceModifyOperation() {

			@Override
			protected void execute(IProgressMonitor monitor)
					throws CoreException, InvocationTargetException,
					InterruptedException {
				writeMainFile(configuration, monitor, sourceFolder);

				createDefinitionHeader(configuration, monitor, sourceFolder);
			}
		};

		try {
			writeStuffOp.run(monitor);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return;
		} catch (InterruptedException e) {
			// canceled
			return;
		}

		runMakefile(configuration, monitor, sourceFolder);

		runExperiment(configuration, monitor, sourceFolder);
	}

	/**
	 * @param configuration
	 * @return
	 * @throws CoreException
	 */
	private void initLaunch(final ILaunchConfiguration configuration)
			throws CoreException {
		String sourcePath = configuration.getAttribute(OUTPUT_PATH_KEY, "");

		if (sourcePath.isEmpty()) {
			System.err
					.println("Could not launch. No path for C++ sources given.");
			return;
		}

		sourceFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(
				new Path(sourcePath));
		if (!sourceFolder.exists()) {
			System.err
					.println("Could not launch. Path for C++ sources given does not exist.");
			return;
		}

		try {
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(
					configuration.getAttribute(PROJECT_KEY, ""));
		} catch (CoreException e1) {
			e1.printStackTrace();
			return;
		}

		if (!project.exists())
			return;

		mosilabProject = EModelicaPlugin.getDefault().getProjectManager()
				.getMosilabProject(project);

		if (mosilabProject.getMOSILABEnvironment() == null)
			return;
		
		buildOp = new BuildProcessOperation(mosilabProject);
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
			StringBuffer buffer = new StringBuffer();
			String newLine = System.getProperty("line.separator");
			
			buffer.append("/*");
			buffer.append(newLine);
			buffer.append(" * " + HEADER_NAME);
			buffer.append(newLine);
			buffer.append(" * eModelica Experiment definitions - automagically generated, do NOT edit!");
			buffer.append(newLine);
			buffer.append(" */");
			buffer.append(newLine);
			buffer.append("#ifndef EMODELICA_DEFINES");
			buffer.append(newLine);
			buffer.append("#define EMODELICA_DEFINES");
			buffer.append(newLine);

			// root class
			String className = configuration.getAttribute(CLASS_NAME_KEY, "");
			buffer.append("#define ROOT_OBJECT \"");
			buffer.append(className);
			buffer.append("\"");
			buffer.append(newLine);

			// observables
			String observables = configuration.getAttribute(OBSERVABLES_KEY,
					"time ");
			buffer.append("#define OBSERVABLES \"");
			buffer.append(observables);
			buffer.append("\"");
			buffer.append(newLine);

			// solver special values
			for (Object key : configuration.getAttributes().keySet()) {
				if (key instanceof String) {
					if (((String) key).startsWith(relevantValues)) {
						buffer.append("#define "
								+ ((String) key).replaceFirst(relevantValues,
										"") + " "
								+ configuration.getAttributes().get(key));
						buffer.append(newLine);
					}
				}
				monitor.worked(1);
			}

			buffer.append("#endif  //EMODELICA_DEFINES");
			buffer.append(newLine);

			PipedOutputStream out = new PipedOutputStream();
			PipedInputStream in = new PipedInputStream(out, buffer.length());
			
			out.write(buffer.toString().getBytes());
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
				
				buildOp.setCommands(new String[] {getLocation(bin)});
				
				try {
					buildOp.run(monitor);
				} catch (InterruptedException e) {
					//canceled
					return -1;
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return -1;
				}

				BufferedReader reader = new BufferedReader(new InputStreamReader(buildOp.getErrorStream()));
				String s = null;
				while((s = reader.readLine()) != null)
					System.err.println(s);
				
				out.write("\n experiment returned with: " + buildOp.exitValue() + "\n");

				if (buildOp.exitValue() == 0) {
					final InputStream stream = buildOp.getInputStream();
					Display.getDefault().asyncExec(
							new Runnable() {

								@Override
								public void run() {
										System.err.println("creating Experiment in text file.");
										if (mosilabProject.getExperimentContainer() == null) {
											IFolder expFolder = mosilabProject.getProject().getFolder(".experiments");
											if (!expFolder.exists())
												try {
													expFolder.create(true, true, null);
												} catch (CoreException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
													return;
												}
											
											ExperimentContainer container = new ExperimentContainer(
													new ArrayList<IExperiment>(),mosilabProject,expFolder);
											mosilabProject.setExperimentContainer(container);
										}
										// create new Experiment, storage will be done in constructors
										new TextFileExperiment("experiment",mosilabProject.getExperimentContainer(),stream);
									}
								}
					);
				}
				return buildOp.exitValue();
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
				buildOp.setCommands(new String[] {"make",
						"-f", makefile.getAbsolutePath(), "-C",
						getLocation(sourceFolder), MAIN_TARGET, "P=.",
						"TARGET=main", "TARGET_LIB=" + project.getName()});

				buildOp.getEnvironment().put("MOSILAB_ROOT",
						mosilabProject.getMOSILABEnvironment().getLocation());

				try {
					buildOp.run(monitor);
				} catch (InterruptedException e) {
					//canceled
					return -1;
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return -1;
				}

				int r = -1;

				while ((r = buildOp.getInputStream().read()) > -1)
					out.write(r);

				System.err.println("make returned with: " + buildOp.exitValue());
				out.write("\n make returned with: " + buildOp.exitValue() + "\n");

				return buildOp.exitValue();
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
