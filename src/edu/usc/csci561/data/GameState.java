/**
 * 
 */
package edu.usc.csci561.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class creates a singleton instance and manages the Game State
 * 
 * @author mohit aggarwl
 * 
 */
public class GameState {

	private static GameState state;
	private List<City> cities;
	private int turn;
	private Action action;
	private Player player;
	private String destination;

	private GameState() {
		turn = 0;
		cities = new ArrayList<City>();
	}

	/**
	 * returns the singleton instance of this class
	 * 
	 * @return GameState
	 */
	public static synchronized GameState getInstance() {
		if (state == null) {
			state = new GameState();
		}
		return state;
	}

	/**
	 * Gets the list of neutral cities
	 * 
	 * @return
	 */
	public List<City> getNeutralCities() {
		List<City> neutral = new ArrayList<City>();
		for (City c : cities) {
			if (c.getOccupation() == Occupation.NEUTRAL) {
				neutral.add(c);
			}
		}
		return neutral;
	}

	/**
	 * Gets the list of Union cities
	 * 
	 * @return
	 */
	public List<City> getUnionCities() {
		List<City> neutral = new ArrayList<City>();
		for (City c : cities) {
			if (c.getOccupation() == Occupation.UNION) {
				neutral.add(c);
			}
		}
		return neutral;
	}

	/**
	 * Gets the list of confederate cities
	 * 
	 * @return
	 */
	public List<City> getConfederateCities() {
		List<City> neutral = new ArrayList<City>();
		for (City c : cities) {
			if (c.getOccupation() == Occupation.CONFEDERATE) {
				neutral.add(c);
			}
		}
		return neutral;
	}

	/**
	 * 
	 * @param c
	 */
	public void addCity(City c) {
		this.cities.add(c);
	}

	/**
	 * This method checks whether there are any further moves available to make
	 * by players
	 * 
	 * @return
	 */
	public boolean isNoMoreMoves() {
		return getNeutralCities().isEmpty();
	}

	/**
	 * @return the turn
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * @param turn
	 *            the turn to set
	 */
	public void setTurn(int turn) {
		this.turn = turn;
	}

	public void incrementTurn() {
		this.turn++;
	}

	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 *            the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
}