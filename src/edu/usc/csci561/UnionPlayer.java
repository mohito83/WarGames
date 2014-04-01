/**
 * 
 */
package edu.usc.csci561;

import java.io.IOException;

import edu.usc.csci561.data.Action;
import edu.usc.csci561.data.Color;
import edu.usc.csci561.data.GameState;
import edu.usc.csci561.data.Node;
import edu.usc.csci561.data.Player;
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
		switch (this.task) {
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
		GameState state = GameState.getInstance();
		SearchNode root = buildSearchTree(state);
		int val = maxOp(root, Integer.MIN_VALUE, Integer.MAX_VALUE);

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
		try {
			printMoves(getResultLogs(action));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This method generates a search tree with a cutoff depth as defined in the
	 * class instance and perform an Minimax evaluation algorithm
	 */
	private void minimaxEvaluation() {
		GameState state = GameState.getInstance();
		SearchNode root = buildSearchTree(state);
		int val = maxOp(root);
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
		try {
			printMoves(getResultLogs(action));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private int minOp(SearchNode root) {
		if (root.getAdjacencyList().size() == 0) {
			return (int) root.getAction().getEval();
		}

		root.setEval(Integer.MAX_VALUE);
		for (Node<Action> n : root.getAdjacencyList()) {
			int max = maxOp((SearchNode) n);
			root.setEval(Math.min(root.getEval(), max));
		}
		return root.getEval();
	}

	private int maxOp(SearchNode root) {
		if (root.getAdjacencyList().size() == 0) {
			return (int) root.getAction().getEval();
		}

		root.setEval(Integer.MIN_VALUE);
		for (Node<Action> n : root.getAdjacencyList()) {
			int min = minOp((SearchNode) n);
			root.setEval(Math.max(root.getEval(), min));
		}
		return root.getEval();
	}

	private int minOp(SearchNode root, int alpha, int beta) {
		if (root.getAdjacencyList().size() == 0) {
			return (int) root.getAction().getEval();
		}

		root.setEval(Integer.MAX_VALUE);
		for (Node<Action> n : root.getAdjacencyList()) {
			int max = maxOp((SearchNode) n, alpha, beta);
			root.setEval(Math.min(root.getEval(), max));
			if (root.getEval() <= alpha) {
				return root.getEval();
			}
			beta = Math.min(beta, root.getEval());
		}
		return root.getEval();
	}

	private int maxOp(SearchNode root, int alpha, int beta) {
		if (root.getAdjacencyList().size() == 0) {
			return (int) root.getAction().getEval();
		}

		root.setEval(Integer.MIN_VALUE);
		for (Node<Action> n : root.getAdjacencyList()) {
			int min = minOp((SearchNode) n, alpha, beta);
			root.setEval(Math.max(root.getEval(), min));
			if (root.getEval() >= beta) {
				return root.getEval();
			}
			alpha = Math.max(alpha, root.getEval());
		}
		return root.getEval();
	}

}
