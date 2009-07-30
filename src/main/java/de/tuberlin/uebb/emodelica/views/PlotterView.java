/**
 * 
 */
package de.tuberlin.uebb.emodelica.views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.tuberlin.uebb.emodelica.ui.widgets.Curve;
import de.tuberlin.uebb.emodelica.ui.widgets.SimpleGraph;
import de.tuberlin.uebb.emodelica.ui.widgets.Curve.DataPoint;

/**
 * @author choeger
 *
 */
public class PlotterView extends ViewPart {

	class Variable {
		public String name;
	}
		
	ArrayList<Variable> vars = new ArrayList<Variable>(); 
	ArrayList<DataPoint> points = new ArrayList<DataPoint>();
	
	public PlotterView() {
		
	}
	
	/**
	 * 
	 */
	public PlotterView(InputStream dataStream) {
		setDataFromInputStream(dataStream);
	}

	/**
	 * @param dataStream
	 */
	public void setDataFromInputStream(InputStream dataStream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(dataStream));
		
		try {
			String header = reader.readLine();
			if (header == null)
				return;
			
			for (String varName : header.split("\t")) {
				Variable v = new Variable();
				v.name = varName;
				vars.add(v);
			}
			String dataLine = reader.readLine();
			while (dataLine != null) {
				String data[] = dataLine.split("\t");
				double x = Double.parseDouble(data[0]);
				
				for (int i = 1; i < data.length;i++) {			
					DataPoint point = new DataPoint();
					point.index = i;
					point.xValue = x;
					point.yValue = Double.parseDouble(data[i]);
					points.add(point);
				}
				dataLine = reader.readLine();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		SimpleGraph graph = new SimpleGraph(container, SWT.NONE);
		
		FillLayout layout = new FillLayout();
		container.setLayout(layout);
		
		ArrayList<Curve> curves = new ArrayList<Curve>();
		
		for (int i = 0; i < vars.size(); i++)
			curves.add(new Curve());
		
		for (DataPoint point : points) {
			curves.get(point.index-1).getPoints().add(point);
		}
		
		graph.setCurves(curves);

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
