package PacketRouting.Components;

import java.util.ArrayList;

public class Graph {
  private ArrayList<Node> nodeList = new ArrayList<>();

  public void addNode(Node c) {
    nodeList.add(c);
  }
}
