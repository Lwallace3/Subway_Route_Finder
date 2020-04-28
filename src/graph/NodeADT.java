package graph;

import java.util.List;

public interface NodeADT {

    String getId();
    String getName();
    List<EdgeADT> getEdges();
    List<String> getLines();
    Integer getCost();
    void setId(String id);
    void setName(String name);
    void addEdge(EdgeADT e);
    void setCost(Integer cost);
}
