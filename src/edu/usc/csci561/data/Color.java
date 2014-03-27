/**
 * 
 */
package edu.usc.csci561.data;

/**
 * @author mohit aggarwl
 * 
 */
public enum Color {
	RED("UNION"), BLUE("CONFEDERATES");

	private String val;

	private Color(String val) {
		this.val = val;
	}

	public String getValue() {
		return this.val;
	}
}
