/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.experiments.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import de.tuberlin.uebb.emodelica.model.experiments.ICurve;
import de.tuberlin.uebb.emodelica.model.experiments.IExperiment;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;

/**
 * @author choeger
 *
 */
public class TextFileExperiment implements IExperiment {

	private List<ICurve> curves = new ArrayList<ICurve>();
	private Date date = new Date(System.currentTimeMillis());
	private String name = "experiment " + date.toString();
	private IMosilabProject project;
	private List<String> vars = new ArrayList<String>();
	private DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmssZ"); 
	
	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.experiments.IExperiment#getCurves()
	 */
	@Override
	public List<ICurve> getCurves() {
		return curves;
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.experiments.IExperiment#getDate()
	 */
	@Override
	public Date getDate() {
		return date;
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.experiments.IExperiment#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.experiments.IExperiment#getParentProject()
	 */
	@Override
	public IMosilabProject getParentProject() {
		return project;
	}
	
	/**
	 * create a TExtFileExperiment from an InputStream and sync back to disk
	 * (will reside in project/.experiments/name.date
	 * @param name The name of the experiment
	 * @param project the parent project
	 * @param data The data stream (MOSILAB format)
	 */
	public TextFileExperiment(String name, IMosilabProject project, final InputStream data) {
		super();
		this.name = name;
		this.project = project;
		
		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			@Override
			protected void execute(IProgressMonitor monitor)
					throws CoreException, InvocationTargetException,
					InterruptedException {
				IFile file = storeToDisk(data);
				if (file != null)
					setDataFromInputStream(file.getContents());
			}};
		
		try {
			op.run(null);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param name
	 * @param project
	 * @param data
	 */
	private IFile storeToDisk(InputStream data) {
		IFolder expFolder = project.getProject().getFolder(".experiments");
		try {
			
			if (!expFolder.exists())
				expFolder.create(true, true, null);
			
			IFile resourceFile = expFolder.getFile(name + "." + 
					dateFormat.format(date));
		
			if (!resourceFile.exists()) {
				System.err.println("storing " + resourceFile);
				resourceFile.create(data, true, null);
			}
			return resourceFile;
		} catch (CoreException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public TextFileExperiment(IMosilabProject project, IFile file) {
		super();
		this.name = file.getName().split("\\.")[0];
		try {
			this.date = dateFormat.parse(file.getFileExtension());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		this.project = project;
		try {
			setDataFromInputStream(file.getContents());
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param dataStream
	 */
	private void setDataFromInputStream(InputStream dataStream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(dataStream));
		
		try {
			String header = reader.readLine();
			if (header == null)
				return;
			
			for (String varName : header.split("\t")) {
				vars .add(varName);
			}
			
			curves.clear();
			String dataLine = reader.readLine();
			while (dataLine != null) {
				System.err.println("parsing: " + dataLine);
				String data[] = dataLine.split("\t");

				for (int i = 0; i < vars.size(); i++)
					curves.add(new ColoredCurve(this, vars.get(i), new 
							Color(Display.getDefault(),255,255,255)));
								
				for (int i = 0; i < data.length;i++) {			
					double x = Double.parseDouble(data[i]);
					curves.get(i).getPoints().add(new Double(x));
				}
				dataLine = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getUniqueID() {
		//filename is sufficient
		return name + "." + dateFormat.format(date);
	}

}
