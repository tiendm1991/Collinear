import java.util.Arrays;

public class FastCollinearPoints {
	private final int N;
	private final int N_LINE;
	private LineSegment[] segments;
	public FastCollinearPoints(Point[] points) { // finds all line segments containing 4 points
		if(points == null) throw new NullPointerException("Points is null");
		for(int i = 0; i < points.length; i++){
			if(points[i] == null) throw new IllegalArgumentException("Points is Illegal");
		}
		N = points.length;
		N_LINE = N * (N-1);
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
		for(int i = 0 ; i < N-3; i++){
			Point p1 = points[i];
			LineSegment seg = null;
//			System.out.println("***start i= " + i + " , p= "+ p1);
			if(p1 == null) throw new IllegalArgumentException("Point1 is null");
			Point[] pointCheck = new Point[N-i-1];
			for(int j = i+1; j < N ; j++){
				Point p2 = points[j];
				if(p2 == null) throw new IllegalArgumentException("Point2 is null");
				pointCheck[j-i-1] = p2; 
			}
			Arrays.sort(pointCheck, p1.slopeOrder());
			double prevSlope = p1.slopeTo(pointCheck[0]);
			int count = 1;
//			for(int j = 0; j < pointCheck.length; j++){
//				Point p = pointCheck[j];
//				System.out.println(p1.slopeTo(p));
//			}
			for(int j = 1; j < pointCheck.length; j++){
				Point p = pointCheck[j];
				double currSlope = p1.slopeTo(p);
				if( currSlope == prevSlope){
					count++;
					if(j == pointCheck.length-1 && count >= 3){
						if(p1.compareTo(pointCheck[j-count+1]) < 0){
							seg = new LineSegment(p1, pointCheck[j]);
						}else if (p1.compareTo(pointCheck[j]) > 0) {
							seg = new LineSegment(pointCheck[j-count+1], p1);
						}else {
							seg = new LineSegment(pointCheck[j-count+1], pointCheck[j]);
						}
					}
				}else {
					if(count >= 3){
						seg = new LineSegment(p1, pointCheck[j-1]);
					}
					count = 1;
					prevSlope = currSlope;
				}
			}
			boolean exist = false;
			for(LineSegment s : segs){
				if(s == null) break;
				if(seg == null || s.toString().equals(seg.toString())){
					exist = true;
					break;
				}
			}
			if(!exist){
				segs[nbSegments++] = seg;
			}
		}
		LineSegment[] segs_local = new LineSegment[nbSegments];
		for(int i = 0; i < nbSegments; i++){
			segs_local[i] = segs[i];
		}
		return segs_local;
	}
}
