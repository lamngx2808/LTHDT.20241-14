package PacketRouting;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Node { // will always be connecting to a router

  private static int idCounter = 0;
  private int id;
  private String name;
  private String ipAddress;
  private Connection default_gateway;
  private Queue<Packet> packets = new LinkedList<>();
  public Node(String name, String ipAddress) {
    super();
    this.id = idCounter++;
    this.name = name;
    this.ipAddress = ipAddress;
  }
  private ArrayList<Packet> receivePackets = new ArrayList<>();
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

  public Queue<Packet> getPackets() {return packets;}

  public void addPacket(Packet packet) {this.packets.add(packet);}
  public void addPacket(ArrayList<Packet> packets) {this.packets.addAll(packets);}
  public void removePacket(Packet packet) {this.packets.remove(packet);}
  public void removePacket(ArrayList<Packet> packets) {this.packets.removeAll(packets);}

  public void setDefault_gateway(Connection default_gateway) {
    this.default_gateway = default_gateway;
  }

  public Connection getDefault_gateway() {
    return default_gateway;
  }

  public ArrayList<Packet> getReceivedPackets(){return receivePackets;}

  public void tick(){
    while(!packets.isEmpty()){
      Packet packet = packets.poll();
      if(packet.getDestination().getIpAddress().equals(this.ipAddress)) receivePackets.add(packet);
      else
        default_gateway.addPacket(packet, this);
    }
  }
}
