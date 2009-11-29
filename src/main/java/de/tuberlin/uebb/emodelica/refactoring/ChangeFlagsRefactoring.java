/**
 * 
 */
package de.tuberlin.uebb.emodelica.refactoring;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.DocumentChange;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.ModelRepository;
import de.tuberlin.uebb.emodelica.model.Model;
import de.tuberlin.uebb.modelica.im.ILocation;
import de.tuberlin.uebb.modelica.im.StringConstants;
import de.tuberlin.uebb.modelica.im.nodes.ENodeFlags;
import de.tuberlin.uebb.modelica.im.nodes.INode;
import de.tuberlin.uebb.modelica.im.nodes.IVarDefNode;

/**
 * @author choeger
 *
 */
public class ChangeFlagsRefactoring extends ModelicaRefactoring {

	private ILocation fLocation;
	private Set<ENodeFlags> flags;
	private Model modelForNode;
	
	/**
	 * 
	 */
	public ChangeFlagsRefactoring() {
		this.flags = new HashSet<ENodeFlags>();
	}

	public ILocation getLocation() {
		return fLocation;
	}

	public void setLocation(ILocation fLocation) {
		this.fLocation = fLocation;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#checkFinalConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		return checkCommonConds();
	}

	private RefactoringStatus checkCommonConds() {
		if (fLocation instanceof IVarDefNode) {
			flags = ((IVarDefNode)fLocation).getFlags();
			
			if (modelForNode == null) {
				modelForNode = ModelRepository.getModelForNode((INode)fLocation);
				if (modelForNode == null || modelForNode.getDocument() == null)
					return RefactoringStatus.create(new Status(IStatus.ERROR, EModelicaPlugin.PLUGIN_ID, "Could not open the selected Document"));
			}
			
			return RefactoringStatus.create(new Status(IStatus.OK, EModelicaPlugin.PLUGIN_ID, ""));
		} else
			return RefactoringStatus.create(new Status(IStatus.ERROR, EModelicaPlugin.PLUGIN_ID, "The selected location contains no visibility informations."));
	}

	@Override
	public Model getModel() {
		return modelForNode;
	}

	@Override
	public void setModel(Model modelForNode) {
		this.modelForNode = modelForNode;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#checkInitialConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		return checkCommonConds();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#createChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {		
		
		if (fLocation instanceof IVarDefNode) {
			IVarDefNode varDef = (IVarDefNode)fLocation;
			final int end = varDef.getTypeRef().getTypeIdentifier().getStartOffset();
			final int start = varDef.getStartOffset();
			StringBuilder builder = new StringBuilder();
			for (ENodeFlags flag : flags) {
				if (flag == ENodeFlags.PROTECTED)
					builder.insert(0, "protected ");
				else {
 					builder.append(StringConstants.nodeFlagToString.get(flag));
 					builder.append(" ");
				}
			}
			
			SynchronizedDocumentChange documentChange = new SynchronizedDocumentChange("set flags", modelForNode.getDocument());
			TextEdit edit = new ReplaceEdit(start, end - start, builder.toString());
			documentChange.setEdit(edit);
			
			return documentChange;
		}
		
		return null;
	}

	public Set<ENodeFlags> getFlags() {
		return flags;
	}

	public void setFlags(Set<ENodeFlags> flags) {
		this.flags = flags;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#getName()
	 */
	@Override
	public String getName() {
		return "Change visibility";
	}

}
