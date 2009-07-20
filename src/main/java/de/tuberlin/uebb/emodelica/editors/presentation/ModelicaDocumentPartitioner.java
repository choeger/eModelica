package de.tuberlin.uebb.emodelica.editors.presentation;

import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;

import de.tuberlin.uebb.emodelica.Constants;

/**
 * 
 * @author choeger
 *
 */
class ModelicaPartitionScanner extends RuleBasedPartitionScanner {
	
	public ModelicaPartitionScanner() {
		super();
		setPredicateRules(ModelicaScannerRules.getPartitioningRules());
	}
}

/**
 * 
 * @author choeger
 *
 */
public class ModelicaDocumentPartitioner extends FastPartitioner {
	
	public ModelicaDocumentPartitioner() {
		super(new ModelicaPartitionScanner(),Constants.MODELICA_PART_TYPES);
	}
}
