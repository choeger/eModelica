/**
 * 
 */
package de.tuberlin.uebb.emodelica.editors.presentation;

import java.util.ArrayList;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;

import de.tuberlin.uebb.emodelica.editors.ModelicaPresentationReconciler;
import de.tuberlin.uebb.emodelica.generated.parser.NT_String_Comment;
import de.tuberlin.uebb.emodelica.model.Model;
import de.tuberlin.uebb.page.parser.symbols.Absy;

/**
 * @author choeger
 *
 */
public class ModelicaModelPresentation {	
	static class ModelPresenter implements Runnable {
		private Model model;
		private ISourceViewer sourceViewer;
		private ArrayList<StyleRange> styleRanges;
		
		public ModelPresenter(Model model, ISourceViewer sourceViewer) {
			super();
			this.model = model;
			this.sourceViewer = sourceViewer;
			this.styleRanges = new ArrayList<StyleRange>();
		}

		@Override
		public void run() {
			if (model == null)
				return;
			
			styleRanges = new ArrayList<StyleRange>();
			recalculateStyle(model.getChild());
			TextPresentation presentation = getTextPresentation(new Region(0,sourceViewer.getDocument().getLength()));

			for (StyleRange range : styleRanges) {
				//System.err.println("adding style for range: " + range.start + ":" + range.length);
				presentation.replaceStyleRange(range); 
			}
			
//			System.err.println("presentation: " + presentation.getDenumerableRanges());
//			StyleRange range = null;
//			for (Iterator<?> iter = presentation.getAllStyleRangeIterator(); iter.hasNext();) {
//				range = (StyleRange)iter.next();
//				System.err.println("range: " + range.start + ":" + range.length);	
//			}
			
			System.err.println("Applying style...");
			sourceViewer.changeTextPresentation(presentation, false);			
		}

		private TextPresentation getTextPresentation(IRegion damage) {
			ModelicaPresentationReconciler reconciler = ModelicaPresentationReconciler.getInstance();
			
			return reconciler.createRepairDescription(damage, sourceViewer.getDocument());
		}

		private StyleRange createStyleRange(Absy child, Color fg) {
			int start = child.getStartOffset();
			int length = child.getEndOffset() - start;
			StyleRange range = new StyleRange();
			range.start = start;
			range.length = length;
			range.foreground = fg;
			return range;
		}

		private void recalculateStyle(Absy start) {
			if (start != null)
				for (Absy absy : start.getChildren()) {
					if (absy instanceof NT_String_Comment) {
						StyleRange range = createStyleRange(absy, ModelicaColors.stringCommentColor);
						styleRanges.add(range);
					} else
						recalculateStyle(absy);
				}		
		}
	}
	
	public static Runnable getModelPresentationUpdater(Model model, ISourceViewer sourceViewer) {
		return new ModelicaModelPresentation.ModelPresenter(model, sourceViewer);
	}
}
