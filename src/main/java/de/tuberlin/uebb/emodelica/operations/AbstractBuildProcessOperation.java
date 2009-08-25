package de.tuberlin.uebb.emodelica.operations;

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
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;

public abstract class AbstractBuildProcessOperation extends
		WorkspaceModifyOperation {

	protected IMosilabProject mosilabProject;
	protected String[] commands;
	protected Map<String,String> environment = new HashMap<String, String>();
	protected ProcessBuilder processBuilder = new ProcessBuilder();
	protected Process proc;

	public AbstractBuildProcessOperation() {
		super();
	}

	public AbstractBuildProcessOperation(ISchedulingRule rule) {
		super(rule);
	}

	@Override
	protected void execute(IProgressMonitor monitor) throws CoreException,
			InvocationTargetException, InterruptedException {
				try {
					runProcess(monitor, mosilabProject.getProject().getFolder(mosilabProject.getOutputFolder()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

	abstract protected int runProcess(IProgressMonitor monitor, IFolder sourceFolder) throws IOException;

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