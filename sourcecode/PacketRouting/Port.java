package PacketRouting;

public class Port {
    private String portName;
    private String ipAddress; // Địa chỉ IP (nếu có)
    
    // Constructor
    public Port(String portName) {
        this.portName = portName;
    }

    public Port(String portName, String ipAddress) {
        this.portName = portName;
        this.ipAddress = ipAddress;
    }

    // Getter and Setter
    public String getPortName() {
        return portName;
    }
    
    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    @Override
    public boolean equals(Object obj) {
		if (obj instanceof Port) {
			Port that = (Port) obj;
	        return this.portName.equals(that.portName);
		}
		return false;
    }
}
