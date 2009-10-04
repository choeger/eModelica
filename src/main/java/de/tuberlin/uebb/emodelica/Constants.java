/**
 * 
 */
package de.tuberlin.uebb.emodelica;

/**
 * @author choeger
 *
 */
public class Constants {
	/**
	 * Modelica Document Partitioner, this should fit the 3.0 API for document partitioning
	 */
	public static final String ModelicaDocumentPartitioner = "de.tuberlin.uebb.emodelica.editors.presentation.ModelicaDocumentPartitioner";

	/**
	 * Modelica Document partion types
	 */
	public static final String PART_MODELICA_MULTI_LINE_COMMENT = "DE.TU_BERLIN.UEBB.EMODELICA.MULTI_LINE_COMMENT_PARTITION";
	public static final String PART_MODELICA_SINGLE_LINE_COMMENT = "DE.TU_BERLIN.UEBB.EMODELICA.SINGLE_LINE_COMMENT_PARTITION";
	public static final String PART_MODELICA_STRING = "DE.TU_BERLIN.UEBB.EMODELICA.STRING_PARTITION";
	public static final String[] MODELICA_PART_TYPES = {PART_MODELICA_MULTI_LINE_COMMENT,PART_MODELICA_SINGLE_LINE_COMMENT, PART_MODELICA_STRING};

	public static final String XML_MOSILAB_SETTINGS_XSD = "/xml/schema/mosilabSettings.xsd";
}
