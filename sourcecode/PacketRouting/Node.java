package PacketRouting;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

abstract class Node { 
	private static int idCounter = 0; // Static counter for unique IDs
	private int id;
	private String name;
	private String ipAddress;
	private String defaultGateway;
	  
	private ArrayList<Connection> connections = new ArrayList<>();
	private Queue<Packet> packets = new LinkedList<>();
	private ArrayList<Packet> receivedPackets = new ArrayList<>();

	// Constructor
	public Node(String name, String ipAddress) {
	    super();
	    this.id = idCounter++;
	    this.name = name;
	    this.ipAddress = ipAddress;
	}
	
	public Node(String name, String ipAddress, String defaultGateway) {
		super();
		this.name = name;
		this.ipAddress = ipAddress;
		this.defaultGateway = defaultGateway;
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

	public String getDefaultGateway() {
		return defaultGateway;
	}

	public void setDefaultGateway(String defaultGateway) {
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
        } else {
            throw new IllegalArgumentException("Connection does not involve this node.");
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
	
	public void sendPacket(Packet packet) {
		ArrayList<Node> nextHops = routePacket(packet);
		if (nextHops.isEmpty()) {
			System.out.println("No route found for packet\nCannot send\nDropping packet");
			removePacket(packet);
            return;
		}
		
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
			Node other = (Node) obj;
	        return this.ipAddress.equals(other.ipAddress);
		}
		return false;
    }
}