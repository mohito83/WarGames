/**
 * 
 */
package edu.usc.csci561.data;


/**
 * @author mohit aggarwl
 * 
 */
public abstract class Player implements Runnable {

	private String name;
	private Color color;

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
	public abstract void calculateNextStep();
}
