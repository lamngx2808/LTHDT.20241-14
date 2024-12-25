package sourcecode.PacketRouting.Components;

import java.util.ArrayList;

public abstract class RoutingAlgorithm {
	private String algorithmName;
	public abstract ArrayList<RoutingEntry> computeRoutingTable(Router router);
	
	public String getAlgorithmName() {
		return algorithmName;
	}
	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}
}
