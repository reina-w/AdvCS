package KevinBaconGame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Graph<E> {
	
	HashMap<E, Vertex> vertices;
	
	private class Vertex {
		
		E info;
		HashSet<Vertex> neighbors;
		
		public Vertex(E info) {
			this.info = info;
			neighbors = new HashSet<Vertex>();
		}
		
		public boolean equals(Vertex other) {
			return info.equals(other.info);
		}
	}
	
	
	public Graph() {
		
		vertices = new HashMap<E, Vertex>();
	}
	
	public void addVertex(E info) {
		vertices.put(info, new Vertex(info));
	}
	
	public void connect(E info1, E info2) {
		
		Vertex v1 = vertices.get(info1);
		Vertex v2 = vertices.get(info2);
		
		if(v1 != null && v2 != null) {
			v1.neighbors.add(v2);
			v2.neighbors.add(v1);
		}
	}
	

	public ArrayList<E> backTrace(Vertex target, HashMap<Vertex, Vertex> leads) {
		
		ArrayList<E> path = new ArrayList<E>();
		Vertex curr = target;
	
		while(curr != null) {
			path.add(0, curr.info);
			curr = leads.get(curr);
		}
		
//		System.out.println(path);
		return path;
		
	}
	
	public ArrayList<E> BFS(E start, E target) {
		
		ArrayList<Vertex> toVisit = new ArrayList<>();
		toVisit.add(vertices.get(start));
		HashSet<Vertex> visited = new HashSet<Vertex>();
		visited.add(vertices.get(start));
		
		HashMap<Vertex, Vertex> leadsTo = new HashMap<Vertex, Vertex>();
		
		while(!toVisit.isEmpty()) {
			
			Vertex curr = toVisit.remove(0);
			
			for(Vertex n : curr.neighbors) {
				
				if(visited.contains(n)) continue;
				
				leadsTo.put(n, curr);
				if(n.info.equals(target)) {
					
//					System.out.println("FOUND IT!");
					System.out.println(backTrace(n, leadsTo));
					
					
				} else {
					toVisit.add(n);
					visited.add(n);
					
				}
			}
		}
		
		return null;
		
	}
	
	public static void main(String[] args) {
		
		Graph<String> g = new Graph<String>();
		
		g.addVertex("Reina");
		g.addVertex("Veronika");
		g.addVertex("Felicity");
		g.addVertex("Andria");
		g.addVertex("Elgin");
		g.addVertex("Thomas");
		g.addVertex("David");
		g.addVertex("Katie");
		g.addVertex("Lucas");
		g.addVertex("Jackson");
		
		g.connect("Reina", "Lucas");
		g.connect("Reina", "Jackson");
		g.connect("Lucas", "Felicity");
		g.connect("Jackson", "Lucas");
		g.connect("Felicity", "Jackson");
		g.connect("Felicity", "Andria");
		g.connect("Andria", "Reina");
		g.connect("Andria", "Thomas");
		g.connect("Thomas", "David");
		g.connect("Felicity", "Elgin");
		g.connect("Elgin", "Katie");
	
		
		g.BFS("Reina", "Katie");
		g.BFS("Felicity", "David");
	}
	

}
