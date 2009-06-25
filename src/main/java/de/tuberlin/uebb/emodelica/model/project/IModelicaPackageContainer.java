/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project;

import java.util.List;

import org.eclipse.core.resources.IContainer;

/**
 * @author choeger
 * Classes that provide this interface implement getPackages() to return all packages in their scope
 */
public interface IModelicaPackageContainer extends IModelicaResource {

	public List<IModelicaPackage> getPackages();
	
}
