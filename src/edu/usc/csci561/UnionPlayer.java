/**
 * 
 */
package edu.usc.csci561;

import edu.usc.csci561.data.Color;
import edu.usc.csci561.data.Player;

/**
 * @author mohit aggarwl
 * 
 */
public class UnionPlayer extends Player {

	private int cutoffLevel;
	private boolean isPrunning;

	public UnionPlayer(Color red) {
		super(red);
	}

	public UnionPlayer(Color c, int cutOff, boolean prunning) {
		this(c);
		this.cutoffLevel = cutOff;
		this.isPrunning = prunning;
	}

	@Override
	public void calculateNextStep() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the cutoffLevel
	 */
	public int getCutoffLevel() {
		return cutoffLevel;
	}

	/**
	 * @param cutoffLevel the cutoffLevel to set
	 */
	public void setCutoffLevel(int cutoffLevel) {
		this.cutoffLevel = cutoffLevel;
	}

	/**
	 * @return the isPrunning
	 */
	public boolean isPrunning() {
		return isPrunning;
	}

	/**
	 * @param isPrunning the isPrunning to set
	 */
	public void setPrunning(boolean isPrunning) {
		this.isPrunning = isPrunning;
	}

}
