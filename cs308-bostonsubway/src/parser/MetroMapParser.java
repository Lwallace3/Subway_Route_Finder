package parser;

import exception.BadFileException;
import graph.*;

import java.io.*;
import java.util.*;

/**
 * This class reads a text description of a metro subway system
 * and generates a graph representation of the metro.
 *
 * The grammar for the file is described below. A typical Line looks like :
 *
 * 20 NorthStation   Green 19 22  Orange 15 22
 *
 * where :
 * 20 is the StationID
 * NorthStation is the StationName
 * Green 19 22
 * Green is the LineName
 * 19 is the StationID of the outbound station
 * 22 is the StationID of the inbound station
 * Orange 15 22 is a LineID in which :
 * Orange is the LineName
 * 15 is the StationID of the outbound station
 * 22 is the StationID of the inbound station
 *
 * Therefore, NorthStation has two outgoing lines.
 *
 * note : 0 denotes the end of a line : i.e. in this case,
 * OakGrove would be at the end of the line, as there is no other outbound
 * station.
 */

public class MetroMapParser implements GraphParser {

    public MetroMapParser(){

    }
    /**
     * parser.MetroMap
     * <p>
     * Contains a list of stations and edges. 2 helper methods have been created to
     * aid the constructor, which takes in a file and parses it into the graph.
     */

    /**
     * nodeIfExists
     * <p>
     * Used by the constructor to test if a node already exists or not.
     *
     * @param stations
     * @param id
     * @return Node wrapped in optional if a node with the given id already exists.
     * An empty optional if no node with that id has been created.
     */
    private Optional<NodeADT> nodeIfExists(List<NodeADT> stations, final String id) {
        return stations.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    @Override
    public GraphADT parse(File f) {
        GraphADT graph = new Graph();

        BufferedReader fileInput;

        try {
            fileInput = new BufferedReader(new FileReader(f));
            String line = fileInput.readLine();
            StringTokenizer st;
            String stationID;
            String stationName;
            String lineName;
            String outboundID;
            String inboundID;

            while (line != null) {

                st = new StringTokenizer(line);

                //We want to handle empty lines effectively, we just ignore them!
                if (!st.hasMoreTokens()) {
                    line = fileInput.readLine();
                    continue;
                }

                //from the grammar, we know that the Station ID is the first token on the line
                stationID = st.nextToken();

                if (!st.hasMoreTokens()) {
                    throw new BadFileException("no station name");
                }

                //from the grammar, we know that the Station Name is the second token on the line.
                stationName = st.nextToken();

                if (!st.hasMoreTokens()) {
                    throw new BadFileException("station is on no lines");
                }

                /*
                  If the node has not already been created, it is created.
                  If a node ha been created, the extra information provided
                  is added to it.
                 */

                Optional<NodeADT> originalMaybe = nodeIfExists(graph.getNodes(), stationID);
                NodeADT originalNode;
                if (originalMaybe.isEmpty()) {
                    originalNode = new Node(stationID, stationName);
                    graph.addNode(originalNode);
                } else {
                    originalNode = originalMaybe.get();
                    originalNode.setName(stationName);
                }

                while (st.hasMoreTokens()) {
                    lineName = st.nextToken();

                    if (!st.hasMoreTokens()) {
                        throw new BadFileException("poorly formatted line info");
                    }

                    outboundID = st.nextToken();
                    addEdge(graph, lineName, outboundID, originalNode);

                    if (!st.hasMoreTokens()) {
                        throw new BadFileException("poorly formatted adjacent stations");
                    }

                    inboundID = st.nextToken();
                    addEdge(graph, lineName, inboundID, originalNode);

                }

                line = fileInput.readLine();
            }
        } catch (BadFileException | IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    /**
     * addEdge
     *
     * Takes in a graph and a node, along with the information required to construct a new node.
     * First checks if the node has already been created, if it has then it is passed into the edge.
     * If not it is created with limited information to construct the edge, and will have extra
     * values added to it when that node is reached in the file.
     *
     * Potential for danger if the node does not exist later in the file.
     *
     * @param graph the graph being generated.
     * @param lineName the name of the line to add.
     * @param newID the ID of the node the egde leads to.
     * @param originalNode the original node from which the line exends from.
     */

    private void addEdge(GraphADT graph, String lineName, String newID, NodeADT originalNode) {
        NodeADT newNode;
        EdgeADT e;
        if (!newID.equals("0")) {
            Optional<NodeADT> newMaybe = nodeIfExists(graph.getNodes(), newID);
            if (newMaybe.isEmpty()){
                newNode = new Node(newID);
                graph.addNode(newNode);
            }
            else
                newNode = newMaybe.get();
            e = new Edge(newNode, lineName);
            originalNode.addEdge(e);
            graph.addEdge(e);
        }
    }
}