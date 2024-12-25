package PacketRouting;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		// Tạo các Router
		Router routerA = new Router("RouterA", "192.168.1.1", "AA:BB:CC:DD:EE:01");
		Router routerB = new Router("RouterB", "192.168.1.2", "AA:BB:CC:DD:EE:02");
		Router routerC = new Router("RouterC", "192.168.1.3", "AA:BB:CC:DD:EE:03");
		Router routerD = new Router("RouterD", "192.168.1.4", "AA:BB:CC:DD:EE:04");
		Router routerE = new Router("RouterE", "192.168.1.5", "AA:BB:CC:DD:EE:05");
		Router routerF = new Router("RouterF", "192.168.1.6", "AA:BB:CC:DD:EE:06");
		Router routerG = new Router("RouterG", "192.168.1.7", "AA:BB:CC:DD:EE:07");
		Router routerH = new Router("RouterH", "192.168.1.8", "AA:BB:CC:DD:EE:08");

		// Tạo các Port
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

		// Tạo các kết nối
		routerA.addConnection(new Connection(routerA, routerB, portA1, portB1, 5));
		routerA.addConnection(new Connection(routerA, routerC, portA2, portC1, 4));
		routerB.addConnection(new Connection(routerB, routerC, portB2, portC2, -2));
		routerB.addConnection(new Connection(routerB, routerD, portB3, portD1, 6));
		routerC.addConnection(new Connection(routerC, routerD, portC3, portD2, -1));
		routerD.addConnection(new Connection(routerD, routerE, portD3, portE1, 3));
		routerE.addConnection(new Connection(routerE, routerF, portF1, portF2, 5));
		routerF.addConnection(new Connection(routerF, routerG, portG1, portH1, -1));
		routerG.addConnection(new Connection(routerG, routerH, portH1, portH2, -2));
		routerC.addConnection(new Connection(routerC, routerH, portC2, portH1, 8));
		routerA.addConnection(new Connection(routerA, routerF, portA1, portF1, 15));

		// Khởi tạo thuật toán Bellman-Ford
		BellmanFord bellmanFord = new BellmanFord();

		// Tính toán bảng định tuyến cho từng Router
		ArrayList<Router> routers = new ArrayList<>();
		routers.add(routerA);
		routers.add(routerB);
		routers.add(routerC);
		routers.add(routerD);
		routers.add(routerE);
		routers.add(routerF);
		routers.add(routerG);
		routers.add(routerH);

		for (Router router : routers) {
			ArrayList<RoutingEntry> routingTable = bellmanFord.computeRoutingTable(router);

			System.out.println("\nRouting Table for " + router.getName() + ":");
			for (RoutingEntry entry : routingTable) {
				System.out.println("Destination: " + entry.getDestination().getName() + ", Next Hop: "
						+ (entry.getNextHop() != null ? entry.getNextHop().getName() : "None") + ", Cost: "
						+ entry.getCost() + ", Outgoing Port: "
						+ (entry.getOutGoingPort() != null ? entry.getOutGoingPort().getPortName() : "None"));
			}
		}
	}
}
