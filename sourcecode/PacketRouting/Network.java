package PacketRouting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Network extends Graph {
	// Thuộc tính
	private List<Packet> packets; // Danh sách các gói tin trong mạng
	private Map<Node, String> networkState; // Trạng thái các nút trong mạng

	// Constructor
	public Network() {
		super(); // Kế thừa constructor từ lớp Graph
		this.packets = new ArrayList<>();
		this.networkState = new HashMap<>();
	}

	// Phương thức: Gửi một gói tin từ nguồn đến đích
	public void sendPacket(Packet p) {
		// Kiểm tra xem nguồn và đích có tồn tại trong mạng hay không
		if (!nodes.contains(p.getSource()) || !nodes.contains(p.getDestination())) {
			throw new IllegalArgumentException("Source or destination node does not exist in the network.");
		}

		// Thêm gói tin vào danh sách
		packets.add(p);

		// Cập nhật trạng thái các nút
		networkState.put(p.getSource(), "sending");
		networkState.put(p.getDestination(), "waiting");
		System.out.println("Packet sent from " + p.getSource().getName() + " to " + p.getDestination().getName());
	}

	// Phương thức: Định tuyến các gói tin
	public void routePackets() {
		for (Packet packet : packets) {
			Node source = packet.getSource();
			Node destination = packet.getDestination();

			// Sử dụng thuật toán Dijkstra để tìm đường đi
			List<Node> path = runDijkstra(source, destination);

			if (path.isEmpty()) {
				System.out.println("No route available from " + source.getName() + " to " + destination.getName());
			} else {
				System.out.println("Routing packet from " + source.getName() + " to " + destination.getName());
				System.out.println("Path: " + path);
			}
		}
	}

	// Phương thức: Mô phỏng một đơn vị thời gian
	public void simulateTick() {
		List<Packet> deliveredPackets = new ArrayList<>();

		for (Packet packet : packets) {
			Node source = packet.getSource();
			Node destination = packet.getDestination();

			// Kiểm tra xem gói tin đã đến đích hay chưa
			if (source.equals(destination)) {
				deliveredPackets.add(packet);
				networkState.put(destination, "receiving");
				System.out.println("Packet delivered to " + destination.getName());
			} else {
				// Cập nhật trạng thái nếu gói tin chưa đến đích
				networkState.put(source, "idle");
				networkState.put(destination, "waiting");
			}
		}

		// Loại bỏ các gói tin đã đến đích
		packets.removeAll(deliveredPackets);

		// Đặt lại trạng thái các nút
		for (Node node : nodes) {
			networkState.putIfAbsent(node, "idle");
		}
	}

	// Ghi đè phương thức toString để hiển thị thông tin mạng
	@Override
	public String toString() {
		return "Network{\n" + "nodes=" + nodes + ",\n" + "connections=" + connections + ",\n" + "packets=" + packets
				+ ",\n" + "networkState=" + networkState + "\n}";
	}
}