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
	}

	@Override
	public void nextMove() {
		System.out.println("Confederate Player");
		greedyEvaluation();
	}

	/*
	 * @Override public void run() { GameState state = GameState.getInstance();
	 * synchronized (state) { while (!state.isNoMoreMoves()) {
	 * if(state.getPlayer() == Color.BLUE){
	 * 
	 * } calculateNextStep(); state.setPlayer(Color.RED); try { state.wait(); }
	 * catch (InterruptedException e) { System.out.println(e.getMessage()); } }
	 * } }
	 */
}
