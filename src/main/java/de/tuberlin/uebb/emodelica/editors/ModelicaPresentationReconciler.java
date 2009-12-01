/**
 * 
 */
package de.tuberlin.uebb.emodelica.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.RuleBasedScanner;

import de.tuberlin.uebb.emodelica.Constants;
import de.tuberlin.uebb.emodelica.editors.presentation.ModelicaScannerRules;

/**
 * @author choeger
 *
 */
public class ModelicaPresentationReconciler extends PresentationReconciler {
	
	public static ModelicaPresentationReconciler getInstance() {
		//TODO: test Singleton Pattern
		ModelicaPresentationReconciler recon = new ModelicaPresentationReconciler();
		recon.setDocumentPartitioning(Constants.MODELICA_DOCUMENT_PARTITIONING);
		return recon;
	}

	private IDocument fLastDocument;
	
	/**
	 * Constructs a "repair description" for the given damage and returns
	 * this description as a text presentation.
	 * <p>
	 * NOTE: Should not be used if this reconciler is installed on a viewer.
	 * </p>
	 *
	 * @param damage the damage to be repaired
	 * @param document the document whose presentation must be repaired
	 * @return the presentation repair description as text presentation
	 */
	public TextPresentation createRepairDescription(IRegion damage, IDocument document) {
		if (document != fLastDocument) {
			setDocumentToDamagers(document);
			setDocumentToRepairers(document);
			fLastDocument= document;
		}
		return createPresentation(damage, document);
	}	
	
	private ModelicaPresentationReconciler() {
		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getSyntaxScanner());
		this.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		this.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		DefaultDamagerRepairer drString = new DefaultDamagerRepairer(getStringScanner());
		this.setDamager(drString, Constants.PART_MODELICA_STRING);
		this.setRepairer(drString, Constants.PART_MODELICA_STRING);

		DefaultDamagerRepairer drMultiComment = new DefaultDamagerRepairer(getCommentScanner());
		this.setDamager(drMultiComment, Constants.PART_MODELICA_MULTI_LINE_COMMENT);
		this.setRepairer(drMultiComment, Constants.PART_MODELICA_MULTI_LINE_COMMENT);
		
		DefaultDamagerRepairer drSingleComment = new DefaultDamagerRepairer(getCommentScanner());
		this.setDamager(drSingleComment, Constants.PART_MODELICA_SINGLE_LINE_COMMENT);
		this.setRepairer(drSingleComment, Constants.PART_MODELICA_SINGLE_LINE_COMMENT);	
	}
	
	private ITokenScanner getStringScanner() {
		RuleBasedScanner scanner = new RuleBasedScanner();
		scanner.setDefaultReturnToken(ModelicaScannerRules.getStringDefaultToken());
		scanner.setRules(ModelicaScannerRules.getStringPartScannerRules());
		return scanner;
	}

	private ITokenScanner getSyntaxScanner() {
		RuleBasedScanner scanner = new RuleBasedScanner();
		scanner.setRules(ModelicaScannerRules.getSyntaxScannerRules());
		return scanner;
	}
	
	private ITokenScanner getCommentScanner() {
		RuleBasedScanner scanner = new RuleBasedScanner();
		scanner.setDefaultReturnToken(ModelicaScannerRules.getCommentDefaultToken());
		scanner.setRules(ModelicaScannerRules.getCommentPartScannerRules());
		return scanner;
	}
}
