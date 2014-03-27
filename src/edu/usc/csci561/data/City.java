/**
 * 
 */
package edu.usc.csci561.data;

/**
 * @author mohit aggarwl
 * 
 */
public class City extends Node {

	private int value;
	/**
	 * -1 => confederates, 1=> Union, 0=> Neutral
	 */
	private Occupation occupation;

	public City(String name) {
		super(name);
	}

	public City(String name, int value) {
		super(name);
		this.value = value;
	}

	public City(String name, Edge e, int value) {
		super(name, e);
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
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
	public void setValue(int value) {
		this.value = value;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append(name + ", E=" + E.size() + ", " + value + ", " + occupation);
		return buff.toString();
	}
}
