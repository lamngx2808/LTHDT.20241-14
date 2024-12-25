package PacketRouting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BellmanFord extends RoutingAlgorithm {
	private Map<Node, Integer> distances; // Khoảng cách từ nguồn đến các nút
	private Map<Node, Node> predecessors; // Nút trước đó trong đường đi

	public BellmanFord() {
		super();
		this.distances = new HashMap<>();
		this.predecessors = new HashMap<>();
		setAlgorithmName("Bellman-Ford");
	}

	@Override
	public ArrayList<RoutingEntry> computeRoutingTable(Router source) {
		computeShortestPath(source);
		return buildRoutingTable(source);
	}

	private void computeShortestPath(Node source) {
		initialize(source);

		// Duyệt qua tất cả các nút (|V| - 1) lần
		for (int i = 0; i < source.getNeighbors().size(); i++) {
			for (Connection connection : source.getConnections()) {
				relaxEdge(connection);
			}
		}

		// Kiểm tra chu trình trọng số âm
		for (Connection connection : source.getConnections()) {
			if (hasNegativeCycle(connection)) {
				throw new IllegalStateException("Negative weight cycle detected!");
			}
		}
	}

	private void initialize(Node source) {
		distances.clear();
		predecessors.clear();

		for (Node node : source.getNeighbors()) {
			distances.put(node, Integer.MAX_VALUE);
			predecessors.put(node, null);
		}
		distances.put(source, 0);
	}

	private void relaxEdge(Connection connection) {
		Node u = connection.getNode1();
		Node v = connection.getNode2();
		int weight = connection.getLatency();

		if (distances.get(u) != null && distances.get(u) + weight < distances.get(v)) {
			distances.put(v, distances.get(u) + weight);
			predecessors.put(v, u);
		}
	}

	private boolean hasNegativeCycle(Connection connection) {
		Node u = connection.getNode1();
		Node v = connection.getNode2();
		int weight = connection.getLatency();

		return distances.get(u) != null && distances.get(u) + weight < distances.get(v);
	}

	private ArrayList<RoutingEntry> buildRoutingTable(Router router) {
		ArrayList<RoutingEntry> routingTable = new ArrayList<>();

		for (Node destination : router.getNeighbors()) {
			if (destination.equals(router))
				continue;

			if (distances.get(destination) == null || distances.get(destination) == Integer.MAX_VALUE) {
				continue;
			}

			Node nextHop = getNextHop(router, destination);
			if (nextHop == null)
				continue;

			Connection connection = findConnection(router, nextHop);
			Port outgoingPort = null;

			if (connection != null) {
				if (connection.getNode1().equals(router)) {
					outgoingPort = connection.getPort1();
				} else if (connection.getNode2().equals(router)) {
					outgoingPort = connection.getPort2();
				}
			}

			RoutingEntry entry = new RoutingEntry(destination, outgoingPort, nextHop, distances.get(destination),
					connection);
			routingTable.add(entry);
		}

		return routingTable;
	}

	private Node getNextHop(Node source, Node destination) {
		Node current = destination;
		Node nextHop = null;

		while (predecessors.get(current) != null) {
			nextHop = current;
			current = predecessors.get(current);

			if (current.equals(source)) {
				break;
			}
		}

		return nextHop;
	}

	private Connection findConnection(Node source, Node target) {
		for (Connection connection : source.getConnections()) {
			if ((connection.getNode1().equals(source) && connection.getNode2().equals(target))
					|| (connection.getNode2().equals(source) && connection.getNode1().equals(target))) {
				return connection;
			}
		}
		return null;
	}
}
