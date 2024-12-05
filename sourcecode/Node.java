public class Node {
	private static int idCounter = 0;
	private int id;
	private String name;
	private String ipAddress;
	public Node(String name, String ipAddress) {
		super();
		this.id = idCounter++;
		this.name = name;
		this.ipAddress = ipAddress;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}	
}
