package sourcecode.packetrouting.model.routingalgorithm.dijkstra;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import sourcecode.packetrouting.model.Port;
import sourcecode.packetrouting.model.connection.Connection;
import sourcecode.packetrouting.model.node.Node;
import sourcecode.packetrouting.model.node.router.Router;
import sourcecode.packetrouting.model.node.router.RoutingEntry;
import sourcecode.packetrouting.model.routingalgorithm.RoutingAlgorithm;

public class Dijkstra extends RoutingAlgorithm {
	public Dijkstra() {
        this.setAlgorithmName("Dijkstra");
    }
	
	@Override
	public ArrayList<RoutingEntry> computeRoutingTable(Router source) {
	    // Khởi tạo Map để lưu khoảng cách từ nguồn đến các nút khác
	    Map<Node, Integer> distances = new HashMap<>();
	    Map<Node, Node> previousNodes = new HashMap<>();
	    PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));

	    // Khởi tạo khoảng cách ban đầu cho source và đưa vào hàng đợi ưu tiên
	    distances.put(source, 0);
	    pq.add(source);

	    // Áp dụng thuật toán Dijkstra
	    while (!pq.isEmpty()) {
	        Node current = pq.poll();

	        // Duyệt qua danh sách kề của current node
	        for (AdjListNode neighborNode : createAdjacencyList(current)) {
	            Node neighbor = neighborNode.getVertex();
	            int newDist = distances.getOrDefault(current, Integer.MAX_VALUE) + neighborNode.getWeight();

	            // Cập nhật khoảng cách và đường đi nếu tìm được đường đi ngắn hơn
	            if (newDist < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
	                distances.put(neighbor, newDist);
	                previousNodes.put(neighbor, current);
	                pq.add(neighbor);
	            }
	        }
	    }

	    // Tạo bảng định tuyến (Routing Table)
	    ArrayList<RoutingEntry> routingTable = new ArrayList<>();

	    // Duyệt qua tất cả các node được tìm thấy trong distances để tạo RoutingEntry
	    for (Map.Entry<Node, Integer> entry : distances.entrySet()) {
	        Node destination = entry.getKey();
            Node nextHop = getNextHop(source, destination, previousNodes);
            System.out.println("NextHop cho " + destination + "là: " + nextHop);
            // Nếu tìm được nextHop, tạo RoutingEntry
            if (nextHop != null) {
                Connection connection = findConnection(source, nextHop);
                if (connection == null) {
                	System.out.println("Null roi");
                }
                
                if (connection != null) {
	                Port outgoingPort = connection.getPort1(); // Lấy cổng ra của source 
	
	                RoutingEntry routingEntry = new RoutingEntry(
	                    destination,       			// Destination
	                    outgoingPort,      			// OutGoingPort
	                    nextHop,           			// NextHop
	                    connection.getLatency(),    // Cost
	                    connection         			// Connection
	                );
	                routingTable.add(routingEntry);
                } else {
                    System.out.println("Không có kết nối hợp lệ giữa " + source + "và " + nextHop);
                }
            }
	    }

	    return routingTable;
	}
	
	public ArrayList<AdjListNode> createAdjacencyList(Node node) {
        ArrayList<AdjListNode> adjacencyList = new ArrayList<>();
        
        // Lấy danh sách các đỉnh hàng xóm (neighbors) từ node
        ArrayList<Connection> connections = node.getConnections();
        
        // Duyệt qua các liên kết để tạo danh sách kề
        for (Connection connection : connections) {
            Node neighbor = connection.getNode2(); // Lấy đỉnh kế tiếp
            int weight = connection.getLatency(); // Trọng số của cạnh
            
            // Tạo một AdjListNode từ đỉnh và trọng số
            AdjListNode adjListNode = new AdjListNode(neighbor, weight);
            adjacencyList.add(adjListNode);
        }
        
        return adjacencyList;
    }
	
	private Node getNextHop(Node source, Node destination, Map<Node, Node> previousNodes) {
	    if (source.equals(destination)) return null;
	    
		Node current = destination;
	    Node nextHop = null;

	    // Dò ngược từ destination về source
	    while (current != null && !current.equals(source)) {
	        nextHop = current;
	        current = previousNodes.get(current);
	    }

	    return nextHop;
	}
	
	private Connection findConnection(Node from, Node to) {
	    for (Connection conn : from.getConnections()) {
	    	System.out.println("Kiểm tra kết nối giữa " + from + "và " + to);
	    	
	        if (conn.getNode1().equals(from) && conn.getNode2().equals(to)) {
	        	System.out.println("Tìm thấy kết nối hợp lệ: " + conn);
	            return conn;
	        } 
	    }
	    
	    System.out.println("Không tìm thấy kết nối hợp lệ giữa " + from + "và " + to);
	    return null; // Không tìm thấy kết nối
	}
	


}