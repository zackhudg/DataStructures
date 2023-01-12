package DiGraph_A5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class DiGraph implements DiGraphInterface {

	public HashMap<String, Long> nodes;
	public HashSet<Long> nodeIdNums;
	//private HashMap<Long, String> nodeIdNums = new HashMap<>();
	
	public HashMap<String, HashMap<String, Edge>> edges;	//<Starting Node, <Ending Node, edge info>>
	public HashSet<Long> edgeIdNums;
	

	
  // in here go all your data and methods for the graph

	public DiGraph ( ) { // default constructor
	    // explicitly include this
	    // we need to have the default constructor
	    // if you then write others, this one will still be there
		nodes = new HashMap<>();
		nodeIdNums = new HashSet<>();
		edges = new HashMap<>();
		edgeIdNums = new HashSet<>();
	}

	@Override
	public boolean addNode(long idNum, String label) {
		if(idNum < 0 || nodes.containsKey(label) || nodeIdNums.contains(idNum)) {
			//System.out.println("Node not added: " + label);
			return false;	//node, node id does not exist yet
		}
		else {
			nodes.put(label, idNum);
			nodeIdNums.add(idNum);
			//System.out.println("Node added: " + label);
			return true;
		}
	}
	
	public boolean addEdge(long idNum, String sLabel, String dLabel, long weight) {
		return addEdge(idNum,sLabel,dLabel,weight, null);
	}
	
	@Override
	public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
		if(idNum < 0 || edgeIdNums.contains(idNum) || !nodes.containsKey(sLabel) || !nodes.containsKey(dLabel)) return false;	//edge id does not exist yet, nodes exist
		HashMap<String, Edge> destinations = edges.get(sLabel);
		if(destinations != null) {
			if (destinations.containsKey(dLabel)) return false;
			else {
				destinations.put(dLabel, new Edge(idNum, weight, eLabel));
				edgeIdNums.add(idNum);
				return true;
			}
		}
		else {
			destinations = new HashMap<>();
			destinations.put(dLabel, new Edge(idNum, weight, eLabel));
			edges.put(sLabel, destinations);
			edgeIdNums.add(idNum);
			return true;
		}
	}
	
	@Override
	public boolean delNode(String label) {
		if(nodes.containsKey(label)) {
			nodeIdNums.remove(nodes.get(label));
			nodes.remove(label);
			
			//remove edges with this node
			HashMap<String, Edge> destinations = edges.get(label);
			if(destinations != null) {	//edges exist where source = label
				for (Edge edge : destinations.values()) {
					edgeIdNums.remove(edge.id);		//removes edge ids
				}
				edges.remove(label);	//removes edges
			}
			for(String key : edges.keySet()) {
				destinations = edges.get(key);
				if(destinations.containsKey(label)) {
					Edge edge = destinations.get(label);	
					edgeIdNums.remove(edge.id);		
					destinations.remove(key);
				}
			}
			
			//remove node itself
			nodeIdNums.remove(nodes.get(label));
			nodes.remove(label);
			
			return true;
		}
		else return false;
	}
	
	@Override
	public boolean delEdge(String sLabel, String dLabel) {	
		if(nodes.containsKey(sLabel) && nodes.containsKey(dLabel)) {	//both nodes exist
			HashMap<String, Edge> destinations = edges.get(sLabel);
			if(destinations != null && destinations.containsKey(dLabel)) {	//sLabel is the source of some edges && there is an edge connecting s and d
				Edge edge = destinations.get(dLabel);
				edgeIdNums.remove(edge.id);		
				destinations.remove(dLabel);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public long numNodes() {
		return nodes.size();
	}
	
	@Override
	public long numEdges() {
		return edgeIdNums.size();
	}

	@Override
	public ShortestPathInfo[] shortestPath(String label) {
		int insertIndex = 0;

		HashSet<String> known = new HashSet<String>();
		PriorityQueue<ShortestPathInfo> pq = new PriorityQueue<ShortestPathInfo>(new PathComparator());
		ShortestPathInfo[] shortestPathArray = new ShortestPathInfo[nodes.size()];
		
		pq.add(new ShortestPathInfo(label, 0));

		while(pq.size() != 0) {
			ShortestPathInfo entryPair = pq.poll();
			if(known.contains(entryPair.getDest())) continue;
			shortestPathArray[insertIndex] = entryPair;
			insertIndex++;
			known.add(entryPair.getDest());
			
			HashMap<String, Edge> destinations = edges.get(entryPair.getDest());
			if(destinations != null) {
				for(String key : destinations.keySet()) {
					long weight = entryPair.getTotalWeight()+destinations.get(key).weight;
					pq.add(new ShortestPathInfo(key, weight));
				}
			}
		}
		
		if(insertIndex < shortestPathArray.length) {
			for(String key : nodes.keySet()) {
				if(!known.contains(key)) {
					shortestPathArray[insertIndex] = new ShortestPathInfo(key, -1);
					insertIndex++;
				}
			}
		}
		
		return shortestPathArray;
	}
	  
  // rest of your code to implement the various operations
}