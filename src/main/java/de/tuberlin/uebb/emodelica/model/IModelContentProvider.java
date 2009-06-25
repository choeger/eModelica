/**
 * 
 */
package de.tuberlin.uebb.emodelica.model;

import java.io.BufferedReader;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;

/**
 * @author choeger
 * Provides Content from which a model may be generated
 */
public interface IModelContentProvider {

	/**
	 * 
	 * @return InputStream to parse model
	 * @throws CoreException 
	 */
	public BufferedReader getContent();
	
	public IDocument getDocument();
	
}
