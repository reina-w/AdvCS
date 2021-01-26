package KevinBaconGame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LabeledGraph<E, T> {
	
	HashMap<E, Vertex> vertices;
	
	private class Vertex {
		
		E info;
		HashSet<Edge> edges;
		
		public Vertex(E info) {
			this.info = info;
			edges = new HashSet<Edge>();
		}
		
		public boolean equals(Vertex other) {
			return info.equals(other.info);
		}
		
		public String toString() {
			return info.toString();
		}
	}
	
	private class Edge {
		
		Vertex v1, v2;
		T label;
		
		public Edge(T label, Vertex v1, Vertex v2) {
			this.label = label;
			this.v1 = v1;
			this.v2 = v2;
		}
		
		public Vertex getNeighbor(Vertex v) {
			
			if(v.info.equals(v1.info)) return v2;
			return v1; 
		}
		
		public String toString() {
			return label.toString();
		}
	}
	
	public LabeledGraph() {
		
		vertices = new HashMap<E, Vertex>();
	}
	
	public void addVertex(E info) {
		vertices.put(info, new Vertex(info));
	}
	
	public void connect(E info1, E info2, T label) {
		
		Vertex v1 = vertices.get(info1);
		Vertex v2 = vertices.get(info2);
		
		Edge e = new Edge(label, v1, v2);
		
		v1.edges.add(e);
		v2.edges.add(e);
	}
	

	public ArrayList<Object> backTrace(Vertex start, Vertex target, HashMap<Vertex, Edge> leads) {
		
		ArrayList<Object> path = new ArrayList<Object>();
		Vertex curr = target;
	
//		System.out.println(leads.get(curr).getNeighbor(curr));
		while(curr != start) {
			
//			System.out.println(curr.info);
			path.add(0, curr.info);
			path.add(0, leads.get(curr));
			
			curr = leads.get(curr).getNeighbor(curr);
		}
		
		path.add(0, start);
		
//		System.out.println(path);
		return path;
		
	}
	
	public ArrayList<E> BFS(E start, E target) {
		
		ArrayList<Vertex> toVisit = new ArrayList<>();
		toVisit.add(vertices.get(start));
		
		HashSet<Vertex> visited = new HashSet<Vertex>();
		visited.add(vertices.get(start));
		
		HashMap<Vertex, Edge> leadsTo = new HashMap<Vertex, Edge>();
		
		while(!toVisit.isEmpty()) {
			
			Vertex curr = toVisit.remove(0);
			
			for(Edge e : curr.edges) {
				
				Vertex n = e.getNeighbor(curr);
				
				if(visited.contains(n)) continue;
				
				leadsTo.put(n, e);
				if(n.info.equals(target)) {
					
//					System.out.println("FOUND IT!");
					System.out.println(backTrace(vertices.get(start), n, leadsTo));
					
					
				} else {
					toVisit.add(n);
					visited.add(n);
					
				}
			}
		}
		
		return null;
		
	}
	
	public static void main(String[] args) {
		
		LabeledGraph<Integer, String> g = new LabeledGraph<Integer, String>();
		g.addVertex(1);
		g.addVertex(2);
		g.addVertex(3);
		g.addVertex(4);
		g.addVertex(5);
		
		g.connect(1, 2, "a");
		g.connect(1, 3, "b");
		g.connect(2, 4, "c");
		g.connect(4, 5, "d");
		
		g.BFS(1, 5);
		
		
	}
	

}
