import java.io.IOException;
import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private MinPQ<Board> queue;
	private MinPQ<Board> queueTwin;
	private Board prev;
	private Board prevTwin;
	private boolean solvable;
	private ArrayList<Board> prevList;
	private ArrayList<Board> prevListTwin;
	
	
	public Solver(Board initial) {
		queue = new MinPQ<Board>();
		prev = null;
		initial.setPriority(0);
		queue.insert(initial);
		System.out.println("Original:");
		System.out.print(initial);
		
		prevList = new ArrayList<Board>();
		
		queueTwin = new MinPQ<Board>();
		prevTwin = null;
		Board initialTwin = initial.twin();
		initialTwin.setPriority(0);
		queueTwin.insert(initialTwin);
		System.out.println("Twin:");
		System.out.print(initialTwin);
		prevListTwin = new ArrayList<Board>();
		
		solvable = false;
		
		while(!queue.isEmpty() && !queueTwin.isEmpty()) {
//			Board temp = queue.delMin();
//			System.out.println("taking:");
//			System.out.print(temp);
//			int preMoveCount = temp.getPriority() - temp.hamming();
//			if (temp.isGoal()){
//				solvable = true;
//				break;
//			} else {
//				prev = temp;
//				prevList.add(temp);
//				for (Board b : temp.neighbors()) {
//					if (!prev.equals(b) && !prevList.contains(b)){
//						b.setPriority(preMoveCount+1);
//						queue.insert(b);					
//					}
//				}
//				System.out.println("have scanned " + prevList.size() + " items");
//			}
			Board tempTwin = queueTwin.delMin();
			System.out.println("Twin taking:");
			System.out.print(tempTwin);
			int preMoveCountTwin = tempTwin.getPriority() - tempTwin.hamming();
			if (tempTwin.isGoal()){
				System.out.println("The twin has completed");
				solvable = false;
				break;
			} else {
				prevListTwin.add(tempTwin);
				for (Board bTwin : tempTwin.neighbors()) {
					boolean duplicated = false;
//					System.out.println("In the Archive:");
					for (Board archv : prevListTwin) {
//						System.out.print(archv);
						if (bTwin.equals(archv)) {
//							System.out.println("Processed before, skipped");
							duplicated = true;
						}
					}
//					System.out.println("End of Archive");
					if(!duplicated){
						bTwin.setPriority(preMoveCountTwin+1);
						queueTwin.insert(bTwin);
//						System.out.println("Put into PQ:");
//						System.out.print(bTwin);
					}
//					try {
//						System.in.read();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
			}
		}
	}
	
	public boolean isSolvable() {
		return solvable;
	}

	public static void main(String[] args) {

	    // create initial board from file
	    In in = new In(args[0]);
	    int N = in.readInt();
	    int[][] blocks = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
//	    else {
//	        StdOut.println("Minimum number of moves = " + solver.moves());
//	        for (Board board : solver.solution())
//	            StdOut.println(board);
//	    }

	}

}
