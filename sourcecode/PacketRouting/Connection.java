package PacketRouting;

import java.util.ArrayList;

public class Connection {
  private Node node1;
  private Node node2;
  private int latency; // the amount of time unit this connection needs to send 1 unit of data from
                       // this node to the other node.
  private ArrayList<Packet> packets = new ArrayList<>();
  private ArrayList<Integer> progress = new ArrayList<>();
  private ArrayList<Integer> dest = new ArrayList<>();
  // the 3 array list create a table with 3 cols of packets that is on the connection, the progress will
  // the time unit it's spent on the connection, if the progress == latency, the packet will be delivered to the
  // destination node and removed from the connection arrays. the dest array denote whether the destination node is
  // node 1 or node 2
  public Connection(Node node1, Node node2, int latency) {
    super();
    this.node1 = node1;
    this.node2 = node2;
    node1.addConnection(this);
    node2.addConnection(this);
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

  public void addPacket(Packet packet, Node sender) {
    packets.add(packet);
    progress.add(0);
    if(sender == this.node1) dest.add(1);
    else dest.add(2);
  }

  public void tick(){
    for(int i = 0; i < packets.size(); i++){
      progress.set(i, progress.get(i)+1);
      if(progress.get(i) == latency){
        if(dest.get(i) == 1) node1.addPacket(packets.get(i));
        else node2.addPacket(packets.get(i));
        packets.remove(i);
        progress.remove(i);
        dest.remove(i);
        i--;
      }
    }
  }
}
