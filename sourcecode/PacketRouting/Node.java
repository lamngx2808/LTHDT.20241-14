package PacketRouting;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Node { // will always be connecting to a router
  private static int idCounter = 0; // Static counter for unique IDs
  private int id;
  private String name;
  private String ipAddress;
  private Connection default_gateway;

  // connections contain the connection to immediate node and the routing table contain the connection that is in a path to
  // the destination node
  private final ArrayList<RoutingEntry> routingTable = new ArrayList<>();
  private final ArrayList<Connection> connections = new ArrayList<>();
  private Queue<Packet> packets = new LinkedList<>();
  private ArrayList<Packet> receivePackets = new ArrayList<>();

  public Node(String name, String ipAddress) {
    super();
    this.id = idCounter++;
    this.name = name;
    this.ipAddress = ipAddress;
  }

  // Getters and Setters
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public Connection getDefault_gateway() {
    return default_gateway;
  }

  public void setDefault_gateway(Connection default_gateway) {
    this.default_gateway = default_gateway;
  }

  // Routing Table Methods
  public ArrayList<RoutingEntry> getRoutingTable() {
    return routingTable;
  }

  public void addRoutingEntry(RoutingEntry re) {
    routingTable.add(re);
  }

  public void addRoutingEntry(ArrayList<RoutingEntry> re) {
    routingTable.addAll(re);
  }

  public void removeRoutingEntry(RoutingEntry re) {
    routingTable.remove(re);
  }

  public void removeRoutingEntry(ArrayList<RoutingEntry> re) {
    routingTable.removeAll(re);
  }

  public void updateRoutingEntry(RoutingEntry pikachu, RoutingEntry mewtwo) {
    routingTable.remove(pikachu);
    routingTable.add(mewtwo);
  }

  // Packet Methods
  public Queue<Packet> getPackets() {
    return packets;
  }

  public void addPacket(Packet packet) {
    this.packets.add(packet);
  }

  public void addPacket(ArrayList<Packet> packets) {
    this.packets.addAll(packets);
  }

  public void removePacket(Packet packet) {
    this.packets.remove(packet);
  }

  public void removePacket(ArrayList<Packet> packets) {
    this.packets.removeAll(packets);
  }

  // Connection Methods
  public ArrayList<Connection> getConnections() {
    return connections;
  }

  public void addConnection(Connection connection) {
    connections.add(connection);
  }

  public void addConnection(ArrayList<Connection> connections) {
    this.connections.addAll(connections);
  }

  public void removeConnection(Connection connection) {
    connections.remove(connection);
  }

  public void removeConnection(ArrayList<Connection> connections) {
    this.connections.removeAll(connections);
  }

  // Received Packets
  public ArrayList<Packet> getReceivedPackets() {
    return receivePackets;
  }

  // Tick Method
  public void tick() {
    while (!packets.isEmpty()) {
      Packet packet = packets.poll();
      if (packet.getDestination().getIpAddress().equals(this.ipAddress))
        receivePackets.add(packet);
      else
        default_gateway.addPacket(packet, this);
    }
  }
}