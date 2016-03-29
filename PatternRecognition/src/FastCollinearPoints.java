import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {
	private int N;
	private ArrayList<LineSegment> lines;
	private ArrayList<Double> slopes;
	private ArrayList<Point> startPoints;
	
	public FastCollinearPoints(Point[] points) {
		lines = new ArrayList<LineSegment>();
		slopes = new ArrayList<Double>();
		startPoints = new ArrayList<Point>();
		N = points.length;
		Arrays.sort(points);
		for (Point p : points) {
			Point[] pointsClone = points.clone();
			Arrays.sort(pointsClone, p.slopeOrder());
			System.out.println(p);
			double slopeRef = Double.NEGATIVE_INFINITY;
			ArrayList<Point> line = new ArrayList<Point>();
			line.add(p);
			for (int i = 0; i < N; i++) {
				if (!p.equals(pointsClone[i])) {
					double slope1 = p.slopeTo(pointsClone[i]);
//					System.out.println(pointsClone[i] + ":" + slope1);
					if (slope1 == slopeRef) {
						line.add(pointsClone[i]);
					} else {
						if (line.size() >= 4){
							int idx = slopes.indexOf(slopeRef);
							if (idx == -1){
								slopes.add(slopeRef);
								Collections.sort(line);
								startPoints.add(line.get(0));
								System.out.println("new line:" + slopeRef);
								for (Point pt : line) {
									System.out.print(pt);
								}
								System.out.println();
								lines.add(new LineSegment(line.get(0), line.get(line.size() - 1)));
							} else {
								System.out.println("Same slope: " + slopeRef);
								Collections.sort(line);
								for (Point pt : line) {
									System.out.print(pt);
								}
								System.out.println();
								if (!line.get(0).equals(startPoints.get(idx))){
									slopes.add(slopeRef);
									startPoints.add(line.get(0));
									System.out.println("new line:" + slopeRef);
									for (Point pt : line) {
										System.out.print(pt);
									}
									System.out.println();
									lines.add(new LineSegment(line.get(0), line.get(line.size() - 1)));
								}
							}
						}
						slopeRef = slope1;
						line = new ArrayList<Point>();
						line.add(p);
						line.add(pointsClone[i]);
					}
				}				
			}
			if (line.size() >= 4){
				int idx = slopes.indexOf(slopeRef);
				if (idx == -1){
					slopes.add(slopeRef);
					Collections.sort(line);
					startPoints.add(line.get(0));
					System.out.println("new line:" + slopeRef);
					for (Point pt : line) {
						System.out.print(pt);
					}
					System.out.println();
					lines.add(new LineSegment(line.get(0), line.get(line.size() - 1)));
				} else {
					System.out.println("Same slope: " + slopeRef);
					Collections.sort(line);
					for (Point pt : line) {
						System.out.print(pt);
					}
					System.out.println();
					if (!line.get(0).equals(startPoints.get(idx))){
						slopes.add(slopeRef);
						startPoints.add(line.get(0));
						System.out.println("new line:" + slopeRef);
						for (Point pt : line) {
							System.out.print(pt);
						}
						System.out.println();
						lines.add(new LineSegment(line.get(0), line.get(line.size() - 1)));
					}
				}
			}
//			for (Point q : pointsClone) {
//				if (!p.equals(q)) {
//					System.out.print(q + ":"+p.slopeTo(q) + " ");
//				}				
//			}
			System.out.println();
		}
	}

	public int numberOfSegments() {
		return lines.size();
	}
	
	public LineSegment[] segments() {
		return lines.toArray(new LineSegment[0]);
	}
}
