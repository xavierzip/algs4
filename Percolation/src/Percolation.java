import edu.princeton.cs.algs4.*;

public class Percolation {
	private WeightedQuickUnionUF sites;
	private boolean[] isOpen;
	private int dim;
	
	public Percolation(int N) {
		if (N <= 0) throw new IllegalArgumentException();
		sites = new WeightedQuickUnionUF(N*N);
		dim = N;
		isOpen = new boolean[N*N];
		for (int i = 0; i < isOpen.length; i++) {
			isOpen[i] = false;
		}
		System.out.println("Percolation init with size " + N);
	}
	
	public void open(int i, int j) {
		if (i < 1 || i > dim || j < 1 || j > dim)
			throw new IndexOutOfBoundsException();
		int rowIdx = i - 1;
		int colIdx = j - 1;
		int siteIdx = rowIdx * dim + colIdx;
		isOpen[siteIdx] = true;
		if (colIdx != 0) {
			if (isOpen(i, j - 1)) {
				int leftNBIdx = rowIdx * dim + colIdx - 1; 
				sites.union(siteIdx, leftNBIdx);
			}
		}
		if (colIdx != (dim - 1)) {
			if (isOpen(i, j + 1)) {
				int rightNBIdx = rowIdx * dim + colIdx + 1;
				sites.union(siteIdx, rightNBIdx);
			}
		}
		if (rowIdx != 0) {
			if (isOpen(i - 1, j)) {
				int upNBIdx = (rowIdx - 1) * dim + colIdx;
				sites.union(siteIdx, upNBIdx);
			}
		}
		if (rowIdx != (dim - 1)) {
			if (isOpen(i + 1, j)) {
				int  downNBIdx = (rowIdx + 1) * dim + colIdx;
				sites.union(siteIdx, downNBIdx);
			}
		}
	}
	
	public boolean isOpen(int i, int j) {
		if (i < 1 || i > dim || j < 1 || j > dim)
			throw new IndexOutOfBoundsException();
		int idx = (i - 1) * dim + j - 1;
		return isOpen[idx];		
	}
	
	public boolean isFull(int i, int j) {
		if (i < 1 || i > dim || j < 1 || j > dim)
			throw new IndexOutOfBoundsException();
		int idx = (i - 1) * dim + j - 1;
		return !isOpen[idx];
	}
	
	public boolean percolates() {
		for (int i = 0; i < dim - 1; i++) {
			for (int j = dim * (dim - 1); j < dim * dim; j++) {
				if (sites.connected(i, j)) return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		int count = 0;
		Percolation trial = new Percolation(N);
		while (!trial.percolates()) {
			int rolIdx = StdRandom.uniform(N) + 1;
			int colIdx = StdRandom.uniform(N) + 1;
			if (trial.isOpen(rolIdx, colIdx)) continue;
//			System.out.println("opening " + rolIdx + "," + colIdx);
			trial.open(rolIdx, colIdx);
			count++;
		}
		double rate = (double) count / (N * N);
		System.out.println(count + "out of " + N * N + " i.e. " + rate + "%" + " sites is open now");
	}
}
