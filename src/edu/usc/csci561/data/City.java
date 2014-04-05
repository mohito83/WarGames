/**
 * 
 */
package edu.usc.csci561.data;

/**
 * @author mohit aggarwl
 * 
 */
public class City extends Node<String> implements Cloneable {

	private int resourceValue;
	/**
	 * -1 => confederates, 1=> Union, 0=> Neutral
	 */
	private Occupation occupation;

	public City(String name) {
		super(name);
	}

	public City(String name, int value) {
		super(name);
		this.resourceValue = value;
	}

	public City(String name, Edge<String> e, int value) {
		super(name, e);
		this.resourceValue = value;
	}

	/**
	 * @return the value
	 */
	public int getResourceValue() {
		return resourceValue;
	}

	/**
	 * @return the occupation
	 */
	public Occupation getOccupation() {
		return occupation;
	}

	/**
	 * @param occupation
	 *            the occupation to set
	 */
	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setResourceValue(int value) {
		this.resourceValue = value;
	}

	public City clone() {
		City c = new City(getVal());
		c.setState(state);
		c.setOccupation(getOccupation());
		c.setResourceValue(getResourceValue());
		return c;
	}
}
