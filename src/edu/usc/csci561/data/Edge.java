/**
 * 
 */
package edu.usc.csci561.data;

/**
 * @author mohit aggarwl
 * 
 */
public class Edge {
	protected Node a, b;

	public Edge(Node a, Node b) {
		this.a = a;
		this.b = b;
	}

	public Node getOtherEndofEdge(Node x) {
		if (x == a) {
			return b;
		} else {
			return a;
		}
	}
}
