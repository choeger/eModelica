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
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.part.FileEditorInput;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;

public class NewFragmentCreationOperation extends WorkspaceModifyOperation {

	private String packageName;
	private String sourceFolder;
	private String typeName;
	private String kind;

	public NewFragmentCreationOperation(String sourceFolder,
			String pkgName, String typeName, String kind) {
		super();
		this.packageName = pkgName;
		this.sourceFolder = sourceFolder;
		this.typeName = typeName;
		this.kind = kind;
	}

	@Override
	protected void execute(IProgressMonitor arg0) throws CoreException,
			InvocationTargetException, InterruptedException {
		IFolder srcFolder = ResourcesPlugin.getWorkspace().getRoot()
				.getFolder(new Path(sourceFolder));
		
		IFolder last = srcFolder;
		for (String fName : packageName.split("\\.")) {
			last = last.getFolder(fName);
		}

		IFile sourceFile = last.getFile(typeName + ".mo");
		
		try {
			String newLine = System.getProperty("line.separator");

			StringBuffer buffer = new StringBuffer();
			buffer.append("within ");
			buffer.append(packageName);
			buffer.append(";");
			buffer.append(newLine);

			buffer.append(kind);
			buffer.append(" ");
			buffer.append(typeName);
			buffer.append(newLine);

			buffer.append(newLine);
			buffer.append("end ");
			buffer.append(typeName);
			buffer.append(";");
			buffer.append(newLine);

			PipedOutputStream out = new PipedOutputStream();
			PipedInputStream in = new PipedInputStream(out, buffer.length());
			
			out.write(buffer.toString().getBytes());
			out.close();

			sourceFile.create(in, true, null);

			/*
			 * open that file
			 */
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
			.getActivePage();
			IEditorDescriptor desc = PlatformUI.getWorkbench().
			        getEditorRegistry().getDefaultEditor(sourceFile.getName());
			page.openEditor(new FileEditorInput(sourceFile), desc.getId());
			
		} catch (IOException e) {
			// TODO change -1 to error constant
			throw new CoreException(new Status(IStatus.ERROR,
					EModelicaPlugin.PLUGIN_ID, -1, e.getMessage(), e));
		}
	}

}