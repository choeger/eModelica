/**
 * 
 */
package de.tuberlin.uebb.emodelica.editors.completion;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension5;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension6;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import de.tuberlin.uebb.emodelica.Images;
import de.tuberlin.uebb.emodelica.model.project.IModelicaPackage;
import de.tuberlin.uebb.modelica.im.nodes.IClassNode;

/**
 * @author choeger
 * 
 */
public class PartialNameCompletionProposal extends AbstractModelicaCompletionProposal implements ICompletionProposal,
		ICompletionProposalExtension5,
		ICompletionProposalExtension6 {

	private final int offset;
	private final int length;
	private final IClassNode value;
	private final StyledString styledString;
	private IContextInformation contextInformation;

	public PartialNameCompletionProposal(int offset, int length, IClassNode value) {
		super();
		this.length = length;
		this.offset = offset;
		this.value = value;

		styledString = createStyledString();
	}

	private StyledString createStyledString() {
		final StyledString styledString = new StyledString();
		styledString.append(value.getFullComponentName());

		return styledString;
	}

	@Override
	public void apply(IDocument document) {
		try {
			document.replace(offset, length, value.getFullComponentName());
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
		return Images.PKG_IMAGE_DESCRIPTOR.createImage();
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
		return getAnnotation(value);
	}

	@Override
	public int getPrefixCompletionStart(IDocument document, int completionOffset) {
		return offset;
	}

	@Override
	public CharSequence getPrefixCompletionText(IDocument document,
			int completionOffset) {
		return value.getFullComponentName();
	}

}
