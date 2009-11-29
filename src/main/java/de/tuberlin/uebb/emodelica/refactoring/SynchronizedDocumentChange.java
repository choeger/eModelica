package de.tuberlin.uebb.emodelica.refactoring;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ISynchronizable;
import org.eclipse.ltk.core.refactoring.DocumentChange;
import org.eclipse.swt.widgets.Display;

public class SynchronizedDocumentChange extends DocumentChange {
	
	final IDocument fakeDocument; 
	final IDocument fDocument;
	
	public SynchronizedDocumentChange(String name, IDocument document) {
		super(name, document);
		fakeDocument = new Document (document.get());
		this.fDocument = document;
	}

	@Override
	protected IDocument acquireDocument(IProgressMonitor pm)
			throws CoreException {

		return fakeDocument;
	}

	@Override
	protected void commit(IDocument document, IProgressMonitor pm)
			throws CoreException {
		
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				fDocument.set(fakeDocument.get());
			}});
	}
	@Override
	protected void releaseDocument(IDocument document, IProgressMonitor pm)
			throws CoreException {
		// TODO Auto-generated method stub
	}

}
