/**
 * 
 */
package edu.usc.csci561.searchtree;

import edu.usc.csci561.data.Action;
import edu.usc.csci561.data.Node;

/**
 * @author mohit aggarwl
 * 
 */
public class SearchNode extends Node<Action> {

	private MiniMax type;
	private int depth;

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

}
