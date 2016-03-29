import java.util.ArrayList;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;

public final class Board implements Comparable<Board> {
	private final int N;
	private final int[][] bd;
	private int spaceCol;
	private int spaceRol;
	private int priority;

	public Board(int[][] blocks) {
		N = blocks.length;
		bd = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				bd[i][j] = blocks[i][j];
				if (bd[i][j] == 0) {
					spaceRol = i;
					spaceCol = j;
				}
			}
		}
		priority = hamming();
	}
	
	public void setPriority(int moves) {
		priority += moves;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public int dimension() {
		return N;
	}
	
	public int hamming() {
		int priority = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (bd[i][j] != (i * N + j + 1)) {
					if ((i * N + j + 1) != N * N)
						priority++;
				}
			}
		}
		return priority;
	}
	
	public int manhattan() {
		int priority = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (bd[i][j] != (i * N + j + 1)) {
					if ((i * N + j + 1) != N * N){
						for (int rolF = 0; rolF < N; rolF++) {
							for (int colF = 0; colF < N; colF++) {
								if ((i * N + j + 1) == bd[rolF][colF]){									
									priority += (Math.abs(rolF-i)+ Math.abs(colF-j));
								}
							}
						}
					}
				}
			}
		}
		return priority;		
	}
	
	public Board twin() {
		int[][] copybd = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++){
				copybd[i][j] = bd[i][j];
			}
		}
		int temp = copybd[(spaceRol+1)%N][spaceCol];
		copybd[(spaceRol+1)%N][spaceCol] = copybd[spaceRol][(spaceCol+1)%N];
		copybd[spaceRol][(spaceCol+1)%N] = temp;
		Board copy = new Board(copybd);
		return copy;		
	}
	
	public int compareTo(Board that) {
		if (this.priority < that.priority) return -1;
		if (this.priority > that.priority) return 1;
		return 0;
	}
	
	public boolean isGoal() {
		return hamming() == 0;
	}
	
	public boolean equals(Board that) {
		return this.toString().equals(that.toString());
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(N+"\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				str.append(" " + bd[i][j]);
			}
			str.append("\n");
 		}
		return str.toString();
	}
	
	public Iterable<Board> neighbors() {
		int[][] copybd = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				copybd[i][j] = bd[i][j];
			}
		}
		ArrayList<Board> nbs = new ArrayList<Board>();
		if (spaceRol > 0) {
			copybd[spaceRol][spaceCol] = copybd[spaceRol - 1][spaceCol];
			copybd[spaceRol - 1][spaceCol] = 0;
			Board copy1 = new Board(copybd);
			nbs.add(copy1);
			copybd[spaceRol - 1][spaceCol] = copybd[spaceRol][spaceCol];
			copybd[spaceRol][spaceCol] = 0;
		}
		if (spaceRol < N - 1) {
			copybd[spaceRol][spaceCol] = copybd[spaceRol + 1][spaceCol];
			copybd[spaceRol + 1][spaceCol] = 0;
			Board copy2 = new Board(copybd);
			nbs.add(copy2);
			copybd[spaceRol + 1][spaceCol] = copybd[spaceRol][spaceCol];
			copybd[spaceRol][spaceCol] = 0;
		}
		if (spaceCol > 0) {
			copybd[spaceRol][spaceCol] = copybd[spaceRol][spaceCol - 1];
			copybd[spaceRol][spaceCol - 1] = 0;
			Board copy3 = new Board(copybd);
			nbs.add(copy3);
			copybd[spaceRol][spaceCol - 1] = copybd[spaceRol][spaceCol];
			copybd[spaceRol][spaceCol] = 0;
		}
		if (spaceCol < N - 1) {
			copybd[spaceRol][spaceCol] = copybd[spaceRol][spaceCol + 1];
			copybd[spaceRol][spaceCol + 1] = 0;
			Board copy4 = new Board(copybd);
			nbs.add(copy4);
			copybd[spaceRol][spaceCol + 1] = copybd[spaceRol][spaceCol];
			copybd[spaceRol][spaceCol] = 0;
		}
		return nbs;
	}
	
	public Comparator<Board> manPriorityOrder() {
		return new ManhattanPriorityOrder();
	}
	
	private class ManhattanPriorityOrder implements Comparator<Board> {
		public int compare(Board p, Board q) {
			int pP = p.manhattan();
			int pQ = q.manhattan();
			if (pP < pQ) return -1;
			if (pP > pQ) return 1;
			return 0;
		}
	}
	public Comparator<Board> hamPriorityOrder() {
		return new HammingPriorityOrder();
	}
	
	private class HammingPriorityOrder implements Comparator<Board> {
		public int compare(Board p, Board q) {
			int pP = p.hamming();
			int pQ = q.hamming();
			if (pP < pQ) return -1;
			if (pP > pQ) return 1;
			return 0;
		}
	}

	public static void main(String[] args) {
	    In in = new In(args[0]);
	    int N = in.readInt();
	    int[][] blocks = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++)
	            blocks[i][j] = in.readInt();
	    Board bd = new Board(blocks);
	    ArrayList<Board> list = new ArrayList<Board>();
	    list.add(bd);
	    System.out.println("test array list contains: " + list.contains(bd));
		System.out.println("test hamming: "+bd.hamming());
		System.out.println("test manhattan: " + bd.manhattan());
		System.out.println("test print");
		System.out.print(bd);
		System.out.println("test twin");
		System.out.print(bd.twin());
		System.out.println("test equal to itself: "+bd.equals(bd));
		System.out.println("test neighbours:");
		for (Board b : bd.neighbors()) {
			System.out.println(b);
		}
	}

}
