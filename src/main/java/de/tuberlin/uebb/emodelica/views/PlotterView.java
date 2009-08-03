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

/**
 * @author choeger
 *
 */
public class PlotterView extends ViewPart {
		
	ArrayList<String> vars = new ArrayList<String>();
	private ArrayList<Curve> curves;
	private SimpleGraph graph; 
	
	public PlotterView() {
		curves = new ArrayList<Curve>();
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
				vars.add(varName);
			}
			curves.clear();
			String dataLine = reader.readLine();
			while (dataLine != null) {
				System.err.println("parsing: " + dataLine);
				String data[] = dataLine.split("\t");

				for (int i = 0; i < vars.size(); i++)
					curves.add(new Curve(vars.get(i)));
								
				for (int i = 0; i < data.length;i++) {			
					double x = Double.parseDouble(data[i]);
					curves.get(i).getPoints().add(new Double(x));
					System.err.println("curve has " + curves.get(i).getPoints().size());
				}
				dataLine = reader.readLine();
			}
			graph.setCurves(curves);
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
		Composite container = new Composite(parent, SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED);
		graph = new SimpleGraph(container, SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED);
		
		FillLayout layout = new FillLayout();
		container.setLayout(layout);
		
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
