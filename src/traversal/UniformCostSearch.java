package traversal;

import graph.*;

import java.lang.reflect.Array;
import java.util.*;


/**
 * This class implements the Uniform Cost Search algorithm to find the distance between
 * two nodes in a graph.
 */

public class UniformCostSearch implements Search {
    public HashMap<NodeADT, NodeADT> pathMap;
    private ArrayList<NodeADT> visited;
    private ArrayList<NodeADT> path;
    private ArrayList<NodeADT> agenda;
    private ArrayList<NodeADT> nodes;

    public UniformCostSearch(GraphADT graph) {
        this.nodes = new ArrayList<>();
        nodes.addAll(graph.getNodes());

        this.pathMap = new HashMap<>();
        this.visited = new ArrayList<>();
        this.path = new ArrayList<>();
        this.agenda = new ArrayList<>();
    }

    /**
     * findPath
     *
     * A method for finding the optimal path between two nodes, resets the
     * structures required for the algorithm to execute, then executes a
     * Uniform Cost Search.
     *
     * @param source the source Node.
     * @param goal   the Destination node.
     * @return A path between the two nodes.
     */
    public ArrayList<NodeADT> findPath(NodeADT source, NodeADT goal) {
        /* Initalisation of variables required for search */
        pathMap = new HashMap<>();
        visited = new ArrayList<>();
        path = new ArrayList<>();
        agenda = new ArrayList<>();

        setLineWeights();
        source.setCost(0);
        boolean pathFound = false;
        ArrayList<NodeADT> nextStates;
        NodeADT selectedNode = null;
        pathMap.put(source, null);
        agenda.add(source);

        // Search algorithm runs until a path is found
        while (!pathFound) {
            // Select minimum cost node from the current agenda
            selectedNode = selectNode(agenda);
            visited.add(selectedNode);
            agenda.remove(selectedNode);
            // Check if the node that was selected is the correct node using ID's
            if (selectedNode.getId().equals(goal.getId())) {
                path.add(goal);
                pathFound = true;
            } else {
                // Expands selected node and finds the next states
                nextStates = expandNode(selectedNode);
                // Remove the selected node from the agenda as it's already been searched
                agenda.remove(selectedNode);
                // Add nonvisited children to the agenda
                agenda.addAll(nextStates);
            }
        }
        System.out.println("Creating Path...");

        // Unravel the pathmap to backtrack down the correct path
        while(path.get(path.size()-1).getName() != source.getName()) {
            NodeADT searchingNode = path.get(path.size()-1);
            NodeADT temp = pathMap.get(searchingNode);
            path.add(temp);
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * selectNode
     *
     * Helper function that selects the best node to expand
     * from the current agenda.
     *
     * @param agenda
     * @return The node selected for expansion.
     */

    private NodeADT selectNode(ArrayList<NodeADT> agenda) {
        NodeADT toExpand = agenda.get(0);

        for (NodeADT node : agenda) {
            if (node.getCost() != null) {
                toExpand = node;
            }
        }
        return toExpand;
    }

    /**
     * expandNode
     *
     * Expands the current node and returns it's children populated with their cost.
     * @param node the node to be expanded.
     * @return A list of the child nodes belonging to selected parent.
     */
    private ArrayList<NodeADT> expandNode(NodeADT node) {
        ArrayList<NodeADT> nextStates = new ArrayList<>();
        List<EdgeADT> edges = node.getEdges();

        // We want the resulting node for each edge.
        for (EdgeADT edge : edges) {
            Integer weight = edge.getWeight();
            NodeADT nextState = edge.getNode();
            boolean inAgenda = false;
            boolean isVisited = false;
            for (NodeADT agendaNode : agenda) {
                if (agendaNode.getId().equals(nextState.getId())) {
                    inAgenda = true;
                }
            }
            for (NodeADT visitedNode : visited) {
                if (visitedNode.getId().equals(nextState.getId())) {
                    isVisited = true;
                }
            }
            if (!inAgenda && !isVisited) {
                nextState.setCost(weight);
                nextStates.add(nextState);
                this.pathMap.put(nextState, node);
            }
        }
        return nextStates;
    }

    /**
     * setLineWeights
     *
     * A helper method which populates each line with a weight based on the line that
     * it lays on.
     */

    private void setLineWeights(){
        //hashMap will contain all the different line names each with a unique weight
        HashMap<String, Integer> lineTypes= new HashMap<>();
        int newWeight = 10;

        //Makes sure its decoupled i.e handles any amount of lines with any names
        for(NodeADT node : nodes) {
            List<String> lines = node.getLines();
            for(String line: lines) {
                if (lineTypes.get(line) == null) {
                    lineTypes.put(line,newWeight);
                    System.out.println("Line: " + line + " has an assigned weight of " + newWeight);
                    newWeight += 2;
                }
            }
        }
        System.out.println();
        //assigns the all the edges from each node their updated weight
        for(NodeADT node : nodes) {
            for(EdgeADT edge: node.getEdges()) {
                edge.setWeight(lineTypes.get(edge.getLine()));
            }
        }
    }
}

//method to return line weights