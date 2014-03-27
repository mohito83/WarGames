/**
 * 
 */
package edu.usc.csci561.data;

/**
 * @author mohit aggarwl
 * 
 */
public enum Occupation {
	CONFEDERATE(-1), NEUTRAL(0), UNION(1);
	int val;

	private Occupation(int val) {
		this.val = val;
	}

	public int getValue() {
		return val;
	}
};
