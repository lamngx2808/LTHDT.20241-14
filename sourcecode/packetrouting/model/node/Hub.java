package sourcecode.packetrouting.model.node;

import java.util.ArrayList;

import sourcecode.packetrouting.model.Packet;

public class Hub extends Node {
	
	// Constructor
	public Hub(String name, String macAddress) {
		super(name, macAddress);
	}
	
	public Hub(String name, String ipAddress, String macAddress) {
		super(name, ipAddress, macAddress);
	}
	
	public Hub(String name, String ipAddress, String macAddress, Node defaultGateway) {
		super(name, ipAddress, macAddress, defaultGateway);
	}

	@Override
	public ArrayList<Node> routePacket(Packet packet) {
	    return this.getNeighbors();
	}
}
