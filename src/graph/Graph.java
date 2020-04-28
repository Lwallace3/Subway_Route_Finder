package graph;

import java.util.ArrayList;
import java.util.List;

public class Graph extends GraphADT{

    /**
     * Graph
     *
     * A graph is a simple class containing a list of nodes and edges,
     * along with methods to retrieve them.
     */

    List<NodeADT> nodes;
    List<EdgeADT> edges;

    public Graph(){

        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    /**
     * Returns the list of Nodes in the Graph.
     *
     * @return the list of Nodes in the Graph.
     */
    @Override
    public List<NodeADT> getNodes() { return nodes; }

    /**
     * Returns the list of Edges in the Graph.
     *
     * @return the list of Edges in the Graph.
     */
    @Override
    public List<EdgeADT> getEdges() { return edges; }

    /**
     * Adds a Node to the Graph.
     *
     * @param n Node to be added to the Graph.
     */
    @Override
    public void addNode(NodeADT n) { nodes.add(n); }

    /**
     * Adds an edge to the Graph.
     *
     * @param e Edge to be added to the Graph.
     */
    @Override
    public void addEdge(EdgeADT e) { edges.add(e); }


}
