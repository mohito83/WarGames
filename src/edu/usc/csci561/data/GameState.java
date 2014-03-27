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

	private GameState() {
		cities = new ArrayList<City>();
	}

	public static synchronized GameState getInstance() {
		if (state == null) {
			state = new GameState();
		}
		return state;
	}

	public List<City> getNeutralCities() {
		List<City> neutral = new ArrayList<City>();
		for (City c : cities) {
			if (c.getOccupation() == Occupation.NEUTRAL) {
				neutral.add(c);
			}
		}
		return neutral;
	}

	public List<City> getUnionCities() {
		List<City> neutral = new ArrayList<City>();
		for (City c : cities) {
			if (c.getOccupation() == Occupation.UNION) {
				neutral.add(c);
			}
		}
		return neutral;
	}

	public List<City> getConfederateCities() {
		List<City> neutral = new ArrayList<City>();
		for (City c : cities) {
			if (c.getOccupation() == Occupation.CONFEDERATE) {
				neutral.add(c);
			}
		}
		return neutral;
	}

	public void addCity(City c) {
		this.cities.add(c);
	}
}
