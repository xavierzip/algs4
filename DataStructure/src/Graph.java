import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Graph {
	private final int V;
	private int E;
	private Bag<Integer>[] adj;

	public Graph(int V) {
		if (V < 0) 
			throw new IllegalArgumentException(
					"Number of vertices must be nonngeative");
		this.V = V;
		this.E = 0;
		adj = (Bag<Integer>[]) new Bag[V];
		for (int v = 0; v < V; v++) {
			adj[v] = new Bag<Integer>();
		}
	}
	
	public Graph(In in) {
		this(in.readInt());
		int numOfEdges = in.readInt();
		if (numOfEdges < 0) 
			throw new IllegalArgumentException(
					"Number of edges must be nonnegative");
		for (int i = 0; i < numOfEdges; i++)	{
			int v = in.readInt();
			int w = in.readInt();
			addEdge(v, w);
		}
	}
	
	private void validateVertex(int v) {
		if (v < 0 || v >= V) {
			throw new IndexOutOfBoundsException("vertex " + v + " is not found");
		}
	}
	
	public void addEdge(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		adj[v].add(w);
		adj[w].add(v);
		E++;
	}
	
	public Iterable<Integer> adj(int v) {
		validateVertex(v);
		return adj[v];
	}
	
	public int getV() {
		return V;
	}
	
	public int getE() {
		return E;
	}
	
	public int degree(int v) {
		validateVertex(v);
		return adj[v].size();
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(V + " vertices, " + E + " edges\n");
		for (int v = 0; v < V; v++) {
			s.append(v + ": ");
			for (int w : adj[v]) {
				s.append(w + " ");
			}
			s.append("\n");
		}
		return s.toString();
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		Graph G = new Graph(in);
		StdOut.println(G);
	}
	
}
