package Compression;

import java.util.ArrayList;

public class priorityQ<T> {
	
	private ArrayList<Node<T>> queue = new ArrayList<Node<T>>();
	
	public int size() {
		return queue.size();
	}
	
	public String toString() {
		return queue.toString();
	}
	
	public Node<T> pop() { //remove
		return queue.remove(queue.size()-1);
	}
	
	public boolean add(T info, int priority) {
		
		Node<T> newNode = new Node(info, priority);
		
		if(queue.size() == 0) {
			queue.add(newNode);
		}
		
		if(queue.get(0).priority < newNode.priority) {
			queue.add(0, newNode);
			
		} else if (queue.get(queue.size()-1).priority > newNode.priority) {
			queue.add(newNode);
			
		} else {
			
			int low = 0, high = queue.size()-1;
			
			while (low < high){
				
				int mid = (low + high)/2;
				if(queue.get(mid).priority > newNode.priority)
					low = mid + 1;
				else
					high = mid;
			}
			
			queue.add(low, newNode); 

		}
		
		return true;
	}
	
	
	public static void main(String args[]) {
		
		priorityQ<Character> pq = new priorityQ<Character>();
		
		pq.add('g', 6);
		pq.add('a', 5);
		pq.add('n', 10);
		
		System.out.println(pq);
	}

}
