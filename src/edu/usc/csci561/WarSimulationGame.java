/**
 * 
 */
package edu.usc.csci561;

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

	// Graph related instance variables

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		parseCommandLine(args);
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
