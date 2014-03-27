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
public class Node {

	public enum State {
		UNVIISTED, VISITING, VISITED;
	}

	protected String name;
	protected List<Edge> E;
	State state;

	public Node() {
		this(null);
	}

	public Node(String name) {
		this.name = name;
		state = State.UNVIISTED;
		E = new ArrayList<Edge>();
	}

	public Node(String name, Edge e) {
		this(name);
		this.E.add(e);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	public void addEdge(Edge e) {
		this.E.add(e);
	}

	/**
	 * 
	 * @return
	 */
	public List<Edge> getEdges() {
		return this.E;
	}

	public void addEdge(Node n){
		Edge e = new Edge(this,n);
		this.E.add(e);
	}
}
