/**
 * 
 */
package edu.usc.csci561.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mohit aggarwl
 * 
 */
public class Node<T> {

	public enum State {
		UNVIISTED, VISITING, VISITED;
	}

	protected T val;
	protected List<Edge<T>> E;
	State state;

	public Node() {
		this(null);
	}

	public Node(T name) {
		this.val = name;
		state = State.UNVIISTED;
		E = new ArrayList<Edge<T>>();
	}

	public Node(T name, Edge<T> e) {
		this(name);
		this.E.add(e);
	}

	/**
	 * @return the name
	 */
	public T getVal() {
		return val;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setVal(T name) {
		this.val = name;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * 
	 * @param e
	 */
	public void addEdge(Edge<T> e) {
		this.E.add(e);
	}

	/**
	 * 
	 * @return
	 */
	public List<Edge<T>> getEdges() {
		return this.E;
	}

	/**
	 * This method calculates and return the adjacency list of this node.
	 * 
	 * @return
	 */
	public List<Node<T>> getAdjacencyList() {
		List<Node<T>> adjList = new ArrayList<Node<T>>();
		for (Edge<T> e : E) {
			adjList.add(e.getOtherEndofEdge(this));
		}
		return adjList;
	}

	public void addEdge(Node<T> n) {
		Edge<T> e = new Edge<T>(this, n);
		this.E.add(e);
	}
}
