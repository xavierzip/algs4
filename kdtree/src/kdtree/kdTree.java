package kdtree;

import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class kdTree {
	private static final boolean HORIZONTAL = false;
	
	private Node root;
	
	private class Node {
		private Point2D point;
		private Node left, right;
		private int N;
		private boolean divider;
		private double lowerX;
		private double lowerY;
		private double upperX;
		private double upperY;
		
		public Node (Point2D point, int N, boolean divider, double lX, double lY, double uX, double uY) {
			this.point = point;
			this.divider = divider;
			this.N = N;
			this.lowerX = lX;
			this.lowerY = lY;
			this.upperX = uX;
			this.upperY = uY;
		}
	}

	public kdTree() {
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public int size() {
		return size(root);
	}
	
	private int size(Node x) {
		if (x == null) return 0;
		else return x.N;
	}
	
	public void insert(Point2D p) {
		if (p == null) throw new NullPointerException("argument to insert() is null");
		if (isEmpty()) {
			root = insert(root, p, HORIZONTAL, 0, 0, 1, 1);
		} else {
			root = insert(root, p, root.divider, 0, 0, 1, 1);
		}		
		assert check();
	}
	
	private Node insert(Node x, Point2D p, boolean divider, double lX, double lY, double uX, double uY) {
		if (x == null) return new Node(p, 1, !divider, lX, lY, uX, uY);	// if there is nothing in the tree yet, just add the node to the tree, it will be root
		if (x.point.equals(p)) x.point = p;
		if (x.divider) {
			// the node is a vertical node, shall compare the x coordinates
			if (p.x() < x.point.x()) {
				x.left = insert(x.left, p, x.divider, x.lowerX, x.lowerY, x.point.x(), x.upperY);
			} else {
				x.right = insert(x.right, p, x.divider, x.point.x(), x.lowerY, x.upperX, x.upperY);
			}
		} else {
			// the node is a horizontal node, shall compare the y coordinates
			if (p.y() < x.point.y()) {
				x.left = insert(x.left, p, x.divider, x.lowerX, x.lowerY, x.upperX, x.point.y());
			} else {
				x.right = insert(x.right, p, x.divider, x.lowerX, x.point.y(), x.upperX, x.upperY);				
			}
		}
		x.N = 1 + size(x.left) + size(x.right);
		return x;
	}
	
	public boolean contains(Point2D p) {
		return get(root, p) != null;
	}
	
	private Point2D get(Node x, Point2D p) {
		if (x == null) return null;
		if (x.point.equals(p)) return x.point;
		if (x.divider) {
			// the node is a vertical node, shall compare the x coordinates
			if (p.x() < x.point.x()) {
				return  get(x.left, p);
			} else {
				return  get(x.right, p);
			}
		} else {
			// the node is a horizontal node, shall compare the y coordinates
			if (p.y() < x.point.y()) {
				return get(x.left, p);
			} else {
				return get(x.right, p);
			}
		}
	}
	
	public void draw() {
		drawLine(root);
	}
	
	private void drawLine(Node x) {
		if (x == null) return;
//		System.out.println("drawing " + x.point.x() + "," + x.point.y() );
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
		x.point.draw();
		
		// to draw line for debugging
//		if (x.divider) {
//	        StdDraw.setPenColor(StdDraw.RED);
//	        StdDraw.setPenRadius(.005);
//	        StdDraw.line(x.point.x(), x.lowerY, x.point.x(), x.upperY);
//		} else {
//	        StdDraw.setPenColor(StdDraw.BLUE);
//	        StdDraw.setPenRadius(.005);
//	        StdDraw.line(x.lowerX, x.point.y(), x.upperX, x.point.y());
//		}
		// end of debugging
		
		drawLine(x.left);
		drawLine(x.right);
	}
	
	private boolean check() {
		if (!isSizeConsistent()) StdOut.println("Subtree counts not consistent");
		return isSizeConsistent();
	}
	
	private boolean isSizeConsistent() {
		return isSizeConsistent(root);
	}
	
	private boolean isSizeConsistent(Node x) {
		if (x == null) return true;
		if (x.N != size(x.left) + size(x.right) + 1) return false;
		return isSizeConsistent(x.left) && isSizeConsistent(x.right);
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		ArrayList<Point2D> points = new ArrayList<Point2D>();
		range(root, points, rect);
		return points;
	}
	
	private void range(Node x, ArrayList<Point2D> points, RectHV rect) {
		if (x == null) return;
		if (x.upperY < rect.ymin() || x.lowerY > rect.ymax() 
				|| x.lowerX > rect.xmax() || x.upperX < rect.xmin()) {
			return;
		}
		if (rect.contains(x.point)) {
			points.add(x.point);
		}
		range(x.left, points, rect);
		range(x.right, points, rect);
		return;
	}
	
	public Point2D nearest(Point2D p) {
		Node x = searchBf(root, p, root);
		if (x == null) return null;
		else return x.point;
	}
	
	private Node searchBf(Node x, Point2D p, Node bf) {
		if (x == null) return bf;
		System.out.println("New point: "+ "("+x.point.x()+","+x.point.y()+")");
		double minD = bf.point.distanceTo(p);	// this is the minimum distance so far
		RectHV rect = new RectHV(x.lowerX, x.lowerY, x.upperX, x.upperY);
		System.out.println("new rect: " + "("+x.lowerX+","+x.lowerY+")"+","+"("+x.upperX+","+x.upperY+")");
		double checkD = rect.distanceTo(p);
		System.out.println("Rect dist " + checkD);
		if (checkD > minD) {
			// termination point
			System.out.println("Stop search the subtree");
			return bf;
		} else {
			double newD = x.point.distanceTo(p);
			System.out.println("New dist: " + newD);
			if (newD < minD) {
				bf = x;
				System.out.println("update bf");
			}
			if ((p.x() < x.point.x() && x.divider) || (!x.divider && p.y() < x.point.y())) {
				bf =   searchBf(x.left, p, bf);
				bf =   searchBf(x.right, p, bf);
			} else {
				bf =   searchBf(x.right, p, bf);
				bf =   searchBf(x.left, p, bf);
			}
			return bf;
		}
	}
	
	
	public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        StdDraw.show(0);

        // initialize the two data structures with point from standard input
        PointSET brute = new PointSET();
        kdTree kdtree = new kdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }

        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            brute.draw();

            // draw in red the nearest neighbor (using brute-force algorithm)
//            StdDraw.setPenRadius(.03);
//            StdDraw.setPenColor(StdDraw.RED);
//            brute.nearest(query).draw();
//            StdDraw.setPenRadius(.02);

            // draw in blue the nearest neighbor (using kd-tree algorithm)
            StdDraw.setPenColor(StdDraw.BLUE);
            kdtree.nearest(query).draw();
            StdDraw.show(0);
            StdDraw.show(40);
        }
	}

}
