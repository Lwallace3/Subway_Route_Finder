package traversal;

import graph.*;
import org.junit.Test;
import parser.MetroMapParser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class UniformCostSearchTest {

    MetroMapParser mp = new MetroMapParser();
    GraphADT graph = mp.parse(new File("testfile"));
    UniformCostSearch search = new UniformCostSearch(graph);
    ArrayList<NodeADT> path = new ArrayList<>();

    /**
     * Used to take in a valid station name and return the Node object which matches that name
     * So that it can be passed into the search class
     * @param nodeName
     * The name of the Node which is to be returned
     * @return
     * The Node object for the requested station pulled from the list of stations in the graph
     */
    private NodeADT getValidNode(String nodeName) {
        List<NodeADT> stations = graph.getNodes();

        for (NodeADT station : stations) {
            if (station.getName().toUpperCase().equals(nodeName.toUpperCase())) {
                return station;
            }
        }
        return stations.get(stations.size() - 1);
    }



    /**
     * This test is used to check if a path is found between Node1 and Node6...
     * */
    @Test
    public void testNode1toNode6(){



        path.add(getValidNode("Node1"));
        path.add(getValidNode("Node2"));
        path.add(getValidNode("Node5"));
        path.add(getValidNode("Node6"));


        //System.out.println(search.findPath(getValidNode("Node1"), getValidNode("Node3")));

        //Path from Node 1 to Node 6
        assertEquals(path, search.findPath(getValidNode("Node1"), getValidNode("Node6")));

    }

    /**
     * This test is used to check if a path is found between Node5 and Node1...
     * */
    @Test
    public void testNode1toNode5(){


        path.add(getValidNode("Node5"));
        path.add(getValidNode("Node2"));
        path.add(getValidNode("Node1"));

        //System.out.println(search.findPath(getValidNode("Node1"), getValidNode("Node3")));

        //Path from Node 1 to Node 5
        assertEquals(path, search.findPath(getValidNode("Node5"), getValidNode("Node1")));

    }

    /**
     * This test is used to check if a path is found between Node1 and Node3...
     * */
    @Test
    public void testNode1toNode3(){


        path.add(getValidNode("Node1"));
        path.add(getValidNode("Node2"));
        path.add(getValidNode("Node3"));

        //System.out.println(search.findPath(getValidNode("Node1"), getValidNode("Node3")));

        //Path from Node 1 to Node 3
        assertEquals(path, search.findPath(getValidNode("Node1"), getValidNode("Node3")));

    }

    /**
     * This test is used to check if a path is found between Node6 and Node2...
     * */
    @Test
    public void testNode6toNode2(){


        path.add(getValidNode("Node6"));
        path.add(getValidNode("Node5"));
        path.add(getValidNode("Node2"));

        //System.out.println(search.findPath(getValidNode("Node1"), getValidNode("Node3")));

        //Path from Node 6 to 2
        assertEquals(path, search.findPath(getValidNode("Node6"), getValidNode("Node2")));

    }

    /**
     * This test is used to check if a path is found between Node3 and Node5...
     * */
    @Test
    public void testNode3toNode5(){



        path.add(getValidNode("Node3"));
        path.add(getValidNode("Node4"));
        path.add(getValidNode("Node5"));

        //System.out.println(search.findPath(getValidNode("Node1"), getValidNode("Node3")));

        //Path from Node 1 to Node 6
        assertEquals(path, search.findPath(getValidNode("Node3"), getValidNode("Node5")));

    }
}