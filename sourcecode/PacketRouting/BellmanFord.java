package PacketRouting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BellmanFord extends RoutingAlgorithm {

	public BellmanFord(List<Node> nodes, List<Connection> connections) {
		super("Bellman-Ford");
		this.nodes = nodes;
		this.connections = connections;
		this.distances = new HashMap<>();
		this.predecessors = new HashMap<>();
	}

	private List<Node> nodes;
	private List<Connection> connections;
	private Map<Node, Integer> distances;
	private Map<Node, Node> predecessors;

	@Override
	public void computeRoutingTable() {
		for (Node source : nodes) {
			try {
				computeShortestPath(source);
			} catch (IllegalStateException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	public void computeShortestPath(Node source) {
		initialize(source);

		for (int i = 0; i < nodes.size() - 1; i++) {
			for (Connection connection : connections) {
				relaxEdge(connection);
			}
		}

		for (Connection connection : connections) {
			if (isNegativeCycle(connection)) {
				throw new IllegalStateException("Negative weight cycle detected in the graph!");
			}
		}

		printRoutingTable(source);
	}

	private void initialize(Node source) {
		distances.clear();
		predecessors.clear();

		for (Node node : nodes) {
			distances.put(node, Integer.MAX_VALUE);
			predecessors.put(node, null);
		}
		distances.put(source, 0);
	}

	private void relaxEdge(Connection connection) {
		Node u = connection.getNode1();
		Node v = connection.getNode2();
		int weight = connection.getLatency();

		if (distances.get(u) != Integer.MAX_VALUE && distances.get(u) + weight < distances.get(v)) {
			distances.put(v, distances.get(u) + weight);
			predecessors.put(v, u);
		}
	}

	private boolean isNegativeCycle(Connection connection) {
		Node u = connection.getNode1();
		Node v = connection.getNode2();
		int weight = connection.getLatency();

		return distances.get(u) != Integer.MAX_VALUE && distances.get(u) + weight < distances.get(v);
	}

	private void printRoutingTable(Node source) {
		System.out.println("Routing Table for Node: " + source.getName());
		for (Node node : nodes) {
			if (distances.get(node) == Integer.MAX_VALUE) {
				System.out.println("Destination: " + node.getName() + " | Distance: Infinity | Path: No path");
			} else {
				System.out
						.print("Destination: " + node.getName() + " | Distance: " + distances.get(node) + " | Path: ");
				printPath(node);
				System.out.println();
			}
		}
	}

	private void printPath(Node target) {
		List<Node> path = new ArrayList<>();
		Node current = target;

		while (current != null) {
			path.add(0, current);
			current = predecessors.get(current);
		}

		for (int i = 0; i < path.size(); i++) {
			if (i > 0)
				System.out.print(" -> ");
			System.out.print(path.get(i).getName());
		}
	}
}
