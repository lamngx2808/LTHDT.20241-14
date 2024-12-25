package PacketRouting;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {

		Router routerA = new Router("A", "192.168.0.1", "00:11:22:33:44:55");
		Router routerB = new Router("B", "192.168.0.2", "00:11:22:33:44:66");
		Router routerC = new Router("C", "192.168.0.3", "00:11:22:33:44:77");
		Router routerD = new Router("D", "192.168.0.4", "00:11:22:33:44:88");
		Router routerE = new Router("E", "192.168.0.5", "00:11:22:33:44:99");

		Hub hub1 = new Hub("Hub1", "192.168.0.100", "00:11:22:33:AA:BB");
		Hub hub2 = new Hub("Hub2", "192.168.0.101", "00:11:22:33:CC:DD");

		Connection connectionAB = new Connection(routerA, routerB, 1);
		Connection connectionBC = new Connection(routerB, routerC, 2);
		Connection connectionAC = new Connection(routerA, routerC, 4);
		Connection connectionCD = new Connection(routerC, routerD, 1);
		Connection connectionDE = new Connection(routerD, routerE, 3);
		Connection connectionAE = new Connection(routerA, routerE, 10);
		Connection connectionAHub1 = new Connection(routerA, hub1, 2);
		Connection connectionHub1Hub2 = new Connection(hub1, hub2, 1);
		Connection connectionHub2D = new Connection(hub2, routerD, 3);

		routerA.addConnection(connectionAB);
		routerA.addConnection(connectionAC);
		routerA.addConnection(connectionAE);
		routerA.addConnection(connectionAHub1);
		routerB.addConnection(connectionAB);
		routerB.addConnection(connectionBC);
		routerC.addConnection(connectionAC);
		routerC.addConnection(connectionBC);
		routerC.addConnection(connectionCD);
		routerD.addConnection(connectionCD);
		routerD.addConnection(connectionDE);
		routerD.addConnection(connectionHub2D);
		routerE.addConnection(connectionDE);
		routerE.addConnection(connectionAE);
		hub1.addConnection(connectionAHub1);
		hub1.addConnection(connectionHub1Hub2);
		hub2.addConnection(connectionHub1Hub2);
		hub2.addConnection(connectionHub2D);

		BellmanFord bellmanFord = new BellmanFord();
		routerA.setRoutingAlgorithm(bellmanFord);

		try {
			ArrayList<RoutingEntry> routingTableA = bellmanFord.computeRoutingTable(routerA);

			System.out.println("Routing table for Router A:");
			for (RoutingEntry entry : routingTableA) {
				System.out.println("Destination: " + entry.getDestination().getName() + ", Next Hop: "
						+ entry.getNextHop().getName() + ", Cost: " + entry.getCost());
			}
		} catch (IllegalStateException e) {
			System.out.println("Error in computing routing table for Router A: " + e.getMessage());
		}

		try {
			routerC.setRoutingAlgorithm(bellmanFord);
			ArrayList<RoutingEntry> routingTableC = bellmanFord.computeRoutingTable(routerC);

			System.out.println("\nRouting table for Router C:");
			for (RoutingEntry entry : routingTableC) {
				System.out.println("Destination: " + entry.getDestination().getName() + ", Next Hop: "
						+ entry.getNextHop().getName() + ", Cost: " + entry.getCost());
			}
		} catch (IllegalStateException e) {
			System.out.println("Error in computing routing table for Router C: " + e.getMessage());
		}

		try {
			Connection connectionDC = new Connection(routerD, routerC, -5);
			routerD.addConnection(connectionDC);
			routerC.addConnection(connectionDC);

			routerA.setRoutingAlgorithm(bellmanFord);
			bellmanFord.computeRoutingTable(routerA);
		} catch (IllegalStateException e) {
			System.out.println("\nError: " + e.getMessage());
		}
	}
}
