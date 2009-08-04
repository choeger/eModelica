package de.tuberlin.uebb.emodelica.model.experiments.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import de.tuberlin.uebb.emodelica.model.experiments.ICurve;
import de.tuberlin.uebb.emodelica.model.experiments.IExperiment;

public class ColoredCurve implements ICurve {
	
	private List<Double> points = new ArrayList<Double>();
	private Color color = null;
	String name;
	private IExperiment experiment;
	
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

	public ColoredCurve(IExperiment experiment, 
			String name, Color color) {
		this.experiment = experiment;
		this.name = name;
		this.color = color;
	}

	/**
	 * @return the points
	 */
	public List<Double> getPoints() {
		return points;
	}

	@Override
	public IExperiment getExperiment() {
		return experiment;
	}

	@Override
	public String getVariableName() {
		return name;
	}

	@Override
	public void setPoints(List<Double> points) {
		this.points = points;		
	}
}
