/**
 * 
 */
package edu.usc.csci561.searchtree;

/**
 * @author mohit aggarwl
 * 
 */
public enum MiniMax {
	MIN("min"), MAX("max");

	private String val;

	private MiniMax(String val) {
		this.val = val;
	}

	public String getValue() {
		return this.val;
	}
}
