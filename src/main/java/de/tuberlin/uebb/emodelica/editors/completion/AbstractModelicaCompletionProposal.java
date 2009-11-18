package de.tuberlin.uebb.emodelica.editors.completion;

import org.eclipse.jface.text.AbstractReusableInformationControlCreator;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension3;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import de.tuberlin.uebb.emodelica.util.BrowserInformationControl;

public abstract class AbstractModelicaCompletionProposal implements
		ICompletionProposalExtension3 {
		
	private static final class ControlCreator extends
			AbstractReusableInformationControlCreator {

		/*
		 * @seeorg.eclipse.jdt.internal.ui.text.java.hover.
		 * AbstractReusableInformationControlCreator
		 * #doCreateInformationControl(org.eclipse.swt.widgets.Shell)
		 */
		public IInformationControl doCreateInformationControl(Shell parent) {
			// presenter.
			final IInformationControl defaultInformationControl = new BrowserInformationControl(parent, SWT.SHELL_TRIM | SWT.TOOL ,SWT.NONE);
			return defaultInformationControl;
		}
	}

	private static final ControlCreator controlCreator = new ControlCreator();
	
	public AbstractModelicaCompletionProposal() {
		super();
	}

	@Override
	public IInformationControlCreator getInformationControlCreator() {
		return controlCreator;
	}

}