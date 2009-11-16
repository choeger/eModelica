package de.tuberlin.uebb.emodelica.editors.completion;

import org.eclipse.jface.text.AbstractReusableInformationControlCreator;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension3;
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractModelicaCompletionProposal implements ICompletionProposalExtension3 {
	
	private static final class ControlCreator extends AbstractReusableInformationControlCreator {
		         /*
		          * @see org.eclipse.jdt.internal.ui.text.java.hover.AbstractReusableInformationControlCreator#doCreateInformationControl(org.eclipse.swt.widgets.Shell)
		          */
				public IInformationControl doCreateInformationControl(Shell parent) {
		        	 
		        	 final DefaultInformationControl defaultInformationControl = new DefaultInformationControl(parent);
		        	 defaultInformationControl.setStatusText("created by myself!");
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