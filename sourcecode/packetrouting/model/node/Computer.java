package sourcecode.packetrouting.model.node;

import java.util.ArrayList;

import sourcecode.packetrouting.model.Packet;
import sourcecode.packetrouting.model.connection.Connection;

public class Computer extends Node {
	
	// Constructor
	public Computer(String name, String macAddress) {
		super(name, macAddress);
	}
	
	public Computer(String name, String ipAddress, String macAddress) {
		super(name, ipAddress, macAddress);
	}
	
	public Computer(String name, String ipAddress, String macAddress, Node defaultGateway) {
		super(name, ipAddress, macAddress, defaultGateway);
	}

	@Override
    public ArrayList<Node> routePacket(Packet packet) {
		// Kiểm tra xem đích có trong mạng cục bộ không
		ArrayList<Node> nextHops = new ArrayList<Node>();
		
		for (Connection connection : this.getConnections()) {
            if (connection.getNode2().equals(packet.getDestination())) {
            	nextHops.add(connection.getNode2());
                return nextHops; // Gửi trực tiếp đến đích
            }
        }
		
		return new ArrayList<>();
    }
}
