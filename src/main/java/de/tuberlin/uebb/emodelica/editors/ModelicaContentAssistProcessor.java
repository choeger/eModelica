package de.tuberlin.uebb.emodelica.editors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import de.tuberlin.uebb.emodelica.model.Model;
import de.tuberlin.uebb.modelica.im.nodes.IClassNode;
import de.tuberlin.uebb.modelica.im.nodes.INode;
import de.tuberlin.uebb.page.grammar.symbols.Terminal;

public class ModelicaContentAssistProcessor implements IContentAssistProcessor {

	class IDECompletion implements ICompletionProposal {

		private int offset;
		private INode value;
		
		
		
		public IDECompletion(int offset, INode value) {
			super();
			this.offset = offset;
			this.value = value;
		}

		@Override
		public void apply(IDocument document) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getAdditionalProposalInfo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IContextInformation getContextInformation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getDisplayString() {
			return value.getName();
		}

		@Override
		public Image getImage() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point getSelection(IDocument document) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {

		if (viewer instanceof ModelicaSourceViewer) {
			Model model = ((ModelicaSourceViewer) viewer).getModelManager().getModel();
			
			Terminal last = model.getTerminalBefore(offset);
			
			if (last == null) {
				List<IClassNode> enclosing = new ArrayList<IClassNode>();
				model.getEnclosingLexicalNodes(offset, model.getRootNode(), enclosing);
				HashMap<String, INode> elements = new HashMap<String, INode>();
				for (IClassNode node : enclosing) {
					elements.putAll(node.getChildren());
				}
				
				ICompletionProposal[] proposals = new ICompletionProposal[elements.size()];
				int i = 0;
				for (String id : elements.keySet()) {
					proposals[i++] = new IDECompletion(offset, elements.get(id));
				}
					
				return proposals;
			}
		}
		return null;
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
