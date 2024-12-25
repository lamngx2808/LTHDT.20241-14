package sourcecode.PacketRouting.Components;

import java.util.ArrayList;

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
