import java.util.Arrays;

public class BruteCollinearPoints {
	private final int N;
	private final int N_LINE;
	private LineSegment[] segments;

	public BruteCollinearPoints(Point[] points) { // finds all line segments
													// containing 4 points
		if(points == null) throw new NullPointerException("Points is null");
		for(int i = 0; i < points.length; i++){
			if(points[i] == null) throw new IllegalArgumentException("Points is Illegal");
		}
		N = points.length;
		N_LINE = N * (N - 1);
		segments = initsegments(points);
	}

	public int numberOfSegments() { // the number of line segments
		return segments.length;
	}

	public LineSegment[] segments() { // the line segments
		return segments;
	}

	private LineSegment[] initsegments(Point[] points) {
		Arrays.sort(points);
		LineSegment[] segs = new LineSegment[N_LINE];
		int nbSegments = 0;
		for (int i = 0; i < N; i++) {
			Point p1 = points[i];
			if (p1 == null)
				throw new IllegalArgumentException("Point is null");
			Point[] sub = new Point[4];
			sub[0] = p1;
			// System.out.println("**Start***");
			// System.out.println("p1: " + p1);
			for (int j = i + 1; j < N; j++) {
				Point p2 = points[j];
				if (p2 == null)
					throw new IllegalArgumentException("Point is null");
				// System.out.println("p2: " + p2);
				double slope12 = p1.slopeTo(p2);
				// System.out.println("slope12: " + slope12);
				sub[1] = p2;
				for (int k = j + 1; k < N; k++) {
					Point p3 = points[k];
					if (p3 == null)
						throw new IllegalArgumentException("Point is null");
					// System.out.println("p3: " + p3);
					double slope13 = p1.slopeTo(p3);
					// System.out.println("slope13: " + slope13);
					if (slope12 != slope13)
						continue;
					sub[2] = p3;
					for (int l = k + 1; l < N; l++) {
						Point p4 = points[l];
						if (p4 == null)
							throw new IllegalArgumentException("Point is null");
						// System.out.println("p4: " + p4);
						double slope14 = p1.slopeTo(p4);
						// System.out.println("slope14: " + slope14);
						if (slope13 == slope14) {
							sub[3] = p4;
							Arrays.sort(sub);
							LineSegment seg = new LineSegment(sub[0], sub[3]);
							boolean exist = false;
							for (LineSegment s : segs) {
								if (s == null)
									break;
								if (seg == null || s.toString().equals(seg.toString())) {
									exist = true;
									break;
								}
							}
							if (!exist) {
								segs[nbSegments++] = seg;
								// System.out.println("create seg : " + seg);
							}
						}
					}
				}
			}
		}
		LineSegment[] segments = new LineSegment[nbSegments];
		for (int i = 0; i < nbSegments; i++) {
			segments[i] = segs[i];
		}
		return segments;
	}

}
