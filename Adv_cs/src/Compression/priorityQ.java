package Compression;

import java.util.ArrayList;

public class priorityQ<T> {
	
	private ArrayList<Node> queue;
	
	private class Node {
		
		private T info;
		private int priority;
		
		public Node(T info, int priority) {
			this.info = info;
			this.priority = priority;
		}
		
	}
	
	public boolean add(Node curr) {
		
		int l = queue.size();
		int low = 0;
		int high = l-1;
		while (low < high){
			int mid = (low + high)/2;
			if(queue.get(mid).priority < curr.priority)
				low = mid + 1;
			else
				high = mid;
		}
		queue.add(low, curr); 

		return true;
	}
	
	public Node remove() {
		
		int l = queue.size();
		Node last = queue.remove(l-1);
		
		return last;
	}
	
	public String toString() {
		
		for(int i=0; i<=queue.size()-1; i++)
			return "[" + queue.get(i).info + ", " + queue.get(i).priority + "]";
		
		return null;
	}
	
	public static void main(String args[]) {
		
		priorityQ<Character> pq = new priorityQ<Character>();
		pq.add(new Node('a', 5));
		pq.add(new Node('n', 10));
	}

}
