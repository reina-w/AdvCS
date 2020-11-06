package linkedlist;

import java.util.AbstractList;

public class LinkedLists<T> extends AbstractList<T>{
	
	//<T> represents whatever the user choose it to be 

	private Node head;
	
	private class Node{
		
		private Node next;
		private T info; //a generic type
		
		public Node(T info) {
			this.info = info;
		}
		
		public Node(T info, Node next) {
			this.info = info;
			this.next = next;
		}
		
	}
	
	public boolean add(T info) {
		
		if(head == null) {
			
			head = new Node(info);
			
		} else {
			
			Node curr = head;
			while(curr.next != null) {
				curr = curr.next;
			}
			
			curr.next = new Node(info);
		}

		return true;
	}
	
	public void addIndex(int index, T info) {
		
		if(index == 0) {
			
			head = new Node(info, head);
			
		} else {
			
			Node curr = head;
			for(int i=0; i<index-1; i++) {
				curr = curr.next;
			}
			curr.next = new Node(info, curr.next);
		}
		
	}
	
	@Override
	public T get(int index) {
		// TODO Auto-generated method stub
		
		Node curr = head;
		for(int i=0; i<index; i++) {
			curr = curr.next;
		}
		
		return curr.info;
	}

	//index must be within our list
	public T remove(int index) {
		
		 Node curr = head;
		 for(int i=0; i<index-1; i++) {
			 curr = curr.next;
		 }
		 
		 Node temp = curr.next;
		 curr.next = curr.next.next;
		 
		 return temp.info;
	}
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		
		int size = 0;
		Node curr = head;
		while(curr != null) {
			curr = curr.next;
			size++;
		}
		return size;
	}
	
	@Override
	public String toString() {
		
		if(size() == 0) return "[]";
		String output = "[";
		Node curr = head;
		while(curr != null) {
			output += curr.info.toString() + ", ";
			curr = curr.next;
		}
		return output.substring(0, output.length()-2) + "]";
	}
	
	public static void main(String[] args) {
		
		LinkedLists<String> myList = new LinkedLists<String>();
		
		myList.add("hi");
		myList.add("hello");
		myList.add("apple");
		System.out.println(myList);
		myList.addIndex(1, "name");
		System.out.println(myList);
		System.out.println(myList.get(1));
		myList.remove(2);
		System.out.println(myList);
		System.out.println(myList.size());

	}


}
