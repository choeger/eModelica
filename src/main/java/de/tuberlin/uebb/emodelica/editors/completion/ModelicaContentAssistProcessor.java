package de.tuberlin.uebb.emodelica.editors.completion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;

import de.tuberlin.uebb.emodelica.ModelRepository;
import de.tuberlin.uebb.emodelica.editors.ModelicaSourceViewer;
import de.tuberlin.uebb.emodelica.model.Model;
import de.tuberlin.uebb.emodelica.model.project.IModelicaPackage;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;
import de.tuberlin.uebb.modelica.im.nodes.IClassNode;
import de.tuberlin.uebb.modelica.im.nodes.INode;

public class ModelicaContentAssistProcessor implements IContentAssistProcessor {

	private final Matcher ideDetector;
	private List<IClassNode> enclosingClasses = new ArrayList<IClassNode>();
	
	public ModelicaContentAssistProcessor() {

		Pattern ideDetectorPattern = Pattern.compile("(_|\\p{Alnum})*");
		
		ideDetector = ideDetectorPattern.matcher("");
	}
	
	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		if (viewer instanceof ModelicaSourceViewer) {
			Model model = ((ModelicaSourceViewer) viewer).getModelManager().getModel();
		
			enclosingClasses.clear();
			model.getEnclosingLexicalNodes(offset, model.getRootNode(),enclosingClasses);						

			int start = offset - 1;
			int end = offset+1;
			final String string = viewer.getDocument().get();
			ideDetector.reset(string);
			
			if (start >= 0)
				ideDetector.region(start, offset);

			while(start >= 1 && ideDetector.matches()) {
				ideDetector.region(--start, offset);
			}
			
			if (end < string.length())
				ideDetector.region(offset, end);
			while(end < string.length() && ideDetector.matches()) {
				ideDetector.region(offset, ++end);
			}
			
			start++;
			end--;
			String lhsValue = string.substring(start, offset);;
			String rhsValue = string.substring(offset, end);
			
			IClassNode prefixClass = null;
			ArrayList<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
			
			if (!lhsValue.isEmpty()) {
				addAllVisibleProposals(viewer, start, end, lhsValue, rhsValue, proposals);
			}
			
			HashMap<String, INode> elements = new HashMap<String, INode>();
			if (prefixClass == null)
			for (IClassNode node : enclosingClasses) {
				elements.putAll(node.getChildren());
			}
			
			for (String entry : elements.keySet())
				if (entry.startsWith(lhsValue) && entry.endsWith(rhsValue))
					proposals.add(new IDECompletionProposal(start, end - start, elements.get(entry)));
				
			return proposals.toArray(new ICompletionProposal[] {});	
		}

		return new ICompletionProposal[]{};
	}

	private final void addAllVisibleProposals(ITextViewer viewer, int start, int end,
			String lhsValue, String rhsValue,
			ArrayList<ICompletionProposal> proposals) {
		final IEditorInput editorInput = ((ModelicaSourceViewer) viewer).getEditorInput();
		if (editorInput instanceof FileEditorInput) {
			final IFile file = ((FileEditorInput) editorInput).getFile();
			final IProject project = file.getProject();
			IMosilabProject adapter = (IMosilabProject) project.getAdapter(IModelicaResource.class);
			if (adapter != null) {
				
				if (adapter.getMOSILABEnvironment() != null) 
					for (IModelicaPackage pkg : adapter.getMOSILABEnvironment().getPackages())
						addProposalsForPackage(start, end, lhsValue, rhsValue, proposals, pkg);
				
				for (IMosilabSource source : adapter.getSrcFolders()) {
					addProposalForSourceFolder(start, end, lhsValue, rhsValue, proposals, source);
					for (IResource res : source.getContents())
						if (res instanceof IFile) {
							IFile srcFile = (IFile)res;
							addProposalForFile(start, end, lhsValue, rhsValue, proposals, srcFile);
						}
				}
			}
		}
	}

	private final void addProposalForSourceFolder(int start, int end, String lhsValue,
			String rhsValue, ArrayList<ICompletionProposal> proposals,
			IMosilabSource source) {
		
		for (IModelicaPackage pkg : source.getPackages()) {
			addProposalsForPackage(start, end, lhsValue, rhsValue, proposals, pkg);
		}
		
		for (IResource res : source.getContents())
			if (res instanceof IFile) {
				IFile srcFile = (IFile)res;
				addProposalForFile(start, end, lhsValue, rhsValue, proposals, srcFile);
			}		
	}

	private void addProposalsForPackage(int start, int end, String lhsValue,
			String rhsValue, ArrayList<ICompletionProposal> proposals,
			IModelicaPackage pkg) {
		if (pkg.getFullName().startsWith(lhsValue) && pkg.getFullName().endsWith(rhsValue))
			proposals.add(new PartialNameCompletionProposal(start, end - start, pkg));
	}

	private final void addProposalForFile(int start, int end, String lhsValue,
			String rhsValue, ArrayList<ICompletionProposal> proposals,
			IFile srcFile) {
		Model modelForFile = ModelRepository.getModelForFileUnblocking(srcFile);
		if (modelForFile == null)
			return; //synchronizer not yet done
		
		for (String child : modelForFile.getRootNode().getChildren().keySet())
			if (child.startsWith(lhsValue) && child.endsWith(rhsValue) && modelForFile.getRootNode().getChildren().get(child) instanceof IClassNode)
				proposals.add(new IDECompletionProposal(start, end - start, modelForFile.getRootNode().getChild(child)));
	}

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
