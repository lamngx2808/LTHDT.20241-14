package PacketRouting;

import java.util.ArrayList;

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
	public void removePort(Port... ports) {
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

	public MACAddressEntry findMACAddress(String macAddress) {
		for (MACAddressEntry en : macAddressTable) {
			if (en.getMacAddress().equals(macAddress)) {
				return en;
			}
		}
		return null;
	}

	@Override
	public ArrayList<Node> routePacket(Packet packet) {
		ArrayList<Node> nextHops = new ArrayList<>();

		// Tra cứu bảng MAC
		MACAddressEntry entry = findMACAddress(packet.getDestination().getMacAddress());

		if (entry != null) {
			// Nếu tìm thấy địa chỉ MAC, thêm Node tương ứng vào danh sách nextHops
			Port port = entry.getPort();
			for (Connection conn : this.getConnections()) {
				if (conn.getPort2().equals(port)) {
					nextHops.add(conn.getNode2());
					break;
				}
			}
		} else {
			// Nếu không tìm thấy địa chỉ MAC trong bảng MAC, broadcast đến tất cả các Node
			// hàng xóm
			nextHops = this.getNeighbors();
		}

		return nextHops;
	}
}