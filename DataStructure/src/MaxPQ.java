import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class MaxPQ<Key> implements Iterable<Key>{
	private Key[] pq;
	private int N;
	private Comparator<Key> comparator;
	
	
	public MaxPQ(int initCapacity) {
		pq = (Key[]) new Object[initCapacity + 1];
		N = 0;
	}
	
	public MaxPQ() {
		this(1);
	}
	
	public MaxPQ(int initCapacity, Comparator<Key> comparator) {
		this.comparator = comparator;
		pq = (Key[]) new Object[initCapacity + 1];
		N = 0;
	}
	
	public MaxPQ(Comparator<Key> comparator) {
		this(1, comparator);
	}
	
	public MaxPQ(Key[] keys) {
		N = keys.length;
		pq = (Key[]) new Object[N + 1];
		for (int i = 0; i < N; i++) {
			pq[i+1] = keys[i+1];
		}
		for (int k = N/2; k >= 1; k--) {
			sink(k);
		}
		assert isMaxHeap();
	}
	
	public void insert(Key v) {
		if (N >= pq.length - 1) resize(2 * pq.length);
		
		pq[++N] = v;
		swim(N);
		assert isMaxHeap();
	}
	
	public Key delMax() {
		Key max = pq[1];
		exch(1, N--);
		sink(1);
		pq[N+1] = null;
		return max;
	}
	
	public int size() {
		return N;
	}
	
	boolean isEmpty() {
		return N == 0;
	}
	
	private void swim(int k) {
		while (k > 1 && less(k/2, k)) {
			exch(k, k/2);
			k = k/2;
		}
	}

	private void sink(int k) {
		while (2 * k <= N) {
			int j = 2 * k;
			if (j < N && less(j, j + 1)) j++;
			if (!less(k, j)) break;
			exch(k, j);
			k = j;
		}
	}
	
	private boolean less(int i, int j) {
		if (comparator == null) {
			return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
		}
		else {
			return comparator.compare(pq[i], pq[j]) < 0;
		}
	}
	
	private void exch(int i, int j) {
		Key swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
	}
	
	private void resize(int capacity) {
		assert capacity > N;
		Key[] temp = (Key[]) new Object[capacity];
		for (int i = 0; i <= N; i++) {
			temp[i] = pq[i];
		}
		pq = temp;
	}
	
	private boolean isMaxHeap() {
		return isMaxHeap(1);
	}
	
	private boolean isMaxHeap(int k) {
		if (k > N) return true;
		int left = 2 * k, right = 2 * k + 1;
		if (left <= N && less(k, left)) return false;
		if (right <= N && less(k, right)) return false;
		return isMaxHeap(left) && isMaxHeap(right);
	}
	
	public Iterator<Key> iterator() {
		return new HeapIterator();
	}
	
	private class HeapIterator implements Iterator<Key> {
		private MaxPQ<Key> copy;
		
		public HeapIterator() {
			if (comparator == null) copy = new MaxPQ<Key>(size());
			else copy = new MaxPQ<Key>(size(), comparator);
			for (int i = 1; i <= N; i++) {
				copy.insert(pq[i]);
			}
		}
		
		public boolean hasNext() {
			return !copy.isEmpty();
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		public Key next() {
			if (!hasNext()) throw new NoSuchElementException();
			return copy.delMax();
		}
	}
	
	public static void main(String[] args) {
		MaxPQ<String> pq = new MaxPQ<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-")) {
				pq.insert(item);
				System.out.println(item + " added");
			}
			else if(!pq.isEmpty()) {
				StdOut.print(pq.delMax() + " ");
				
			}
		}
		StdOut.println("(" + pq.size() + " left on pq)");
		for (String s : pq) {
			System.out.print(s + " ");
		}
		System.out.println();
	}
}
