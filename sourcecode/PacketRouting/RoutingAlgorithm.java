package PacketRouting;

public abstract class RoutingAlgorithm {
	protected Graph graph;

	public RoutingAlgorithm(Graph graph) {
		this.graph = graph;
	}

	public abstract void initializeRoutingTable();

	public abstract void computeShortestPath(Node source, Node target);
}
