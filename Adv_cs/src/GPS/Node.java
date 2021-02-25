package GPS;

public class Node<T> {
	
	public T info;
	public double priority;
	
	public Node(T info, double priority) {
		this.info = info;
		this.priority = priority;
	}
	
	public String toString() {
		return "[(" + info + ", " + priority + "])";
	}

}
