/**
 * 
 */
package de.tuberlin.uebb.emodelica.model;

import java.io.BufferedReader;
import java.io.StringReader;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;

/**
 * @author choeger
 *
 */
public class SimpleModelicaReconcilingStrategy implements IReconcilingStrategy, IReconcilingStrategyExtension, IModelContentProvider {

	private IModelManager modelManager = null;
	private IDocument document;
	
	public SimpleModelicaReconcilingStrategy(
			ModelicaModelManager modelicaModelManager) {
		modelManager = modelicaModelManager;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.reconciler.IReconcilingStrategy#reconcile(org.eclipse.jface.text.IRegion)
	 */
	@Override
	public void reconcile(IRegion partition) {
		modelManager.contentChanged();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.reconciler.IReconcilingStrategy#reconcile(org.eclipse.jface.text.reconciler.DirtyRegion, org.eclipse.jface.text.IRegion)
	 */
	@Override
	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.reconciler.IReconcilingStrategy#setDocument(org.eclipse.jface.text.IDocument)
	 */
	@Override
	public void setDocument(IDocument document) {
		modelManager.setContentProvider(this);
		
		this.document = document;
	}

	@Override
	public void initialReconcile() {
		System.err.println("Reconciling...");
		modelManager.contentChanged();
	}

	@Override
	public void setProgressMonitor(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BufferedReader getContent() {
		StringReader reader = new StringReader(document.get());
		return new BufferedReader(reader);
	}

	/**
	 * @return the document
	 */
	@Override
	public IDocument getDocument() {
		return document;
	}

}
