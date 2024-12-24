package PacketRouting;

import java.util.ArrayList;

public class Connection {
	private Node node1;
	private Node node2;
	private int latency; // the amount of time unit this connection needs to send 1 unit of data 
	                     // from this node to the other node.
	private ArrayList<ActivePacket> activePackets = new ArrayList<ActivePacket>();
	
	// Constructor
	public Connection(Node node1, Node node2, int latency) {
		super();
	    this.node1 = node1;
	    this.node2 = node2;
	    this.latency = latency;
	}
	
	// Getter and Setter
	public Node getNode1() {
	    return node1;
	}
	
	public Node getNode2() {
	    return node2;
	}
	
	public int getLatency() {
	    return latency;
	}
	
	public void setLatency(int latency) {
	    this.latency = latency;
	}
	
	public void addPacket(Packet packet) {
	    activePackets.add(new ActivePacket(packet));
	}
	
	public void removePacket(ActivePacket packet) {
		activePackets.remove(packet);
	}
	
	// Phuong thuc mo phong 1 don vi thoi gian
	public void tick() {		
	    for (ActivePacket activePacket : activePackets) {
	    	if (activePacket.getProgress() >= this.latency) {
	    		// Neu goi tin da truyen xong thi xu ly goi tin do
	    		processCompletedPacket(activePacket.getPacket());
	    		
	    		// Xoa goi tin khoi ds cac goi tin dang truyen trong lien ket hien tai
	    		this.removePacket(activePacket);
	    	} else {
	    		activePacket.incProgress();
	    	}
	    }
	}
	
	// Xu ly cac goi tin da truyen xong
	private void processCompletedPacket(Packet packet) {
	    if (packet.getDestination().equals(this.node2)) {
	    	// Neu goi tin da den dich thi them vao danh sach cac goi tin nhan dc
	        this.node2.addReceivedPacket(packet);
	    } else {
	        // Gói tin chưa đến đích, thêm vào hàng đợi để định tuyến tiếp
	        this.node2.addPacket(packet);
	    }
	}
}
