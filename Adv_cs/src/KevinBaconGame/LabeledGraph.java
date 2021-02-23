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
	public ArrayList<Object> BFS(E start, E target) {
		
		//the vertices that are going to be visited
		ArrayList<Vertex> toVisit = new ArrayList<>();
		toVisit.add(vertices.get(start));
		
		//the verticies that have been visited
		HashSet<Vertex> visited = new HashSet<Vertex>();
		visited.add(vertices.get(start));
		
		//the map that helps build the path from start to target
		HashMap<Vertex, Edge> leadsTo = new HashMap<Vertex, Edge>();
		
		//when there are still vertices to visit
		while(!toVisit.isEmpty()) {
			
			//get the first vertex in toVisit and remove it from the list
			Vertex curr = toVisit.remove(0);
			
			//get all edges
			for(Edge e : curr.edges) {
				
				//go to its neighbor
				Vertex n = e.getNeighbor(curr);
				
				//if the neighbor has been visited
				if(visited.contains(n)) continue;
				
				//keep track of the edge that leads to the neighbor vertex
				leadsTo.put(n, e);
				
				//if found target
				if(n.info.equals(target)) {
					
					//use backTrace function to return the path
					return backTrace(vertices.get(start), n, leadsTo);
					
				//if not found then add the neighbor to both toVisit and Visited
				} else {
					toVisit.add(n);
					visited.add(n);
					
				}
			}
		}
		
		return null;
		
	}
	
	//used for the findCommonMovie function in the game
	public HashSet<T> getEdges(E info1, E info2) {
		
		//initialize the set of answers
		HashSet<T> labels = new HashSet<T>();
		
		//get all the edges that contains info1 and 
		//check whether the other vertex is info2
		for(Edge e : vertices.get(info1).edges) {
			 if (e.v2.equals(vertices.get(info2))) 
				 labels.add(e.label);
			 if (e.v1.equals(vertices.get(info2))) 
				 labels.add(e.label);
		}
		
		//get all the edges that contains info2 and 
		//check whether the other vertex is info1
		for(Edge e : vertices.get(info2).edges) {
			 if (e.v2.equals(vertices.get(info1))) 
				 labels.add(e.label);
			 if (e.v1.equals(vertices.get(info1))) 
				 labels.add(e.label);
		}
		
		return labels;
	}
	
	
	//used for the findCommonActors function in the game
	public HashSet<E> getCommonPair(E info1, E info2) {
		
		HashSet<E> common = new HashSet<E>();
		HashSet<E> set1 = new HashSet<E>();
		HashSet<E> set2 = new HashSet<E>();
		
		//get the actors that have been collaborated with info1
		for(Edge e : vertices.get(info1).edges) {
			if(e.v1.equals(vertices.get(info1))) {
				set1.add(e.v2.info);
			} else if (e.v2.equals(vertices.get(info1))) {
				set1.add(e.v1.info);
			}
		}
		
		//get the actors that have been collaborated with info2
		for(Edge e : vertices.get(info2).edges) {
			if(e.v1.equals(vertices.get(info2))) {
				set2.add(e.v2.info);
			} else if (e.v2.equals(vertices.get(info2))) {
				set2.add(e.v1.info);
			}
		}
	
		//find the intersection
		common.addAll(set1);
		common.retainAll(set2);
		
		return common;
	}
	
	//used for the findFurthest function in the game
	public HashSet<E> getFurthest(E start) {
		
		ArrayList<Vertex> toVisit = new ArrayList<>();
		toVisit.add(vertices.get(start));
		
		HashSet<Vertex> visited = new HashSet<Vertex>();
		visited.add(vertices.get(start));
		
		//the vertices that have actually been on
		ArrayList<Vertex> isOn = new ArrayList<>();
		
		//the map that helps build the path from start to target
		HashMap<Vertex, Edge> leadsTo = new HashMap<Vertex, Edge>();
		
		//the current vertex as well as the answer after the while loop ends
		Vertex curr = null;
		while(!toVisit.isEmpty()) {
			
			curr = toVisit.remove(0);
			isOn.add(curr);
			
			//get all edges
			for(Edge e : curr.edges) {
				
				//get its neighbor
				Vertex n = e.getNeighbor(curr);
				
				//if the neighbor has been visited 
				if(visited.contains(n)) continue;
				
				//keep track of the edge that leads to the neighbor vertex
				leadsTo.put(n, e);
				
				//if not then add the neighbor to both toVisit and Visited
				toVisit.add(n);
				visited.add(n);
			}
		}
		
		//figure out which layer the furthest vertex is in
		HashSet<E> fur = new HashSet<>();
		int layer = backTrace(vertices.get(start), curr, leadsTo).size();
		
		for(int i=isOn.size()-1; i>=0; i--) {
			if(backTrace(vertices.get(start), isOn.get(i), leadsTo).size() >= layer) {
				fur.add(isOn.get(i).info);
			} else {
				break;
			}
		}
		//return the furthest vertice
		return fur;
		
	}

	public static void main(String[] args) {
		
		LabeledGraph<Integer, String> g = new LabeledGraph<Integer, String>();
		g.addVertex(1);
		g.addVertex(2);
		g.addVertex(3);
		g.addVertex(4);
		g.addVertex(5);
		g.addVertex(6);
		g.addVertex(7);
		
		g.connect(1, 2, "a");
		g.connect(1, 3, "b");
		g.connect(2, 4, "c");
		g.connect(4, 5, "d");
		g.connect(3, 6, "e");
		g.connect(6, 7, "f");
		 
		System.out.println(g.getFurthest(1));
		
	}
	

}
