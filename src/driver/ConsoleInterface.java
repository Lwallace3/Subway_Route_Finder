package driver;

import exception.BadFileException;
import graph.EdgeADT;
import graph.GraphADT;
import graph.Node;
import graph.NodeADT;
import parser.MetroMapParser;
import traversal.UniformCostSearch;
import traversal.Search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class works as the user interface and as the main running class for the program
 */
class ConsoleInterface {
    private GraphADT graph;

    /**
     * Creates a new parser and uses it to generate a graph of the subway system
     *
     * @param filename The name of the file which contains the information to be parsed into a graph
     */
    ConsoleInterface(String filename) {
        MetroMapParser parse = new MetroMapParser();
        graph = parse.parse(new File(filename));
    }

    /**
     * The main running method of the program.
     * Takes in user input, validates it and gives it to the search class.
     * Formats the output from the search class into a readable format to be presented to the user.
     * It will run until the user enters "end"
     */
    void run() {
        Scanner scan = new Scanner(System.in);
        String input;
        NodeADT nodeFrom;
        NodeADT nodeTo;
        Search search = new UniformCostSearch(graph);
        List<NodeADT> output;

        System.out.println("For a list of stations, simply enter 'stations'");
        System.out.println("Please enter the station at the start of the route, enter 'end' if you want to stop");
        input = scan.nextLine().trim();

        while (!input.toUpperCase().equals("END")) {
            nodeFrom = inputCheck(input, scan, "");
            System.out.println("Please enter the destination station for this route.");
            input = scan.nextLine().trim();
            nodeTo = inputCheck(input, scan, nodeFrom.getId());
            output = search.findPath(nodeFrom, nodeTo);
            System.out.println(formatOutput(output));
            System.out.println("Please enter the station at the start of the route, enter 'end' if you want to stop");
            input = scan.nextLine().trim();
        }
    }

    /**
     * Takes in the user's input and uses the validInput() function to check it is valid before returning it
     * If the input is invalid, it will keep asking the user until they enter a valid value
     *
     * @param input       The value from the user to be checked
     * @param scan        The Scanner object to be used if the user enters an invalid input so that they can be asked to re-enter
     * @param stationFrom Used to check if the user has entered the same thing for their origin and destination stations
     *                    so that if an error message is required, it is more descriptive
     * @return The valid string once it has been checked and made valid if necessary
     */
    private NodeADT inputCheck(String input, Scanner scan, String stationFrom) {

        while (!validInput(input)) {
            if (input.trim().toLowerCase().equals("stations")) {
                List<NodeADT> stations = graph.getNodes();
                int counter = 0;

                for (NodeADT station : stations) {
                    if (counter < 4) {
                        System.out.printf("%32s", station.getName());
                    } else {
                        System.out.println();
                        System.out.printf("%32s", station.getName());
                        counter = 0;
                    }
                    counter++;
                }
                System.out.println();
                System.out.println("For a list of stations, simply enter 'stations");
                System.out.println("Please enter a station, enter 'end' if you want to stop");
                input = scan.nextLine().trim();
            } else {
                System.out.println("That station does not exist, please enter a valid station");
                System.out.println("Did you mean '" + getMostLikeStation(input) + "'?");
                input = scan.nextLine().trim();
            }
        }
        if (getValidNode(input).getId().equals(stationFrom)) {
            System.out.println("That is the same as your origin station, please enter a different station");
            input = scan.nextLine().trim();
            inputCheck(input, scan, stationFrom);
        }
        return checkDuplicateNodeName(input, scan);
    }

    private NodeADT checkDuplicateNodeName(String nodeName, Scanner scan) {
        ArrayList<NodeADT> dups = new ArrayList<>();
        List<NodeADT> stations = graph.getNodes();
        String input;

        for(int i = 0; i < stations.size(); i++){
            if(stations.get(i).getName().toUpperCase().equals(nodeName.toUpperCase())){
                dups.add(stations.get(i));
            }
        }

        if(dups.size() == 1){
            return dups.get(0);
        }else{
            System.out.println("There are multiple stations with that name, please select the station you mean");
            for(int j = 0; j < dups.size(); j++){
                System.out.println("if you would like the station connected to "+dups.get(j).getEdges().get(0).getNode().getName()+" enter number: "+dups.get(j).getId());
            }
            input = scan.nextLine().trim();
            while(!dups.contains(getValidNode(input))){
                System.out.println("That was not one of the options offered, please select one of the stations above");
                input = scan.nextLine().trim();
            }
        }
        return getValidNode(input);
    }

