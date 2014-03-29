/**
 * 
 */
package edu.usc.csci561.data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.usc.csci561.UnionPlayer;

/**
 * @author mohit aggarwl
 * 
 */
public abstract class Player {

	protected String name;
	private Color color;
	private FileWriter logWriter;
	private FileWriter movesWriter;

	public Player() {
		this(Color.RED);
	}

	public Player(Color c) {
		this.color = c;
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

	protected void greedyEvaluation() {
		GameState state = GameState.getInstance();
		List<City> cities = null;
		if (this instanceof UnionPlayer) {
			cities = state.getUnionCities();
		} else {
			cities = state.getConfederateCities();
		}

		List<Action> actions = new ArrayList<Action>();

		// for evaluating the forced march strategy
		for (City c : cities) {
			List<Node> adjList = c.getAdjacencyList();
			for (Node e : adjList) {
				City x = (City) e;
				if (x.getOccupation() == Occupation.NEUTRAL) {
					Action act = new Action(state.clone(), this, x, 1);
					act.performForcedMarch();
					act.eval();
					actions.add(act);
					try {
						printLogs(act.getLog());
					} catch (IOException e1) {
						System.out.println(e1.getMessage());
					}
				}
			}
		}

		// evaluate the paratroop drop startegy
		cities = state.getNeutralCities();
		for (City c : cities) {
			Action act = new Action(state.clone(), this, c, 1);
			act.performParatroopDrop();
			act.eval();
			actions.add(act);
			try {
				printLogs(act.getLog());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

		// sort the Actions based on the eval value
		Action maxAct = Collections.max(actions, new Comparator<Action>() {

			@Override
			public int compare(Action o1, Action o2) {
				return (int) (o1.getEval() - o2.getEval());
			}
		});

		state.getUpdateCities(maxAct.getUpdatedCitiesList());
		state.incrementTurn();
		try {
			printMoves(getResultLogs(maxAct));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private String getResultLogs(Action maxAct) {
		GameState state = GameState.getInstance();
		StringBuffer buff = new StringBuffer();
		buff.append("TURN = " + state.getTurn());
		buff.append(System.getProperty("line.separator"));
		buff.append("Player = " + this.getName());
		buff.append(System.getProperty("line.separator"));
		buff.append("Action = ");
		buff.append(maxAct.isForcedMarch() ? "Force March" : "Paratroop Drop");
		buff.append(System.getProperty("line.separator"));
		buff.append("Destination = " + maxAct.getDestination().getName());
		buff.append(System.getProperty("line.separator"));

		int i = 0;
		double sum = 0.0;
		for (City c : state.getUnionCities()) {
			sum += c.getValue();
			buff.append(c.getName());
			if (i < state.getUnionCities().size()) {
				buff.append(",");
			}
			i++;
		}
		buff.append("},");
		buff.append(sum);

		buff.append(System.getProperty("line.separator"));
		buff.append("Confederacy,{");
		i = 0;
		sum = 0.0;
		for (City c : state.getConfederateCities()) {
			sum += c.getValue();
			buff.append(c.getName());
			if (i < state.getConfederateCities().size()) {
				buff.append(",");
			}
			i++;
		}
		buff.append("},");
		buff.append(sum);
		buff.append(System.getProperty("line.separator"));
		buff.append("--------------------------------------------------------------");
		buff.append(System.getProperty("line.separator"));

		return buff.toString();
	}
}
