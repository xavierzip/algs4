import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class BFS {
	private boolean[] marked;
	private int[] edgeTo;
	private int[] distTo;

	public BFS(Graph G, int s) {
		marked = new boolean[G.getV()];
		edgeTo = new int[G.getV()];
		distTo = new int[G.getV()];
		Queue<Integer> queue = new Queue<Integer>();
		for (int v = 0; v < G.getV(); v++) {
			distTo[v] = Integer.MIN_VALUE;
		}
		queue.enqueue(s);
		marked[s] = true;
		distTo[s] = 0;
		while (!queue.isEmpty()) {
			int w = queue.dequeue();
			for (int v : G.adj(w)) {
				if(!marked[v]) {
					queue.enqueue(v);
					distTo[v] = distTo[w] + 1;
					edgeTo[v] = w; 
					marked[v] = true;
				}
			}			
		}
	}
	
	public boolean hasPathTo(int v) {
		return marked[v];
	}
	
	public Iterable<Integer> pathTo(int v) {
		if (!hasPathTo(v)) return null;
		Stack<Integer> path = new Stack<Integer>();
		int x;
		for (x = v; distTo[x] != 0; x = edgeTo[x]) {
			path.push(x);
		}
		path.push(x);
		return path;
	}
	
	public int distTo(int v) {
		return distTo[v];
	}

	public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        // StdOut.println(G);

        int s = Integer.parseInt(args[1]);
        BFS bfs = new BFS(G, s);

        for (int v = 0; v < G.getV(); v++) {
            if (bfs.hasPathTo(v)) {
                StdOut.printf("%d to %d (%d):  ", s, v, bfs.distTo(v));
                for (int x : bfs.pathTo(v)) {
                    if (x == s) StdOut.print(x);
                    else        StdOut.print("-" + x);
                }
                StdOut.println();
            }

            else {
                StdOut.printf("%d to %d (-):  not connected\n", s, v);
            }

        }
	}

}
