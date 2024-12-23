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
	
	public void tick() {		
	    for (ActivePacket activePacket : activePackets) {
	    	if (activePacket.getProgress() == this.latency) {
	    		// Neu goi tin da den dich thi them vao danh sach cac goi tin nhan dc
	    		if (activePacket.getPacket().getDestination().getId() == this.node2.getId()) {
	    			this.node2.addReceivedPacket(activePacket.getPacket());
	    		} else {
	    			// Neu goi tin chua den dich thi them goi tin vao list packet de tiep tuc dinh tuyen
	    			this.node2.addPacket(activePacket.getPacket());
	    		}
	    		// Xoa goi tin khoi ds cac goi tin dang truyen trong lien ket hien tai
	    		this.removePacket(activePacket);
	    	} else {
	    		activePacket.incProgress();
	    	}
	    }
	}
}
