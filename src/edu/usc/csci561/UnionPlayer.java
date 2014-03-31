/**
 * 
 */
package edu.usc.csci561;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import edu.usc.csci561.data.Action;
import edu.usc.csci561.data.City;
import edu.usc.csci561.data.Color;
import edu.usc.csci561.data.GameState;
import edu.usc.csci561.data.Player;
import edu.usc.csci561.searchtree.MiniMax;
import edu.usc.csci561.searchtree.SearchNode;

/**
 * @author mohit aggarwl
 * 
 */
public class UnionPlayer extends Player {

	public enum SearchAlgo {
		GREEDY(1), MINIMAX(2), ALPHA_BETA_PRUNNING(3);
		int val;

		private SearchAlgo(int val) {
			this.val = val;
		}

		public int getValue() {
			return this.val;
		}

	};

	private int cutoffLevel;
	private SearchAlgo search;

	public UnionPlayer(Color red) {
		super(red);
		name = "Union";
	}

	public UnionPlayer(Color c, int cutOff, SearchAlgo search) {
		this(c);
		this.cutoffLevel = cutOff;
		this.search = search;
	}

	@Override
	public void nextMove() {
		System.out.println("Union Player");
		switch (search.getValue()) {
		case 1:
			greedyEvaluation();
			break;
		case 2:
			minimaxEvaluation();
			break;
		case 3:
			alphaBetaPruningEvaluation();
			break;

		}
	}

	/**
	 * This method generates a search tree with a cutoff depth as defined in the
	 * class instance and perform an Minimax evaluation with alpha beta pruning
	 * algorithm
	 */
	private void alphaBetaPruningEvaluation() {
		SearchNode root = buildSearchTree();
	}

	/**
	 * This method generates a search tree with a cutoff depth as defined in the
	 * class instance and perform an Minimax evaluation algorithm
	 */
	private void minimaxEvaluation() {
		SearchNode root = buildSearchTree();
		Stack<SearchNode> stack = new Stack<SearchNode>();
		stack.add(root);

		while (!stack.isEmpty()) {
			SearchNode n = stack.pop();
		}
	}

	/**
	 * This method builds the search tree.
	 */
	private SearchNode buildSearchTree() {
		GameState originalState = GameState.getInstance();
		resetVisitingStateofNode(originalState);

		List<City> cities = originalState.getUnionCities();

		Action dummy = new Action(originalState, this, null, 0);
		SearchNode root = new SearchNode(dummy, MiniMax.MAX);

		Queue<SearchNode> queue = new LinkedList<SearchNode>();
		queue.add(root);
		while (!queue.isEmpty()) {

			SearchNode node = queue.peek();

			// write the breaking condition
			// 1. if it has reached the cut off depth
			// 2. of if the game has reached it end
			if (node.getDepth() == cutoffLevel
					|| node.getAction().getGameState().isNoMoreMoves()) {
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

	/**
	 * @return the search
	 */
	public SearchAlgo getSearch() {
		return search;
	}

	/**
	 * @param search
	 *            the search to set
	 */
	public void setSearch(int search) {
		switch (search) {
		case 1:
			this.search = SearchAlgo.GREEDY;
			break;
		case 2:
			this.search = SearchAlgo.MINIMAX;
			break;
		case 3:
			this.search = SearchAlgo.ALPHA_BETA_PRUNNING;
			break;
		}
	}

}
