# Sprint 2 - Back to Basics 🚀
 - Date: 30/01/2020;
 - Time: 13:00;
 - Location: Livingstone Tower;


## Topic #1 - Directed Edges
One graph
Within the graph, Array of nodes
Within the nodes, Array of outgoing edges
Edge holds the line information
## Topic #2 - Using Packages and program Layout
Parser
 - Interfaces
    - Parser interface - public
    - 1 method - parse

Graph Package
  - Interfaces
   - Edge - Package Private
   - Node - Package Private
   - Graph - Public

Traversal package
 - Interface
   - Djikstra - public
   - 1 method - traverse

Driver package
 - 1 Class - public

Exception package
 - Exception class - public 

## Topic #3 - Line information
We have been informed that extra marks are given if weightings are added to the program - we will do this by assigning arbitrary weight information to lines. The low-level implementation of this was not discussed.
## Topic #4 - MIT Questions
Here are some quick-fire answers to the questions given out by MIT:

Will nodes be required only to satisfy the interface of .lang.Object? or will you design a  interface for nodes?

```A node interface will be created to ensure correctness```

Will the graph be implemented as a single class, or will there be a separate interface for the Graph specification and a class for the implementation?

```There will be an interface for the graph, which will contain the appropriate methods to ensure the correct implementation of the graph class.```

Will edges be objects in their own right? will they be visible to a client of the abstract type?

```Edges will be objects. They will be package visible.```

Will it be possible to find the successor of a node from the node alone, or will the graph be needed too? can a node belong to multiple graphs?

```Yes, as the node will contain the edges that will allow traversal.```

Would path-finding operations be included as methods of the graph, or should they be implemented in client code on top of the graph?

```The pathfinding will be implemented in its own class, with its own interface. ```

Will the type implement any standard collection interfaces?

```Yes - Arraylist```

Will the type use any standard collections in its implementation?
```Yes - Arraylist of nodes and edges within the nodes.```

## Topic #5 - AOB
 - JDoc commenting
 - Camelcase variable names
 - We need an abstract class
 - Add weightings to the edges


