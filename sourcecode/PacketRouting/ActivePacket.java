package PacketRouting;

public class ActivePacket {
	private Packet packet;
	private int progress;

	public ActivePacket(Packet packet) {
		super();
		this.packet = packet;
		this.progress = 0;
	}

	public Packet getPacket() {
		return packet;
	}

	public int getProgress() {
		return progress;
	}

	public void incProgress() {
		this.progress++;
	}
}