/**
 * 
 */
package de.tuberlin.uebb.emodelica.views.statistics;

import de.tuberlin.uebb.emodelica.model.Model;
import de.tuberlin.uebb.page.parser.symbols.IAbsy;
import de.tuberlin.uebb.page.parser.symbols.ListToken;

/**
 * @author choeger
 *
 */
public class ModelStatistic {
	private String filename;
	private long token = 0;
	private long nodes = 0;
	private long lists = 0;
	
	public ModelStatistic(Model model) {
		gatherStats(model.getChild());
	}
	
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the token
	 */
	public long getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(long token) {
		this.token = token;
	}

	/**
	 * @return the nodes
	 */
	public long getNodes() {
		return nodes;
	}

	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(long nodes) {
		this.nodes = nodes;
	}

	/**
	 * @return the lists
	 */
	public long getLists() {
		return lists;
	}

	/**
	 * @param lists the lists to set
	 */
	public void setLists(long lists) {
		this.lists = lists;
	}

	private void gatherStats(IAbsy node){
		nodes++;
		if (node instanceof ListToken)
			lists++;
		
		if (node.hasChildren()) {
			for (IAbsy child : node.getChildren())
				gatherStats(child);
		}
	}
}
