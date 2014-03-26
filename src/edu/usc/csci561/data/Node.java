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

	protected String name;
	protected List<Edge> E;
	int state;

	public Node(String name) {
		this.name = name;
		state = State.UNVIISTED;
		E = new ArrayList<>();
	}

}

interface State {
	int UNVIISTED = 0;
	int VISITING = 1;
	int VISITED = 2;
}