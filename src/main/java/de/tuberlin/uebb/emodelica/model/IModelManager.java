/**
 * 
 */
package de.tuberlin.uebb.emodelica.model;

import java.util.Set;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;

import de.tuberlin.uebb.page.parser.ParseError;

/**
 * @author choeger
 * This Interface describes how to access a model manager
 */
public interface IModelManager {
	
	/**
	 * to be invoked when the content has changed
	 */
	public void contentChanged();
	
	/**
	 * 
	 * @return the current model
	 */
	public Model getModel();

	/**
	 * set the Content provider
	 * @param provider
	 */
	public void setContentProvider(IModelContentProvider provider);
	
	/**
	 * register a Listener, which gets informed when the model has changed
	 * @param listener
	 */
	public void registerListener(IModelChangedListener listener);
	
	/**
	 * 
	 * @return a valid reconciling strategy
	 */
	public IReconcilingStrategy getReconcilingStrategy();
	
	/**
	 * 
	 * @return all parse Errors
	 */
	public Set<ParseError> getParseErrors();
	
}
