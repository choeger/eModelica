/**
 * 
 */
package de.tuberlin.uebb.emodelica.editors;

import java.util.ArrayList;

import org.eclipse.jface.text.ITextPresentationListener;
import org.eclipse.jface.text.ITextViewerExtension;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;

import de.tuberlin.uebb.emodelica.model.IModelManager;

/**
 * @author choeger
 * 
 */
public class ModelicaSourceViewer extends ProjectionViewer implements ITextViewerExtension {

	private IModelManager modelManager;
	private IEditorInput editorInput;

	public ModelicaSourceViewer(Composite parent, IVerticalRuler ruler,
			IOverviewRuler overviewRuler, boolean overviewRulerVisible,
			int styles, IModelManager modelManager, IEditorInput iEditorInput) {
		super(parent, ruler, overviewRuler, overviewRulerVisible, styles);
		this.modelManager = modelManager;
		this.editorInput = iEditorInput;
	}

	public IEditorInput getEditorInput() {
		return editorInput;
	}


	public IModelManager getModelManager() {
		return modelManager;
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
