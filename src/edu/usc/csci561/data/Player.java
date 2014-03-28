/**
 * 
 */
package edu.usc.csci561.data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import edu.usc.csci561.UnionPlayer;

/**
 * @author mohit aggarwl
 * 
 */
public abstract class Player {// implements Runnable {

	private String name;
	private Color color;
	private FileWriter logWriter;
	private FileWriter movesWriter;

	// private List<City> cities;

	public Player() {
		this(Color.RED);
	}

	public Player(Color c) {
		this.color = c;
		// cities = new ArrayList<City>();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

	/*
	 * public void addCity(City c) { this.cities.add(c); }
	 * 
	 * public boolean containsCity(City c) { return this.cities.contains(c); }
	 * 
	 * public boolean removeCity(City c) { return this.cities.remove(c); }
	 */
	public abstract void nextMove();

	public void printLogs(String str) throws IOException {
		logWriter.write(str);
	}

	public void printMoves(String str) throws IOException {
		movesWriter.write(str);
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

	/*
	 * public void doNotify() { GameState state = GameState.getInstance();
	 * synchronized (state) { state.notify(); } }
	 */

	protected void greedyEvaluation() {
		GameState state = GameState.getInstance();
		List<City> cities = null;
		if (this instanceof UnionPlayer) {
			cities = state.getUnionCities();
		} else {
			cities = state.getConfederateCities();
		}
		
		for(City c : cities){
			c.getEdges();
		}
	}
}
