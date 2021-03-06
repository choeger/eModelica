/**
 * 
 */
package de.tuberlin.uebb.emodelica.model;

import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.tuberlin.uebb.emodelica.ModelRepository;
import de.tuberlin.uebb.modelica.im.ILocation;
import de.tuberlin.uebb.modelica.im.impl.nodes.Node;
import de.tuberlin.uebb.modelica.im.nodes.IClassNode;
import de.tuberlin.uebb.modelica.im.nodes.INode;
import de.tuberlin.uebb.modelica.im.nodes.IStoredDefinitionNode;

/**
 * @author choeger
 *
 */
public class ModelTreeContentProvider implements IStructuredContentProvider, ITreeContentProvider {
	
	private static final Comparator<ILocation> modelComparator = new Comparator<ILocation>() {

		@Override 
		public int compare(ILocation o1,ILocation o2){
			return o1.getEndOffset() - o2.getEndOffset();
		}
		
	};
	
	private IStoredDefinitionNode invisibleRoot;

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		if (newInput instanceof IStoredDefinitionNode) {
			invisibleRoot = (IStoredDefinitionNode)newInput;
			v.refresh();
		}
	}
	public void dispose() {
	}
	
	public Object[] getElements(Object parent) {
		if (parent instanceof IFile) {
			IFile file = (IFile)parent;
			Model model = ModelRepository.getModelForFileBlocking(file);
			if (model != null)
				return model.getRootNode().getChildren().values().toArray();
		}
		
		if (!(parent instanceof INode)) {
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
			Model model = ModelRepository.getModelForFileBlocking(file);
			if (model != null)
				return model.getRootNode().getChildren().values().toArray();
		}		
		
		if (parent instanceof IClassNode) {
			final IClassNode classNode = (IClassNode)parent;
			ILocation[] children;
			int size = classNode.getChildren().size();
			if(classNode.getImports() != null)
				size++;
			if(classNode.getExtends() != null)
				size++;
			children = new ILocation[size];
			int index = 0;
			if(classNode.getImports() != null)
				children[index++] = classNode.getImports();
			if(classNode.getExtends() != null)
				children[index++] = classNode.getExtends();
			for(String nodeName : classNode.getChildrenInOrder())
				children[index++] = classNode.getChild(nodeName);
			return children;
		}
		if (parent instanceof IStoredDefinitionNode) {
			final IStoredDefinitionNode storedDefinitionNode = (IStoredDefinitionNode) parent;
			ILocation[] children;
			if (storedDefinitionNode.getWithinStatement() != null) {
				final int size = storedDefinitionNode.getChildren().size() + 1;
				children = new ILocation[size];
				children[0] = storedDefinitionNode.getWithinStatement();
				for (int i = 1; i  < size; i++) {
					INode[] modelChildren = storedDefinitionNode.getChildren().values().toArray(new INode[] {});
					Arrays.sort(modelChildren, modelComparator);
					children[i] = modelChildren[i-1];
				}
			}	
			else {
				children = storedDefinitionNode.getChildren().values().toArray(new INode[]{});
				Arrays.sort((ILocation[])children, modelComparator);
			}
			return children;
		}
		
		if (parent instanceof Node) {
			final INode[] array = ((Node)parent).getChildren().values().toArray(new INode[]{});
			Arrays.sort(array, modelComparator);
			return array;
		}
		return new Object[0];
	}
	
	public boolean hasChildren(Object parent) {
		if (parent instanceof IFile) {
			IFile file = (IFile)parent;
			Model model = ModelRepository.getModelForFileBlocking(file);
			if (model != null)
				return model.getRootNode().getChildren().size() > 0;
		}
		
		if (parent instanceof IClassNode) {
			final IClassNode classNode = (IClassNode) parent;
			return ((classNode.getImports() != null && !classNode.getImports().getChildren().isEmpty()) ||
					!classNode.getChildren().isEmpty());
		}
			
		
		if (parent instanceof IStoredDefinitionNode) {
			final IStoredDefinitionNode storedDefinitionNode = (IStoredDefinitionNode) parent;
			return (storedDefinitionNode.getWithinStatement() != null || !storedDefinitionNode.getChildren().isEmpty());			
		}
		
		if (parent instanceof Node)
			return ((Node)parent).getChildren().size() >0;
		return false;
	}

	private void initialize() {

	}
}
