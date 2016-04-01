package kdtree;

import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
	private SET<Point2D> pointSet;

	public PointSET() {
		pointSet = new SET<Point2D>();
	}
	
	public boolean isEmpty() {
		return pointSet.isEmpty();
	}
	
	public int size() {
		return pointSet.size();
	}
	
	public void insert(Point2D p) {
		pointSet.add(p);
	}
	
	public boolean contains(Point2D p) {
		return pointSet.contains(p);
	}
	
	public void draw() {
		for (Point2D p : pointSet) {
			p.draw();
		}
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		ArrayList<Point2D> pointsInside = new ArrayList<Point2D>();
		for (Point2D p : pointSet) {
			if (rect.contains(p)) {
				pointsInside.add(p);
			}
		}
		return pointsInside;
	}
	
	public Point2D nearest(Point2D p) {
		double minDistance = Double.POSITIVE_INFINITY;
		Point2D closestPoint = null;
		for (Point2D q : pointSet) {
			if (q.distanceTo(p) < minDistance && !q.equals(p)) {
				closestPoint = q;
			}
		}
		return closestPoint;
	}

	public static void main(String[] args) {

        String filename = args[0];
        In in = new In(filename);

        StdDraw.show(0);

        // initialize the data structures with N points from standard input
        PointSET brute = new PointSET();
//        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
//            kdtree.insert(p);
            brute.insert(p);
        }
        System.out.println(brute.size() + " points are added");

        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        brute.draw();

        while (true) {
            StdDraw.show(40);

            // user starts to drag a rectangle
            if (StdDraw.mousePressed() && !isDragging) {
                x0 = StdDraw.mouseX();
                y0 = StdDraw.mouseY();
                isDragging = true;
                continue;
            }

            // user is dragging a rectangle
            else if (StdDraw.mousePressed() && isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
                continue;
            }

            // mouse no longer pressed
            else if (!StdDraw.mousePressed() && isDragging) {
                isDragging = false;
            }


            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
                                     Math.max(x0, x1), Math.max(y0, y1));
            // draw the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            brute.draw();

            // draw the rectangle
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();
            rect.draw();

            // draw the range search results for brute-force data structure in red
            StdDraw.setPenRadius(.01);
            StdDraw.setPenColor(StdDraw.RED);
            for (Point2D p : brute.range(rect))
                p.draw();

            // draw the range search results for kd-tree in blue
//            StdDraw.setPenRadius(.02);
//            StdDraw.setPenColor(StdDraw.BLUE);
//            for (Point2D p : kdtree.range(rect))
//                p.draw();

            StdDraw.show(40);
        }
	}

}
