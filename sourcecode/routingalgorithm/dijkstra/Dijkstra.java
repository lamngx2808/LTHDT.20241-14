package routingalgorithm.dijkstra;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import PacketRouting.Connection;
import PacketRouting.Node;
import PacketRouting.Port;
import PacketRouting.Router;
import PacketRouting.RoutingAlgorithm;
import PacketRouting.RoutingEntry;

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

            // Nếu tìm được nextHop, tạo RoutingEntry
            if (nextHop != null) {
                Connection connection = findConnection(source, nextHop);
                Port outgoingPort = connection.getPort1(); // Lấy cổng ra của source 

                RoutingEntry routingEntry = new RoutingEntry(
                    destination,       			// Destination
                    outgoingPort,      			// OutGoingPort
                    nextHop,           			// NextHop
                    connection.getLatency(),    // Cost
                    connection         			// Connection
                );
                routingTable.add(routingEntry);
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
	        if (conn.getNode2().equals(to)) {
	            return conn;
	        }
	    }
	    return null; // Không tìm thấy kết nối
	}
	
	public static void main(String[] args) {
	    // Tạo các router
	    Router routerA = new Router("RouterA", "192.168.1.1", "AA:BB:CC:DD:EE:01");
	    Router routerB = new Router("RouterB", "192.168.1.2", "AA:BB:CC:DD:EE:02");
	    Router routerC = new Router("RouterC", "192.168.1.3", "AA:BB:CC:DD:EE:03");
	    Router routerD = new Router("RouterD", "192.168.1.4", "AA:BB:CC:DD:EE:04");
	    Router routerE = new Router("RouterE", "192.168.1.5", "AA:BB:CC:DD:EE:05");

	    // Tạo các cổng cho từng router
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

	    // Tạo và thêm các kết nối vào các router
	    Connection connAB = new Connection(routerA, routerB, portA1, portB1, 5);
	    routerA.addConnection(connAB);
	    routerB.addConnection(connAB);

	    Connection connAC = new Connection(routerA, routerC, portA2, portC1, 4);
	    routerA.addConnection(connAC);
	    routerC.addConnection(connAC);

	    Connection connBC = new Connection(routerB, routerC, portB2, portC2, 2);
	    routerB.addConnection(connBC);
	    routerC.addConnection(connBC);

	    Connection connBD = new Connection(routerB, routerD, portB3, portD1, 6);
	    routerB.addConnection(connBD);
	    routerD.addConnection(connBD);

	    Connection connCD = new Connection(routerC, routerD, portC3, portD2, 3);
	    routerC.addConnection(connCD);
	    routerD.addConnection(connCD);
	    
	    Connection connDE = new Connection(routerD, routerE, portD3, portE1, 3);
	    routerD.addConnection(connDE);
	    routerE.addConnection(connDE);

	    // Áp dụng thuật toán Dijkstra
	    RoutingAlgorithm dijkstra = new Dijkstra();
	    ArrayList<RoutingEntry> routingTable = dijkstra.computeRoutingTable(routerA);

	    // In bảng định tuyến
	    System.out.println("Routing table for " + routerA.getName() + ":");
	    for (RoutingEntry entry : routingTable) {
	        System.out.println("Destination: " + entry.getDestination().getName() +
	                           ", Outgoing Port: " + entry.getOutGoingPort().getPortName() +
	                           ", NextHop: " + entry.getNextHop().getName() +
	                           ", Cost: " + entry.getCost());
	    }
	}

}