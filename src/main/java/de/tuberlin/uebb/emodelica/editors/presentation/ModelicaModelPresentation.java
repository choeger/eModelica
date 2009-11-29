/**
 * 
 */
package de.tuberlin.uebb.emodelica.editors.presentation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
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

import de.tuberlin.uebb.emodelica.editors.ModelicaPresentationReconciler;
import de.tuberlin.uebb.emodelica.editors.ModelicaSourceViewer;
import de.tuberlin.uebb.emodelica.model.Model;
import de.tuberlin.uebb.modelica.im.nodes.INode;
import de.tuberlin.uebb.modelica.im.nodes.IStoredDefinitionNode;
import de.tuberlin.uebb.page.grammar.symbols.Terminal;
import de.tuberlin.uebb.page.parser.symbols.IAbsy;

/**
 * @author choeger
 *
 */
public class ModelicaModelPresentation implements ITextPresentationListener, ITextInputListener {	
	private static final String HIGHLIGHTED_POSITIONS = "de.tuberlin.uebb.emodelica.categories.HIGHLIGHTED_POSITIONS";
	private static final String SEMANTIC_HIGHLIGHTING_EXTENSION = "de.tuberlin.uebb.emodelica.semanticHighlighter";
	
	class ModelPresenter implements Runnable {
		
		private Model model;
		private ISourceViewer sourceViewer;
		private List<ISemanticHighlighter> highlighters = new ArrayList<ISemanticHighlighter>();
		
		public ModelPresenter(Model model, ISourceViewer sourceViewer) {
			super();
			
			IExtensionRegistry reg = Platform.getExtensionRegistry();
			
			IExtensionPoint point = reg.getExtensionPoint(SEMANTIC_HIGHLIGHTING_EXTENSION);
			
			for (IExtension extension : point.getExtensions()) {
				
				for (IConfigurationElement element : extension.getConfigurationElements()) {
					if (element.getName().equals("Highlighter")) {
						
						String className = element.getAttribute("Class");
						try {
							Class<?> cls = Class.forName(className);
							highlighters.add((ISemanticHighlighter) cls.newInstance());
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}	
			}
			
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
			
			Set<ISemanticHighlighter> allHighlighters = new HashSet<ISemanticHighlighter>();
			allHighlighters.addAll(highlighters);
			recalculateHighlighting(model.getRootNode(), allHighlighters);
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
			
			System.err.println("Setting text presentation");
			sourceViewer.changeTextPresentation(presentation, true);
		}

		private TextPresentation getTextPresentation(IRegion damage) {
			ModelicaPresentationReconciler reconciler = ModelicaPresentationReconciler.getInstance();
			
			final IDocument document = sourceViewer.getDocument();
			if (document==null)
				return null;
			return reconciler.createRepairDescription(damage, document);
		}

		private void recalculateHighlighting(INode currentNode, Set<ISemanticHighlighter> lastActiveHighlighters) {
			if (currentNode == null)
				return;
			
			final Set<ISemanticHighlighter> activeHighlighters = new HashSet<ISemanticHighlighter>();
			activeHighlighters.addAll(lastActiveHighlighters);
			
			for (ISemanticHighlighter highlighter : highlighters)
				if (activeHighlighters.contains(highlighter)) {
					boolean active = highlighter.handleNode(currentNode);
					if (!active)
						activeHighlighters.remove(highlighter);
				}
				
			if (!activeHighlighters.isEmpty())
				for (INode child : currentNode.getChildren().values())
					recalculateHighlighting(child, activeHighlighters);
					
			for (ISemanticHighlighter highlighter : highlighters) {
				highlighter.nodeHandled(currentNode);
			}							
			
			for (ISemanticHighlighter highlighter : highlighters)
				highlighted.addAll(highlighter.getHighlightingPositions());
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
