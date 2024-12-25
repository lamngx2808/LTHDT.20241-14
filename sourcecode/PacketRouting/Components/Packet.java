package sourcecode.PacketRouting.Components;

public class Packet {
	private Node source;
	private Node destination;
	private String payload;
	private int size;
	private Port outgoingPort;
	
	public Packet(Node source, Node destination, String payload) {
        this.source = source;
        this.destination = destination;
        this.payload = payload;
        this.size = calculateSize();
    }
	
	public Packet(Node source, Node destination, String payload, int size) {
	    this.source = source;
	    this.destination = destination;
	    this.payload = payload;
	    this.size = size;
	}
	
	public Packet(Node source, Node destination, String payload, Port outgoingPort) {
		super();
		this.source = source;
		this.destination = destination;
		this.payload = payload;
		this.outgoingPort = outgoingPort;
		this.size = calculateSize();
	}

	public Packet(Node source, Node destination, String payload, int size, Port outgoingPort) {
		super();
		this.source = source;
		this.destination = destination;
		this.payload = payload;
		this.size = size;
		this.outgoingPort = outgoingPort;
	}

	public Node getSource() {
	    return source;
	}
	
	public Node getDestination() {
	    return destination;
	}
	
	public String getPayload() {
	    return payload;
	}
	
	public int getSize() {
	    return size;
	}
	
	public Port getOutgoingPort() {
        return outgoingPort;
    }

    public void setOutgoingPort(Port outgoingPort) {
        this.outgoingPort = outgoingPort;
    }
	
	private int calculateSize() {
        int headerSize = 20; // Giả sử header có kích thước cố định 20 bytes
        int payloadSize = payload.getBytes().length; // Tính số byte của payload
        return headerSize + payloadSize;
    }

	@Override
	public String toString() {
		return "Packet\n- Source = " + source + "\n- Destination = " + destination + "\n- Payload = " + payload + "\n- Size = " + size
				+ "\n- OutgoingPort = " + outgoingPort + "\n";
	}
	
	
}
