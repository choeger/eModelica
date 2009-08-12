/**
 * 
 */
package de.tuberlin.uebb.emodelica.operations;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;

/**
 * @author choeger
 * This class is a WorkspaceModifyOperation that runs given commands inside the output directory of the parent project
 */
public class BuildProcessOperation extends WorkspaceModifyOperation {

	private IMosilabProject mosilabProject;
	private String[] commands;
	private Map<String,String> environment = new HashMap<String, String>();
	private ProcessBuilder processBuilder = new ProcessBuilder();
	private Process proc;
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.WorkspaceModifyOperation#execute(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void execute(IProgressMonitor monitor) throws CoreException,
			InvocationTargetException, InterruptedException {
		try {
			runProcess(monitor, mosilabProject.getProject().getFolder(mosilabProject.getOutputFolder()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int runProcess(IProgressMonitor monitor, IFolder sourceFolder) throws IOException {
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

	public InputStream getInputStream() {
		if (proc != null)
			return proc.getInputStream();
		return null;
	}
	
	public InputStream getErrorStream() {
		if (proc != null)
			return proc.getErrorStream();
		return null;
	}

	/**
	 * @return the mosilabProject
	 */
	public IMosilabProject getMosilabProject() {
		return mosilabProject;
	}

	/**
	 * @param mosilabProject the mosilabProject to set
	 */
	public void setMosilabProject(IMosilabProject mosilabProject) {
		this.mosilabProject = mosilabProject;
	}

	/**
	 * @return the commands
	 */
	public String[] getCommands() {
		return commands;
	}

	/**
	 * @param commands the commands to set
	 */
	public void setCommands(String[] commands) {
		this.commands = commands;
	}

	/**
	 * @return the environment
	 */
	public Map<String, String> getEnvironment() {
		return environment;
	}

	/**
	 * @param environment the environment to set
	 */
	public void setEnvironment(Map<String, String> environment) {
		this.environment = environment;
	}

	/**
	 * @return the processBuilder
	 */
	public ProcessBuilder getProcessBuilder() {
		return processBuilder;
	}

	/**
	 * @param processBuilder the processBuilder to set
	 */
	public void setProcessBuilder(ProcessBuilder processBuilder) {
		this.processBuilder = processBuilder;
	}

	/**
	 * @return the proc
	 */
	public Process getProc() {
		return proc;
	}

	/**
	 * @param proc the proc to set
	 */
	public void setProc(Process proc) {
		this.proc = proc;
	}

	/**
	 * @param resource
	 * @return
	 */
	public String getLocation(IResource resource) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(
				resource.getFullPath()).getLocation().toOSString();
	}

	public int exitValue() {
		if (proc != null)
			return proc.exitValue();
		return -1;
	}
	
}
