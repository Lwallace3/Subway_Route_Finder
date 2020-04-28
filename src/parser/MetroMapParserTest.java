package parser;

import graph.*;
import org.junit.Test;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MetroMapParserTest {

    MetroMapParser mp = new MetroMapParser();
    GraphADT graphParse = mp.parse(new File("testfile"));
    List<NodeADT> nodesParsed = new ArrayList<>();
    List<NodeADT> nodesManual = new ArrayList<>();
    List<EdgeADT> edgeManual = new ArrayList<>();
    List<EdgeADT> edgeParsed = new ArrayList<>();


    public GraphADT makeGraphManually(){
        GraphADT graphManual = new Graph();

        Node node1 = new Node("1", "Node1");
        Node node2 = new Node("2", "Node2");
        Node node3 = new Node("3", "Node3");
        Node node4 = new Node("4", "Node4");
        Node node5 = new Node("5", "Node5");
        Node node6 = new Node("6", "Node6");

        Edge edge1_2 = new Edge(node1, "Orange");
        Edge edge2_1 = new Edge(node2, "Orange");

        Edge edge2_3 = new Edge(node2, "Orange");
        Edge edge3_2 = new Edge(node3, "Orange");

        Edge edge3_4 = new Edge(node3, "Orange");
        Edge edge4_3 = new Edge(node4, "Orange");

        Edge edge4_5 = new Edge(node4, "Orange");
        Edge edge5_4 = new Edge(node5, "Orange");

        Edge edge2_5 = new Edge(node2, "Blue");
        Edge edge5_2 = new Edge(node5, "Blue");

        Edge edge5_6 = new Edge(node5, "Blue");
        Edge edge6_5 = new Edge(node6, "Blue");


        node1.addEdge(edge2_1);
        node2.addEdge(edge1_2);
        node2.addEdge(edge3_2);
        node3.addEdge(edge2_3);
        node3.addEdge(edge4_3);
        node4.addEdge(edge3_4);
        node4.addEdge(edge5_4);
        node5.addEdge(edge4_5);
        node2.addEdge(edge5_2);
        node5.addEdge(edge2_5);
        node5.addEdge(edge6_5);
        node6.addEdge(edge5_6);

        graphManual.addNode(node1);
        graphManual.addNode(node2);
        graphManual.addNode(node3);
        graphManual.addNode(node5);
        graphManual.addNode(node4);
        graphManual.addNode(node6);

        graphManual.addEdge(edge2_1);
        graphManual.addEdge(edge1_2);
        graphManual.addEdge(edge3_2);
        graphManual.addEdge(edge5_2);
        graphManual.addEdge(edge2_3);
        graphManual.addEdge(edge4_3);
        graphManual.addEdge(edge3_4);
        graphManual.addEdge(edge5_4);
        graphManual.addEdge(edge4_5);
        graphManual.addEdge(edge2_5);
        graphManual.addEdge(edge6_5);
        graphManual.addEdge(edge5_6);



        return graphManual;
    }

    /**
     * This class is used to test the MetroMapParser class operates as expected
     * A graph is manually created in the makeGraphManually() class
     * This graph is known to be correct, and is compared against the parsed graph
     * Getter methods are tested for nodes and edges
     * */

    @Test
    public void parseTest(){

        GraphADT graphManual = makeGraphManually();
        nodesParsed = graphParse.getNodes();
        nodesManual = graphManual.getNodes();

        edgeManual = graphManual.getEdges();
        edgeParsed = graphParse.getEdges();


        for(int i = 0; i < nodesManual.size(); i++){

            assertEquals(nodesManual.get(i).getEdges().size(), nodesParsed.get(i).getEdges().size());
            assertEquals(nodesManual.get(i).getName(), nodesParsed.get(i).getName());
            assertEquals(nodesManual.get(i).getId(), nodesParsed.get(i).getId());

        }

        for(int i=0; i < edgeManual.size(); i++){

            assertEquals(edgeManual.get(i).getNode().getName(), edgeParsed.get(i).getNode().getName());
            assertEquals(edgeManual.get(i).getLine(), edgeParsed.get(i).getLine());
        }
    }

    @Test
    public void testParserReadsInAllNodesandEdges(){
        GraphADT graphManual = makeGraphManually();
        nodesParsed = graphParse.getNodes();
        nodesManual = graphManual.getNodes();

        edgeManual = graphManual.getEdges();
        edgeParsed = graphParse.getEdges();

        assertEquals(edgeManual.size(), edgeParsed.size());
        assertEquals(nodesManual.size(), nodesParsed.size());
    }


}