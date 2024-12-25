package PacketRouting;

public class Packet {
	private Node source;
	private Node destination;
	private String payload;
	private int size;

	public Packet(Node source, Node destination, String payload, int size) {
		this.source = source;
		this.destination = destination;
		this.payload = payload;
		this.size = size;
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
}
