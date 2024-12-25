package PacketRouting.Components;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Node { 
	private static int idCounter = 0; // Static counter for unique IDs
	private int id;
	private String name;
	private String ipAddress;
	private String macAddress;
	private Node defaultGateway;
	  
	private ArrayList<Connection> connections = new ArrayList<>();
	private Queue<Packet> packets = new LinkedList<>();
	private ArrayList<Packet> receivedPackets = new ArrayList<>();

	// Constructor
	
	public Node(String name, String macAddress) {
		super();
		this.id = idCounter++;
		this.name = name;
		this.macAddress = macAddress;
	}
	
	public Node(String name, String ipAddress, String macAddress) {
		super();
		this.id = idCounter++;
		this.name = name;
		this.ipAddress = ipAddress;
		this.macAddress = macAddress;
	}

	public Node(String name, String ipAddress, String macAddress, Node defaultGateway) {
		super();
		this.id = idCounter++;
		this.name = name;
		this.ipAddress = ipAddress;
		this.macAddress = macAddress;
		this.defaultGateway = defaultGateway;
		Connection conn = new Connection(this, defaultGateway);
		this.addConnection(conn);
		defaultGateway.addConnection(conn);
	}

	// Getter and Setter
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public String getMacAddress() {
	    return macAddress;
	}

	public void setMacAddress(String macAddress) {
	    this.macAddress = macAddress;
	}

	public Node getDefaultGateway() {
		return defaultGateway;
	}

	public void setDefaultGateway(Node defaultGateway) {
		this.defaultGateway = defaultGateway;
	}

	public ArrayList<Connection> getConnections() {
		return connections;
	}

	public Queue<Packet> getPackets() {
		return packets;
	}

	public ArrayList<Packet> getReceivedPackets() {
		return receivedPackets;
	}
	
	// Connection methods
	public void addConnection(Connection connection) {
        if (connection.getNode1().getId() == this.id) {
            this.connections.add(connection);
        } else if (connection.getNode2().getId() == this.id) {
            Connection reversedConnection = new Connection(this, connection.getNode1(), connection.getLatency());
            this.connections.add(reversedConnection);
        }
    }
	
	public void removeConnection(Connection connection) {
		connections.remove(connection);
	}
	
	// Packet methods
	public void addPacket(Packet packet) {
		packets.add(packet);
	}
	
	public void removePacket(Packet packet) {
		packets.remove(packet);
	}
	
	public abstract ArrayList<Node> routePacket(Packet packet);
	
	public void broadcast(Packet packet) {
		for (Connection conn : this.getConnections()) {
			conn.getNode2().addPacket(packet);
		}
		
		removePacket(packet);
        return;
    }
	
	public void sendPacket(Packet packet) {
		// Nếu đích là chính nó
        if (packet.getDestination().equals(this)) {
        	this.removePacket(packet); // Xoa goi tin
            return;
        }
		
		ArrayList<Node> nextHops = routePacket(packet);
		
		if (nextHops.isEmpty()) {
			// Neu khong tim thay duong di -> gui goi tin toi default gateway
			if (this.defaultGateway != null) {
	            System.out.println("Forwarding packet to default gateway: " + defaultGateway.getName());
	            for (Connection connection : connections) {
	                if (connection.getNode2().equals(defaultGateway)) {
	                    connection.addPacket(packet);
	                    break;
	                }
	            }
	        } else {
	            System.out.println("No route found for packet\nCannot send\nDropping packet");
	        }
		} else {
			// Neu tim thay duong di -> gui goi tin toi cac nut tiep theo
			for (Node nextHop : nextHops) {
	            for (Connection connection : connections) {
	                if (connection.getNode2().equals(nextHop)) {
	                    System.out.println("Sending packet from " + this.name + " to " + nextHop.name);
	                    connection.addPacket(packet); // Transmit packet to a connection
	                    break;
	                }
	            }
	        }
		}
		
		removePacket(packet);
        return;
	}
	
	// Received Packet methods
	public void addReceivedPacket(Packet packet) {
		receivedPackets.add(packet);
	}
	
	private void processReceivedPackets() {
    	for (Packet p : receivedPackets) {
            System.out.println("Processing packet at " + this.getName() + ":\n" + p.getPayload());
    	}
    }
	
	public ArrayList<Node> getNeighbors() {
		ArrayList<Node> neighbors = new ArrayList<Node>();
		for (Connection conn : this.connections) {
			neighbors.add(conn.getNode2());
		}
		return neighbors;
	}
	
	@Override
    public boolean equals(Object obj) {
		if (obj instanceof Node) {
			Node that = (Node) obj;
	        return this.ipAddress.equals(that.ipAddress);
		}
		return false;
    }
}