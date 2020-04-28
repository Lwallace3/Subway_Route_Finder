package graph;


public interface EdgeADT {
    NodeADT getNode();
    Integer getWeight();
    String getLine();
    void setWeight(Integer w);
}
