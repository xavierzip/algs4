import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class CC {
	private int count;
	private int[] cc;
	private boolean[] marked;
	private int[] size;

	public CC(Graph G) {
		cc = new int[G.getV()];
		marked = new boolean[G.getV()];
		size = new int[G.getV()];
		count = 0;
		for (int v = 0; v < G.getV(); v++) {
			if (!marked[v]) {
				dfs(G, v);
				count++;
			}
		}
	}

	private void dfs(Graph G, int s) {
		marked[s] = true;
		cc[s] = count;
		size[count]++;
		for (int v : G.adj(s)) {
			if(!marked[v])
				dfs(G, v);
		}
	}
	
	public int id(int v) {
		return cc[v];
	}
	
	public int size(int v) {
		return size[cc[v]];
	}
	
	public int count() {
		return count;
	}
	
	public boolean areConnected(int v, int w) {
		return cc[v] == cc[w];
	}
	
	public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        CC cc = new CC(G);

        // number of connected components
        int M = cc.count();
        StdOut.println(M + " components");

        // compute list of vertices in each connected component
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[M];
        for (int i = 0; i < M; i++) {
            components[i] = new Queue<Integer>();
        }
        for (int v = 0; v < G.getV(); v++) {
            components[cc.id(v)].enqueue(v);
        }

        // print results
        for (int i = 0; i < M; i++) {
            for (int v : components[i]) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
	}

}
