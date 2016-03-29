import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Deque<Item> implements Iterable<Item> {
	private Node first;
	private Node last;
	private int size;
	
	private class Node {
		Item item;
		Node next;
		Node prev;
	}	
	
	public Deque() {
		first = null;
		last = null;
		size = 0;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	public void addFirst(Item item) {
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = oldfirst;
		first.prev = null;
		if (last == null){
			last = first;
		} else {
			oldfirst.prev = first;
		}
		size++;
	}
	
	public void addLast(Item item) {
		Node oldlast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		last.prev = oldlast;
		if (first == null) {
			first = last;
		} else {
			oldlast.next = last;
		}
		size++;
	}
	
	public Item removeFirst() {
		if (isEmpty()) throw new NoSuchElementException("Deque underflow");
		Item item = first.item;
		first = first.next;
		size--;
		if (isEmpty()) last = null;
		return item;		
	}
	
	public Item removeLast() {
		if (isEmpty()) throw new NoSuchElementException("Deque underflow");
		Item item = last.item;
		last = last.prev;
		size--;
		if (isEmpty()) first = null;
		return item;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Item item : this) {
			s.append(item + "-");
		}
		return s.toString();
	}
	
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}
	
	private class DequeIterator implements Iterator<Item> {
		private Node current = first;
		
		public boolean hasNext() {
			return current != null;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
	
	public static void main(String[] args) {
		Deque<String> dq = new Deque<String>();
		Deque<String> dq_rev = new Deque<String>();
		Scanner input = new Scanner(System.in);
		while (true) {
			String item = input.next();
			if (item.indexOf("exit") != -1) break;
			dq.addFirst(item);
		}
		input.close();
		System.out.println(dq.size() + " items added");
		System.out.println("rev: " + dq.toString());
		while (dq.size() > 0) {
			String item = dq.removeLast();
			System.out.println(item + " has been removed");
			dq_rev.addLast(item);
		}
		System.out.println("Now the Deque is empty");
		System.out.println("org: " + dq_rev.toString());
	}

}
