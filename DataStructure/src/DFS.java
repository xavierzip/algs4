import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class DFS {
	private boolean[] marked;
	private int[] edgeTo;
	private int count;
	private int s;

	public DFS(Graph G, int s) {
		marked = new boolean[G.getV()];
		edgeTo = new int[G.getV()];
		this.s = s;
		dfs(G, s);
	}
	
	private void dfs(Graph G, int v) {
		count++;
		marked[v] = true;
		for (int w : G.adj(v)) {
			if (!marked[w]) {
				edgeTo[w] = v;
				dfs(G, w);
			}
		}
	}
	
	public boolean marked(int v) {
		return marked[v];
	}
	
	public int count() {
		return count;
	}
	
	public boolean hasPathTo(int v) {
		return marked[v];
	}
	
	public Iterable<Integer> pathTo(int v) {
			if (!hasPathTo(v)) return null;
			Stack<Integer> path = new Stack<Integer>();
			for (int x = v; x != s; x = edgeTo[x]) {
				path.push(x);
			}
			path.push(s);
			return path;
	}

	public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        int s = Integer.parseInt(args[1]);
        DFS dfs = new DFS(G, s);
        for (int v = 0; v < G.getV(); v++) {
            if (dfs.hasPathTo(v)) {
                StdOut.printf("%d to %d:  ", s, v);
                for (int x : dfs.pathTo(v)) {
                    if (x == s) StdOut.print(x);
                    else        StdOut.print("-" + x);
                }
                StdOut.println();
            }

            else {
                StdOut.printf("%d to %d:  not connected\n", s, v);
            }

        }

	}

}
