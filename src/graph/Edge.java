package graph;

public class Edge implements EdgeADT{

    /**
     * Edge
     *
     * Edges are contained within nodes.
     *
     * They contain the other node that it is attached to, along with
     * a weight and a line on which it lays.
     *
     * Since the weight is added after the edge is constructed, it contains a setter,
     * along with getter methods for each of its values.
     */

    private NodeADT node;
    private Integer weight;
    private String line;

    public Edge(NodeADT n, String line) {
        this.node = n;
        this.line = line;
    }

    /**
     * Returns the Node that the Edge is pointing to.
     *
     * @return the Node that the Edge is pointing to.
     */
    public NodeADT getNode() { return node; }

    /**
     * Returns the line name of the Edge.
     *
     * @return the line name of the Edge.
     */
    public String getLine(){ return line; }

    /**
     * Returns the weight associated with traversing the edge.
     *
     * @return the weight associated with traversing the edge.
     */
    public Integer getWeight() { return weight; }

    /**
     * Sets the weight associated with traversing the edge.
     *
     * @param weight the weight the edge is to be set to.
     */
    public void setWeight(Integer weight) { this.weight = weight; }

}