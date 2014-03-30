/**
 * 
 */
package edu.usc.csci561.data;

/**
 * @author mohit aggarwl
 * 
 */
public class Edge<T> implements Cloneable {
	protected Node<T> a, b;

	public Edge(Node<T> a, Node<T> b) {
		this.a = a;
		this.b = b;
	}

	public Node<T> getOtherEndofEdge(Node<T> x) {
		if (x == a) {
			return b;
		} else {
			return a;
		}
	}

	public Edge<T> clone() {
		Edge<T> e = new Edge<T>(this.a, this.b);
		return e;
	}
}
