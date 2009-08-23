/**
 * 
 */
package de.tuberlin.uebb.emodelica.operations;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import de.tuberlin.uebb.emodelica.model.project.IModelicaPackage;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;

/**
 * @author choeger
 * This class resembles the mkSelector.sh script from the MOSILAB distribution
 * for the reasons of a) performance and b) latency
 */
public class SelectCPPFilesOperation extends WorkspaceModifyOperation {

	/**
	 * @param mosilabProject
	 */
	public SelectCPPFilesOperation(IMosilabProject mosilabProject) {
		super();
		this.mosilabProject = mosilabProject;
	}

	private IMosilabProject mosilabProject;
	private List<String> names = new ArrayList<String>();

	@Override
	protected void execute(IProgressMonitor monitor) throws CoreException,
			InvocationTargetException, InterruptedException {

		initNames();

		StringBuilder builder = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		builder.append("// this file was automatically generated! Do not edit!!!");
		builder.append(newLine);
		builder.append("#include <iostream>");
		builder.append(newLine);
		builder.append("#include \"_selector.h\"");
		builder.append(newLine);
		builder.append("#include \"string.h\"");
		builder.append(newLine);

		builder.append(newLine);

		IFolder folder = mosilabProject.getProject().getFolder(
				mosilabProject.getOutputFolder());

		for (String name : names) {
			builder.append("#include \"" + name + ".hh\"");
			builder.append(newLine);
		}

		builder.append(newLine);
		
		builder.append("NumObject* _failToLoadRootClass(char *classname) {");
		builder.append(newLine);
		builder.append("	if (!classname)");
		builder.append(newLine);
		builder.append("		classname = \"(null)\";");
		builder.append(newLine);
		builder.append("	std::cerr << \"Class '\" << classname << \"' not found!\\n\";");
		builder.append(newLine);
		builder.append("	return 0;");
		builder.append(newLine);
		builder.append("}");
		builder.append(newLine);
		
		builder.append(newLine);

		builder.append("NumObject* _selectRootObject(char *classname) {");
		builder.append(newLine);
		builder.append("	if (!classname)");
		builder.append(newLine);
		builder.append("		return _failToLoadRootClass(classname);");
		builder.append(newLine);
		
		for (String name : names) {
			String cname = name.replaceAll("__", ".");
			
			builder.append("	else if (!strcmp(classname,\"" + name + "\"))");
			builder.append(newLine);
			builder.append("		return new " + name + "(0);");
			builder.append(newLine);
			builder.append("	else if (!strcmp(classname,\"" + cname + "\"))");
			builder.append(newLine);
			builder.append("		return new " + name + "(0);");
			builder.append(newLine);
		}
		builder.append("	else return _failToLoadRootClass(classname);");
		builder.append(newLine);
		builder.append("}");
		
		try {
			PipedInputStream source = new PipedInputStream();
			PipedOutputStream out = new PipedOutputStream(source);

			out.write(builder.toString().getBytes());
			out.close();
			
			IFile file = folder.getFile("_selector.cpp");
			if (!file.exists())
				file.create(source, true, monitor);
			else
				file.setContents(source, true, false, monitor);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		System.err.println("exec() done");
	}

	/**
	 * @throws CoreException
	 */
	private void initNames() throws CoreException {
		for (IMosilabSource source : mosilabProject.getSrcFolders())
			for (IModelicaPackage pkg : source.getPackages()) {
				IFolder packageFolder = (IFolder) pkg.getResource();

				IPath path = packageFolder.getFullPath();
				
				path = path.removeFirstSegments(source.getResource().getFullPath().segmentCount());
				String prefix = "";
				for (String segment : path.segments())
					prefix += segment + "__";
				for (IResource member : packageFolder.members())
					if (member instanceof IFile
							&& member.getName().endsWith(".mo") && (!member.getName().equals("package.mo"))) {
						String name = member.getName().substring(0,
								member.getName().lastIndexOf(".mo"));
						names.add(prefix + name);
					}
			}
	}

}
