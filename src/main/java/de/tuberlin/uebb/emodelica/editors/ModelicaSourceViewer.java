/**
 * 
 */
package de.tuberlin.uebb.emodelica.editors;

import java.util.ArrayList;

import org.eclipse.jface.text.ITextPresentationListener;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * @author choeger
 * 
 */
public class ModelicaSourceViewer extends ProjectionViewer {

	public ModelicaSourceViewer(Composite parent, IVerticalRuler ruler,
			IOverviewRuler overviewRuler, boolean overviewRulerVisible,
			int styles) {
		super(parent, ruler, overviewRuler, overviewRulerVisible, styles);
	}

	
	/* taken from jdts JavaSourceViewer, should probably go into ITextViewerExtension ... */
	/**
	 * Prepends the text presentation listener at the beginning of the viewer's
	 * list of text presentation listeners. If the listener is already
	 * registered with the viewer this call moves the listener to the beginning
	 * of the list.
	 *
	 * Read more:
	 * http://kickjava.com/src/org/eclipse/jdt/internal/ui/javaeditor/
	 * JavaSourceViewer.java.htm#ixzz0Lc49CFLW
	 * 
	 * @param listener
	 */
	@SuppressWarnings("unchecked")
	public void prependTextPresentationListener(
			ITextPresentationListener listener) {

		if (fTextPresentationListeners == null)
			fTextPresentationListeners = new ArrayList();
		fTextPresentationListeners.remove(listener);
		fTextPresentationListeners.add(0, listener);
	}

}
