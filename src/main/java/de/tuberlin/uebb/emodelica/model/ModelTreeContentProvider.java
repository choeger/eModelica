/**
 * 
 */
package de.tuberlin.uebb.emodelica.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.tuberlin.uebb.emodelica.ModelRepository;
import de.tuberlin.uebb.modelica.im.ClassNode;
import de.tuberlin.uebb.modelica.im.Node;

/**
 * @author choeger
 *
 */
public class ModelTreeContentProvider implements IStructuredContentProvider, 
ITreeContentProvider {
	private ClassNode invisibleRoot;

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		if (newInput instanceof ClassNode) {
			invisibleRoot = (ClassNode)newInput;
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
				return model.getRootNode().getChildren().values().toArray();
		}
		
		if (!(parent instanceof Node)) {
			if (invisibleRoot==null) initialize();
			return getChildren(invisibleRoot);
		} else
		return getChildren(parent);
	}
	
	public Object getParent(Object child) {
		if (child instanceof Node) {
			return ((Node)child).getParent();
		}
		return null;
	}
	
	public Object [] getChildren(Object parent) {
		if (parent instanceof IFile) {
			IFile file = (IFile)parent;
			Model model = ModelRepository.getModelForFile(file);
			if (model != null)
				return model.getRootNode().getChildren().values().toArray();
		}		
		
		if (parent instanceof Node) {
			return ((Node)parent).getChildren().values().toArray();
		}
		return new Object[0];
	}
	
	public boolean hasChildren(Object parent) {
		if (parent instanceof IFile) {
			IFile file = (IFile)parent;
			Model model = ModelRepository.getModelForFile(file);
			if (model != null)
				return model.getRootNode().getChildren().size() > 0;
		}
		
		if (parent instanceof Node)
			return ((Node)parent).getChildren().size() >0;
		return false;
	}

	private void initialize() {

	}
}
