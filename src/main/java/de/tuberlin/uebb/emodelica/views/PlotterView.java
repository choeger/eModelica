/**
 * 
 */
package de.tuberlin.uebb.emodelica.views;

import java.io.InputStream;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import sun.awt.geom.Curve;
import de.tuberlin.uebb.emodelica.model.experiments.IExperiment;
import de.tuberlin.uebb.emodelica.model.experiments.impl.TextFileExperiment;
import de.tuberlin.uebb.emodelica.ui.widgets.SimpleGraph;

/**
 * @author choeger
 *
 */
public class PlotterView extends ViewPart {
		
	ArrayList<String> vars = new ArrayList<String>();
	private IExperiment experiment;
	private SimpleGraph graph; 
	
	public PlotterView(IExperiment experiment) {
		this.experiment = experiment;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED);
		graph = new SimpleGraph(container, SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED);
		
		FillLayout layout = new FillLayout();
		container.setLayout(layout);
		
		//TODO: x-axis selection
		graph.setCurves(experiment.getCurves());

		container.pack();
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}

}
