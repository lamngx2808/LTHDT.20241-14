package PacketRouting;

import java.util.ArrayList;

public class Connection {
  private Node node1;
  private Node node2;
  private int latency; // the amount of time unit this connection needs to send 1 unit of data from
                       // this node to the other node.
  private ArrayList<Packet> packets = new ArrayList<>();

  private ArrayList<Integer> progress = new ArrayList<>();

  public Connection(Node node1, Node node2, int latency) {
    super();
    this.node1 = node1;
    this.node2 = node2;
    this.latency = latency;
  }

  public Node getNode1() {
    return node1;
  }

  public Node getNode2() {
    return node2;
  }

  public int getLatency() {
    return latency;
  }

  public void setLatency(int l) {
    this.latency = l;
  }

}
