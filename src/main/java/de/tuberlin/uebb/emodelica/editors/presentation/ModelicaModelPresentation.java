/**
 * 
 */
package de.tuberlin.uebb.emodelica.editors.presentation;

import java.util.ArrayList;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextInputListener;
import org.eclipse.jface.text.ITextPresentationListener;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import de.tuberlin.uebb.emodelica.editors.ModelicaPresentationReconciler;
import de.tuberlin.uebb.emodelica.editors.ModelicaSourceViewer;
import de.tuberlin.uebb.emodelica.model.Model;
import de.tuberlin.uebb.modelica.im.impl.generated.moparser.NT_Component_Reference;
import de.tuberlin.uebb.modelica.im.impl.generated.moparser.NT_String_Comment;
import de.tuberlin.uebb.page.parser.symbols.Absy;

/**
 * @author choeger
 *
 */
public class ModelicaModelPresentation implements ITextPresentationListener, ITextInputListener {	
	private static final String HIGHLIGHTED_POSITIONS = "de.tuberlin.uebb.emodelica.categories.HIGHLIGHTED_POSITIONS";
	
	class ModelPresenter implements Runnable {
		
		private Model model;
		private ISourceViewer sourceViewer;
		
		public ModelPresenter(Model model, ISourceViewer sourceViewer) {
			super();
			this.model = model;
			this.sourceViewer = sourceViewer;
		}

		@Override
		public void run() {
			if (model == null)
				return;
			
			final IDocument document = sourceViewer.getDocument();
			for (HighlightingPosition pos : highlighted)
				try {
					document.removePosition(HIGHLIGHTED_POSITIONS,pos);
				} catch (BadPositionCategoryException e1) {	
					e1.printStackTrace();
				}
			highlighted.clear();
			
			recalculateHighlighting(model.getChild());
			TextPresentation presentation = getTextPresentation(new Region(0, document.getLength()));
			
			for (HighlightingPosition pos : highlighted) {
				try {
					document.addPosition(HIGHLIGHTED_POSITIONS, pos);
					presentation.replaceStyleRange(pos.getStyleRange()); 
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPositionCategoryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			System.err.println("[Async] Applying style...");
			sourceViewer.changeTextPresentation(presentation, true);
			System.err.println("[Async] Applying style done.");
		}

		private TextPresentation getTextPresentation(IRegion damage) {
			ModelicaPresentationReconciler reconciler = ModelicaPresentationReconciler.getInstance();
			
			final IDocument document = sourceViewer.getDocument();
			if (document==null)
				return null;
			return reconciler.createRepairDescription(damage, document);
		}

		private HighlightingPosition createHighlighting(Absy child, TextAttribute textAttribute) { 
			int start = model.getInput().get(child.getRange().getStartToken()).getStartOffset();			
			int length = model.getInput().get(child.getRange().getEndToken()).getEndOffset() - start;
			
			HighlightingPosition pos = new HighlightingPosition(start, length, textAttribute);
			
			return pos;
		}

		private void recalculateHighlighting(Absy start) {
			if (start != null)
				for (Absy absy : start.getChildren()) {
					if (absy instanceof NT_String_Comment) {
						HighlightingPosition pos = createHighlighting(absy, ModelicaColors.stringCommentText);
						highlighted.add(pos);
					} else if (absy instanceof NT_Component_Reference) {
						NT_Component_Reference reference = (NT_Component_Reference)absy;
						TextAttribute attr = new TextAttribute(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE), null, SWT.ITALIC);
						Absy last = reference.getNt_referencepart().getLastChild();
						HighlightingPosition pos = createHighlighting(last, attr);
						highlighted.add(pos);
					} else recalculateHighlighting(absy);
				}		
		}
	}

	private ModelicaSourceViewer sourceViewer;
	private ArrayList<HighlightingPosition> highlighted;
	
	public ModelicaModelPresentation(ModelicaSourceViewer sourceViewer) {
		this.sourceViewer = sourceViewer;
		this.sourceViewer.prependTextPresentationListener(this);
		this.sourceViewer.addTextInputListener(this);
		this.highlighted = new ArrayList<HighlightingPosition>();
	}
	
	public Runnable getModelPresentationUpdater(Model model) {
		return new ModelicaModelPresentation.ModelPresenter(model, sourceViewer);
	}


	@Override
	public void applyTextPresentation(TextPresentation presentation) {
		try {
			for (Position pos : sourceViewer.getDocument().getPositions(HIGHLIGHTED_POSITIONS)) {
				if (!pos.overlapsWith(presentation.getExtent().getOffset(), presentation.getExtent().getLength()))
					continue;
				
				HighlightingPosition highPos = (HighlightingPosition)pos;
								
				if (!pos.isDeleted())
					presentation.replaceStyleRange(highPos.getStyleRange());

			}
		} catch (BadPositionCategoryException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param document
	 */
	private void manageDocument(final IDocument document) {
		if (document != null) {
			document.addPositionCategory(HIGHLIGHTED_POSITIONS);		
			document.addPositionUpdater(new DefaultPositionUpdater(HIGHLIGHTED_POSITIONS));
		}
	}

	@Override
	public void inputDocumentAboutToBeChanged(IDocument arg0, IDocument arg1) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void inputDocumentChanged(IDocument oldDocument, IDocument newDocument) {
		manageDocument(newDocument);
	}
}
