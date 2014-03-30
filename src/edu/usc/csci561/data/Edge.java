/**
 * 
 */
package edu.usc.csci561.data;

/**
 * @author mohit aggarwl
 * 
 */
public class Edge implements Cloneable {
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

	public Edge clone() {
		Edge e = new Edge(this.a, this.b);
		return e;
	}
}
