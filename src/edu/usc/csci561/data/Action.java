package edu.usc.csci561.data;

import java.util.List;

import edu.usc.csci561.searchtree.MiniMax;

public class Action {
	private City destination;
	private double eval;
	private int depth;
	private GameState gameState;
	private MiniMax player;
	private boolean isForcedMarch;

	public Action(City c) {
		this.destination = c;
	}

	public Action(GameState state, MiniMax player, City c, int depth) {
		this(c);
		this.depth = depth;
		this.gameState = state;
		this.player = player;
	}

	/**
	 * @return the city
	 */
	public City getDestination() {
		return destination;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setDestination(City city) {
		this.destination = city;
	}

	/**
	 * @return the eval
	 */
	public double getEval() {
		return eval;
	}

	/**
	 * @param eval
	 *            the eval to set
	 */
	public void setEval(double eval) {
		this.eval = eval;
	}

	/**
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * This method evaluates the state of the game for the given player.
	 */
	public void eval() {
		List<City> cities = gameState.getAllCities();

		eval = 0;
		for (City c : cities) {
			if (player == MiniMax.MAX) {
				if (c.getOccupation() == Occupation.UNION) {
					eval += c.getResourceValue();
				} else if (c.getOccupation() == Occupation.CONFEDERATE) {
					eval -= c.getResourceValue();
				}
			} else {
				if (c.getOccupation() == Occupation.CONFEDERATE) {
					eval += c.getResourceValue();
				} else if (c.getOccupation() == Occupation.UNION) {
					eval -= c.getResourceValue();
				}
			}
		}

	}

	/**
	 * This method performs the paratroop drop on the destination city
	 */
	public void performParatroopDrop() {
		isForcedMarch = false;
		if (player == MiniMax.MAX) {
			destination.setOccupation(Occupation.UNION);
		} else {
			destination.setOccupation(Occupation.CONFEDERATE);
		}
	}

	public void performForcedMarch() {
		performParatroopDrop();
		isForcedMarch = true;
		List<Node<String>> nodes = destination.getAdjacencyList();
		for (Node<String> n : nodes) {
			City c = (City) n;
			if (c.getOccupation() != Occupation.NEUTRAL) {
				if (player == MiniMax.MAX) {
					c.setOccupation(Occupation.UNION);
				} else {
					c.setOccupation(Occupation.CONFEDERATE);
				}
			}
		}
	}

	public List<City> getUpdatedCitiesList() {
		return gameState.getAllCities();
	}

	/**
	 * @return the isForcedMarch
	 */
	public boolean isForcedMarch() {
		return isForcedMarch;
	}

	/**
	 * @return the gameState
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * @return the player
	 */
	public MiniMax getPlayer() {
		return player;
	}
}
