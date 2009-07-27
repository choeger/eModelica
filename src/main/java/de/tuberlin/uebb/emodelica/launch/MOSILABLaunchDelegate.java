/**
 * 
 */
package de.tuberlin.uebb.emodelica.launch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;

/**
 * @author choeger
 * 
 */
public class MOSILABLaunchDelegate implements ILaunchConfigurationDelegate {

	public static final String SOLVER_DEFINES_PREFIX_KEY = "SOLVER_DEFINES_PREFIX";
	public static final String MAIN_FILE_TEMPLATE_KEY = "MAIN_FILE_TEMPLATE";
	public static final String SOLVER_NAME_KEY = "SOLVER_NAME";
	public static final String HEADER_NAME = "eModelicaExperimentDefinitions.h";

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
		String sourcePath = configuration.getAttribute("C++_PATH", "c++");

		try {
			URI uri = FileLocator.resolve(this.getClass().getResource(mainFilePath)).toURI();
			
			System.err.println("using main file template: " + uri);
			File mainFile = new File(uri);
			File outFile = new File(sourcePath + File.separator + "main.cc");
			if (outFile.delete())
				outFile.createNewFile();
			copyFile(mainFile, outFile);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String relevantValues = configuration.getAttribute(SOLVER_DEFINES_PREFIX_KEY, "DEFINES_IDA_");
		
		String definesName = sourcePath + File.separator + HEADER_NAME;
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(definesName, false));
			writer.write("/*"); writer.newLine();
			writer.write(" * " + HEADER_NAME); writer.newLine();
			writer.write(" * eModelica Experiment definitions - automagically generated, do NOT edit!"); writer.newLine();
			writer.write(" */"); writer.newLine();
			writer.write("#ifndef EMODELICA_DEFINES"); writer.newLine();
			writer.write("#define EMODELICA_DEFINES"); writer.newLine();

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

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	/**
	 * @param inFile
	 * @param outFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void copyFile(File inFile, File outFile)
			throws FileNotFoundException, IOException {
		FileChannel inChannel = new FileInputStream(inFile).getChannel();
		FileChannel outChannel = new FileOutputStream(outFile).getChannel();
		inChannel.transferTo(0, inChannel.size(),
		        outChannel);
		if (inChannel != null) inChannel.close();
		if (outChannel != null) outChannel.close();
	}
}
