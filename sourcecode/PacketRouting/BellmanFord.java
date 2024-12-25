package PacketRouting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BellmanFord extends RoutingAlgorithm {
	public BellmanFord() {
		this.setAlgorithmName("Bellman-Ford");
	}

	@Override
	public ArrayList<RoutingEntry> computeRoutingTable(Router router) {
		ArrayList<RoutingEntry> routingTable = new ArrayList<>();
		Map<Node, Integer> distances = new HashMap<>();
		Map<Node, Node> predecessors = new HashMap<>();
		ArrayList<Node> nodes = new ArrayList<>();

		for (Connection connection : router.getConnections()) {
			if (!nodes.contains(connection.getNode1())) {
				nodes.add(connection.getNode1());
			}
			if (!nodes.contains(connection.getNode2())) {
				nodes.add(connection.getNode2());
			}
		}

		for (Node node : nodes) {
			distances.put(node, Integer.MAX_VALUE);
			predecessors.put(node, null);
		}
		distances.put(router, 0);

		for (int i = 0; i < nodes.size() - 1; i++) {
			for (Connection connection : router.getConnections()) {
				Node u = connection.getNode1();
				Node v = connection.getNode2();
				int weight = connection.getLatency();

				if (distances.get(u) != Integer.MAX_VALUE && distances.get(u) + weight < distances.get(v)) {
					distances.put(v, distances.get(u) + weight);
					predecessors.put(v, u);
				}

				if (distances.get(v) != Integer.MAX_VALUE && distances.get(v) + weight < distances.get(u)) {
					distances.put(u, distances.get(v) + weight);
					predecessors.put(u, v);
				}
			}
		}

		for (Connection connection : router.getConnections()) {
			Node u = connection.getNode1();
			Node v = connection.getNode2();
			int weight = connection.getLatency();

			if (distances.get(u) != Integer.MAX_VALUE && distances.get(u) + weight < distances.get(v)) {
				throw new IllegalStateException("Graph contains a negative-weight cycle");
			}
		}

		for (Node destination : nodes) {
			if (!destination.equals(router)) {
				Node nextHop = getNextHop(predecessors, destination, router);
				Connection connection = getConnectionBetween(router, nextHop);
				if (connection != null) {
					routingTable.add(new RoutingEntry(destination, connection.getPort1(), nextHop,
							distances.get(destination), connection));
				}
			}
		}

		return routingTable;
	}

	private Node getNextHop(Map<Node, Node> predecessors, Node destination, Node source) {
		Node current = destination;
		while (predecessors.get(current) != null && !predecessors.get(current).equals(source)) {
			current = predecessors.get(current);
		}
		return current;
	}

	private Connection getConnectionBetween(Node node1, Node node2) {
		for (Connection connection : node1.getConnections()) {
			if (connection.getNode2().equals(node2)) {
				return connection;
			}
		}
		return null;
	}

	private void printDebugInfo(Map<Node, Integer> distances, Map<Node, Node> predecessors) {
		System.out.println("Node distances:");
		for (Map.Entry<Node, Integer> entry : distances.entrySet()) {
			System.out.println(entry.getKey().getName() + " -> " + entry.getValue());
		}

		System.out.println("Predecessors:");
		for (Map.Entry<Node, Node> entry : predecessors.entrySet()) {
			if (entry.getValue() != null) {
				System.out.println(entry.getKey().getName() + " <- " + entry.getValue().getName());
			} else {
				System.out.println(entry.getKey().getName() + " <- null");
			}
		}
	}
}
