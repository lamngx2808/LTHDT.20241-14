package PacketRouting.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class HelpController {
  TextArea dijkstra;
  TextArea bellmanFord;
  TextArea flooding;

  @FXML
  private BorderPane borderPane;
  public HelpController() {

    dijkstra = new TextArea();
    dijkstra.setEditable(false);
    dijkstra.setWrapText(true);
    dijkstra.setFont(new Font(30));

    dijkstra.setText(
            "     Dijkstra's algorithm is a method for finding the shortest paths between nodes in a weighted graph. It is effective for graphs with non-negative edge weights and is used in various fields such as network routing, geographic information systems, and robotics.\n     The purpose of Dijkstra's algorithm is to determine the shortest path from a source node to all other nodes in a graph. It can also be adapted to find the shortest path to a specific destination node by stopping the process once that node is reached.\n     The basic steps of Dijkstra's algorithm are as follows:\n     1) Initialization: Start with a set of unvisited nodes. Assign a distance value to every node: the distance from the source node to itself is set to zero, while all other nodes are initialized to infinity since their distances are unknown initially.\n     2) Selection of Current Node: Select the unvisited node with the smallest tentative distance (initially, this will be the source node).\n     3) Updating Distances: For each neighbor of the current node, calculate the tentative distance from the source through the current node. If this distance is less than the previously recorded distance, update it. Mark the current node as visited, meaning its shortest distance is finalized.\n     4) Repeat: Repeat the selection and updating steps until all nodes have been visited or the shortest path to the target node has been found.\n     For example, consider a graph where nodes represent locations and edges represent distances between them. Starting at node A (distance = 0), you would examine its neighbors. If A connects to B (distance 2) and C (distance 5), you would update their distances. You would then move to B next (the closest unvisited), and update its neighbors. This process continues until all nodes are visited or the destination is reached.\n     Dijkstra's algorithm uses a greedy approach, always choosing the nearest unvisited vertex and updating paths accordingly. The time complexity can vary based on implementation. Using an adjacency matrix and simple lists results in O(V^2) complexity, while using priority queues results in O((V + E) log V) complexity, where V is the number of vertices and E is the number of edges.\n     However, Dijkstra's algorithm does not work with graphs that contain negative weight edges. In such cases, algorithms like Bellman-Ford should be used instead.\n     Applications of Dijkstra's algorithm include GPS navigation systems for route finding, network routing protocols like OSPF (Open Shortest Path First), and robotics for path planning in environments with obstacles. This algorithm remains one of the most fundamental techniques for solving shortest path problems in both theoretical and practical scenarios.");

    bellmanFord = new TextArea();
    bellmanFord.setEditable(false);
    bellmanFord.setWrapText(true);
    bellmanFord.setFont(new Font(30));

    bellmanFord.setText(
            "     The Bellman-Ford algorithm is a method used to find the shortest paths from a single source vertex to all other vertices in a weighted graph. " +
                    "It is particularly useful for graphs that may contain negative weight edges.\n" +
                    "     The algorithm works by initially overestimating the distance from the starting vertex to all other vertices. " +
                    "It then iteratively relaxes these estimates by checking all edges and updating the distances if a shorter path is found. " +
                    "The process is repeated for a total of V - 1 times, where V is the number of vertices in the graph. " +
                    "This ensures that the shortest path is found even if it involves multiple edges.\n" +
                    "     To implement the Bellman-Ford algorithm, you start by creating a distance array initialized to infinity for all vertices except the source vertex, which is set to zero. " +
                    "For each edge in the graph, if the distance to the destination vertex can be shortened by taking the edge from the source vertex, you update the distance. " +
                    "After V - 1 iterations, you can check for negative weight cycles by attempting to relax the edges one more time; if any distance can still be updated, it indicates a negative cycle exists.\n" +
                    "     The time complexity of the Bellman-Ford algorithm is O(V * E), where V is the number of vertices and E is the number of edges in the graph. " +
                    "This makes it less efficient than Dijkstra's algorithm for graphs without negative weights but more versatile in handling graphs with negative weights."
    );

    flooding = new TextArea();
    flooding.setEditable(false);
    flooding.setWrapText(true);
    flooding.setFont(new Font(30));
    flooding.setText(
            "     Flooding in computer networks is a routing technique used to send data packets to all nodes in a network without needing specific routing tables or paths. " +
                    "It operates on a simple principle: every router or node forwards incoming packets to all its neighbors except the source. " +
                    "To prevent infinite loops, mechanisms like hop limits or sequence numbers are used.\n" +
                    "     The flooding routing algorithm involves the following steps:\n" +
                    "     1) Initialize: A node receives a packet.\n" +
                    "     2) Check Condition: Verifies if the packet has reached its destination or meets a limit.\n" +
                    "     3) Forward Packet: Sends the packet to all neighbors except the sender.\n" +
                    "     4) Prevent Loop: Uses hop count or sequence number to avoid duplication.\n" +
                    "     5) Terminate: Stops forwarding when the packet reaches its destination.\n" +
                    "     Flooding is used in various applications such as routing protocols like OSPF (Open Shortest Path First), network broadcasts, emergency communication, and dynamic topologies like wireless ad-hoc networks. " +
                    "While flooding ensures that messages reach their destinations, it can generate a vast number of duplicate packets, so suitable damping mechanisms must be employed."
    );
  }
  @FXML
  public void displayFlooding(){
    borderPane.setCenter(flooding);
  }

  @FXML
  public void displayDijkstra(){
    borderPane.setCenter(dijkstra);
  }

  @FXML
  public void displayBellmanFord(){
    borderPane.setCenter(bellmanFord);
  }

  @FXML
  public void backToMain(){
    MainController.backToMainScene();
    System.out.println("ASD");
  }
}
