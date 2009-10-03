/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.project.IModelicaPackage;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IMosilabEnvironment;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;

/**
 * @author choeger
 *
 */
public class MosilabEnvironment extends WorkspaceModelicaPackageContainer implements IMosilabEnvironment {

	private static final String COMPILER_FIELD = "compiler";
	private static final String DEFAULT_FIELD = "default";
	private static final String NAME_FIELD = "name";
	private static final String PATH_FIELD = "path";
	public static final String FIELD_DELIMITER_CHARACTER = ",";
	public static final String ESCAPE_CHARACTER = "\\\\";
	public static final String VALUE_DELIMITER_CHARACTER = "=";
	
	/**
	 * creates a environment from a String representation
	 * @param envCoded
	 * @return the environment encoded in envCoded
	 */
	public static MosilabEnvironment createFromString(String envCoded) {
		//split into fields
		String[] fields = envCoded.split("(?<!" + ESCAPE_CHARACTER + ")" + FIELD_DELIMITER_CHARACTER);
		
		String compiler = "";
		String path = "";
		String name = "";
		boolean def = false;
		
		for (String field : fields) {
			String[] pair = field.split("(?<!" + ESCAPE_CHARACTER + ")" + VALUE_DELIMITER_CHARACTER);		
			if (pair.length != 2)
				continue;
			
			String fieldName = pair[0];
			String value = pair[1];
			
			//TODO: replace with a code/decode hashmap
			if (fieldName.equals(COMPILER_FIELD)) {
				compiler = value;
			} else if (fieldName.equals(PATH_FIELD)) {
				path = value;
			} else if (fieldName.equals(NAME_FIELD)) {
				name = value;
			} else if (fieldName.equals(DEFAULT_FIELD)) {
				def = true;
			} else System.err.println("unknown field: " + fieldName);
		}
		
		MosilabEnvironment env = new MosilabEnvironment(path, name);
		env.setDefault(def);
		env.setCompilerCommand(compiler);
		return env;
	}
	
	private static String encodeField(String field, String value) {
		String escapedField = field.replaceAll(VALUE_DELIMITER_CHARACTER, ESCAPE_CHARACTER + VALUE_DELIMITER_CHARACTER);
		escapedField = escapedField.replaceAll(FIELD_DELIMITER_CHARACTER,ESCAPE_CHARACTER + FIELD_DELIMITER_CHARACTER);
		
		String escapedValue = value.replaceAll(VALUE_DELIMITER_CHARACTER, ESCAPE_CHARACTER + VALUE_DELIMITER_CHARACTER);
		escapedValue = escapedValue.replaceAll(FIELD_DELIMITER_CHARACTER,ESCAPE_CHARACTER + FIELD_DELIMITER_CHARACTER);
		
		return escapedField + VALUE_DELIMITER_CHARACTER + escapedValue;
	}
	
	/**
	 * encodes the given environment as String
	 * @param env environment to encode
	 * @return the String representation
	 */
	public static String encodeToString(IMosilabEnvironment env) {
		final String[] fields = { PATH_FIELD, COMPILER_FIELD, NAME_FIELD };
		final String[] values = { env.mosilabRoot(), env.compilerCommand(), env.getName() };
		
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < fields.length; i++) {
			buffer.append(encodeField(fields[i],values[i]));
			buffer.append(FIELD_DELIMITER_CHARACTER);
		}
		if (env.isDefault())
			buffer.append(encodeField(DEFAULT_FIELD,"true"));
		else {
			final int lastIndexOf = buffer.lastIndexOf(FIELD_DELIMITER_CHARACTER);
			buffer.delete(lastIndexOf, lastIndexOf + FIELD_DELIMITER_CHARACTER.length());
		}
		return buffer.toString();
	}	
	
	private String compilerCommand = "bin/mosilac";
	private boolean isDefault = false;
	private String rootPath;
	private String name;
	private Set<IMosilabProject> usedBy = new HashSet<IMosilabProject>();
	
	public MosilabEnvironment(String path, String name) {
		rootPath = path;
		this.name = name;
		
		IFolder folder = EModelicaPlugin.getDefault().getCommonMosilabResources().getFolder(name);
		try {
			if (folder.exists())
				folder.delete(true, null);			
			final IPath extPath = new Path(path).append("base-lib");
			System.err.println("LINK: " + extPath + " -> " + folder);
			folder.createLink(extPath, IResource.NONE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		setContainer(folder);
	}
	
	@Override
	public void setCompilerCommand(String compiler) {
		compilerCommand = compiler;		
	}

	@Override
	public String compilerCommand() {
		// TODO Auto-generated method stub
		return compilerCommand;
	}

	@Override
	public boolean isDefault() {
		return isDefault;
	}
	
	@Override
	public void setDefault(boolean value) {
		isDefault = value;
	}

	@Override
	public String mosilabRoot() {
		return rootPath;
	}

	@Override
	public void setMosilabRoot(String root) {
		rootPath = root;		
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getLocation() {
		return rootPath;
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IModelicaResource getParent() {
		// Environments do not have unique parents
		return null;
	}

	@Override
	public Set<IMosilabProject> getReferencingProjects() {
		return usedBy ;
	}

	@Override
	public void removeReferencedBy(IMosilabProject project) {
		usedBy.remove(project);
	}

	@Override
	public void setReferencedBy(IMosilabProject project) {
		usedBy.add(project);
	}

	@Override
	public void setParent(IModelicaResource newParent) {
		// Environments do not have unique parents
	}
}
