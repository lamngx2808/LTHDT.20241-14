package PacketRouting;

public class MACAddressEntry {
	private String macAddress;
	private Port port;
	int TTL = 300; // Thoi gian giu lai thong tin trong bang (giay)
	
	// Constructor
	public MACAddressEntry(String macAddress, Port port) {
		super();
		this.macAddress = macAddress;
		this.port = port;
	}

	public MACAddressEntry(String macAddress, Port port, int TTL) {
		super();
		this.macAddress = macAddress;
		this.port = port;
		this.TTL = TTL;
	}

	// Getter and Setter
	public String getMacAddress() {
		return macAddress;
	}
	
	public Port getPort() {
		return port;
	}
	
	public void setPort(Port port) {
		this.port = port;
	}
	
	public int getTTL() {
		return TTL;
	}
	
	public void setTTL(int tTL) {
		TTL = tTL;
	}
}
