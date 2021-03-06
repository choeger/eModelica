/**
 * 
 */
package de.tuberlin.uebb.emodelica.operations;



import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;

import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;

/**
 * @author choeger
 * This class is a WorkspaceModifyOperation that runs given commands inside the output directory of the parent project
 */
public class BuildProcessOperation extends AbstractBuildProcessOperation {

	protected int runProcess(IProgressMonitor monitor, IFolder sourceFolder) throws IOException {
		System.err.println("running: " + commands[0]);
		if (!mosilabProject.getProject().exists())
			return -1;
	
		if (mosilabProject.getMOSILABEnvironment() == null)
			return -2;
	
		processBuilder.directory(new File(getLocation(sourceFolder)));
		processBuilder = new ProcessBuilder(commands);
		processBuilder.environment().putAll(environment);
	
		proc = processBuilder.start();
		try {
			proc.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return proc.exitValue();
	}
	
	public BuildProcessOperation(IMosilabProject mosilabProject) {
		super();
		this.mosilabProject = mosilabProject;
	}
	
}
