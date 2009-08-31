/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project.impl;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.tuberlin.uebb.emodelica.Constants;
import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.experiments.IExperiment;
import de.tuberlin.uebb.emodelica.model.experiments.IExperimentContainer;
import de.tuberlin.uebb.emodelica.model.experiments.impl.ExperimentContainer;
import de.tuberlin.uebb.emodelica.model.experiments.impl.TextFileExperiment;
import de.tuberlin.uebb.emodelica.model.project.ILibraryContainer;
import de.tuberlin.uebb.emodelica.model.project.ILibraryEntry;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IMosilabEnvironment;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;
import de.tuberlin.uebb.emodelica.preferences.PreferenceConstants;
import de.tuberlin.uebb.emodelica.util.ModelicaToResourcesAdapterFactory;
import de.tuberlin.uebb.emodelica.util.ResourcesToModelicaAdapterFactory;

/**
 * @author choeger
 * 
 */
public class MosilabProject extends ModelicaResource implements IMosilabProject {

	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<mosilab:mosilabProject xmlns:mosilab=\"http://projects.uebb.tu-berlin.de/mf/emodelica/mosilabSettings\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://projects.uebb.tu-berlin.de/mf/emodelica/mosilabSettings mosilabSettings.xsd \">";
	private static final String XML_FOOTER = "</mosilab:mosilabProject>";

	private IProject project;
	private String outputFolder = "build";
	private ILibraryContainer libs = new LibraryContainer(this,
			"Referenced Libraries");
	private List<IMosilabSource> srcFolders;
	private IMosilabEnvironment mosilabInstallation = null;
	private List<Object> children = new ArrayList<Object>();
	private IExperimentContainer experiments;
	private boolean dirty;

	public MosilabProject(IProject project) {
		setResource(project);
		srcFolders = new ArrayList<IMosilabSource>();

		setupProperties();
		readSettings();
		
		doRefresh();
		syncChildren();
	}

