/**
 * 
 */
package de.tuberlin.uebb.emodelica.editors;

import org.eclipse.jface.text.DefaultTextHover;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHoverExtension2;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.source.ISourceViewer;

/**
 * @author choeger
 *
 */
public class ModelicaTextHover extends DefaultTextHover implements
		ITextHoverExtension2 {

	public ModelicaTextHover(ISourceViewer sourceViewer) {
		super(sourceViewer);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.ITextHoverExtension2#getHoverInfo2(org.eclipse.jface.text.ITextViewer, org.eclipse.jface.text.IRegion)
	 */
	@Override
	public Object getHoverInfo2(ITextViewer textViewer, IRegion hoverRegion) {
		return super.getHoverInfo(textViewer, hoverRegion);
	}

}
