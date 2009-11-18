/**
 * 
 */
package de.tuberlin.uebb.emodelica.editors;

import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.DefaultTextHover;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.source.DefaultAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

import de.tuberlin.uebb.emodelica.Constants;
import de.tuberlin.uebb.emodelica.editors.completion.ModelicaContentAssistProcessor;
import de.tuberlin.uebb.emodelica.editors.formatting.ModelicaSourceAutoEditStrategy;
import de.tuberlin.uebb.emodelica.editors.formatting.MultiLineCommentAutoEditStrategy;
import de.tuberlin.uebb.emodelica.editors.formatting.SingleLineCommentAutoEditStrategy;

/**
 * @author choeger
 *
 */
public class ModelicaSourceViewerConfiguration extends
		SourceViewerConfiguration {

	private ModelicaEditor editor = null;
	private MonoReconciler reconciler = null;
	private ITextHover textHover;

	public ModelicaSourceViewerConfiguration(ModelicaEditor editor) {
		super();
		this.editor = editor;
	}

	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		if (reconciler == null)
			reconciler= new MonoReconciler(editor.getModelManager().getReconcilingStrategy(), false);
		
		return reconciler;
	}
	
	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assistant =  new ContentAssistant();
		assistant.setContentAssistProcessor(new ModelicaContentAssistProcessor(), IDocument.DEFAULT_CONTENT_TYPE);
		assistant.setInformationControlCreator(getInformationControlCreator(sourceViewer));
		assistant.enableColoredLabels(true);
		assistant.setShowEmptyList(true);
		assistant.setEmptyMessage("No MODELICA Proposals");
		
		return assistant;
	}
	
	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		return ModelicaPresentationReconciler.getInstance();
	}
	
	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
		textHover = new ModelicaTextHover(sourceViewer);
		return textHover;
	}
	
	@Override
	public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
		final IAnnotationHover hover = new DefaultAnnotationHover();
		return hover;
	}

	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer,
			String contentType, int stateMask) {
		return getTextHover(sourceViewer, contentType);
	}

	@Override
	public String getConfiguredDocumentPartitioning(ISourceViewer sourceViewer) {
	    return Constants.ModelicaDocumentPartitioner;
	}
	
	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return Constants.MODELICA_PART_TYPES;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getAutoEditStrategies(org.eclipse.jface.text.source.ISourceViewer, java.lang.String)
	 */
	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(
			ISourceViewer sourceViewer, String contentType) {
		if (contentType.equals(Document.DEFAULT_CONTENT_TYPE))
			return new IAutoEditStrategy[] {new DefaultIndentLineAutoEditStrategy(), new ModelicaSourceAutoEditStrategy()};
		else if (contentType.equals(Constants.PART_MODELICA_MULTI_LINE_COMMENT)) {		
			return new IAutoEditStrategy[] {new DefaultIndentLineAutoEditStrategy(), new MultiLineCommentAutoEditStrategy()};
		} else if (contentType.equals(Constants.PART_MODELICA_SINGLE_LINE_COMMENT)) {
			return new IAutoEditStrategy[] {new DefaultIndentLineAutoEditStrategy(), new SingleLineCommentAutoEditStrategy()};
		} else 
			return super.getAutoEditStrategies(sourceViewer, contentType);
	}

}
