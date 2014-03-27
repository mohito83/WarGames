/**
 * 
 */
package edu.usc.csci561;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.usc.csci561.data.City;
import edu.usc.csci561.data.Color;
import edu.usc.csci561.data.Edge;
import edu.usc.csci561.data.Player;
import edu.usc.csci561.data.City.Occupation;

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

	// Graph related instance variables

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Player union = new UnionPlayer(Color.RED);
		Player confed = new ConfederationPlayer(Color.BLUE);

		parseCommandLine(args);

		((UnionPlayer) union).setCutoffLevel(cutOffDepth);
		((UnionPlayer) union).setPrunning(task == 3);

		parseMapFile(mapFile);

		parseInitFile(initFile, union, confed);
	}

	/**
	 * This method parses the Init file which populates resources and occupancy
	 * fields of the city
	 * 
	 * @param initFile2
	 * @param confed
	 * @param union
	 */
	private static void parseInitFile(String initFile2, Player union,
			Player confed) {
		File f = new File(initFile2);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f));
			String str = null;
			while ((str = reader.readLine()) != null) {
				String[] tokens = str.split(",");
				City c = nodeMap.get(tokens[0]);
				c.setValue(Integer.parseInt(tokens[1]));
				switch (Integer.parseInt(tokens[2])) {
				case -1:
					c.setOccupation(Occupation.CONFEDERATE);
					confed.addCity(c);
					break;
				case 0:
					c.setOccupation(Occupation.NEUTRAL);
					break;
				case 1:
					c.setOccupation(Occupation.UNION);
					union.addCity(c);
					break;
				}
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
