/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project.impl;

import java.util.ArrayList;
import java.util.List;

import de.tuberlin.uebb.emodelica.model.project.ILibraryContainer;
import de.tuberlin.uebb.emodelica.model.project.ILibraryEntry;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;

/**
 * @author choeger
 *
 */
public class LibraryContainer extends ModelicaResource implements ILibraryContainer {

	private List<ILibraryEntry> libs;
	private String name;
	private IMosilabProject parent;
	
	public LibraryContainer(IMosilabProject parent, String name) {
		this.parent = parent;
		libs = new ArrayList<ILibraryEntry>();
		this.name = name;
	}
	
	@Override
	public List<ILibraryEntry> getLibraries() {
		return libs;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<? extends Object> getChildren() {
		return libs;
	}

	@Override
	public IModelicaResource getParent() {
		return parent;
	}

	@Override
	public void syncChildren() {
		/* do nothing for now */
	}

	@Override
	public void add(String location, String name, String version) {
		libs.add(new LibraryEntry(this, location, name, version));
	}
	
}
