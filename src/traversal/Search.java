package traversal;

import graph.NodeADT;

import java.util.ArrayList;

public interface Search {

    ArrayList<NodeADT> findPath(NodeADT source, NodeADT goal);
}

