import java.util.Arrays;

public class FastCollinearPoints {
    Point[] points;
    public FastCollinearPoints(Point[] points)
    {   // finds all line segments containing 4 or more points
        for (int i=0; i<points.length-1; i++)
            for (int j=i+1; j<points.length;j++)
                if (points[i].compareTo(points[j])==0)
                    throw new java.lang.IllegalArgumentException("");
        for (int i=0; i<points.length; i++)
            if (points[i]==null)
                throw new java.lang.IllegalArgumentException("");
        // Sort the points according to the order defined in Point.java
        Arrays.sort(points);
        this.points = points;
    }
    public  int numberOfSegments()
    {   // the number of line segments
        LineSegment[] results = segments();
        return results.length;
    }
    public LineSegment[] segments()
    {   // the line segments
        LineSegment[] newseg = new LineSegment[4];
        return newseg;
    }



}