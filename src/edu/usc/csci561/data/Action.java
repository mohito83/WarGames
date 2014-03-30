package edu.usc.csci561.data;

import java.util.List;

import edu.usc.csci561.UnionPlayer;

public class Action {
	private City destination;
	private double eval;
	private int depth;
	private GameState gameState;
	private Player player;
	private boolean isForcedMarch;

	public Action(City c) {
		this.destination = c;
	}

	public Action(GameState state, Player player, City c, int depth) {
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
	 * @param depth
	 *            the depth to set
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * This method evaluates the state of the game for the given player.
	 */
	public void eval() {
		List<City> cities = gameState.getAllCities();// null;

		eval = 0;
		for (City c : cities) {
			if (player instanceof UnionPlayer) {
				if (c.getOccupation() == Occupation.UNION) {
					eval += c.getValue();
				} else if (c.getOccupation() == Occupation.CONFEDERATE) {
					eval -= c.getValue();
				}
			} else {
				if (c.getOccupation() == Occupation.CONFEDERATE) {
					eval += c.getValue();
				} else if (c.getOccupation() == Occupation.UNION) {
					eval -= c.getValue();
				}
			}
		}

	}

	/**
	 * This method performs the paratroop drop on the destination city
	 */
	public void performParatroopDrop() {
		isForcedMarch = false;
		if (player instanceof UnionPlayer) {
			destination.setOccupation(Occupation.UNION);
		} else {
			destination.setOccupation(Occupation.CONFEDERATE);
		}
	}

	public void performForcedMarch() {
		performParatroopDrop();
		isForcedMarch = true;
		List<Node> nodes = destination.getAdjacencyList();
		for (Node n : nodes) {
			City c = (City) n;
			if (c.getOccupation() != Occupation.NEUTRAL) {
				if (player instanceof UnionPlayer) {
					c.setOccupation(Occupation.UNION);
				} else {
					c.setOccupation(Occupation.CONFEDERATE);
				}
			}
		}
	}

	/**
	 * This method generates the Log string
	 * 
	 * @return
	 */
	public String getLog() {
		StringBuffer buff = new StringBuffer();
		if (player instanceof UnionPlayer) {
			buff.append("Union,");
		} else {
			buff.append("Confedracy,");
		}

		if (isForcedMarch) {
			buff.append("Force March,");
		} else {
			buff.append("Paratroop Drop,");
		}

		buff.append(destination.getName());
		buff.append(",");
		buff.append(depth);
		buff.append(",");
		buff.append(eval);
		buff.append(System.getProperty("line.separator"));

		return buff.toString();
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
}
