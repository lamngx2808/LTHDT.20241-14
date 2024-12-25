package sourcecode.packetrouting.main;

import java.util.ArrayList;

import sourcecode.packetrouting.model.Port;
import sourcecode.packetrouting.model.connection.Connection;
import sourcecode.packetrouting.model.node.router.Router;
import sourcecode.packetrouting.model.node.router.RoutingEntry;
import sourcecode.packetrouting.model.routingalgorithm.RoutingAlgorithm;
import sourcecode.packetrouting.model.routingalgorithm.dijkstra.Dijkstra;

public class Main {

  public static void main(String[] args) {
    // Tạo các router
    Router routerA = new Router("RouterA", "192.168.1.1", "AA:BB:CC:DD:EE:01");
    Router routerB = new Router("RouterB", "192.168.1.2", "AA:BB:CC:DD:EE:02");
    Router routerC = new Router("RouterC", "192.168.1.3", "AA:BB:CC:DD:EE:03");
    Router routerD = new Router("RouterD", "192.168.1.4", "AA:BB:CC:DD:EE:04");
    Router routerE = new Router("RouterE", "192.168.1.5", "AA:BB:CC:DD:EE:05");

    // Tạo các cổng cho từng router
    Port portA1 = new Port("PortA1");
    Port portB1 = new Port("PortB1");
    Port portA2 = new Port("PortA2");
    Port portC1 = new Port("PortC1");
    Port portB2 = new Port("PortB2");
    Port portC2 = new Port("PortC2");
    Port portB3 = new Port("PortB3");
    Port portD1 = new Port("PortD1");
    Port portC3 = new Port("PortC3");
    Port portD2 = new Port("PortD2");
    Port portD3 = new Port("PortD3");
    Port portE1 = new Port("PortE1");

    // Tạo và thêm các kết nối vào các router
    Connection connAB = new Connection(routerA, routerB, portA1, portB1, 5);
    routerA.addConnection(connAB);
    routerB.addConnection(connAB);

    Connection connAC = new Connection(routerA, routerC, portA2, portC1, 4);
    routerA.addConnection(connAC);
    routerC.addConnection(connAC);

    Connection connBC = new Connection(routerB, routerC, portB2, portC2, 2);
    routerB.addConnection(connBC);
    routerC.addConnection(connBC);

    Connection connBD = new Connection(routerB, routerD, portB3, portD1, 6);
    routerB.addConnection(connBD);
    routerD.addConnection(connBD);

    Connection connCD = new Connection(routerC, routerD, portC3, portD2, 3);
    routerC.addConnection(connCD);
    routerD.addConnection(connCD);

    Connection connDE = new Connection(routerD, routerE, portD3, portE1, 3);
    routerD.addConnection(connDE);
    routerE.addConnection(connDE);

    // Áp dụng thuật toán Dijkstra
    RoutingAlgorithm dijkstra = new Dijkstra();
    ArrayList<RoutingEntry> routingTable = dijkstra.computeRoutingTable(routerA);

    // In bảng định tuyến
    System.out.println("Routing table for " + routerA.getName() + ":");
    for (RoutingEntry entry : routingTable) {
      System.out.println("Destination: " + entry.getDestination().getName() +
              ", Outgoing Port: " + entry.getOutGoingPort().getPortName() +
              ", NextHop: " + entry.getNextHop().getName() +
              ", Cost: " + entry.getCost());
    }
  }
}
