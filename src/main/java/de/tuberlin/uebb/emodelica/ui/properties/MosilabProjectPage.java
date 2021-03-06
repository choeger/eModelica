/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.properties;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.PropertyPage;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.Images;
import de.tuberlin.uebb.emodelica.model.project.ILibraryEntry;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;
import de.tuberlin.uebb.emodelica.model.project.IProjectManager;
import de.tuberlin.uebb.emodelica.ui.LibrariesSelectionView;
import de.tuberlin.uebb.emodelica.ui.SourceFolderSelectionView;

/**
 * @author choeger
 * 
 */
public class MosilabProjectPage extends PropertyPage implements
		IWorkbenchPropertyPage {

	IMosilabProject project;
	private LibrariesSelectionView libsView;
	private SourceFolderSelectionView sourceView;

	/**
	 * 
	 */
	public MosilabProjectPage() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse
	 * .swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		System.err.println("getting project ... " + this.getElement());
		// TODO: check for making IProject adapt IMosilabProject
		setTitle("MOSILAB project properties");
		IProject eclipseProject = (IProject) this.getElement().getAdapter(
				IProject.class);
		IProjectManager projectManager = EModelicaPlugin.getDefault()
				.getProjectManager();
		project = projectManager.getMosilabProject((eclipseProject));

		Composite container = new Composite(parent, SWT.NONE);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		container.setLayout(gridLayout);

		TabFolder tabFolder = new TabFolder(container, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		tabFolder.setLayoutData(data);
		container.setLayoutData(data);

		createSourceFolderTab(tabFolder);
		createLibrariesTab(tabFolder);

		tabFolder.pack();

		return container;
	}

	/**
	 * @param tabFolder
	 */
	private void createSourceFolderTab(TabFolder tabFolder) {
		TabItem sourceFolderTab = new TabItem(tabFolder, SWT.NULL);
		sourceFolderTab.setText("&Source");
		sourceFolderTab.setImage(Images.SOURCE_FOLDER_DESCRIPTOR.createImage());

		Composite sourceFolderComposite = new Composite(tabFolder, SWT.NONE);
		GridLayout masterLayout = new GridLayout();
		masterLayout.numColumns = 1;
		sourceFolderComposite.setLayout(masterLayout);

		sourceView = new SourceFolderSelectionView(getShell(), project);

		Composite sourceViewComp = sourceView.getControl(sourceFolderComposite);
		setButtonLayoutData(sourceView.getAddButton());
		setButtonLayoutData(sourceView.getEditButton());
		setButtonLayoutData(sourceView.getDeleteButton());
		sourceViewComp.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite outputFolderComp = new Composite(sourceFolderComposite,
				SWT.NONE);
		outputFolderComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		outputFolderComp.setLayout(layout);

		Label label = new Label(outputFolderComp, SWT.NONE);
		label.setText("&Output folder:");
		GridData labelData = new GridData(GridData.FILL_HORIZONTAL);
		labelData.horizontalSpan = 2;
		label.setLayoutData(labelData);

		Text outFolderText = new Text(outputFolderComp, SWT.BORDER);
		outFolderText.setText(project.getOutputFolder());
		GridData textData = new GridData(GridData.FILL_HORIZONTAL);
		outFolderText.setLayoutData(textData);
		Button browseButton = new Button(outputFolderComp, SWT.PUSH);
		browseButton.setText(JFaceResources.getString("openBrowse"));
		setButtonLayoutData(browseButton);

		sourceFolderTab.setControl(sourceFolderComposite);
	}

	/**
	 * @param tabFolder
	 */
	private void createLibrariesTab(TabFolder tabFolder) {
		TabItem sourceFolderTab = new TabItem(tabFolder, SWT.NULL);
		sourceFolderTab.setText("&Libraries");
		sourceFolderTab
				.setImage(Images.LIBRARY_FOLDER_DESCRIPTOR.createImage());

		Composite librariesComposite = new Composite(tabFolder, SWT.NONE);
		GridLayout masterLayout = new GridLayout();
		masterLayout.numColumns = 1;
		librariesComposite.setLayout(masterLayout);

		libsView = new LibrariesSelectionView(getShell(), project);

		Composite sourceViewComp = libsView.getControl(librariesComposite);
		setButtonLayoutData(libsView.getAddButton());
		setButtonLayoutData(libsView.getEditButton());
		setButtonLayoutData(libsView.getDeleteButton());
		sourceViewComp.setLayoutData(new GridData(GridData.FILL_BOTH));

		sourceFolderTab.setControl(librariesComposite);
	}

	@Override
	public boolean performOk() {
		project.setMOSILABEnvironment(libsView.getEnvironment());

		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {

			@Override
			protected void execute(IProgressMonitor monitor)
					throws CoreException, InvocationTargetException,
					InterruptedException {
				// safe remove! or concurrent mod. exception!
				ArrayList<IMosilabSource> oldSources = new ArrayList<IMosilabSource>();
				oldSources.addAll(project.getSrcFolders());
				for (IMosilabSource src : oldSources)
					project.removeSource(src);

				for (IMosilabSource src : sourceView.getSources())
					project.addSrc(src);

				project.setMOSILABEnvironment(libsView.getEnvironment());
				// TODO: add libs likewise
				project.syncChildren();
				// store complex properties
				project.writeBackProperties();
				project.getProject().touch(monitor);
			}
		};

		try {
			op.run(null);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return super.performOk();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performCancel()
	 */
	@Override
	public boolean performCancel() {
		for (IMosilabSource src : sourceView.getSources()) {
			if (src.getParent() == null)
				src.setResource(null);
		}
		sourceView = null;

		for (ILibraryEntry lib : libsView.getLibraries()) {
			if (lib.getParent() == null)
				lib.setResource(null);
		}
		libsView = null;

		return super.performCancel();
	}

}
