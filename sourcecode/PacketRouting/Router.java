package PacketRouting;

import java.util.ArrayList;
import java.util.Queue;

public class Router extends Node {
  private RoutingAlgorithm routingAlgorithm;

  Router(String name, String ip){
    super(name, ip);
  }

  @Override
  public void tick(){
    Queue<Packet> packets = this.getPackets();
    while(!packets.isEmpty()){
      Packet packet = packets.poll();
      if(packet.getDestination().getIpAddress().equals(this.getIpAddress())) this.getReceivedPackets().add(packet);
      else{
        boolean found = false;
        for(RoutingEntry re: getRoutingTable())
          if(re.getDestination().equals(packet.getDestination().getIpAddress())) {
            re.getConnection().addPacket(packet, this);
            found = true;
            break;
          }

        if(!found)
          this.getDefault_gateway().addPacket(packet, this);
      }
    }
  }
}
