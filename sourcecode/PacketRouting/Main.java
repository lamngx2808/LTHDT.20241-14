package PacketRouting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	public static void main(String[] args) {

		Router routerA = new Router("RouterA", "192.168.1.1", "AA:BB:CC:DD:EE:01");
		Router routerB = new Router("RouterB", "192.168.1.2", "AA:BB:CC:DD:EE:02");
		Router routerC = new Router("RouterC", "192.168.1.3", "AA:BB:CC:DD:EE:03");
		Router routerD = new Router("RouterD", "192.168.1.4", "AA:BB:CC:DD:EE:04");
		Router routerE = new Router("RouterE", "192.168.1.5", "AA:BB:CC:DD:EE:05");
		Router routerF = new Router("RouterF", "192.168.1.6", "AA:BB:CC:DD:EE:06");
		Router routerG = new Router("RouterG", "192.168.1.7", "AA:BB:CC:DD:EE:07");
		Router routerH = new Router("RouterH", "192.168.1.8", "AA:BB:CC:DD:EE:08");

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
		Port portF1 = new Port("PortF1");
		Port portF2 = new Port("PortF2");
		Port portG1 = new Port("PortG1");
		Port portH1 = new Port("PortH1");
		Port portH2 = new Port("PortH2");

		Connection connAB = new Connection(routerA, routerB, portA1, portB1, 5);
		Connection connAC = new Connection(routerA, routerC, portA2, portC1, 4);
		Connection connBC = new Connection(routerB, routerC, portB2, portC2, -2);
		Connection connBD = new Connection(routerB, routerD, portB3, portD1, 6);
		Connection connCD = new Connection(routerC, routerD, portC3, portD2, -1);
		Connection connDE = new Connection(routerD, routerE, portD3, portE1, 3);
		Connection connEF = new Connection(routerE, routerF, portF1, portF2, 5);
		Connection connFG = new Connection(routerF, routerG, portG1, portH1, -1);
		Connection connGH = new Connection(routerG, routerH, portH1, portH2, -2);
		Connection connCH = new Connection(routerC, routerH, portC2, portH1, 8);
		Connection connAF = new Connection(routerA, routerF, portA1, portF1, 15); // Trọng số lớn để kiểm tra logic

		List<Connection> connections = Arrays.asList(connAB, connAC, connBC, connBD, connCD, connDE, connEF, connFG,
				connGH, connCH, connAF);

		routerA.addConnection(connAB);
		routerA.addConnection(connAC);
		routerA.addConnection(connAF);
		routerB.addConnection(connAB);
		routerB.addConnection(connBC);
		routerB.addConnection(connBD);
		routerC.addConnection(connAC);
		routerC.addConnection(connBC);
		routerC.addConnection(connCD);
		routerC.addConnection(connCH);
		routerD.addConnection(connBD);
		routerD.addConnection(connCD);
		routerD.addConnection(connDE);
		routerE.addConnection(connDE);
		routerE.addConnection(connEF);
		routerF.addConnection(connEF);
		routerF.addConnection(connFG);
		routerF.addConnection(connAF);
		routerG.addConnection(connFG);
		routerG.addConnection(connGH);
		routerH.addConnection(connGH);
		routerH.addConnection(connCH);

		List<Node> nodes = Arrays.asList(routerA, routerB, routerC, routerD, routerE, routerF, routerG, routerH);

		BellmanFord bellmanFord = new BellmanFord(nodes, connections);
		ArrayList<RoutingEntry> routingTable = bellmanFord.computeRoutingTable(routerA);

		System.out.println("\nRouting Table for RouterA:");
		for (RoutingEntry entry : routingTable) {
			System.out.println("Destination: " + entry.getDestination().getName() + ", Next Hop: "
					+ (entry.getNextHop() != null ? entry.getNextHop().getName() : "None") + ", Cost: "
					+ entry.getCost() + ", Outgoing Port: "
					+ (entry.getOutGoingPort() != null ? entry.getOutGoingPort().getPortName() : "None"));
		}
	}
}
