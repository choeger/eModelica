/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;

import de.tuberlin.uebb.emodelica.ModelRepository;
import de.tuberlin.uebb.emodelica.ModelRepository.IModelReturn;
import de.tuberlin.uebb.emodelica.model.Model;
import de.tuberlin.uebb.emodelica.model.project.IModelicaPackage;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.modelica.im.impl.nodes.DocumentationAnnotation;
import de.tuberlin.uebb.modelica.im.nodes.IAnnotation;
import de.tuberlin.uebb.modelica.im.nodes.IClassNode;
import de.tuberlin.uebb.modelica.im.nodes.INode;

/**
 * @author choeger
 *
 */
public class ModelicaPackage extends ModelicaResource  implements IModelicaPackage, IAdaptable {

	private String fullName;
	private String shortName;
	private ArrayList<IFile> children;
	private IContainer completePath;
	private IModelicaResource parent;
	private boolean onlyModelicaResources = false;
	private IClassNode packageDef;
	
	@Override
	public String toString() {
		return fullName;
	}

	public ModelicaPackage(IModelicaResource parent, IContainer parentDir, IContainer completePath) {
		this.parent = parent;
		setResource(completePath);
		
		IPath suffix = completePath.getFullPath().removeFirstSegments(parentDir.getFullPath().segmentCount());
		this.fullName = suffix.toString().replace(IPath.SEPARATOR, '.');
		this.shortName = suffix.lastSegment().toString();
		
		children = new ArrayList<IFile>();
		
		doRefresh();
		syncChildren();
	}
	
	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.project.IModelicaPackage#getContents()
	 */
	@Override
	public List<IFile> getContents() {
		return children;
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.project.IModelicaPackage#getFullName()
	 */
	@Override
	public String getFullName() {
		return fullName;
	}

	@Override
	public List<? extends Object> getChildren() {
		return children;
	}

	@Override
	public IModelicaResource getParent() {
		return parent;
	}

	@Override
	public void syncChildren() {
		/* only one kind of children, see doRefresh() */
	}

	@Override
	public boolean hasOnlyModelicaResources() {
		return onlyModelicaResources;
	}

	@Override
	public Object getAdapter(Class arg0) {
		
		if (arg0.equals(IResource.class)) {
			return completePath;
		}
		
		return null;
	}

	@Override
	public IContainer getContainer() {
		return completePath;
	}

	@Override
	public IResource getResource() {
		return completePath;
	}

	@Override
	protected void doRefresh() {
		children.clear();
		try {
			onlyModelicaResources = true;
			for (IResource member : completePath.members()) {
				if (member.getType() == IResource.FILE && member.getName().endsWith(".mo")) {
					if (member.getName().equals("package.mo")) {
						ModelRepository.getModelForFileAsync((IFile) member, new IModelReturn() {
							@Override
							public void setModel(Model newModel) {
								setPackageDef(newModel);
							}
						});
						
					} else { 
						children.add((IFile)member);
						ModelRepository.enqueueFile((IFile) member);
					}
				} else onlyModelicaResources = false;	
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}	
		
	}

	private void setPackageDef(Model packageModel) {
		//parsing successfull?
		if (packageModel == null || packageModel.getRootNode() == null) return;
		final INode child = packageModel.getRootNode().getChild(shortName);
		if (child instanceof IClassNode) {
			packageDef = (IClassNode) child;
			packageDef.setFullComponentName(this.fullName);
		}
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.project.impl.ModelicaResource#setResource(org.eclipse.core.resources.IResource)
	 */
	@Override
	public void setResource(IResource resource) {
		if (resource instanceof IContainer) {
			super.setResource(resource);
			completePath = (IContainer) resource;
		}
	}

	@Override
	public void setParent(IModelicaResource newParent) {
		this.parent = newParent;		
	}
	
	public IClassNode getPackageDef() {
		return packageDef;
	}
	
	public String getDocumentation() {
		if (packageDef == null)
			return null;
		final IAnnotation annotation = packageDef.getAnnotationForName("Documentation");
		if (annotation != null)
			return ((DocumentationAnnotation)annotation).getHTMLComment();
		return null;
	}
}
