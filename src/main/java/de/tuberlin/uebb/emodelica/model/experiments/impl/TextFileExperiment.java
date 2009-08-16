/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.experiments.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ide.undo.CreateFileOperation;

import de.tuberlin.uebb.emodelica.model.experiments.ICurve;
import de.tuberlin.uebb.emodelica.model.experiments.IExperiment;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.impl.ModelicaResource;

/**
 * @author choeger
 * 
 */
public class TextFileExperiment extends ModelicaResource implements IExperiment {

	private List<ICurve> curves = new ArrayList<ICurve>();
	private Date date = new Date(System.currentTimeMillis());
	private String name = "experiment " + date.toString();
	private IMosilabProject project;
	private DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmssZ");
	private IFile file = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tuberlin.uebb.emodelica.model.experiments.IExperiment#getCurves()
	 */
	@Override
	public List<ICurve> getCurves() {
		return curves;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tuberlin.uebb.emodelica.model.experiments.IExperiment#getDate()
	 */
	@Override
	public Date getDate() {
		return date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tuberlin.uebb.emodelica.model.experiments.IExperiment#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tuberlin.uebb.emodelica.model.experiments.IExperiment#getParentProject
	 * ()
	 */
	@Override
	public IMosilabProject getParentProject() {
		return project;
	}

	/**
	 * create a TExtFileExperiment from an InputStream and sync back to disk
	 * (will reside in project/.experiments/name.date
	 * 
	 * @param name
	 *            The name of the experiment
	 * @param project
	 *            the parent project
	 * @param data
	 *            The data stream (MOSILAB format)
	 */
	public TextFileExperiment(String name, IMosilabProject project,
			final InputStream data) {
		super();
		try {
			this.name = name;
			this.project = project;
			IFolder expFolder = project.getProject().getFolder(".experiments");
			if (!expFolder.exists())
				expFolder.create(true, true, null);

			setResource(expFolder.getFile(name + "." + dateFormat.format(date)));
			CreateFileOperation create = new CreateFileOperation(file, null,
					data, "save experiment");

			create.execute(null, null);

			setDataFromInputStream(file.getContents());
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		setResource(file);
		
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
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				dataStream));

		try {
			String header = reader.readLine();
			if (header == null)
				return;

			curves.clear();
			for (String varName : header.split("\\s+")) {
				curves.add(new ColoredCurve(this, varName, new Color(Display
						.getDefault(), 255, 255, 255)));
			}

			String dataLine = reader.readLine();
			while (dataLine != null) {
				String data[] = dataLine.split("\\s+");

				for (int i = 0; i < data.length; i++) {
					double x = Double.parseDouble(data[i]);
					curves.get(i).getPoints().add(new Double(x));
				}
				dataLine = reader.readLine();
			}

			System.err.println("parsed: " + curves.size() + " curves");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getUniqueID() {
		// filename is sufficient
		return name + "." + dateFormat.format(date);
	}

	/**
	 * @return the file
	 */
	public IFile getFile() {
		return file;
	}

	@Override
	protected void doRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<? extends Object> getChildren() {
		return null;
	}

	@Override
	public IModelicaResource getParent() {
		return 
	}

	@Override
	public IResource getResource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void syncChildren() {
		// TODO Auto-generated method stub
		
	}

}
