package PacketRouting;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		// Tạo các nút
		Node nodeA = new Node("A", "192.168.0.1");
		Node nodeB = new Node("B", "192.168.0.2");
		Node nodeC = new Node("C", "192.168.0.3");

		// Tạo đồ thị
		List<Node> nodes = new ArrayList<>(List.of(nodeA, nodeB, nodeC));
		List<Connection> connections = new ArrayList<>();

		// Tạo các kết nối
		connections.add(new Connection(nodeA, nodeB, 4));
		connections.add(new Connection(nodeB, nodeC, 3));
		connections.add(new Connection(nodeA, nodeC, 5));

		// Chạy thuật toán Bellman-Ford
		BellmanFord bellmanFord = new BellmanFord(nodes, connections);
		bellmanFord.computeRoutingTable();
	}
}
