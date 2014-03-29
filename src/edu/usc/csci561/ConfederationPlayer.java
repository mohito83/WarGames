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
public class ConfederationPlayer extends Player {

	public ConfederationPlayer(Color blue) {
		super(blue);
		name = "Confederacy";
	}

	@Override
	public void nextMove() {
		System.out.println("Confederate Player");
		greedyEvaluation();
	}

}
