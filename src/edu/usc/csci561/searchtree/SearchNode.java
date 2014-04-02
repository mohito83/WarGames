/**
 * 
 */
package edu.usc.csci561.searchtree;

import edu.usc.csci561.UnionPlayer;
import edu.usc.csci561.data.Action;
import edu.usc.csci561.data.Node;

/**
 * @author mohit aggarwl
 * 
 */
public class SearchNode extends Node<Action> {

	private MiniMax type;
	private int depth;
	private int eval;

	public SearchNode(Action a) {
		super(a);
	}

	public SearchNode(Action a, MiniMax type) {
		super(a);
		this.type = type;
	}

	public Action getAction() {
		return this.getVal();
	}

	/**
	 * @return the type
	 */
	public MiniMax getType() {
		return type;
	}

	/**
	 * This method gets the depth of this node in the search tree
	 * 
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * This method sets the depth of the node in the search tree.
	 * 
	 * @param depth
	 *            the depth to set
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(MiniMax type) {
		this.type = type;
	}

	/**
	 * @return the eval
	 */
	public int getEval() {
		return eval;
	}

	/**
	 * @param eval
	 *            the eval to set
	 */
	public void setEval(int eval) {
		this.eval = eval;
	}

	/**
	 * This method generates the Log string
	 * 
	 * @return
	 */
	public String getLog() {
		StringBuffer buff = new StringBuffer();
		if (this.getVal().getPlayer() instanceof UnionPlayer) {
			buff.append("Union,");
		} else {
			buff.append("Confedracy,");
		}

		if (this.getAction().isForcedMarch()) {
			buff.append("Force March,");
		} else {
			buff.append("Paratroop Drop,");
		}

		buff.append(this.getAction().getDestination().getVal());
		buff.append(",");
		buff.append(depth);
		buff.append(",");
		buff.append(eval);
		buff.append(System.getProperty("line.separator"));

		return buff.toString();
	}

	// TODO this method is only for debugging.. remove all such toString() from
	// the application before submission, as it causes performace issue.
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("[");
		buff.append(getVal().getDepth());
		buff.append(",");
		if (getVal().getDestination() != null) {
			buff.append(getVal().getDestination().getVal());
		} else {
			buff.append("ROOT");
		}
		buff.append(",");
		if (getVal().isForcedMarch()) {
			buff.append("Force March,");
		} else {
			buff.append("Paratroop Drop,");
		}
		buff.append(getVal().getEval());
		//buff.append(getAdjacencyList());
		buff.append("]");
		return buff.toString();
	}
}