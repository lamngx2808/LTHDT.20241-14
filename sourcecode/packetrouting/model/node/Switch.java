package sourcecode.packetrouting.model.node;

import java.util.ArrayList;

import sourcecode.packetrouting.model.Packet;
import sourcecode.packetrouting.model.Port;
import sourcecode.packetrouting.model.connection.Connection;

public class Switch extends Node {
	private ArrayList<MACAddressEntry> macAddressTable = new ArrayList<MACAddressEntry>();
	private ArrayList<Port> ports;
	
	// Constructor
	public Switch(String name, String macAddress) {
		super(name, macAddress);
	}
	
	public Switch(String name, String ipAddress, String macAddress) {
		super(name, ipAddress, macAddress);
	}
	
	public Switch(String name, String ipAddress, String macAddress, Node defaultGateway) {
		super(name, ipAddress, macAddress, defaultGateway);
	}
	
	public Switch(String name, String macAddress, ArrayList<Port> ports) {
		super(name, macAddress);
		this.ports = ports;
	}
	
	public Switch(String name, String ipAddress, String macAddress, ArrayList<Port> ports) {
		super(name, ipAddress, macAddress);
		this.ports = ports;
	}
	
	public Switch(String name, String ipAddress, String macAddress, ArrayList<Port> ports, Node defaultGateway) {
		super(name, ipAddress, macAddress, defaultGateway);
		this.ports = ports;
	}

	// Getter and Setter
	public ArrayList<MACAddressEntry> getMACAddressTable() {
		return macAddressTable;
	}
	
	public ArrayList<Port> getPorts() {
		return ports;
	}

	public void setPorts(ArrayList<Port> ports) {
		this.ports = ports;
	}
	
	// Port methods
	// Remove when ports is broken
	public void removePort(Port...ports) {
		for (Port port : ports) {
			this.ports.remove(port);
		}
	}

	// MAC table methods
	public void addMACAddress(MACAddressEntry entry) {
		macAddressTable.add(entry);
	}
	
	public void removeMACAddress(MACAddressEntry entry) {
		macAddressTable.remove(entry);
	}
	
	public MACAddressEntry findMACAddress(String macAddress) {
		for (MACAddressEntry en : macAddressTable) {
			if (en.getMacAddress().equals(macAddress)) {
				return en;
			}
		}
		return null;
	}
	
	public void learnMACAddress(String sourceMAC, Port port) {
	    MACAddressEntry entry = findMACAddress(sourceMAC);
	    if (entry == null) {
	        // Thêm địa chỉ MAC mới nếu chưa có
	        macAddressTable.add(new MACAddressEntry(sourceMAC, port)); // TTL mặc định là 300
	    } else {
	        // Cập nhật port và reset TTL nếu đã có
	        entry.setPort(port);
	        entry.setTTL(300);
	    }
	}
	
	@Override
	public ArrayList<Node> routePacket(Packet packet) {
		ArrayList<Node> nextHops = new ArrayList<>();

	    // Tra cứu bảng MAC 
		String sourceMAC = packet.getSource().getMacAddress();
	    Port outgoingPort = packet.getOutgoingPort();
	    System.out.println("Source MAC: " + sourceMAC + ", Outgoing Port: " + outgoingPort);
	    
		MACAddressEntry entry = findMACAddress(sourceMAC);
		if (entry == null) {
			// Nếu không có địa chỉ nguồn trong bảng MAC thì học địa chỉ vào bảng MAC
			System.out.println("Learning source MAC: " + sourceMAC + " on port: " + outgoingPort);
			macAddressTable.add(new MACAddressEntry(sourceMAC, outgoingPort));
		}
		
	    entry = findMACAddress(packet.getDestination().getMacAddress());
	    if (entry != null) {
	    	System.out.println("Destination MAC found: " + packet.getDestination().getMacAddress());
	        // Nếu tìm thấy địa chỉ đích trong bảng MAC, chuyển gói tin qua port tương ứng
	    	Port port = entry.getPort();
	        for (Connection conn : this.getConnections()) {
	            if (conn.getPort1().equals(port)) {
	                nextHops.add(conn.getNode2());
	                break;
	            } else if (conn.getPort2().equals(port)) {
	                nextHops.add(conn.getNode1());
	                break;
	            }
	        }
	    } else {
	    	System.out.println("Destination MAC not found, broadcasting to neighbors.");
	        // Nếu không tìm thấy địa chỉ đích trong bảng MAC, broadcast đến tất cả các Node hàng xóm
	        nextHops = this.getNeighbors();
	    }
	    
	    System.out.println("Next hops: " + nextHops);
	    return nextHops;
	}
	
	public static void main(String[] args) {
        // Tạo các node, ví dụ các Switches và một Computer
        Node switch1 = new Switch("Switch 1", "00:11:22:33:44:55");
        Node switch2 = new Switch("Switch 2", "00:11:22:33:44:56");
        Node computer1 = new Computer("Computer 1", "192.168.0.1", "00:11:22:33:44:57");
        Node computer2 = new Computer("Computer 2", "192.168.0.2", "00:11:22:33:44:58");
        
        // Tạo các port cho switch
        Port port1 = new Port("Port 1");
        Port port2 = new Port("Port 2");
        Port port3 = new Port("Port 3");
        Port port4 = new Port("Port 4");
        
        // Tạo các kết nối giữa các node
        Connection conn1 = new Connection(switch1, switch2, port1, port2);
        Connection conn2 = new Connection(switch1, computer1, port3, null);
//        Connection conn3 = new Connection(switch2, computer2, port4, null);
        
        // Thêm kết nối cho các switch và computer
        switch1.addConnection(conn1);
        switch1.addConnection(conn2);
        switch2.addConnection(conn1);
//        switch2.addConnection(conn3);
        computer1.addConnection(conn2);
//        computer2.addConnection(conn3);
        
        // Tạo một gói tin từ switch1 đến switch2
        Packet packet = new Packet(switch1, switch2, "Hello, Computer 2!", port1);

        // switch1 gửi gói tin
        switch1.sendPacket(packet);
        
        // Kiểm tra kết quả
        System.out.println("switch 1 packets: " + switch1.getPackets().size());
        System.out.println("switch 2 received packets: " + switch2.getReceivedPackets().size());
    }
}
