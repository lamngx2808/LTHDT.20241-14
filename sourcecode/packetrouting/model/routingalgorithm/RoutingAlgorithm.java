package sourcecode.packetrouting.model.routingalgorithm;

import java.util.ArrayList;

import sourcecode.packetrouting.model.node.router.Router;
import sourcecode.packetrouting.model.node.router.RoutingEntry;

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
