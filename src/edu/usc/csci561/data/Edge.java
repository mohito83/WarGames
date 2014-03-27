/**
 * 
 */
package edu.usc.csci561.data;

/**
 * @author mohit aggarwl
 * 
 */
public class Edge {
	protected Node[] nodes;

	public Edge(int size) {
		nodes = new Node[size];
	}

	public Edge(Node a, Node b) {
		nodes = new Node[2];
		nodes[0] = a;
		nodes[1] = b;
	}
}
