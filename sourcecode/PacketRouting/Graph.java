package PacketRouting;

import java.util.*;

// Định nghĩa lớp Graph
public class Graph {
    // Thuộc tính
	protected List<Node> nodes;              // Danh sách các nút
	protected List<Connection> connections; // Danh sách các kết nối

    // Constructor
    public Graph() {
        this.nodes = new ArrayList<>();
        this.connections = new ArrayList<>();
    }

    // Phương thức: Thêm nút vào đồ thị
    public void addNode(Node n) {
        if (!nodes.contains(n)) {
            nodes.add(n);
        }
    }

    // Phương thức: Xóa nút khỏi đồ thị
    public void removeNode(Node n) {
        nodes.remove(n);
        // Xóa các kết nối liên quan đến nút này
        connections.removeIf(connection -> 
            connection.getNode1().equals(n) || connection.getNode2().equals(n)
        );
    }

    // Phương thức: Thêm kết nối vào đồ thị
    public void addConnection(Connection connection) {
        if (nodes.contains(connection.getNode1()) && nodes.contains(connection.getNode2())) {
            connections.add(connection);
        }
    }

    // Phương thức: Xóa kết nối khỏi đồ thị
    public void removeConnection(Connection connection) {
        connections.remove(connection);
    }

    // Phương thức: Lấy danh sách các nút lân cận của một nút
    public List<Node> getNeighbors(Node n) {
        List<Node> neighbors = new ArrayList<>();
        for (Connection connection : connections) {
            if (connection.getNode1().equals(n)) {
                neighbors.add(connection.getNode2());
            } else if (connection.getNode2().equals(n)) {
                neighbors.add(connection.getNode1());
            }
        }
        return neighbors;
    }

    // Phương thức: Lấy danh sách các kết nối liên quan đến một nút
    public List<Connection> getConnections(Node node) {
        List<Connection> nodeConnections = new ArrayList<>();
        for (Connection connection : connections) {
            if (connection.getNode1().equals(node) || connection.getNode2().equals(node)) {
                nodeConnections.add(connection);
            }
        }
        return nodeConnections;
    }

    // Phương thức: Thuật toán Dijkstra để tìm đường đi ngắn nhất
    public List<Node> runDijkstra(Node source, Node target) {
        Map<Node, Double> distances = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparing(distances::get));

        // Khởi tạo khoảng cách
        for (Node node : nodes) {
            distances.put(node, Double.MAX_VALUE);
            previous.put(node, null);
        }
        distances.put(source, 0.0);
        queue.add(source);

        // Thuật toán Dijkstra
        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.equals(target)) break;

            for (Node neighbor : getNeighbors(current)) {
                double weight = connections.stream()
                        .filter(conn -> (conn.getNode1().equals(current) && conn.getNode2().equals(neighbor)) ||
                                        (conn.getNode2().equals(current) && conn.getNode1().equals(neighbor)))
                        .findFirst()
                        .map(Connection::getLatency) // Sử dụng độ trễ làm trọng số
                        .orElse(Integer.MAX_VALUE);

                double altDistance = distances.get(current) + weight;

                if (altDistance < distances.get(neighbor)) {
                    distances.put(neighbor, altDistance);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        // Xây dựng đường đi
        List<Node> path = new ArrayList<>();
        for (Node at = target; at != null; at = previous.get(at)) {
            path.add(0, at);
        }
        return path.isEmpty() || !path.get(0).equals(source) ? List.of() : path;
    }

    // Phương thức: Thuật toán Bellman-Ford để tìm đường đi ngắn nhất
    public Map<Node, Double> runBellmanFord(Node source) {
        Map<Node, Double> distances = new HashMap<>();
        for (Node node : nodes) {
            distances.put(node, Double.MAX_VALUE);
        }
        distances.put(source, 0.0);

        // Relax edges |V| - 1 lần
        for (int i = 0; i < nodes.size() - 1; i++) {
            for (Connection connection : connections) {
                Node u = connection.getNode1();
                Node v = connection.getNode2();
                double weight = connection.getLatency();

                if (distances.get(u) + weight < distances.get(v)) {
                    distances.put(v, distances.get(u) + weight);
                }
                if (distances.get(v) + weight < distances.get(u)) {
                    distances.put(u, distances.get(v) + weight);
                }
            }
        }

        // Phát hiện chu kỳ trọng số âm
        for (Connection connection : connections) {
            Node u = connection.getNode1();
            Node v = connection.getNode2();
            double weight = connection.getLatency();

            if (distances.get(u) + weight < distances.get(v)) {
                throw new IllegalStateException("Negative weight cycle detected!");
            }
        }

        return distances;
    }

    // Phương thức: Hiển thị thông tin của Graph
    @Override
    public String toString() {
        return "Graph{" +
                "nodes=" + nodes +
                ", connections=" + connections +
                '}';
    }
}
