package DiGraph_A5;

public class Edge {
	public long id; 
	public long weight;
	public String label;
	
	public Edge(long id, long weight) {
		this(id, weight, null);
	}
	
	public Edge(long id, long weight, String label) {
		this.id = id;
		this.weight = weight;
		this.label = label;
		if (label == null) this.label = "";
	}
}
