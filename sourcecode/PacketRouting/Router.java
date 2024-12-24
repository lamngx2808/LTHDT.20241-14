package PacketRouting;

import java.util.ArrayList;
import java.util.Queue;

public class Router extends Node {
  private RoutingAlgorithm routingAlgorithm;
  private ArrayList<RoutingEntry> routingTable = new ArrayList<>();
  // Constructor
  public Router(String name, String ip) {
    super(name, ip);
  }

  @Override
  public ArrayList<Node> routePacket(Packet packet) {
    ArrayList<Node> nodes = new ArrayList<>();
    for(RoutingEntry entry : routingTable) {
      if(packet.getDestination().equals(entry.getDestination())) {
        nodes.add(entry.getConnection().getNode1() == this ? entry.getConnection().getNode2() : entry.getConnection().getNode1());
      }
    }
    return nodes;
  }

  public void addEntry(RoutingEntry entry) {
    routingTable.add(entry);
  }
  public void removeEntry(RoutingEntry entry) {
    routingTable.remove(entry);
  }
  public void updateEntry(RoutingEntry old, RoutingEntry n){
    routingTable.remove(old);
    routingTable.add(n);
  }

  public ArrayList<RoutingEntry> getRoutingTable() {
    return routingTable;
  }
}