/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.dom;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;

import de.tuberlin.uebb.emodelica.generated.parser.NT_ClassDefSingleKW;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Class_Def_KW;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Class_Definition;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Specifier;
import de.tuberlin.uebb.emodelica.model.Model;
import de.tuberlin.uebb.page.parser.symbols.Absy;
import de.tuberlin.uebb.page.parser.symbols.ListToken;
import de.tuberlin.uebb.page.parser.util.Range;

/**
 * This class is used for views on Modelica AST
 * currently this is a week typed element
 * @author choeger
 *
 */
public class DOMNode {
	
	/**
	 * turn a ListToken into a string representation
	 * @param separator the separator to use
	 * @return a String in the form of Absy.toString() + separator + Absy.toString()...
	 */
	public static String ListTokenToString(ListToken list, String separator) {
		StringBuffer buffer = new StringBuffer();
		for (Absy child : list.getChildren()) {
			buffer.append(child.toString());
			buffer.append(separator);
		}
		
		buffer.delete(buffer.length() - separator.length() - 1, buffer.length());
		return buffer.toString();
	}
	
	protected Absy absy;
	private String value;
	private List<DOMNode> children;
	private DOMNode parent;
	private ImageDescriptor imageDescriptor;
	protected Model parentModel;
	
	public DOMNode(Model parentModel) {
		//invisible
		this.setParentModel(parentModel);
		value=null;
		children=new ArrayList<DOMNode>();
	}
	
	/**
	 * @param value
	 * @param children
	 * @param parent
	 * @param imageDescriptor
	 */
	public DOMNode(String value, DOMNode parent,
			ImageDescriptor imageDescriptor) {
		super();
		this.value = value;
		this.children = new ArrayList<DOMNode>();
		this.parent = parent;
		this.imageDescriptor = imageDescriptor;
	}
	
	/**
	 * add a child
	 */
	public void addChild(DOMNode child) {
		children.add(child);
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the children
	 */
	public List<DOMNode> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(List<DOMNode> children) {
		this.children = children;
	}
	/**
	 * @return the parent
	 */
	public DOMNode getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(DOMNode parent) {
		this.parent = parent;
	}
	/**
	 * @return the imageDescriptor
	 */
	public ImageDescriptor getImageDescriptor() {
		return imageDescriptor;
	}
	/**
	 * @param imageDescriptor the imageDescriptor to set
	 */
	public void setImageDescriptor(ImageDescriptor imageDescriptor) {
		this.imageDescriptor = imageDescriptor;
	}
	
	public String toString() {
		return value;
	}

	/**
	 * @param parentModel the parentModel to set
	 */
	public void setParentModel(Model parentModel) {
		this.parentModel = parentModel;
	}

	/**
	 * @return the parentModel
	 */
	public Model getParentModel() {
		return parentModel;
	}
	
	public int getStartOffset() {
		if (absy == null)
			return 0;
	
		Range range = absy.getRange();
		System.err.println("start Token: " + range.getStartToken());
		return parentModel.getInput().get(range.getStartToken()).getStartOffset();		
	}
	
	public int getEndOffset() {
		if (absy == null)
			return 0;
		
		Range range = absy.getRange();
		System.err.println("end Token: " + range.getEndToken());
		return parentModel.getInput().get(range.getEndToken()).getEndOffset();	
	}

	/**
	 * @param classDef
	 */
	protected void addClassDefDOM(NT_Class_Definition classDef) {
		parentModel.getAllFoldablePositions().add(parentModel.rangeToFoldablePosition(classDef.getRange()));
		
		NT_Class_Def_KW classDefKW = classDef.getNt_class_def_kw();
		if (classDefKW instanceof NT_ClassDefSingleKW) {
			NT_ClassDefSingleKW singleKW = (NT_ClassDefSingleKW) classDefKW;
	
			NT_Specifier nt_specifier = classDef.getNt_specifier();
			if (singleKW.getValue().getValue().equals("package"))
				addChild(new DOMPackage(getParentModel(), nt_specifier,
						this));
			if (singleKW.getValue().getValue().equals("class"))
				addChild(new DOMClass(getParentModel(), nt_specifier,
						this));
			if (singleKW.getValue().getValue().equals("record"))
				addChild(new DOMRecord(getParentModel(), nt_specifier,
						this));
			if (singleKW.getValue().getValue().equals("model"))
				addChild(new DOMModel(getParentModel(), nt_specifier,
						this));
			if (singleKW.getValue().getValue().equals("function"))
				addChild(new DOMFunction(getParentModel(), nt_specifier,
						this));
			if (singleKW.getValue().getValue().equals("type"))
				addChild(new DOMType(getParentModel(), nt_specifier,
						this));
			if (singleKW.getValue().getValue().equals("block"))
				addChild(new DOMBlock(getParentModel(), nt_specifier,
						this));
		}
	}
}
