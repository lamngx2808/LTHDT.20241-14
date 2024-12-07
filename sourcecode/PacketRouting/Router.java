package PacketRouting;

import java.util.ArrayList;
import java.util.Queue;

public class Router extends Node {
  private RoutingAlgorithm routingAlgorithm;
  Router(String name, String ip){
    super(name, ip);
  }

  private ArrayList<RoutingEntry> routingTable = new ArrayList<>();

  public ArrayList<RoutingEntry> getRoutingTable() {
    return routingTable;
  }

  public void addRoutingEntry(RoutingEntry re){routingTable.add(re);}
  public void addRoutingEntry(ArrayList<RoutingEntry> re){routingTable.addAll(re);}

  public void removeRoutingEntry(RoutingEntry re){routingTable.remove(re);}
  public void removeRoutingEntry(ArrayList<RoutingEntry> re){routingTable.removeAll(re);}

  public void updateRoutingEntry(RoutingEntry pikachu, RoutingEntry mewtwo){
    routingTable.remove(pikachu);
    routingTable.add(mewtwo);
  }

  @Override
  public void tick(){
    Queue<Packet> packets = this.getPackets();
    while(!packets.isEmpty()){
      Packet packet = packets.poll();
      if(packet.getDestination().getIpAddress().equals(this.getIpAddress())) this.getReceivedPackets().add(packet);
      else{
        boolean found = false;
        for(RoutingEntry re: routingTable){
          if(re.getDestination().equals(packet.getDestination().getIpAddress())) {
            re.getConnection().addPacket(packet, this);
            found = true;
            break;
          }
        }
        if(!found){
          this.getDefault_gateway().addPacket(packet, this);
        }
      }
    }
  }
}
