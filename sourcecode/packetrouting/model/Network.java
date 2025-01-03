package sourcecode.packetrouting.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import sourcecode.packetrouting.model.connection.Connection;
import sourcecode.packetrouting.model.node.Computer;
import sourcecode.packetrouting.model.node.Node;
import sourcecode.packetrouting.model.node.router.Router;

public class Network {
	private ArrayList<Node> nodes = new ArrayList<>();

	public Network() {

	}

	public Network(ArrayList<Node> nodes) {
	    super();
	    this.nodes = nodes;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void addNodes(Node... nodes) {
		for (Node node : nodes) {
			this.nodes.add(node);
		}
	}

	public void removeNodes(Node... nodes) {
		for (Node node : nodes) {
			this.nodes.remove(node);
		}
	}

  	public void addConnections(Connection... connections) {
	    for (Connection conn : connections) {
	    	conn.getNode1().addConnection(conn);
	    	conn.getNode2().addConnection(conn);
	    }
  	}
  
  	public void simulateRoutingPacket(Packet packet) {
	    Node currentNode = packet.getSource(); // Bắt đầu từ nút nguồn
	    Node destinationNode = packet.getDestination();
	    Queue<Node> nextHops = new LinkedList<Node>();
	    
	    // Gửi gói tin đến nút tiếp theo cho đến khi đến đích
	    ArrayList<Connection> conns = new ArrayList<Connection>();    
	    while (currentNode != null) {
	        // Kiểm tra xem gói tin đã đến đích chưa
	        if (currentNode.equals(destinationNode)) {
	            // Nếu đã đến đích, thêm gói tin vào danh sách gói tin nhận được
	            destinationNode.addReceivedPacket(packet);
	            System.out.println("Packet has reached the destination: " + destinationNode.getName());
	            break;
	        }
	        
	        // Nếu chưa đến đích gửi gói tin tới nút tiếp theo 
	        conns = currentNode.sendPacket(packet);
	        for (Connection conn : conns) {
//	        	while (!conn.getActivePackets().isEmpty()) {
//	        		conn.tick();
//	        	}
	        	nextHops.add(conn.getNode2());
	        }
	        
	        currentNode = nextHops.poll();
	    }
	    
	    System.out.println("Source packets: " + packet.getSource().getPackets().size());
        System.out.println("Destination packets: " + packet.getDestination().getReceivedPackets().size());
	}

	private Connection findConnection(Node from, Node to) {
	    for (Connection conn : from.getConnections()) {
	        if (conn.getNode1().equals(to) || conn.getNode2().equals(to)) {
	            return conn;
	        }
	    }
	    return null;
	}


  public void flooding(Packet packet, Node startNode) {
    ArrayList<Node> visited = new ArrayList<>();
    Queue<Node> queue = new LinkedList<>();

    queue.add(startNode);
    visited.add(startNode);

    System.out.println("Starting flooding from node: " + startNode.getName());

    while (!queue.isEmpty()) {
      Node currentNode = queue.poll();
      System.out.println("Processing node: " + currentNode.getName());

      // Get neighbors of the current node
      ArrayList<Node> neighbors = currentNode.getNeighbors();

      for (Node neighbor : neighbors) {
        if (!visited.contains(neighbor)) {
          // Add the neighbor to the queue and mark it as visited
          queue.add(neighbor);
          visited.add(neighbor);

          // Broadcast the packet to the neighbor
          neighbor.broadcast(packet);
          System.out.println("Broadcasting packet to neighbor: " + neighbor.getName());
        }
      }
    }

    System.out.println("Flooding completed.");
  }

//  public static void main(String[] args) {
//    // Create some nodes
//    Node nodeA = new Router("A", "192.168.1.1", "00:1A:2B:3C:4D:5E");
//    Node nodeB = new Computer("B", "192.168.1.2", "00:1A:2B:3C:4D:5F");
//    Node nodeC = new Computer("C", "192.168.1.3", "00:1A:2B:3C:4D:60");
//    Node nodeD = new Computer("D", "192.168.1.4", "00:1A:2B:3C:4D:61");
//    Node nodeE = new Switch("E", "192.168.1.5", "00:1A:2B:3C:4D:62");
//    Node nodeF = new Computer("F", "192.168.1.6", "00:1A:2B:3C:4D:63");
//    Node nodeG = new Hub("G", "192.168.1.7", "00:1A:2B:3C:4D:64");
//    Node nodeH = new Computer("H", "192.168.1.8", "00:1A:2B:3C:4D:65");
//    Node nodeI = new Computer("I", "192.168.1.9", "00:1A:2B:3C:4D:66");
//
//    // Create ports for the nodes
//    Port portA1 = new Port("PortA1");
//    Port portA2 = new Port("PortA2");
//    Port portA3 = new Port("PortA3");
//    Port portB1 = new Port("PortB1");
//    Port portC1 = new Port("PortC1");
//    Port portE1 = new Port("PortE1");
//    Port portE2 = new Port("PortE2");
//    Port portE3 = new Port("PortE3");
//    Port portF1 = new Port("PortF1");
//    Port portG1 = new Port("PortG1");
//    Port portG2 = new Port("PortG2");
//    Port portG3 = new Port("PortG3");
//    Port portH1 = new Port("PortH1");
//    Port portI1 = new Port("PortI1");
//
//    // Create connections ensuring each port connects to only one connection
//    Connection connAB = new Connection(nodeA, nodeB, portA1, portB1, 1);
//    Connection connAC = new Connection(nodeA, nodeC, portA2, portC1, 1);
//    Connection connAE = new Connection(nodeA, nodeE, portA3, portE1, 2);
//    Connection connEF = new Connection(nodeE, nodeF, portE2, portF1, 1);
//    Connection connEG = new Connection(nodeE, nodeG, portE3, portG1, 2);
//    Connection connGH = new Connection(nodeG, nodeH, portG2, portH1, 1);
//    Connection connGI = new Connection(nodeG, nodeI, portG3, portI1, 1);
//
//    // Add nodes and connections to the network
//    Network network = new Network();
//    network.addNodes(nodeA, nodeB, nodeC, nodeD, nodeE, nodeF, nodeG, nodeH, nodeI);
//    network.addConnections(connAB, connAC, connAE, connEF, connEG, connGH, connGI);
//
//    // Create a packet to flood the network
//    String data = "Test message for flooding.";
//    Packet packet = new Packet(nodeA, null, data, data.length());
//
//    // Perform flooding
//    network.flooding(packet, nodeA);
//  }

  	public static void main(String[] args) {
	    // Create some nodes
	    Router nodeA = new Router("A", "192.168.1.1", "00:1A:2B:3C:4D:5E");
//	    nodeA.setRoutingAlgorithm("BellmanFord");
	    nodeA.setRoutingAlgorithm("Dijkstra");
	    Node nodeB = new Computer("B", "192.168.1.2", "00:1A:2B:3C:4D:5F");
	    Node nodeC = new Computer("C", "192.168.1.3", "00:1A:2B:3C:4D:60");
	    Node nodeD = new Computer("D", "192.168.1.4", "00:1A:2B:3C:4D:61");

	    // Create ports for the nodes
	    Port portA1 = new Port("PortA1");
	    Port portB1 = new Port("PortB1");
	    Port portC1 = new Port("PortC1");
	    Port portD1 = new Port("PortD1");

	    // Create connections
	    Connection connAB = new Connection(nodeA, nodeB, portA1, portB1, 1);
	    Connection connBC = new Connection(nodeB, nodeC, portB1, portC1, 1);
	    Connection connCD = new Connection(nodeC, nodeD, portC1, portD1, 1);

	    // Add nodes and connections to the network
	    Network network = new Network();
	    network.addNodes(nodeA, nodeB, nodeC, nodeD);
	    network.addConnections(connAB, connBC, connCD);

	    // Create a packet to simulate routing
	    String data = "Test packet data.";
	    Packet packet = new Packet(nodeA, nodeB, data, data.length());

	    // Simulate routing of the packet
	    network.simulateRoutingPacket(packet);
	}
}
