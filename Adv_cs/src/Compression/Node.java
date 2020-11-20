package Compression;

public class Node<T> {
	
	public T info;
	public int priority;
	
	public Node(T info, int priority) {
		this.info = info;
		this.priority = priority;
	}
	
	public String toString() {
		return "[(" + info + ", " + priority + "])";
	}

}
