package PacketRouting;

public class RoutingEntry {
  private String destination;
  private String outGoingHop; // The interface used to send packets
  private String nextHop; // Refers to the IP of the connection's other node
  private int cost; // The sum of all connections' latency
  private Connection connection;

  // Constructor
  public RoutingEntry(String destination, String outGoingHop, String nextHop, int cost, Connection connection) {
    this.destination = destination;
    this.outGoingHop = outGoingHop;
    this.nextHop = nextHop;
    this.cost = cost;
    this.connection = connection;
  }

  // Getters and Setters
  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getOutGoingHop() {
    return outGoingHop;
  }

  public void setOutGoingHop(String outGoingHop) {
    this.outGoingHop = outGoingHop;
  }

  public String getNextHop() {
    return nextHop;
  }

  public void setNextHop(String nextHop) {
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
}