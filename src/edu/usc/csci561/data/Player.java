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

import edu.usc.csci561.ConfederationPlayer;
import edu.usc.csci561.UnionPlayer;
import edu.usc.csci561.data.Node.State;
import edu.usc.csci561.searchtree.MiniMax;
import edu.usc.csci561.searchtree.SearchNode;

/**
 * @author mohit aggarwl
 * 
 */
public abstract class Player {

	private Color color;
	private FileWriter logWriter;
	private FileWriter movesWriter;

	protected int cutoffLevel;
	protected int task;

	protected Comparator<Node<Action>> nodeComparator = new Comparator<Node<Action>>() {

		@Override
		public int compare(Node<Action> o1, Node<Action> o2) {
			return (int) (o1.getVal().getEval() - o2.getVal().getEval());
		}
	};

	public Player() {
		this(Color.RED, 1, 1);
	}

	public Player(Color c, int cutoff, int task) {
		this.color = c;
		this.task = task;
		if (task == 1 || this instanceof ConfederationPlayer) {
			this.cutoffLevel = 1;
		} else {
			this.cutoffLevel = cutoff;
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return color == Color.RED ? "Union" : "Confederacy";
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

	public void printLogs(String str) {
		try {
			logWriter.write(str);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void printMoves(String str) {
		try {
			movesWriter.write(str);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
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

		MiniMax type = null;
		if (this instanceof UnionPlayer) {
			type = MiniMax.MAX;
		} else {
			type = MiniMax.MIN;
		}

		SearchNode root = buildSearchTree(state, type);

		// print logs only in case of union player
		if (this instanceof UnionPlayer) {
			for (Node<Action> node : root.getAdjacencyList()) {
				printLogs(getLog((SearchNode) node));
			}
		}

		// sort the Actions based on the eval value
		Node<Action> maxAct = Collections.max(root.getAdjacencyList(),
				nodeComparator);

		state.getUpdateCities(maxAct.getVal().getUpdatedCitiesList());
		state.incrementTurn();
		printMoves(getResultLogs(maxAct.getVal()));
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
					Action act = new Action(clonedState, root.getType(),
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
			Action act = new Action(clonedState, root.getType(),
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
		buff.append("----------------------------------------------");
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

	/**
	 * This method builds the search tree.
	 */
	protected SearchNode buildSearchTree(GameState originalState, MiniMax type) {
		Action dummy = new Action(originalState, type, null, 0);
		SearchNode root = new SearchNode(dummy, type);

		Queue<SearchNode> queue = new LinkedList<SearchNode>();
		queue.add(root);
		while (!queue.isEmpty()) {

			SearchNode node = queue.peek();
			resetVisitingStateofNode(node.getAction().getGameState());

			List<City> cities = null;
			if (node.getType() == MiniMax.MAX) {
				cities = node.getAction().getGameState().getUnionCities();
			} else {
				cities = node.getAction().getGameState().getConfederateCities();
			}

			// write the breaking condition
			// 1. if it has reached the cut off depth
			// 2. of if the game has reached it end

			if (node.getDepth() >= cutoffLevel) {
				break;
			}

			performForcedMarch(cities, queue);

			// XXX a hack to ensure that the root node is available for the
			// paratroop drop operation.
			((LinkedList<SearchNode>) queue).addFirst(node);

			performParatroopDrop(queue);

		}

		return root;
	}

	/**
	 * This method generates the log
	 * 
	 * @return
	 */
	public String getLog(SearchNode node) {
		StringBuffer buff = new StringBuffer();
		City destination = node.getAction().getDestination();
		if (destination != null) {
			if (node.getAction().getPlayer() == MiniMax.MAX) {
				buff.append("Union,");
			} else {
				buff.append("Confederacy,");
			}

			if (node.getAction().isForcedMarch()) {
				buff.append("Force March,");
			} else {
				buff.append("Paratroop Drop,");
			}

			buff.append(destination.getVal());
			buff.append(",");
			buff.append(node.getDepth());
			buff.append(",");
			int val = node.getEval();
			if (val == Integer.MAX_VALUE)
				buff.append("Infinity");
			else if (val == Integer.MIN_VALUE)
				buff.append("-Infinity");
			else
				buff.append((double) val);
		} else {
			buff.append("N/A,N/A,N/A,0,-Infinity");
		}
		buff.append(System.getProperty("line.separator"));

		return buff.toString();
	}

	/**
	 * @return the cutoffLevel
	 */
	public int getCutoffLevel() {
		return cutoffLevel;
	}

	/**
	 * @param cutoffLevel
	 *            the cutoffLevel to set
	 */
	public void setCutoffLevel(int cutoffLevel) {
		this.cutoffLevel = cutoffLevel;
	}
}
