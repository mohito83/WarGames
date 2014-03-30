/**
 * 
 */
package edu.usc.csci561;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import edu.usc.csci561.data.City;
import edu.usc.csci561.data.Color;
import edu.usc.csci561.data.Edge;
import edu.usc.csci561.data.GameState;
import edu.usc.csci561.data.Occupation;
import edu.usc.csci561.data.Player;

/**
 * This is the main class for CSCI561 Assignment -3 : Adversarial Search
 * 
 * @author mohit aggarwl
 * 
 */
public class WarSimulationGame {

	// Input parameters
	private static int task;
	private static int cutOffDepth;
	private static String mapFile;
	private static String initFile;
	private static String outputPath;
	private static String outputLog;

	private static Map<String, City> nodeMap = new HashMap<String, City>();

	private static GameState gameState;

	// Graph related instance variables

	/**
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		Player union = new UnionPlayer(Color.RED);
		Player confed = new ConfederationPlayer(Color.BLUE);
		gameState = GameState.getInstance();

		parseCommandLine(args);

		((UnionPlayer) union).setCutoffLevel(cutOffDepth);
		((UnionPlayer) union).setSearch(task);

		parseMapFile(mapFile);

		parseInitFile(initFile);

		// initial print
		FileWriter logsWriter = null;
		FileWriter movesWriter = null;
		try {
			logsWriter = new FileWriter(new File(outputLog));
			movesWriter = new FileWriter(new File(outputPath));
			union.setLogWriter(logsWriter);
			union.setMovesWriter(movesWriter);
			confed.setLogWriter(logsWriter);
			confed.setMovesWriter(movesWriter);
			logsWriter.write("Player,Action,Destination,Depth,Value");
			logsWriter.write(System.getProperty("line.separator"));
			StringBuffer buff = new StringBuffer();
			buff.append("TURN = 0");
			buff.append(System.getProperty("line.separator"));
			buff.append("Player = N/A");
			buff.append(System.getProperty("line.separator"));
			buff.append("Action = N/A");
			buff.append(System.getProperty("line.separator"));
			buff.append("Destination = N/A");
			buff.append(System.getProperty("line.separator"));
			buff.append("Union,{");
			int i = 0;
			double sum = 0.0;
			for (City c : gameState.getUnionCities()) {
				i++;
				sum += c.getResourceValue();
				buff.append(c.getVal());
				if (i < gameState.getUnionCities().size()) {
					buff.append(",");
				}
			}
			buff.append("},");
			buff.append(sum);

			buff.append(System.getProperty("line.separator"));
			buff.append("Confederacy,{");
			i = 0;
			sum = 0.0;
			for (City c : gameState.getConfederateCities()) {
				i++;
				sum += c.getResourceValue();
				buff.append(c.getVal());
				if (i < gameState.getConfederateCities().size()) {
					buff.append(",");
				}
			}
			buff.append("},");
			buff.append(sum);
			buff.append(System.getProperty("line.separator"));
			buff.append("--------------------------------------------------------------");
			movesWriter.write(buff.toString());
			movesWriter.write(System.getProperty("line.separator"));

		} catch (IOException e1) {
			System.out
					.println("Exception occurred while writing to log files - "
							+ e1.getMessage());
		}

		Collections.sort(gameState.getAllCities(), new Comparator<City>() {

			@Override
			public int compare(City o1, City o2) {
				return o1.getVal().compareTo(o2.getVal());
			}
		});

		long i = 2L;
		while (!gameState.isNoMoreMoves()) {
			if (i % 2 == 0) {
				union.nextMove();
			} else {
				confed.nextMove();
			}
			i++;
		}

		if (logsWriter != null && movesWriter != null) {
			try {
				logsWriter.close();
				movesWriter.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * This method parses the Init file which populates resources and occupancy
	 * fields of the city
	 * 
	 * @param initFile2
	 */
	private static void parseInitFile(String initFile2) {
		File f = new File(initFile2);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f));
			String str = null;
			while ((str = reader.readLine()) != null) {
				String[] tokens = str.split(",");
				City c = nodeMap.get(tokens[0]);
				c.setResourceValue(Integer.parseInt(tokens[1]));
				switch (Integer.parseInt(tokens[2])) {
				case -1:
					c.setOccupation(Occupation.CONFEDERATE);
					break;
				case 0:
					c.setOccupation(Occupation.NEUTRAL);
					break;
				case 1:
					c.setOccupation(Occupation.UNION);
					break;
				}
				gameState.addCity(c);
			}
		} catch (IOException e) {
			System.out
					.println("Exception occurred while parsing the Init file- "
							+ e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					System.out
							.println("Exception occurred while closing the Init file- "
									+ e.getMessage());
				}
			}
		}
	}

	/**
	 * This method parses the Map file and populate the graph used in this
	 * assignment
	 * 
	 * @param mapFile2
	 */
	private static void parseMapFile(String mapFile2) {
		File f = new File(mapFile2);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f));
			String str = null;
			while ((str = reader.readLine()) != null) {
				String[] nodes = str.split(",");
				City c1 = new City(nodes[0]);
				City c2 = new City(nodes[1]);
				if (nodeMap.containsKey(nodes[0])) {
					c1 = nodeMap.get(nodes[0]);
				} else {
					nodeMap.put(nodes[0], c1);
				}

				if (nodeMap.containsKey(nodes[1])) {
					c2 = nodeMap.get(nodes[1]);
				} else {
					nodeMap.put(nodes[1], c2);
				}

				Edge e = new Edge(c1, c2);
				c1.addEdge(e);
				c2.addEdge(e);
			}
		} catch (IOException e) {
			System.out
					.println("Exception occurred while parsing the Map file- "
							+ e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					System.out
							.println("Exception occurred while closing the Map file- "
									+ e.getMessage());
				}
			}
		}

	}

	/**
	 * This method parses the command line arguments
	 * 
	 * @param args
	 */
	private static void parseCommandLine(String[] args) {

		int i = 0;
		String tmp = null;
		while (i < args.length && args[i].startsWith("-")) {
			tmp = args[i++];

			if (tmp.equals("-t")) {
				try {
					task = Integer.parseInt(args[i++]);
				} catch (NumberFormatException e) {
					System.out.println("Invalid command line argument!! - ("
							+ e.getMessage() + ")");
				}
			} else if (tmp.equals("-d")) {
				try {
					cutOffDepth = Integer.parseInt(args[i++]);
				} catch (NumberFormatException e) {
					System.out.println("Invalid command line argument!! - ("
							+ e.getMessage() + ")");
				}
			} else if (tmp.equals("-m")) {
				mapFile = args[i++];
			} else if (tmp.equals("-i")) {
				initFile = args[i++];
			} else if (tmp.equals("-op")) {
				outputPath = args[i++];
			} else if (tmp.equals("-ol")) {
				outputLog = args[i++];
			}
		}

		System.out.println("Test command line parsing logic\n");
		System.out.println("task=" + task + "\t cut_off_depth=" + cutOffDepth
				+ "\tmap_file=" + mapFile + "\tinitfile=" + initFile
				+ "\toutputpathfile=" + outputPath + "\toutputLogfile="
				+ outputLog);

	}

}
