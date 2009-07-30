package de.tuberlin.uebb.emodelica.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class Curve {
	
	public static class DataPoint {
		public int index;
		public double xValue;
		public double yValue;
	}
	
	private List<DataPoint> points = new ArrayList<DataPoint>();
	private Color color = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	public Curve() {
		
	}

	/**
	 * @return the points
	 */
	public List<DataPoint> getPoints() {
		return points;
	}
}
