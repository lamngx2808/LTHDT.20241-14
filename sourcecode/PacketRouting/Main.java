package PacketRouting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		Node routerA = new Router("RouterA", "192.168.0.1");
		Node routerB = new Router("RouterB", "192.168.0.2");
		Node routerC = new Router("RouterC", "192.168.0.3");
		Node routerD = new Router("RouterD", "192.168.0.4");
		Node routerE = new Router("RouterE", "192.168.0.5");

		Node switch1 = new Switch("Switch1", "192.168.1.1");
		Node switch2 = new Switch("Switch2", "192.168.1.2");

		Node hub1 = new Hub("Hub1", "192.168.2.1");
		Node hub2 = new Hub("Hub2", "192.168.2.2");

		List<Connection> connections = new ArrayList<>();
		connections.add(new Connection(routerA, routerB, 4)); // A -> B
		connections.add(new Connection(routerB, routerC, 6)); // B -> C
		connections.add(new Connection(routerC, routerD, 5)); // C -> D
		connections.add(new Connection(routerA, switch1, 3)); // A -> Switch1
		connections.add(new Connection(switch1, routerE, 7)); // Switch1 -> E
		connections.add(new Connection(routerE, hub1, 2)); // E -> Hub1
		connections.add(new Connection(hub1, hub2, 1)); // Hub1 -> Hub2
		connections.add(new Connection(hub2, switch2, 2)); // Hub2 -> Switch2
		connections.add(new Connection(switch2, routerD, 3)); // Switch2 -> D
		connections.add(new Connection(routerB, switch2, 5)); // B -> Switch2

		List<Node> nodes = Arrays.asList(routerA, routerB, routerC, routerD, routerE, switch1, switch2, hub1, hub2);

		BellmanFord bellmanFord = new BellmanFord(nodes, connections);
		bellmanFord.computeShortestPath(routerA);
	}
}
