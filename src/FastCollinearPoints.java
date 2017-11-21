import java.util.Arrays;

public class FastCollinearPoints {
	private final int N;
	private final int N_LINE;
	private LineSegment[] segments;
	private int nbSegments = 0;

	public FastCollinearPoints(Point[] points) { // finds all line segments
													// containing 4 points
		validPoints(points);
		N = points.length;
		N_LINE = N * (N - 1);
		segments = initsegments(points);
	}

	private void validPoints(Point[] points) {
		if (points == null)
			throw new IllegalArgumentException("Points is null");
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null)
				throw new IllegalArgumentException("Point is null");
		}
		Arrays.sort(points);
		for (int i = 1; i < points.length; i++) {
			if (points[i].compareTo(points[i - 1]) == 0)
				throw new IllegalArgumentException("Points is duplicate");
		}
	}

	public int numberOfSegments() { // the number of line segments
		return nbSegments;
	}

	public LineSegment[] segments() { // the line segments
		return segments;
	}

	private LineSegment[] initsegments(Point[] points) {
		Point[] pointStarts = new Point[N_LINE];
		Point[] pointEnds = new Point[N_LINE];
		for (int i = 0; i < N - 3; i++) {
			Point p1 = points[i];
//			System.out.println("***start i= " + i + " , p= " + p1);
			Point[] pointCheck = new Point[N - i - 1];
			for (int j = i + 1; j < N; j++) {
				Point p2 = points[j];
				pointCheck[j - i - 1] = p2;
			}
			Arrays.sort(pointCheck, p1.slopeOrder());
			double prevSlope = p1.slopeTo(pointCheck[0]);
			int count = 1;
//			for (int j = 0; j < pointCheck.length; j++) {
//				Point p = pointCheck[j];
//				System.out.println(p1.slopeTo(p));
//			}
			for (int j = 1; j < pointCheck.length; j++) {
				Point start = null, end = null;
				Point p = pointCheck[j];
				double currSlope = p1.slopeTo(p);
				if (currSlope == prevSlope) {
					count++;
					if (j == pointCheck.length - 1 && count >= 3) {
						if (p1.compareTo(pointCheck[j - count + 1]) < 0) {
							start = p1;
							end = pointCheck[j];
						} else if (p1.compareTo(pointCheck[j]) > 0) {
							start = pointCheck[j - count + 1];
							end = p1;
						} else {
							start = pointCheck[j - count + 1];
							end = pointCheck[j];
						}
					}
				} else {
					if (count >= 3) {
						start = p1;
						end = pointCheck[j - 1];
					}
					count = 1;
					prevSlope = currSlope;
				}
				if (start != null && end != null) {
					insertSegments(pointStarts, pointEnds, start, end);
				}
			}
		}
		LineSegment[] segs_local = new LineSegment[nbSegments];
		for (int i = 0; i < nbSegments; i++) {
			segs_local[i] = new LineSegment(pointStarts[i], pointEnds[i]);
		}
		return segs_local;
	}

	private void insertSegments(Point[] pointStarts, Point[] pointEnds, Point start, Point end) {
		boolean exist = false;
		for (int j = 0; j < pointStarts.length; j++) {
			Point s = pointStarts[j];
			Point e = pointEnds[j];
			if (s == null || e == null)
				break;
			if (isCollinear(s, e, start, end)) {
				exist = true;
				break;
			}
		}
		if (!exist) {
			pointStarts[nbSegments] = start;
			pointEnds[nbSegments] = end;
			nbSegments++;
		}
	}

	private boolean isCollinear(Point start1, Point end1, Point start2, Point end2) {
		double a1 = start1.slopeTo(end1) - start2.slopeTo(end2);
		double a2 = start1.slopeTo(start2) - end1.slopeTo(end2);
		return a1 == 0L && a2 == 0L;
	}
}
