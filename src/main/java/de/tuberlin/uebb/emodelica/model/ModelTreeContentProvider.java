/**
 * 
 */
package de.tuberlin.uebb.emodelica.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.tuberlin.uebb.emodelica.ModelRepository;
import de.tuberlin.uebb.emodelica.model.dom.DOMNode;
import de.tuberlin.uebb.emodelica.model.dom.DOMRootNode;

/**
 * @author choeger
 *
 */
public class ModelTreeContentProvider implements IStructuredContentProvider, 
ITreeContentProvider {
	private DOMNode invisibleRoot;

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		if (newInput instanceof DOMRootNode) {
			invisibleRoot = (DOMNode)newInput;
			v.refresh();
		}
	}
	public void dispose() {
	}
	
	public Object[] getElements(Object parent) {
		if (parent instanceof IFile) {
			IFile file = (IFile)parent;
			Model model = ModelRepository.getModelForFile(file);
			if (model != null)
				return model.getDOMRoot().getChildren().toArray();
		}
		
		if (!(parent instanceof DOMNode)) {
			if (invisibleRoot==null) initialize();
			return getChildren(invisibleRoot);
		} else
		return getChildren(parent);
	}
	
	public Object getParent(Object child) {
		if (child instanceof DOMNode) {
			return ((DOMNode)child).getParent();
		}
		return null;
	}
	
	public Object [] getChildren(Object parent) {
		if (parent instanceof IFile) {
			IFile file = (IFile)parent;
			Model model = ModelRepository.getModelForFile(file);
			if (model != null)
				return model.getDOMRoot().getChildren().toArray();
		}		
		
		if (parent instanceof DOMNode) {
			return ((DOMNode)parent).getChildren().toArray();
		}
		return new Object[0];
	}
	
	public boolean hasChildren(Object parent) {
		if (parent instanceof IFile) {
			IFile file = (IFile)parent;
			Model model = ModelRepository.getModelForFile(file);
			if (model != null)
				return model.getDOMRoot().getChildren().size() > 0;
		}
		
		if (parent instanceof DOMNode)
			return ((DOMNode)parent).getChildren().size() >0;
		return false;
	}

	private void initialize() {

	}
}
