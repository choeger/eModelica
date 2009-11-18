package de.tuberlin.uebb.emodelica.editors.completion;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension3;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension5;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension6;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;

import de.tuberlin.uebb.emodelica.Images;
import de.tuberlin.uebb.modelica.im.ICommentable;
import de.tuberlin.uebb.modelica.im.impl.nodes.DocumentationAnnotation;
import de.tuberlin.uebb.modelica.im.nodes.IAnnotable;
import de.tuberlin.uebb.modelica.im.nodes.IAnnotation;
import de.tuberlin.uebb.modelica.im.nodes.INode;
import de.tuberlin.uebb.modelica.im.nodes.IVarDefNode;

public class IDECompletionProposal extends AbstractModelicaCompletionProposal implements ICompletionProposal,
		ICompletionProposalExtension5, ICompletionProposalExtension3,
		ICompletionProposalExtension6 {

	private final int offset;
	private final int length;
	private final INode value;
	private String type;
	private final StyledString styledString;
	private IContextInformation contextInformation;

	public IDECompletionProposal(int offset, int length, INode value) {
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
			return ((IVarDefNode) value).getTypeRef() != null ? " : "
					+ ((IVarDefNode) value).getTypeRef() : "";
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
		return contextInformation;
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

	@Override
	public Object getAdditionalProposalInfo(IProgressMonitor monitor) {
		String htmlString = "<h>" + value.toString() + "</h>";
		
		if (value instanceof IAnnotable) {
			final IAnnotation annotation = ((IAnnotable) value).getAnnotationForName("Documentation");
			if (annotation != null && annotation instanceof DocumentationAnnotation)
				htmlString += "<br>" + ((DocumentationAnnotation)annotation).getHTMLComment();
			
		}
		return htmlString;
	}

	@Override
	public int getPrefixCompletionStart(IDocument document, int completionOffset) {
		return offset;
	}

	@Override
	public CharSequence getPrefixCompletionText(IDocument document,
			int completionOffset) {
		return value.getName();
	}

}
