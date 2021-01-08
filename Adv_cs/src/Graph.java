import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Graph<E> {
	
	HashMap<E, Vertex> vertices;
	
	private class Vertex {
		
		E info;
		HashSet<Vertex> neighbors;
		boolean visited;
		
		public Vertex(E info, boolean visited) {
			this.info = info;
			this.visited = visited;
		}
	}
	
	public Graph() {
		
		vertices = new HashMap<E, Vertex>();
	}
	
	public void addVertex(E info) {
		vertices.put(info, new Vertex(info, false));
	}
	
	public void connect(E info1, E info2) {
		
		Vertex v1 = vertices.get(info1);
		Vertex v2 = vertices.get(info2);
		
		if(v1 != null && v2 != null) {
			v1.neighbors.add(v2);
			v2.neighbors.add(v1);
		}
	}
	
	Vertex cur = null;
	
	public void BFS(Vertex start, Vertex goal) {
		
		ArrayList<Vertex> myList = new ArrayList<>();
		myList.add(start);
		cur = start;
		
		while(true) {
			
			if(cur == goal) {
				return;
				
			} else {
				cur.visited = true;
				myList.remove(cur);
				for(Vertex n : cur.neighbors) {
					BFS(n, goal);
				}
			}
		}
		
		
	}
	

}
