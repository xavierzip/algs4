import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int N;
	private Node first;
	private Node last;
	
	private class Node {
		private Item item;
		private Node next;
	}
	
	
	public RandomizedQueue() {
		first = null;
		last = null;
		N = 0;
	}
	
	public boolean isEmpty() {
		return N == 0;
	}
	
	public int size() {
		return N;
	}
	
	public void enqueue(Item item) {
		Node oldlast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		if (isEmpty()) first = last;
		else oldlast.next = last;
		N++;
	}
	
	public Item dequeue() {
		if (isEmpty()) throw new NoSuchElementException("Queue underflow");
		int idx = StdRandom.uniform(size());
		Node temp = first;
		Node prev = null;
		for (int i = 0; i < idx; i++){
			prev = temp;
			temp = temp.next;
		}
		if (idx == 0){
			first = first.next;
		} else if (idx == N - 1) {
			last = prev;
		} else {
			prev.next = temp.next;
		}
		N--;
		if (isEmpty()) last = null;
		return temp.item;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (Item item : this) {
			str.append(item + "-");
		}
		return str.toString();
	}
	
	public Item sample() {
		if (isEmpty()) throw new NoSuchElementException("Queue underflow");
		int idx = StdRandom.uniform(size());
		Node temp = first;
		for (int i = 0; i < idx; i++) {
			temp = temp.next;
		}
		return temp.item;
	}
	
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}
	
	private class RandomizedQueueIterator implements Iterator<Item> {
		private Node current = first;
		
		public boolean hasNext() {
			return current != null;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		public Item next() {
			if(!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
	
	public static void main(String[] args) {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		Scanner input = new Scanner(System.in);
		while (true) {
			String item = input.next();
			if (item.indexOf("exit") != -1) break;
			rq.enqueue(item);
		}
		input.close();
		System.out.println(rq.size() + " items added");
		System.out.println("queue: " + rq.toString());
		while (rq.size() > 0) {
			String item = rq.dequeue();
			System.out.println(item + " has been removed");
		}
	}

}
