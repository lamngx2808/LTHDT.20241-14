package PacketRouting;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    // Bước 1: Tạo đồ thị
    Graph graph = new Graph();

    // Bước 2: Tạo các nút với constructor (String name, String ipAddress)
    Node node1 = new Node("Node1", "192.168.1.1");
    Node node2 = new Node("Node2", "192.168.1.2");
    Node node3 = new Node("Node3", "192.168.1.3");
    Node node4 = new Node("Node4", "192.168.1.4");

    // Thêm các nút vào đồ thị
    graph.addNode(node1);
    graph.addNode(node2);
    graph.addNode(node3);
    graph.addNode(node4);

    // Bước 3: Tạo các kết nối với constructor (Node, Node, Integer)
    Connection connection1 = new Connection(node1, node2, 10); // Độ trễ 10
    Connection connection2 = new Connection(node2, node3, 15); // Độ trễ 15
    Connection connection3 = new Connection(node3, node4, 5);  // Độ trễ 5
    Connection connection4 = new Connection(node1, node4, 35); // Độ trễ 30

    // Thêm các kết nối vào đồ thị
    graph.addConnection(connection1);
    graph.addConnection(connection2);
    graph.addConnection(connection3);
    graph.addConnection(connection4);

    // Bước 4: Hiển thị thông tin ban đầu của đồ thị
    System.out.println("Danh sách các nút liền kề với node1:");
    for (Node node : graph.getNeighbors(node1)) {
      System.out.println(node.getName());
    }

    // Bước 5: Kiểm tra thuật toán Dijkstra
    System.out.println("\nĐường đi ngắn nhất từ Node1 đến Node4 (Dijkstra):");
    List<Node> pathDijkstra = graph.runDijkstra(node1, node4);
    for (Node node : pathDijkstra) {
      System.out.print(node.getName() + " -> ");
    }
    System.out.println("Kết thúc");

    // Bước 6: Kiểm tra thuật toán Bellman-Ford
    System.out.println("\nKhoảng cách từ Node1 đến tất cả các nút (Bellman-Ford):");
    graph.runBellmanFord(node1).forEach((node, distance) ->
            System.out.println(node.getName() + ": " + distance)
    );

    // Bước 7: Xóa nút và kiểm tra lại
    graph.removeNode(node3);
    System.out.println("\nĐồ thị sau khi xóa Node3:");
    System.out.println(graph);
  }
}
