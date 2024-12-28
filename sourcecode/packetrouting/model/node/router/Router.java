package sourcecode.packetrouting.model.node.router;

import java.util.ArrayList;

import sourcecode.packetrouting.model.Packet;
import sourcecode.packetrouting.model.Port;
import sourcecode.packetrouting.model.node.Node;
import sourcecode.packetrouting.model.routingalgorithm.RoutingAlgorithm;
import sourcecode.packetrouting.model.routingalgorithm.bellmanford.BellmanFord;
import sourcecode.packetrouting.model.routingalgorithm.dijkstra.Dijkstra;

public class Router extends Node {
	private ArrayList<Port> ports;
	private String routingAlgorithm;
	private ArrayList<RoutingEntry> routingTable = new ArrayList<>();
	
	// Constructor
	public Router(String name, String ipAddress) {
	    super(name, ipAddress);
	}
	
	public Router(String name, String ipAddress, String macAddress) {
		super(name, ipAddress, macAddress);
	}
	
	public Router(String name, String ipAddress, String macAddress, Node defaultGateway) {
		super(name, ipAddress, macAddress, defaultGateway);
	}

	// Getter and Setter
	public String getRoutingAlgorithm() {
		return routingAlgorithm;
	}

	public void setRoutingAlgorithm(String routingAlgorithm) {
		this.routingAlgorithm = routingAlgorithm;
	}

	public ArrayList<Port> getPorts() {
		return ports;
	}
	
	public void setPorts(ArrayList<Port> ports) {
		this.ports = ports;
	}

	public ArrayList<RoutingEntry> getRoutingTable() {
	    return routingTable;
	}
	
	// Port methods
	// Remove when ports is broken
	public void removePort(Port...ports) {
		for (Port port : ports) {
			this.ports.remove(port);
		}
	}
	
	// Routing methods
	@Override
	public ArrayList<Node> routePacket(Packet packet) {
		System.out.println("Starting routePacket for packet: " + packet.getPayload());

	    // Debugging: Kiểm tra xem routingAlgorithm có giá trị hợp lệ không
	    System.out.println("Routing algorithm: " + routingAlgorithm);
		if (routingAlgorithm.equals("Dijkstra")) {
			RoutingAlgorithm dijkstra = new Dijkstra();
			System.out.println("Running Dijkstra algorithm...");
			this.routingTable = dijkstra.computeRoutingTable(this);
			
			// Debugging: In ra giá trị routingTable sau khi tính toán
	        if (routingTable == null) {
	            System.out.println("Error: routingTable is null after Dijkstra computation.");
	            return new ArrayList<Node>();
	        } else {
	            System.out.println("Routing table size: " + routingTable.size());
	        }
		} else if (routingAlgorithm.equals("BellmanFord")) {
			RoutingAlgorithm bellmanFord = new BellmanFord();
			System.out.println("Running BellmanFord algorithm...");
			this.routingTable = bellmanFord.computeRoutingTable(this);
			
			// Debugging: In ra giá trị routingTable sau khi tính toán
			if (routingTable == null) {
	            System.out.println("Error: routingTable is null after BellmanFord computation.");
	            return new ArrayList<Node>();
	        } else {
	            System.out.println("Routing table size: " + routingTable.size());
	        }
		}
		
	    ArrayList<Node> nodes = new ArrayList<>();
	    for(RoutingEntry entry : routingTable) {
	    	System.out.println("Checking entry: " + entry);
	    	
	    	// Kiểm tra nếu entry.getConnection() hoặc node1, node2 là null
            if (entry.getConnection() != null) {
                Node node1 = entry.getConnection().getNode1();
                Node node2 = entry.getConnection().getNode2();

                if (node1 != null && node2 != null) {
                    // Kiểm tra nếu node1 hoặc node2 có điểm đến trùng với gói tin
                    if (packet.getDestination().equals(entry.getDestination())) {
                        Node nextNode = node1 == this ? node2 : node1;
                        nodes.add(nextNode);
                        System.out.println("Adding node to route: " + nextNode.getName());
                    }
                } else {
                    System.out.println("Error: One of the nodes in the connection is null.");
                }
            } else {
                System.out.println("Error: Connection is null in routing entry.");
            }
	    }
	    return nodes;
	}
	
	public void addEntry(RoutingEntry entry) {
	    routingTable.add(entry);
	}
	public void removeEntry(RoutingEntry entry) {
	    routingTable.remove(entry);
	}
	public void updateEntry(RoutingEntry oldEntry, RoutingEntry newEntry){
	    routingTable.remove(oldEntry);
	    routingTable.add(newEntry);
	}
}