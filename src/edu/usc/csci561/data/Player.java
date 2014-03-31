/**
 * 
 */
package edu.usc.csci561.data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.usc.csci561.UnionPlayer;
import edu.usc.csci561.data.Node.State;
import edu.usc.csci561.searchtree.MiniMax;
import edu.usc.csci561.searchtree.SearchNode;

/**
 * @author mohit aggarwl
 * 
 */
public abstract class Player {

	protected String name;
	private Color color;
	private FileWriter logWriter;
	private FileWriter movesWriter;

	protected Comparator<SearchNode> nodeComparator = new Comparator<SearchNode>() {

		@Override
		public int compare(SearchNode o1, SearchNode o2) {
			return (int) (o1.getAction().getEval() - o2.getAction().getEval());
		}
	};

	public Player() {
		this(Color.RED);
	}

	public Player(Color c) {
		this.color = c;
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
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	public abstract void nextMove();

	public void printLogs(String str) throws IOException {
		logWriter.write(str);
	}

	public void printMoves(String str) throws IOException {
		movesWriter.write(str);
	}

	/**
	 * @return the logWriter
	 */
	public FileWriter getLogWriter() {
		return logWriter;
	}

	/**
	 * @param logWriter
	 *            the logWriter to set
	 */
	public void setLogWriter(FileWriter logWriter) {
		this.logWriter = logWriter;
	}

	/**
	 * @return the movesWriter
	 */
	public FileWriter getMovesWriter() {
		return movesWriter;
	}

	/**
	 * @param movesWriter
	 *            the movesWriter to set
	 */
	public void setMovesWriter(FileWriter movesWriter) {
		this.movesWriter = movesWriter;
	}

	protected void greedyEvaluation() {
		GameState state = GameState.getInstance();
		resetVisitingStateofNode(state);

		List<City> cities = null;
		if (this instanceof UnionPlayer) {
			cities = state.getUnionCities();
		} else {
			cities = state.getConfederateCities();
		}

		Action dummy = new Action(state, this, null, 0);
		SearchNode root = new SearchNode(dummy, MiniMax.MAX);

		Queue<SearchNode> queue = new LinkedList<SearchNode>();
		queue.add(root);

		// for evaluating the forced march strategy
		performForcedMarch(cities, queue);

		// XXX a hack to ensure that the root node is available for the
		// paratroop drop operation.
		((LinkedList<SearchNode>) queue).addFirst(root);

		// evaluate the paratroop drop startegy
		performParatroopDrop(queue);

		// sort the Actions based on the eval value
		SearchNode maxAct = Collections.max(queue, nodeComparator);

		state.getUpdateCities(maxAct.getAction().getUpdatedCitiesList());
		state.incrementTurn();
		try {
			printMoves(getResultLogs(maxAct.getAction()));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 
	 * @param cities
	 * @param state
	 * @param root
	 * @param queue
	 */
	protected void performForcedMarch(List<City> cities, Queue<SearchNode> queue) {
		SearchNode root = queue.remove();
		GameState state = root.getAction().getGameState();
		for (City c : cities) {
			List<Node<String>> adjList = c.getAdjacencyList();
			for (Node<String> e : adjList) {
				City x = (City) e;
				if (x.getOccupation() == Occupation.NEUTRAL
						&& x.getState() == State.UNVIISTED) {
					GameState clonedState = state.clone();
					Action act = new Action(clonedState, this,
							clonedState.getCityForName(x.getVal()),
							root.getDepth() + 1);
					act.performForcedMarch();
					act.eval();
					SearchNode node = new SearchNode(act);
					if (root.getType() == MiniMax.MAX) {
						node.setType(MiniMax.MIN);
					} else {
						node.setType(MiniMax.MAX);
					}
					node.setDepth(root.getDepth() + 1);
					root.addEdge(node);
					queue.add(node);
					try {
						printLogs(act.getLog());
					} catch (IOException e1) {
						System.out.println(e1.getMessage());
					}
					x.setState(State.VISITED);
				}
			}
		}
	}

	/**
	 * 
	 * @param state
	 * @param root
	 * @param queue
	 */
	protected void performParatroopDrop(Queue<SearchNode> queue) {
		SearchNode root = queue.remove();
		GameState state = root.getAction().getGameState();
		List<City> cities = state.getNeutralCities();
		for (City c : cities) {
			GameState clonedState = state.clone();
			Action act = new Action(clonedState, this,
					clonedState.getCityForName(c.getVal()), root.getDepth() + 1);
			act.performParatroopDrop();
			act.eval();
			SearchNode node = new SearchNode(act);
			if (root.getType() == MiniMax.MAX) {
				node.setType(MiniMax.MIN);
			} else {
				node.setType(MiniMax.MAX);
			}
			node.setDepth(root.getDepth() + 1);
			root.addEdge(node);
			queue.add(node);
			try {
				printLogs(act.getLog());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	protected String getResultLogs(Action maxAct) {
		GameState state = GameState.getInstance();
		StringBuffer buff = new StringBuffer();
		buff.append("TURN = " + state.getTurn());
		buff.append(System.getProperty("line.separator"));
		buff.append("Player = " + this.getName());
		buff.append(System.getProperty("line.separator"));
		buff.append("Action = ");
		buff.append(maxAct.isForcedMarch() ? "Force March" : "Paratroop Drop");
		buff.append(System.getProperty("line.separator"));
		buff.append("Destination = " + maxAct.getDestination().getVal());
		buff.append(System.getProperty("line.separator"));
		buff.append("Union,{");
		int i = 0;
		double sum = 0.0;
		for (City c : state.getUnionCities()) {
			i++;
			sum += c.getResourceValue();
			buff.append(c.getVal());
			if (i < state.getUnionCities().size()) {
				buff.append(",");
			}
		}
		buff.append("},");
		buff.append(sum);

		buff.append(System.getProperty("line.separator"));
		buff.append("Confederacy,{");
		i = 0;
		sum = 0.0;
		for (City c : state.getConfederateCities()) {
			i++;
			sum += c.getResourceValue();
			buff.append(c.getVal());
			if (i < state.getConfederateCities().size()) {
				buff.append(",");
			}
		}
		buff.append("},");
		buff.append(sum);
		buff.append(System.getProperty("line.separator"));
		buff.append("--------------------------------------------------------------");
		buff.append(System.getProperty("line.separator"));

		return buff.toString();
	}

	/**
	 * resets the state information of the nodes.
	 * 
	 * @param state
	 */
	protected void resetVisitingStateofNode(GameState state) {
		List<City> cities = state.getAllCities();
		for (City c : cities) {
			c.setState(State.UNVIISTED);
		}
	}
}
