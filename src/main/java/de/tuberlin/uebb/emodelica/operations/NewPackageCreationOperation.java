/**
 * 
 */
package de.tuberlin.uebb.emodelica.operations;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;

/**
 * @author choeger
 * 
 */
public class NewPackageCreationOperation extends WorkspaceModifyOperation {

	private String sourceFolder;
	private String packageName;
	
	public NewPackageCreationOperation(String sourceFolder, String packageName) {
		super();
		this.sourceFolder = sourceFolder;
		this.packageName = packageName;
	}

	@Override
	protected void execute(IProgressMonitor arg0) throws CoreException,
			InvocationTargetException, InterruptedException {
		System.err.println("creating a new package! (" + packageName + " in " + sourceFolder + ")");

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		Path path = new Path(sourceFolder);
		IFolder srcFolder = root.getFolder(path);
		
		IMosilabSource source = (IMosilabSource) srcFolder.getAdapter(IModelicaResource.class);
		if (source == null)
			return;
		
		IFolder last = srcFolder;

		for (String fName : packageName.split("\\.")) {
			last = last.getFolder(fName);
			System.err.println("handling: " + fName);
			if (!last.exists()) {
				System.err.println("creating...");
				last.create(true, true, null);
			}
		}

		IFile packagemo = last.getFile("package.mo");
		if (packagemo.exists())
			return;
		
		try {
			PipedInputStream in = new PipedInputStream();
			PipedOutputStream out = new PipedOutputStream(in);
			String newLine = System.getProperty("line.separator");

			StringBuffer buffer = new StringBuffer();
			buffer.append("within ");
			
			if (packageName.contains("."))
				buffer.append(packageName
					.substring(0, packageName.lastIndexOf(".")));
			else buffer.append(" ");
			
			buffer.append(";");
			buffer.append(newLine);

			buffer.append("package ");
			buffer.append(packageName
					.substring(packageName.lastIndexOf(".") + 1));
			buffer.append(newLine);

			buffer.append(newLine);
			buffer.append("end ");
			buffer.append(packageName
					.substring(packageName.lastIndexOf(".") + 1));
			buffer.append(";");
			buffer.append(newLine);

			out.write(buffer.toString().getBytes());
			out.close();

			packagemo.create(in, true, null);
			
			source.markAsDirty();
			source.refresh();
			
		} catch (IOException e) {
			// TODO change -1 to error constant
			throw new CoreException(new Status(IStatus.ERROR,
					EModelicaPlugin.PLUGIN_ID, -1, e.getMessage(), e));
		}
	}
}
