package PacketRouting;

public class RoutingEntry {
  private String destination;
  private String netmask;
  private String gateway;
  private String intf;
  private int metric;
  private Connection connection;

  RoutingEntry(String destination, String netmask, String gateway, String intf, int metric, Connection connection) {
    this.destination = destination;
    this.netmask = netmask;
    this.gateway = gateway;
    this.intf = intf;
    this.metric = metric;
    this.connection = connection;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getNetmask() {
    return netmask;
  }

  public void setNetmask(String netmask) {
    this.netmask = netmask;
  }

  public String getGateway() {
    return gateway;
  }

  public void setGateway(String gateway) {
    this.gateway = gateway;
  }

  public String getIntf() {
    return intf;
  }

  public void setIntf(String intf) {
    this.intf = intf;
  }

  public int getMetric() {
    return metric;
  }

  public void setMetric(int metric) {
    this.metric = metric;
  }

  public Connection getConnection() {
    return connection;
  }

  public void setConnection(Connection connection) {
    this.connection = connection;
  }
}