    /**
     * Used to take in a valid station name and return the Node object which matches that name
     * So that it can be passed into the search class
     *
     * @param nodeID The ID of the Node which is to be returned
     * @return The Node object for the requested station pulled from the list of stations in the graph
     */
    private NodeADT getValidNode(String nodeID) {
        List<NodeADT> stations = graph.getNodes();

        for (NodeADT station : stations) {
            if (station.getId().equals(nodeID)) {
                return station;
            }
        }
        return stations.get(stations.size() - 1);
    }

    /**
     * Checks if the input passed in from inputCheck() is the name of a valid node
     *
     * @param input The user input to be checked
     * @return true if the input is the name of a station in the graph
     * false if not
     */
    private boolean validInput(String input) {
        List<NodeADT> stations = graph.getNodes();
        for (NodeADT station : stations) {
            if (station.getName().toUpperCase().equals(input.toUpperCase().trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Builds the output string to be displayed from the user when the path is received from the search class
     *
     * @param route A list of Node objects which contains the route to be displayed to the user
     * @return a StringBuilder object which contains the formatted route information to be shown to the user
     */
    private StringBuilder formatOutput(List<NodeADT> route) {
        String BASIC_FONT_COLOR = "\u001B[0m";
        String HIGHLIGHT_FONT_COLOR = "\u001B[32m";
        String currentline = findLine(route.get(0).getEdges(), route.get(1), "");
        StringBuilder output = new StringBuilder(HIGHLIGHT_FONT_COLOR + "start on line: " + currentline + " | ");
        output.append(BASIC_FONT_COLOR + route.get(0).getName());
        int charCount = 0;
        output.append(" -> ");

        for (int i = 1; i < route.size(); i++) {
            if (i == route.size() - 1) {
                output.append(route.get(i).getName());
            } else if (!findLine(route.get(i).getEdges(), route.get(i + 1), currentline).equals(currentline)) {
                currentline = findLine(route.get(i).getEdges(), route.get(i + 1), currentline);
                output.append(route.get(i).getName());
                output.append(" -> ");
                output.append(HIGHLIGHT_FONT_COLOR + "Change to line: ");
                output.append(currentline);
                output.append(BASIC_FONT_COLOR + " -> ");
            } else {
                output.append(route.get(i).getName());
                output.append(" -> ");
            }
            if (output.length() > (charCount + 40)) {
                output.append('\n');
                charCount = output.length();
            }
        }
        return output;
    }

    /**
     * Used by the formatOutput() method to check if a line change is made between stations
     *
     * @param edges       The list of edges for the current node
     * @param stationTo   The next node in the list to be used to check the list of edges and they line they are on
     * @param currentLine The line the current node is one. Used to ensure that if an edge between the current node and the next exists that is on the current line
     *                    then it is chosen to prevent the appearance of unnecessarily changing lines
     * @return a string containing the line that the edge between the current station and the next lies on
     */
    private String findLine(List<EdgeADT> edges, NodeADT stationTo, String currentLine) {
        List<String> lines = new ArrayList<>();
        for (EdgeADT edge : edges) {
            if (edge.getNode().equals(stationTo)) {
                lines.add(edge.getLine());
            }
        }
        if (lines.contains(currentLine)) {
            return currentLine;
        } else {
            return lines.get(0);
        }
    }

    /**
     * Returns the station that is most similar to the input.
     *
     * @param input the station name the user has entered
     * @return the station that is most similar to the entered string
     */
    private String getMostLikeStation(String input) {
        input = input.toUpperCase(); // Cleaning the string of capitalisation
        NodeADT bestStation = null;
        float bestLikeness = Integer.MAX_VALUE;
        List<NodeADT> stations = graph.getNodes();
        for (NodeADT station : stations) {
            String stationName = station.getName().toUpperCase();
            int stationLikeness = stringLikeness(stationName, input);
            if (stationLikeness < bestLikeness) {
                bestStation = station;
                bestLikeness = stationLikeness;
            }
        }
        return bestStation.getName();
    }

    /**
     * Calculates the likeness of two strings using Levenshtein distance.
     * Code based off "https://rosettacode.org/wiki/Levenshtein_distance"
     *
     * @param s1 - String 1
     * @param s2 - String 2
     * @return the levenshtein distance between the two strings
     */
    private int stringLikeness(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        // i == 0
        int[] costs = new int[s2.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= s1.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= s2.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), s1.charAt(i - 1) == s2.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[s2.length()];
    }

}