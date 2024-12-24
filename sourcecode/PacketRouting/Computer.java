package PacketRouting;

import java.util.ArrayList;

public class Computer extends Node {
	
	public Computer(String name, String ipAddress) {
		super(name, ipAddress);
	}
	
	public Computer(String name, String ipAddress, String default_gateway) {
		super(name, ipAddress, default_gateway);
	}

	@Override
    public ArrayList<Node> routePacket(Packet packet) {
		Node destination = packet.getDestination();
		
		// Nếu đích là chính nó
        if (destination.equals(this)) {
        	destination.removePacket(packet); // Xoa goi tin
            return new ArrayList<>(); // Không chuyển tiếp
        }
		
		// Kiểm tra xem đích có trong mạng cục bộ không
		ArrayList<Node> nextHops = new ArrayList<Node>();
		ArrayList<Node> neighbors = this.getNeighbors();
		
		for (Node neighbor : neighbors) {
            if (neighbor.equals(destination)) {
                nextHops.add(neighbor);
                return nextHops; // Gửi trực tiếp đến đích
            }
        }
		
		// Nếu đích không có trong mạng cục bộ, gửi đến default gateway (nếu có)
        if (this.getDefaultGateway() != null) {
            Node gatewayNode = findNode(this.getDefaultGateway());
            if (gatewayNode != null) {
                System.out.println("Routing packet to default gateway: " + gatewayNode.getName());
                nextHops.add(gatewayNode);
                return nextHops;
            }
        }

        // Nếu không tìm thấy gateway, gói tin không được định tuyến
        System.out.println("Drop packet!");
        destination.removePacket(packet); // Xoa goi tin
        return new ArrayList<>();
    }

    private Node findNode(String ipAddress) {
        for (Connection conn : this.getConnections()) {
            Node neighbor = conn.getNode2();
            if (neighbor.getIpAddress().equals(ipAddress)) {
                return neighbor;
            }
        }
        return null; // Không tìm thấy
    }
}
