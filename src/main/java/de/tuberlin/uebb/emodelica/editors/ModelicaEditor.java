package de.tuberlin.uebb.emodelica.editors;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import de.tuberlin.uebb.emodelica.editors.outline.ModelicaOutline;
import de.tuberlin.uebb.emodelica.editors.presentation.ModelicaModelPresentation;
import de.tuberlin.uebb.emodelica.model.IModelChangedListener;
import de.tuberlin.uebb.emodelica.model.IModelManager;
import de.tuberlin.uebb.emodelica.model.Model;
import de.tuberlin.uebb.emodelica.model.ModelicaModelManager;
import de.tuberlin.uebb.page.parser.ParseError;

/**
 * 
 */

/**
 * @author choeger
 *
 */
public class ModelicaEditor extends TextEditor implements IModelChangedListener {

	private ModelicaOutline outlinePage;
	//TODO: check if this can move to somewhere else
	private IModelManager modelManager;
	private ProjectionSupport projectionSupport;
	private ProjectionAnnotationModel annotationModel;
	private Annotation[] oldAnnotations; 
	
	/* Methods for code folding support */
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		ProjectionViewer viewer =(ProjectionViewer)getSourceViewer();

	    projectionSupport = new ProjectionSupport(viewer,getAnnotationAccess(),getSharedColors());
	    projectionSupport.install();

	    //turn projection mode on
	    viewer.doOperation(ProjectionViewer.TOGGLE);
	    annotationModel = viewer.getProjectionAnnotationModel();
	}
	
	@Override
	protected ISourceViewer createSourceViewer(Composite parent,
			IVerticalRuler ruler, int styles) {
		ISourceViewer viewer = new ProjectionViewer(parent, ruler,
				getOverviewRuler(), isOverviewRulerVisible(), styles);

		// ensure decoration support has been created and configured.
		getSourceViewerDecorationSupport(viewer);

		return viewer;
	}
	
	public void updateFoldingStructure(ArrayList<Position> positions)
	{
	   Annotation[] annotations = new Annotation[positions.size()];

	   //this will hold the new annotations along
	   //with their corresponding positions
	   HashMap<ProjectionAnnotation, Position> newAnnotations = new HashMap<ProjectionAnnotation, Position>();

	   for(int i = 0; i < positions.size();i++)
	   {
	      ProjectionAnnotation annotation = new ProjectionAnnotation();
	      annotation.setRangeIndication(true);
	      newAnnotations.put(annotation, positions.get(i));

	      annotations[i] = annotation;
	   }

	   //TODO: use this method for incremental stuff
	   annotationModel.modifyAnnotations(oldAnnotations, newAnnotations,null);

	   oldAnnotations = annotations;
	}
	
	/**
	 * 
	 */
	public ModelicaEditor() {
		super();
		this.modelManager = new ModelicaModelManager(this);
		this.modelManager.registerListener(this);
		this.setSourceViewerConfiguration(new ModelicaSourceViewerConfiguration(this));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class key) {
		if (IContentOutlinePage.class.equals(key)) {
			if (this.outlinePage == null)
				createOutlinePage();
			return this.outlinePage;
		}
		
		return super.getAdapter(key);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);

	}
	
	private void createOutlinePage() {
		this.outlinePage = new ModelicaOutline(getDocumentProvider(), this);
		this.modelManager.registerListener(this.outlinePage);
		//set initial model
		this.outlinePage.modelChanged(null, modelManager.getModel());
	}

	public IModelManager getModelManager() {
		return this.modelManager;
	}

	@Override
	public void modelChanged(Model oldModel, Model newModel) {
		FileEditorInput input = ((FileEditorInput)this.getEditorInput());
		if (input == null)
			return;
		try {
			input.getFile().deleteMarkers(IMarker.PROBLEM, true, 1);		
			System.err.println("got " + modelManager.getParseErrors().size() + " error markers");
			for (ParseError parseError : modelManager.getParseErrors()) {
				int errStart = parseError.getStartOffset();
				int errEnd   = parseError.getEndOffset();
				int startOffset = newModel.getInput().get(newModel.getInput().size() - 1).getEndOffset();
				int endOffset = startOffset;
				
				if (errStart >= 0) 
					startOffset = newModel.getInput().get(errStart).getStartOffset();
				
				if (errEnd >= 0)
					endOffset = newModel.getInput().get(errEnd).getEndOffset();

				System.err.println("Error! StartOffset: " + startOffset);
				System.err.println("Error! EndOffset: " + endOffset);
				IMarker errorMarker = input.getFile().createMarker(IMarker.PROBLEM);
				errorMarker.setAttribute(IMarker.MESSAGE, parseError.getDescription());
				errorMarker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
				errorMarker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
				errorMarker.setAttribute(IMarker.CHAR_START, startOffset);
				errorMarker.setAttribute(IMarker.CHAR_END, endOffset);
			}
			
			updateFoldingStructure(newModel.getAllFoldablePositions());
			
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		Display.getDefault().asyncExec(ModelicaModelPresentation.getModelPresentationUpdater(newModel, this.getSourceViewer()));
	}
}
