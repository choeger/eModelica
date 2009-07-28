/**
 * 
 */
package de.tuberlin.uebb.emodelica.launch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;

/**
 * @author choeger
 * 
 */
public class MosilabLaunchDelegate implements ILaunchConfigurationDelegate {

	public static final String SOLVER_DEFINES_PREFIX_KEY = "SOLVER_DEFINES_PREFIX";
	public static final String MAIN_FILE_TEMPLATE_KEY = "MAIN_FILE_TEMPLATE";
	public static final String OUTPUT_PATH_KEY = "C++_SOURCES_PATH";
	public static final String SOLVER_NAME_KEY = "SOLVER_NAME";
	public static final String HEADER_NAME = "eModelicaExperimentDefinitions.h";
	public static final String PROJECT_KEY = "MOSILAB_PROJECT";
	public static final String CLASS_NAME_KEY = "ROOT_CLASS_NAME";
	public static final String OBSERVABLES_KEY = "OBSERVABLES";

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
		monitor.beginTask("launching experiment", configuration.getAttributes().size() + 2);
		
		String mainFilePath = configuration.getAttribute(MAIN_FILE_TEMPLATE_KEY, "/experiments/ida_main.cpp");
		String sourcePath = configuration.getAttribute(OUTPUT_PATH_KEY, "");
		
		if (sourcePath.isEmpty()) {
			System.err.println("Could not launch. No path for C++ sources given.");
			return;
		}

		IFolder sourceFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(new Path(sourcePath));
		if (!sourceFolder.exists()) {
			System.err.println("Could not launch. Path for C++ sources given does not exist.");
			return;
		}
		
		try {
			URL url = this.getClass().getResource(mainFilePath);
			if (url == null) {
				System.err.println("Could not launch. Main file template does not exist.");
				return;
			}
				
			URI uri = FileLocator.resolve(url).toURI();
			
			
			System.err.println("using main file template: " + uri);
			File mainFile = new File(uri);
			IFile outFile = sourceFolder.getFile("main.cc");
			FileInputStream in = new FileInputStream(mainFile);
			if (outFile.exists())
				outFile.setContents(in, true, false, monitor);
			else outFile.create(in, true, monitor);
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String relevantValues = configuration.getAttribute(SOLVER_DEFINES_PREFIX_KEY, IDASolverTab.IDA_PREFIX);
		
		IFile definesFile = sourceFolder.getFile(HEADER_NAME);
		try {
			PipedInputStream in = new PipedInputStream();
			PipedOutputStream out = new PipedOutputStream(in);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
			writer.write("/*"); writer.newLine();
			writer.write(" * " + HEADER_NAME); writer.newLine();
			writer.write(" * eModelica Experiment definitions - automagically generated, do NOT edit!"); writer.newLine();
			writer.write(" */"); writer.newLine();
			writer.write("#ifndef EMODELICA_DEFINES"); writer.newLine();
			writer.write("#define EMODELICA_DEFINES"); writer.newLine();
			
			//root class
			String className = configuration.getAttribute(CLASS_NAME_KEY, "");
			writer.write("#define ROOT_OBJECT \""); writer.write(className); writer.write("\""); writer.newLine();
			
			//observables
			String observables = configuration.getAttribute(OBSERVABLES_KEY, "time ");
			writer.write("#define OBSERVABLES \""); writer.write(observables); writer.write("\""); writer.newLine();
			
			//solver special values
			for (Object key : configuration.getAttributes().keySet()) {
				if (key instanceof String) {
					if (((String)key).startsWith(relevantValues)) {
						writer.write("#define " + ((String)key).replaceFirst(relevantValues, "") + " " +
								configuration.getAttributes().get(key));
					}
				}
				monitor.worked(1);
			}
			
			writer.write("#endif  //EMODELICA_DEFINES"); writer.newLine();
			writer.close();
			out.close();
			
			if (definesFile.exists())
				definesFile.setContents(in, true, false, monitor);
			else definesFile.create(in, true, monitor);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
