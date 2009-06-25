package de.tuberlin.uebb.emodelica.editors;

import org.eclipse.core.filebuffers.IDocumentSetupParticipant;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;

import de.tuberlin.uebb.emodelica.Constants;
import de.tuberlin.uebb.emodelica.editors.presentation.ModelicaDocumentPartitioner;
import de.tuberlin.uebb.emodelica.util.PartitionUtils;

/**
 * this class helps in setting up document partitioning without using a complete IDocumentProvider
 * @author choeger
 *
 */
public class DocumentSetupParticipant implements IDocumentSetupParticipant {

	public void setup(IDocument document) {
	    setupPartitioner(document);
	}

	public static void setupPartitioner(IDocument document) {
	    if (PartitionUtils.getPartitioner(document, Constants.ModelicaDocumentPartitioner) == null)
	        PartitionUtils.setDocumentPartitioning(document, Constants.ModelicaDocumentPartitioner, createMyPartitioner());
		}

	private static IDocumentPartitioner createMyPartitioner() {
	    return new ModelicaDocumentPartitioner();
	}

}

