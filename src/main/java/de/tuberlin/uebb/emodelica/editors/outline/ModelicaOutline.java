/**
 * 
 */
package de.tuberlin.uebb.emodelica.editors.outline;


import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import de.tuberlin.uebb.emodelica.editors.ModelicaEditor;
import de.tuberlin.uebb.emodelica.model.IModelChangedListener;
import de.tuberlin.uebb.emodelica.model.Model;
import de.tuberlin.uebb.emodelica.model.ModelLabelProvider;
import de.tuberlin.uebb.emodelica.model.ModelTreeContentProvider;
import de.tuberlin.uebb.modelica.im.impl.nodes.Node;

/**
 * @author choeger
 *
 */
public class ModelicaOutline extends ContentOutlinePage implements IModelChangedListener {
	
	private IDocumentProvider documentProvider;
	private ModelicaEditor editor;
	private Model input;

	public ModelicaOutline(IDocumentProvider documentProvider,
			ModelicaEditor modelicaEditor) {
		super();
		this.editor = modelicaEditor;
		this.documentProvider = documentProvider;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		this.getTreeViewer().setContentProvider(new ModelTreeContentProvider());
		this.getTreeViewer().setLabelProvider(new ModelLabelProvider());
		this.getTreeViewer().setInput(input);
		this.getTreeViewer().addSelectionChangedListener(this);
		modelChanged(null,this.editor.getModelManager().getModel());
	}
	
	public void selectionChanged(SelectionChangedEvent event) {
		super.selectionChanged(event);
		
		IStructuredSelection selection = (IStructuredSelection)event.getSelection();
		Node node = (Node)selection.getFirstElement();
		if (node == null)
			return;
		try {
			System.err.println("setting highlighting to " + node.getStartOffset() + ":" + node.getEndOffset());
			Position pos = new Position(node.getStartOffset(),node.getEndOffset()-node.getStartOffset());
			editor.setHighlightRange(pos.offset, pos.length, true);
			//TODO: change this to happen on a flattened model only for identifiers (see java outline)
			TextSelection textSelection = new TextSelection(documentProvider.getDocument(input),pos.offset, pos.length);
			editor.getSelectionProvider().setSelection(textSelection);
		} catch (IllegalArgumentException x) {
			editor.resetHighlightRange();
		}

	}

	@Override
	public void modelChanged(Model oldModel, Model newModel) {
		this.input = newModel;
		if (input != null)
		System.err.println("setting input to: " + input.getRootNode());
		
		//are we there already?
		if (this.getTreeViewer() == null)
			return;
	
		Runnable update = new Runnable() { public void run() {updateTree();} };
		this.getTreeViewer().getTree().getDisplay().asyncExec(update);
	}

	private void updateTree() {
		if (input != null)
			this.getTreeViewer().setInput(input.getRootNode());
	}
}
