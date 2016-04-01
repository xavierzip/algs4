import java.util.ArrayList;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph g;
    private int gSize;
//    private boolean isDAG;
    
    public SAP(Digraph G) {
        if (G == null)
            throw new NullPointerException("invalid input");
        this.gSize = G.V();
        this.g = new Digraph(G);
//        isDAG = false;
//        for (int i = 0; i < G.V(); i++) {
//            if (G.outdegree(i) == 0) {
//                isDAG = true;
//            }
//        }
        
    }
    
    public int length(int v, int w) {
        if (v < 0 || v > gSize || w < 0 || w > gSize) 
            throw new IndexOutOfBoundsException("vertex not exits");  
        ArrayList<Integer> vL = new ArrayList<Integer>();
        vL.add(v);
        ArrayList<Integer> wL = new ArrayList<Integer>();
        wL.add(w);
        return length(vL, wL);
    }
    
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new NullPointerException("invalid input");
        int len = getAncestor2(v, w)[1];
        if (len != Integer.MAX_VALUE)
            return len;
        return -1;
    }
    
    public int ancestor(int v, int w) {
        if (v < 0 || v > gSize || w < 0 || w > gSize) 
            throw new IndexOutOfBoundsException("vertex not exits");
        ArrayList<Integer> vL = new ArrayList<Integer>();
        vL.add(v);
        ArrayList<Integer> wL = new ArrayList<Integer>();
        wL.add(w);
        return ancestor(vL, wL);
    }
    
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new NullPointerException("invalid input");
        return getAncestor2(v, w)[0];
    }
    
    private int[] getAncestor2(Iterable<Integer> v, Iterable<Integer> w) {
        // Declare variables
        boolean[] vR = new boolean[gSize];
        boolean[] wR = new boolean[gSize];
        int[] vDist = new int[gSize];
        int[] wDist = new int[gSize];
        Queue<Integer> vQ = new Queue<Integer>();
        Queue<Integer> wQ = new Queue<Integer>();
        
        // Initialize the arrays
        for (int i = 0; i < vDist.length; i++) {
            vDist[i] = Integer.MAX_VALUE;
            wDist[i] = Integer.MAX_VALUE;
        }
        for (int t : v) {
            vR[t] = true;
            vDist[t] = 0;
            vQ.enqueue(t);
        }
        while (!vQ.isEmpty()) {
            int i = vQ.dequeue();
            for (int j : g.adj(i)) {
                if (!vR[j]) {
                    vR[j] = true;
                    vDist[j] = vDist[i] + 1;
                    vQ.enqueue(j);
                }
            }
        }
        for (int t : w) {
            wR[t] = true;
            wDist[t] = 0;
            wQ.enqueue(t);
        }
        while (!wQ.isEmpty()) {
            int i = wQ.dequeue();
            for (int j : g.adj(i)) {
                if (!wR[j]) {
                    wR[j] = true;
                    wDist[j] = wDist[i] + 1;
                    wQ.enqueue(j);
                }
            }
        }
        int vCurrentCount = 0;
        boolean[] vM = new boolean[gSize];
        for (int t : v) {
            vQ.enqueue(t);
            vM[t] = true;
            vCurrentCount++;
        }
        int wCurrentCount = 0;
        boolean[] wM = new boolean[gSize];
        for (int t : w) {
            wQ.enqueue(t);
            wM[t] = true;
            wCurrentCount++;
        }
        int vNextCount = 0;
        int wNextCount = 0;
        int finalDistance = Integer.MAX_VALUE;
        int finalAncestor = -1;
        boolean runFlag = true;
        while (runFlag && (!vQ.isEmpty() || !wQ.isEmpty())) {
            while (vCurrentCount > 0) {
                int vItem = vQ.dequeue();
                vCurrentCount--;
                if (wR[vItem]) {
                    int tempDistance = wDist[vItem] + vDist[vItem];
                    if (tempDistance < finalDistance) {
                        finalDistance = tempDistance;
                        finalAncestor = vItem;
                    } else {
//                        if (isDAG) runFlag = false;
                    }
                }
                for (int a : g.adj(vItem)) {
                    if (!vM[a]) {
                        vM[a] = true;
                        vQ.enqueue(a);
                        vNextCount++;
                    }
                }
            }
            vCurrentCount = vNextCount;
            vNextCount = 0;
            while (wCurrentCount > 0) {
                int wItem = wQ.dequeue();
                wCurrentCount--;
                if (vR[wItem]) {
                    int tempDistance = vDist[wItem] + wDist[wItem];
                    if (tempDistance < finalDistance) {
                        finalDistance = tempDistance;
                        finalAncestor = wItem;
                    } else {
//                        if (isDAG) runFlag = false;
                    }
                }
                for (int b : g.adj(wItem)) {
                    if (!wM[b]) {
                        wM[b] = true;
                        wQ.enqueue(b);
                        wNextCount++;
                    }
                }
            }
            wCurrentCount = wNextCount;
            wNextCount = 0;
        }
        return new int[] {finalAncestor, finalDistance};
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            if (v < 0 || w < 0) {
                break;
            }   
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
        ArrayList<Integer> vL = new ArrayList<Integer>();
        ArrayList<Integer> wL = new ArrayList<Integer>();
        vL.add(7);
        vL.add(4);
//      wL.add(4);
        wL.add(2);
        int length   = sap.length(vL, wL);
        int ancestor = sap.ancestor(vL, wL);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);

    }

}
