package graph;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Node implements NodeADT{

    /**
     * Node
     *
     * Contains an ID, name, cost and a list of lines.
     *
     * Since the node may be referenced in an edge before it has been created,
     * there exists 2 constructors, one for when it is referenced from parsing
     * another node, and one for when it is being parsed. Methods for setting values
     * have been created to facilitate this.
     *
     * Nodes also contain methods for retrieving the name, id, cost the list of
     * edges that is it connected to, and the lines that it is a part of.
     */

    private String id;
    private String name;
    private List<EdgeADT> edges;
    private Integer cost;

    public Node(String id) {
        this.setId(id);
        this.edges = new ArrayList<>();
    }

    public Node(String id, String name) {
        this.setId(id);
        this.setName(name);
        this.edges = new ArrayList<>();
    }

    /**
     * Returns the Node ID.
     *
     * @return the node ID.
     */
    public String getId() { return id; }

    /**
     * Returns the name of the station.
     *
     * @return the name of the station.
     */
    public String getName() { return name; }

    /**
     * Returns the cost associated with travelling to the node.
     *
     * @return the cost associated with travelling to the node.
     */
    public Integer getCost(){ return cost;}

    /**
     * Returns all outbound edges of the node.
     *
     * @return list of outbound edges of the node.
     */
    public List<EdgeADT> getEdges() { return edges; }

    /**
     * getLines
     *
     * Loops through the list of edges, and adds the line that the edge lays on to a list
     * then returns that list, reduced to only containing distinct elements.
     *
     * @return list of distinct lines a node is on.
     */
    public List<String> getLines(){
        List<String> lines = new ArrayList<>();
        for (EdgeADT x : edges) {
            lines.add(x.getLine());
        }
        return lines.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Sets the ID of the Node.
     *
     * @param id the ID the Node is to be set to.
     */
    public void setId(String id) { this.id = id; }

    /**
     * Sets the name of the Node.
     *
     * @param name the name for the Node to be set to.
     */
    public void setName(String name) { this.name = name; }

    /**
     * Adds an edge to the Node.
     *
     * @param e the edge to be added.
     */
    public void addEdge(EdgeADT e) { edges.add(e); }

    /**
     * Sets the cost of travel of the Node.
     *
     * @param cost the cost of travel to the Node.
     */
    public void setCost(Integer cost){ this.cost = cost; }
}