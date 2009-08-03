package de.tuberlin.uebb.emodelica.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class Curve {
	
	private List<Double> points = new ArrayList<Double>();
	private Color color = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	String name;
	
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

	public Curve(String name) {
		this.name = name;
	}

	/**
	 * @return the points
	 */
	public List<Double> getPoints() {
		return points;
	}
}
