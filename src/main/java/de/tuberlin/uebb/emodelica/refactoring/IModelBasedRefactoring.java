package de.tuberlin.uebb.emodelica.refactoring;

import de.tuberlin.uebb.emodelica.model.Model;
import de.tuberlin.uebb.modelica.im.ILocation;

public interface IModelBasedRefactoring {

	public void setLocation(ILocation location);
	
	public ILocation getLocation();
	
	public void setModel(Model model);
	
	public Model getModel();
}
