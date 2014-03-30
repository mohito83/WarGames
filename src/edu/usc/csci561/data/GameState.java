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
public class GameState implements Cloneable {

	private static GameState state;
	private List<City> cities;
	private int turn;

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

	public List<City> getAllCities() {
		return this.cities;
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

	public GameState clone() {
		GameState state = new GameState();
		state.turn = this.turn;
		state.cities = new ArrayList<City>();
		for (City c : this.cities) {
			state.cities.add(c.clone());
		}

		// populate the edges
		for (City c : this.cities) {
			City cloneCity = state.getCityForName(c.getName());
			List<Edge> edges = c.getEdges();

			for (Edge e : edges) {
				Node cloneA = state.getCityForName(e.a.getName());
				Node cloneB = state.getCityForName(e.b.getName());
				Edge cloneEdge = new Edge(cloneA, cloneB);
				cloneCity.addEdge(cloneEdge);
			}
		}

		return state;
	}

	public void getUpdateCities(List<City> updatedCitiesList) {
		cities = updatedCitiesList;
	}

	public City getCityForName(String str) {
		City result = null;
		for (City c : this.cities) {
			if (c.getName().equals(str)) {
				result = c;
				break;
			}
		}

		return result;
	}
}
