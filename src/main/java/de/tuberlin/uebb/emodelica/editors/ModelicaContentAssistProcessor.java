package de.tuberlin.uebb.emodelica.editors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension6;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;

import de.tuberlin.uebb.emodelica.Images;
import de.tuberlin.uebb.emodelica.model.Model;
import de.tuberlin.uebb.modelica.im.impl.types.TypeException;
import de.tuberlin.uebb.modelica.im.nodes.IClassNode;
import de.tuberlin.uebb.modelica.im.nodes.INode;
import de.tuberlin.uebb.modelica.im.nodes.IVarDefNode;

public class ModelicaContentAssistProcessor implements IContentAssistProcessor {

	class IDECompletion implements ICompletionProposal,
			ICompletionProposalExtension6 {

		private final int offset;
		private final int length;
		private final INode value;
		private String type;
		private final StyledString styledString;

		public IDECompletion(int offset, int length, INode value) {
			super();
			this.length = length;
			this.offset = offset;
			this.value = value;
			type = getTypeString();

			styledString = createStyledString();
		}

		private StyledString createStyledString() {
			final StyledString styledString = new StyledString();
			styledString.append(value.getName() + type);
			final int l = styledString.length();
			final String classInfo = " - " + value.getParent().getName();
			styledString.append(classInfo);
			styledString.setStyle(l, classInfo.length(), new Styler() {

				@Override
				public void applyStyles(TextStyle textStyle) {
					textStyle.foreground = Display.getDefault().getSystemColor(
							SWT.COLOR_DARK_GRAY);
				}
			});

			return styledString;
		}

		private final String getTypeString() {
			if (value instanceof IVarDefNode)
				return ((IVarDefNode)value).getTypeRef() != null ? " : " + ((IVarDefNode)value).getTypeRef() : "";
			else
				return "";
			
		}

		@Override
		public void apply(IDocument document) {
			try {
				document.replace(offset, length, value.getName());
				} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}

		@Override
		public String getAdditionalProposalInfo() {
			return null;
		}

		@Override
		public IContextInformation getContextInformation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getDisplayString() {
			return styledString.getString();
		}

		@Override
		public Image getImage() {
			return Images.getImageForModel(value);
		}

		@Override
		public Point getSelection(IDocument document) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StyledString getStyledDisplayString() {
			return styledString;
		}

	}

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
			ideDetector.region(start, offset);

			while(start >= 1 && ideDetector.matches()) {
				ideDetector.region(--start, offset);
			}
			
			ideDetector.region(start, end);
			while(end < string.length() && ideDetector.matches()) {
				ideDetector.region(start, ++end);
			}
			
			start++;
			end--;
			
			String lhsValue = string.substring(start, offset);;
			String rhsValue = string.substring(offset, end);
			
			HashMap<String, INode> elements = new HashMap<String, INode>();
			for (IClassNode node : enclosingClasses) {
				elements.putAll(node.getChildren());
			}
			
			ArrayList<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>(elements.size());
			for (String entry : elements.keySet())
				if (entry.startsWith(lhsValue) && entry.endsWith(rhsValue))
					proposals.add(new IDECompletion(start, end - start, elements.get(entry)));
				
			return proposals.toArray(new ICompletionProposal[] {});	
		}

		return new ICompletionProposal[]{};
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
