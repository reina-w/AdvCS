package GPS;

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
		return queue.remove(0);
	}
	
	public boolean put(T info, double priority) {
		
		Node<T> newNode = new Node(info, priority);
		
		for(Node n: queue) {
			if(n.info.equals(info)) {
				if(n.priority > priority) {
					queue.remove(n);
					break;
				} else {
					return true;
				}
			}
		}
		
		if(queue.size() == 0) {
			queue.add(newNode);
			
		} else if(queue.get(0).priority > newNode.priority) {
			queue.add(0, newNode);
			
		} else if (queue.get(queue.size()-1).priority < newNode.priority) {
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
		
		pq.put('g', 6);
		pq.put('g', 3);
		pq.put('a', 5);
		pq.put('n', 10);
		
		System.out.println(pq);
		
		pq.pop();
		System.out.println(pq);
	}

}
