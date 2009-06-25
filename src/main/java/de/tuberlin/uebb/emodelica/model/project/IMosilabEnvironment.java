/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project;


/**
 * This interface provides access to the settings of a MosilabEnvironment that needs not to be bound to the workspace
 * @author choeger
 *
 */
public interface IMosilabEnvironment extends ILibraryEntry {

	/**
	 * @return the MOSILAB_ROOT path of that environment 
	 */
	public String mosilabRoot();
	
	/**
	 * set the MOSILAB_ROOT
	 * @param root the new MOSILAB_ROOT path of that environment 
	 */
	public void setMosilabRoot(String root);
	
	/**
	 * returns the (relative) path to the mosilac binary
	 * @return the mosilac command
	 */
	public String compilerCommand();
	
	/**
	 * check if this is the default MOSILAB environment
	 * @return true if this environment is the default
	 */
	public boolean isDefault();

	/**
	 * set to true to mark that environment as default
	 * @param value
	 */
	public void setDefault(boolean value);

	/**
	 * set the compiler command of this environment
	 * @param compiler
	 */
	public void setCompilerCommand(String compiler);

	/**
	 * @return the name
	 */	
	public String getName();

	/**
	 * @param name the name to set
	 */
	void setName(String name);
	
}
