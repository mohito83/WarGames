/**
 * 
 */
package edu.usc.csci561;

import edu.usc.csci561.data.Color;
import edu.usc.csci561.data.Player;

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
			break;
		case 3:
			break;

		}
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
