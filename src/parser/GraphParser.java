package parser;

import graph.GraphADT;
import java.io.File;

public interface GraphParser {
    GraphADT parse(File f);
}
