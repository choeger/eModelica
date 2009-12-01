/**
 * 
 */
package de.tuberlin.uebb.emodelica.util;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPartitioningException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.ITypedRegion;

import de.tuberlin.uebb.emodelica.Constants;

/**
 * @author choeger
 * 
 */
public class PartitionUtils {

	public static ITypedRegion getPartition(IDocument document, String partitionType, int offset, boolean preferOpenPartition) {
		ITypedRegion region = null;
		try {
			if (document instanceof IDocumentExtension3) {
				IDocumentExtension3 extension = (IDocumentExtension3) document;
				try {
					region = extension.getPartition(Constants.MODELICA_DOCUMENT_PARTITIONING, offset, true);
				} catch (BadPartitioningException e) {
					// Log the error.
				}
			}
		} catch (BadLocationException e) {
			// Log the error.
		}
		return region;
	}

	public static void setDocumentPartitioning(IDocument document, String partitionType, IDocumentPartitioner partitioner) {
		// Setting the partitioner will trigger a partitionChanged
		// listener that will attempt to use the partitioner to
		// initialize the document's partitions. Therefore, need
		// to connect first.
		partitioner.connect(document);
		if (document instanceof IDocumentExtension3) {
			IDocumentExtension3 extension3= (IDocumentExtension3) document;
			extension3.setDocumentPartitioner(partitionType, partitioner);
		} 
	}

	public static IDocumentPartitioner getPartitioner(IDocument document, String partitionType) {
		IDocumentPartitioner result = null;
		if (document instanceof IDocumentExtension3) {
			IDocumentExtension3 extension = (IDocumentExtension3) document;
			result = extension.getDocumentPartitioner(partitionType);
		} 
		return result;
	}
}

