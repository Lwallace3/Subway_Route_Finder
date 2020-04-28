package graph;

import java.util.List;

public abstract class GraphADT {
    public abstract List<NodeADT> getNodes();
    public abstract List<EdgeADT> getEdges();
    public abstract void addNode(NodeADT n);
    public abstract void addEdge(EdgeADT e);
}
