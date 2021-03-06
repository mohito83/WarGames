/**
 * 
 */
package edu.usc.csci561;

import java.util.List;

import edu.usc.csci561.data.Action;
import edu.usc.csci561.data.Color;
import edu.usc.csci561.data.GameState;
import edu.usc.csci561.data.Node;
import edu.usc.csci561.data.Player;
import edu.usc.csci561.searchtree.MiniMax;
import edu.usc.csci561.searchtree.SearchNode;

/**
 * @author mohit aggarwl
 * 
 */
public class UnionPlayer extends Player {

	public UnionPlayer(Color c, int cutOff, int task) {
		super(c, cutOff, task);
	}

	@Override
	public void nextMove() {
		System.out.println("Union Player");
		GameState state = GameState.getInstance();
		switch (this.task) {
		case 1:
			greedyEvaluation();
			break;
		case 2:
			if (state.getNeutralCities().size() > 2)
				minimaxEvaluation();
			else
				greedyEvaluation();
			break;
		case 3:
			if (state.getNeutralCities().size() > 2)
				alphaBetaPruningEvaluation();
			else
				greedyEvaluation();
			break;

		}
	}

	/**
	 * This method generates a search tree with a cutoff depth as defined in the
	 * class instance and perform an Minimax evaluation with alpha beta pruning
	 * algorithm
	 */
	private void alphaBetaPruningEvaluation() {
		GameState state = GameState.getInstance();
		SearchNode root = buildSearchTree(state, MiniMax.MAX);
		int val = maxOp(root, new Integer(Integer.MIN_VALUE), new Integer(
				Integer.MAX_VALUE));

		Action action = null;
		for (Node<Action> n : root.getAdjacencyList()) {
			if (((SearchNode) n).getEval() == val) {
				action = n.getVal();
				break;
			}
		}

		// update the global GameState instance with the next step game state
		state.getUpdateCities(action.getUpdatedCitiesList());
		state.incrementTurn();

		// print the moves
		printMoves(getResultLogs(action));
	}

	/**
	 * This method generates a search tree with a cutoff depth as defined in the
	 * class instance and perform an Minimax evaluation algorithm
	 */
	private void minimaxEvaluation() {
		GameState state = GameState.getInstance();
		SearchNode root = buildSearchTree(state, MiniMax.MAX);
		int val = maxOp(root);
		printLogs(getLog(root));
		Action action = null;
		for (Node<Action> n : root.getAdjacencyList()) {
			if (((SearchNode) n).getEval() == val) {
				action = n.getVal();
				break;
			}
		}

		// update the global GameState instance with the next step game state
		state.getUpdateCities(action.getUpdatedCitiesList());
		state.incrementTurn();

		// print the moves
		printMoves(getResultLogs(action));
	}

	/**
	 * Min operation for Minimax algorithm
	 * 
	 * @param root
	 * @return
	 */
	private int minOp(SearchNode root) {
		if (root.getAdjacencyList().size() == 0) {
			root.getAction().eval();
			int eval = (int) root.getAction().getEval();
			root.setEval(eval);
			return eval;
		}

		root.setEval(Integer.MAX_VALUE);
		printLogs(getLog(root));
		List<Node<Action>> adjList = root.getAdjacencyList();
		int i = 1;
		for (Node<Action> n : adjList) {
			int max = maxOp((SearchNode) n);
			printLogs(getLog((SearchNode) n));
			root.setEval(Math.min(root.getEval(), max));
			if (i < adjList.size())
				printLogs(getLog(root));
			i++;
		}
		return root.getEval();
	}

	/**
	 * Max op for minimax algorithm
	 * 
	 * @param root
	 * @return
	 */
	private int maxOp(SearchNode root) {
		if (root.getAdjacencyList().size() == 0) {
			root.getAction().eval();
			int eval = (int) root.getAction().getEval();
			root.setEval(eval);
			return eval;
		}

		root.setEval(Integer.MIN_VALUE);
		printLogs(getLog(root));
		List<Node<Action>> adjList = root.getAdjacencyList();
		int i = 1;
		for (Node<Action> n : adjList) {
			int min = minOp((SearchNode) n);
			printLogs(getLog((SearchNode) n));
			root.setEval(Math.max(root.getEval(), min));
			if (i < adjList.size())
				printLogs(getLog(root));
			i++;
		}
		return root.getEval();
	}

	/**
	 * Min op or alpha beta pruning algorithm
	 * 
	 * @param root
	 * @param alpha
	 * @param beta
	 * @return
	 */
	private int minOp(SearchNode root, Integer alpha, Integer beta) {
		List<Node<Action>> adjList = root.getAdjacencyList();
		if (adjList.size() == 0) {
			root.getAction().eval();
			int eval = (int) root.getAction().getEval();
			root.setEval(eval);
			return eval;
		}

		root.setEval(Integer.MAX_VALUE);
		printLogs(getLog2(root, alpha, beta, false));
		int i = 1;
		for (Node<Action> n : adjList) {
			SearchNode node = (SearchNode) n;
			int max = maxOp(node, alpha, beta);
			printLogs(getLog2(node, alpha, beta, node.isCutOff() ? true : false));
			root.setEval(Math.min(root.getEval(), max));
			if (root.getEval() <= alpha) {
				root.setCutOff(true);
				return root.getEval();
			}
			beta = new Integer(Math.min(beta.intValue(), root.getEval()));
			if (i < adjList.size())
				printLogs(getLog2(root, alpha, beta, false));
			i++;
		}
		return root.getEval();
	}

	/**
	 * Max op for the alpha beta pruning algorithm
	 * 
	 * @param root
	 * @param alpha
	 * @param beta
	 * @return
	 */
	private int maxOp(SearchNode root, Integer alpha, Integer beta) {
		List<Node<Action>> adjList = root.getAdjacencyList();
		if (adjList.size() == 0) {
			root.getAction().eval();
			int eval = (int) root.getAction().getEval();
			root.setEval(eval);
			return eval;
		}

		root.setEval(Integer.MIN_VALUE);
		printLogs(getLog2(root, alpha, beta, false));
		int i = 1;
		for (Node<Action> n : adjList) {
			SearchNode node = (SearchNode) n;
			int min = minOp(node, alpha, beta);
			printLogs(getLog2(node, alpha, beta, node.isCutOff() ? true : false));
			root.setEval(Math.max(root.getEval(), min));
			if (root.getEval() >= beta) {
				root.setCutOff(true);
				return root.getEval();
			}
			alpha = new Integer(Math.max(alpha.intValue(), root.getEval()));
			if (i < adjList.size())
				printLogs(getLog2(root, alpha, beta, false));
			i++;
		}
		return root.getEval();
	}

	private String getLog2(SearchNode node, Integer alpha, Integer beta,
			boolean isCutOff) {
		StringBuffer buff = new StringBuffer();
		buff.append(getLog(node));
		buff.append(",");
		buff.append(valCalc(alpha.intValue()));
		buff.append(",");
		buff.append(valCalc(beta.intValue()));
		if (isCutOff) {
			buff.append(",");
			buff.append("CUT-OFF");
		}
		return buff.toString();

	}
}
