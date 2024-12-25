package sourcecode.packetrouting.model.routingalgorithm;

import sourcecode.packetrouting.model.Port;
import sourcecode.packetrouting.model.connection.Connection;
import sourcecode.packetrouting.model.node.Node;

public class RoutingEntry {
  private Node destination;
  private Port outGoingPort; // The interface used to send packets
  private Node nextHop; // Refers to the IP of the connection's other node
  private int cost; // The sum of all connections' latency
  private Connection connection;

  // Constructor
  public RoutingEntry(Node destination, Port outGoingPort, Node nextHop, int cost, Connection connection) {
    this.destination = destination;
    this.outGoingPort = outGoingPort;
    this.nextHop = nextHop;
    this.cost = cost;
    this.connection = connection;
  }

  // Getters and Setters
  public Node getDestination() {
    return destination;
  }

  public void setDestination(Node destination) {
    this.destination = destination;
  }

  public Port getOutGoingPort() {
    return outGoingPort;
  }

  public void setOutGoingPort(Port outGoingPort) {
    this.outGoingPort = outGoingPort;
  }

  public Node getNextHop() {
    return nextHop;
  }

  public void setNextHop(Node nextHop) {
    this.nextHop = nextHop;
  }

  public int getCost() {
    return cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  public Connection getConnection() {
    return connection;
  }

  public void setConnection(Connection connection) {
    this.connection = connection;
  }

	@Override
	public String toString() {
		return "RoutingEntry\nDestination = " + destination + "\nOutgoing Port = " + outGoingPort + "\nNextHop = " + nextHop
				+ "\nCost = " + cost + "\n";
	}
}