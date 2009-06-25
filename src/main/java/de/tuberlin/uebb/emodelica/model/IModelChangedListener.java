package de.tuberlin.uebb.emodelica.model;

/**
 * 
 * @author choeger
 *
 */
public interface IModelChangedListener {
	
	/**
	 * 
	 * @param oldModel
	 * @param newModel
	 */
	public void modelChanged(Model oldModel, Model newModel);
	
}
