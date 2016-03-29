
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	private int N;
	private int lineCount;
	private ArrayList<LineSegment> lines;

	public BruteCollinearPoints(Point[] points) {
		lines = new ArrayList<LineSegment>();
		lineCount = 0;
		N = points.length;
		Arrays.sort(points);
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				for (int k = j + 1; k < N; k++) {
					for (int l = k + 1; l < N; l++) {
						double slopeij = points[i].slopeTo(points[j]);
						double slopeik = points[i].slopeTo(points[k]);
						double slopeil = points[i].slopeTo(points[l]);
						if (slopeij == slopeik && slopeik == slopeil){
							LineSegment line = new LineSegment(points[i], points[l]);
							lines.add(line);
							lineCount++;
						}
					}
				}
			}
		}
	}
	
	public int numberOfSegments() {
		return lineCount;
		
	}
	
	public LineSegment[] segments() {
		return lines.toArray(new LineSegment[0]);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
