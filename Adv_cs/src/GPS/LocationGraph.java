package GPS;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LocationGraph<E> {
	
	HashMap<E, Vertex> vertices;
	
	private final double INF = Integer.MAX_VALUE;
	
	private class Vertex {
		
		E info;
		int xLoc, yLoc;
		HashSet<Edge> edges;
		
		public Vertex(E info, int xLoc, int yLoc) {
			this.info = info;
			this.xLoc = xLoc;
			this.yLoc = yLoc;
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
		double label;
		
		public Edge(double label, Vertex v1, Vertex v2) {
			this.label = label;
			this.v1 = v1;
			this.v2 = v2;
		}
		
		public Vertex getNeighbor(Vertex v) {
			
			if(v.info.equals(v1.info)) return v2;
			return v1;
		}
	}
	
	public LocationGraph() {
		
		vertices = new HashMap<E, Vertex>();
	}
	
	public void addVertex(E info, int xLoc, int yLoc) {
		vertices.put(info, new Vertex(info, xLoc, yLoc));
	}
	
	public void connect(E info1, E info2) {
		
		Vertex v1 = vertices.get(info1);
		Vertex v2 = vertices.get(info2);
		
		int x1 = vertices.get(info1).xLoc;
		int y1 = vertices.get(info1).yLoc;
		int x2 = vertices.get(info2).xLoc;
		int y2 = vertices.get(info2).yLoc;
		
		double label = Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
		
		Edge e = new Edge(label, v1, v2);
		
		v1.edges.add(e);
		v2.edges.add(e);
	}
	

	//the helper method for BFS to return the path
	public ArrayList<Object> backTrace(Vertex start, Vertex target, HashMap<Vertex, Edge> leads) {
		
		ArrayList<Object> path = new ArrayList<Object>();
		Vertex curr = target;

		//when curr has not reached the first vertex
		while(!curr.equals(start)) {
			
			//put the current vertex at the front
			path.add(0, curr.info);
			//put the edge at the front
			path.add(0, leads.get(curr));
			//move on tot the previous vertex
			curr = leads.get(curr).getNeighbor(curr);
		}
		
		path.add(0, start);
		
		return path;
		
	}
	
	//used for findConnection function in the game
	public ArrayList<Object> Dijkstras(E start, E target) {
		
		//the vertices that are going to be visited
		priorityQ<Vertex> toVisit = new priorityQ<>();
		toVisit.put(vertices.get(start), 0.0);
		
		//the verticies that have been visited
		HashSet<Vertex> visited = new HashSet<>();
		
		//the map that helps build the path from start to target
		HashMap<Vertex, Edge> leadsTo = new HashMap<>();
		
		//map of vertices and distance to the starting point
		HashMap<Vertex, Double> dis = new HashMap<>();
		
		//set all dis to inf except start
		for(E e: vertices.keySet()) {
			if(e.equals(start)) dis.put(vertices.get(e), 0.0);
			else dis.put(vertices.get(e), INF);
		}
		
		
		//when there are still vertices to visit
		while(toVisit.size() > 0) {
			
			//get the first vertex in toVisit and remove it from the list
			Vertex curr = toVisit.pop().info;
			System.out.println(curr.info);
			
			//check if it is the target
			if(curr.equals(vertices.get(target))) {
				//use backTrace function to return the path
				return backTrace(vertices.get(start), curr, leadsTo);
				
			} else {
				//go through all unvisited neighbors
				for(Edge e : curr.edges) {
					
					Vertex n = e.getNeighbor(curr);
					
					//if the neighbor has been visited
					if(visited.contains(n)) continue;
					
					//calculate the distance from start to neighbor
					double totalDis = dis.get(curr) + e.label;
					
					if(totalDis < dis.get(n)) {
						toVisit.put(n, totalDis);
						leadsTo.put(n, e);
						dis.put(n, totalDis);
					}
				}
				visited.add(curr);
				
			}
			
		}
		
		return null;
		
	}
	
	

	public static void main(String[] args) {
		
		LocationGraph<String> g = new LocationGraph<String>();
		
		g.addVertex("a", 100, 100);
		g.addVertex("b", 1000, 1000);
		g.addVertex("c", 600, 300);
		g.addVertex("d", 100, 300);
		g.addVertex("e", 300, 300);
		
		g.connect("a", "b");
		g.connect("b", "c");
		g.connect("a", "d");
		g.connect("d", "e");
		g.connect("e", "c");
		
		System.out.println(g.Dijkstras("a", "c"));
		
		
	}
	

}