	private void setupProperties() {
		Map persistentProperties;
		try {
			persistentProperties = project.getPersistentProperties();

			if (persistentProperties.containsKey(MOSILAB_OUTFOLDER_KEY)) {
				outputFolder = (String) persistentProperties
						.get(MOSILAB_OUTFOLDER_KEY);
			} else {
				outputFolder = EModelicaPlugin
						.getDefault()
						.getPluginPreferences()
						.getString(
								PreferenceConstants.P_MOSILAB_DEFAULT_OUTFOLDER);
			}

			if (persistentProperties.containsKey(MOSILAB_ENVIRONMENT_KEY)) {
				for (IMosilabEnvironment env : EModelicaPlugin.getDefault()
						.getMosilabEnvironments()) {
					if (env.getName().equals(
							persistentProperties.get(MOSILAB_ENVIRONMENT_KEY))) {
						setMosilabInstallation(env);
						
						break;
					}
				}
			}
			
			if (mosilabInstallation == null) {
				mosilabInstallation = EModelicaPlugin.getDefault()
						.getDefaultMosilabEnvironment();
				if (mosilabInstallation == null)
					return;
				// TODO: handle no default installation!
				project.setPersistentProperty(MOSILAB_ENVIRONMENT_KEY,
						mosilabInstallation.getName());
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * set the IMosilabEnvironment of this project
	 * @param env the new Environment or null if none
	 */
	public void setMosilabInstallation(IMosilabEnvironment env) {
		if (mosilabInstallation != null)
			mosilabInstallation.removeReferencedBy(this);
		mosilabInstallation = env;
		if (env != null)
			mosilabInstallation.setReferencedBy(this);
	}

	private void readSettings() {
		IFile settings = project.getFile(".mosilab.xml");
		System.err.println("testing: " + settings.getFullPath());
		if (settings.exists()) {
			System.err.println("reading " + settings.getName());
			// parse an XML document into a DOM tree
			DocumentBuilder parser;
			try {
				// create a SchemaFactory capable of understanding WXS schemas
				SchemaFactory factory = SchemaFactory
						.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

				// load a WXS schema, represented by a Schema instance
				Source schemaFile = new StreamSource(
						this.getClass().getResourceAsStream(
								Constants.XML_MOSILAB_SETTINGS_XSD));
				Schema schema = factory.newSchema(schemaFile);

				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				dbf.setNamespaceAware(true);
				dbf.setSchema(schema);

				try {
					schema.newValidator().validate(
							new StreamSource(settings.getContents()));
				} catch (SAXException e) {
					System.err.println("Validation failed: " + e.getMessage());
					return;
				}

				parser = dbf.newDocumentBuilder();
				Document document = parser.parse(settings.getContents());

				NodeList envNodes = document
						.getElementsByTagName("mosilab:Environment");
				for (int i = 0; i < envNodes.getLength(); i++) {
					Node envNode = envNodes.item(i);
					String location = envNode.getAttributes().getNamedItem(
							"location").getNodeValue();
					String name = envNode.getAttributes().getNamedItem("name")
							.getNodeValue();
					String version = envNode.getAttributes().getNamedItem(
							"version").getNodeValue();
					libs.add(location, name, version);
				}

				NodeList srcNodes = document
						.getElementsByTagName("mosilab:SourceFolder");
				System.err.println("got " + srcNodes.getLength()
						+ " src folders");
				for (int i = 0; i < srcNodes.getLength(); i++) {
					Node srcNode = srcNodes.item(i);
					String location = srcNode.getAttributes().getNamedItem(
							"location").getNodeValue();
					System.err.println("adding: " + location);
					addSrc(location);
				}
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.err.println("no file " + settings.getName() + " found in "
					+ project.getName());
		}
	}

	@Override
	public ILibraryContainer getLibraries() {
		return libs;
	}

	@Override
	public IMosilabEnvironment getMOSILABEnvironment() {
		return mosilabInstallation;
	}

	@Override
	public String getOutputFolder() {
		return outputFolder;
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public List<IMosilabSource> getSrcFolders() {
		return srcFolders;
	}

	@Override
	public void setOutputFolder(String outputFolder) {
		IFolder out = project.getFolder(outputFolder);
		if (!out.exists())
			try {
				out.create(true, true, null);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		this.outputFolder = outputFolder;
	}

	@Override
	public List<? extends Object> getChildren() {
		
		return children;
	}

	@Override
	public IModelicaResource getParent() {
		// Projects don't have Modelica parents
		return null;
	}
	
	@Override
	public void syncChildren() {
		children.clear();
		
		if (mosilabInstallation != null)
			children.add(getMOSILABEnvironment());
		
		if (experiments != null)
			children.add(experiments);
		
		children.add(libs);
		for (IMosilabSource src : srcFolders)
			if (!src.isRoot())
				children.add(src);
			else {
				children.addAll(src.getPackages());
				children.addAll(src.getContent());
			}		
		
//		if (getMOSILABEnvironment() == null) {
//			try {
//				IMarker probMarker = project.createMarker(IMarker.PROBLEM);
//				probMarker.setAttribute(IMarker.SEVERITY,
//						IMarker.SEVERITY_ERROR);
//				probMarker.setAttribute(IMarker.MESSAGE,
//						"No MOSILAB environment found!");
//			} catch (CoreException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

	}

	private void refreshExperiments() throws CoreException {
		IFolder expFolder = project.getFolder(".experiments");

		if (expFolder.exists()) {
			IModelicaResource cont = (IModelicaResource) expFolder.getAdapter(IModelicaResource.class);
			if (cont != null && cont instanceof IExperimentContainer) {
				experiments = (IExperimentContainer) cont;
			} else {
				experiments = new ExperimentContainer(new ArrayList<IExperiment>(), this, expFolder);
			}
			experiments.refresh();
		} else {
			experiments = null;
		}
	}

	@Override
	public void addSrc(String name) {
		IFolder folder = project.getFolder(name);
		if (!folder.exists())
			try {
				folder.create(true, true, null);
			} catch (CoreException e) {
				e.printStackTrace();
				return;
			}
		IMosilabSource source = new MosilabSource(this, folder);
		this.srcFolders.add(source);
		
		try {
			source.getResource().touch(null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void writeBackPropertiesGuarded() {

		WorkspaceModifyOperation syncOp = new WorkspaceModifyOperation() {

			@Override
			protected void execute(IProgressMonitor monitor) throws CoreException,
					InvocationTargetException, InterruptedException {
				writeBackProperties();
			}
		};

		try {
			syncOp.run(null);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void writeBackProperties() {
		try {
			PipedInputStream in = new PipedInputStream();
			PipedOutputStream out = new PipedOutputStream(in);
			String newLine = System.getProperty("line.separator");

			StringBuffer buffer = new StringBuffer();
			buffer.append(XML_HEADER);
			buffer.append(newLine);

			for (IMosilabSource src : srcFolders) {
				buffer.append("<mosilab:SourceFolder location=\"");
				buffer.append(src.getBasePath().getProjectRelativePath());
				buffer.append("\"/>");
				buffer.append(newLine);
			}

			for (ILibraryEntry env : libs.getLibraries()) {
				buffer.append("<mosilab:Environment location=\"");
				buffer.append(env.getLocation());
				buffer.append("\" name=\"");
				buffer.append(env.getName());
				buffer.append("\" version=\"");
				buffer.append(env.getVersion());
				buffer.append("\"/>");
				buffer.append(newLine);
			}

			buffer.append(XML_FOOTER);

			out.write(buffer.toString().getBytes());

			out.close();
			IFile settings = project.getFile(".mosilab.xml");

			if (!settings.exists())
				settings.create(in, true, null);
			else
				settings.setContents(in, true, false, null);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public IResource getResource() {
		return project;
	}

	@Override
	public IExperimentContainer getExperimentContainer() {
		return experiments;
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.project.impl.ModelicaResource#isDirty()
	 */
	@Override
	protected boolean isDirty() {
		return dirty;
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.project.impl.ModelicaResource#refresh()
	 */
	@Override
	public void doRefresh() {
			try {
				refreshExperiments();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.err.println("adding libs");
			if (!EModelicaPlugin.getDefault().getMosilabEnvironments().contains(mosilabInstallation))
				mosilabInstallation=null;

			if (getMOSILABEnvironment() != null) {			
				getMOSILABEnvironment().refresh();
			}
			
			getLibraries().refresh();

			for (IMosilabSource src : getSrcFolders()) {
				src.refresh();
			}
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.project.impl.ModelicaResource#setResource(org.eclipse.core.resources.IResource)
	 */
	@Override
	public void setResource(IResource resource) {
		if (resource instanceof IProject) {
			super.setResource(resource);
			project = (IProject) resource;
		}
	}

	@Override
	public void setExperimentContainer(IExperimentContainer container) {
		experiments = container;
		syncChildren();
	}

	@Override
	public void setMOSILABEnvironment(IMosilabEnvironment environment) {
		this.mosilabInstallation = environment;
		syncChildren();
	}

	@Override
	public void addSrc(IMosilabSource src) {
		srcFolders.add(src);
		src.setParent(this);
		try {
			src.getResource().touch(null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void removeSource(IMosilabSource src) {
		srcFolders.remove(src);
		src.setParent(null);
		ResourcesToModelicaAdapterFactory.unMap(src.getResource());
		try {
			src.getResource().touch(null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setParent(IModelicaResource newParent) {
		//Projects don't have parents		
	}
}
