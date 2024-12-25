package PacketRouting.dijkstra;

import PacketRouting.Components.Node;

public class AdjListNode {
    private Node vertex;
    private int weight;
    
    // Constructor
    public AdjListNode(Node vertex, int weight) {
		super();
		this.vertex = vertex;
		this.weight = weight;
	}
    
    // Getter and Setter
    public Node getVertex() {
		return vertex;
	}

	public void setVertex(Node vertex) {
		this.vertex = vertex;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
