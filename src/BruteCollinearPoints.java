import edu.princeton.cs.algs4.SegmentTree;

import javax.sound.sampled.Line;

public class BruteCollinearPoints {
    private Point[] points;
    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        for (int i=0; i<points.length-1; i++)
            for (int j=i+1; j<points.length;j++)
                if (points[i].compareTo(points[j])==0)
                    throw new java.lang.IllegalArgumentException("");
        for (int i=0; i<points.length; i++)
            if (points[i]==null)
                throw new java.lang.IllegalArgumentException("");
        this.points = points;
    }

    public int numberOfSegments()
    {   // the number of line segments
        LineSegment[] segments = segments();
        return segments.length;
    }

    public LineSegment[] segments()
    {   // the line segments}
        LineSegment[] finalSegmentArray = new LineSegment[10];
        int NumberOfSegments = 0;
        Point[] fourPoints = new Point[4];
        for (int i=0;i<points.length;i++)
        {
            for (int j = 0; j < points.length; j++)
            {
                if (j==i)   continue;
                for (int k = 0; k < points.length; k++)
                {
                    if (k==j || k==i)   continue;
                    for (int m = 0; m < points.length; m++)
                    {
                        if (m==k || m==j || m==i)   continue;
                        fourPoints[0]=points[i];
                        fourPoints[1]=points[j];
                        fourPoints[2]=points[k];
                        fourPoints[3]=points[m];
                        LineSegment tempSegment = colinearHelper(fourPoints);
                        if (tempSegment!=null)
                        {
                            if (NumberOfSegments == finalSegmentArray.length)
                            {
                                finalSegmentArray= resize(finalSegmentArray,2*finalSegmentArray.length);
                            }

                            finalSegmentArray[NumberOfSegments] = tempSegment;
                            LineSegment tempSegment2 = colinearHelper(fourPoints);

                            NumberOfSegments++;
                        }
                    }
                }
            }
        }
        finalSegmentArray= resize(finalSegmentArray,NumberOfSegments);
        return finalSegmentArray;
    }

    private LineSegment[] resize(LineSegment[] segments, int n)
    {
        LineSegment[] tempSegment = new LineSegment[n];
        for (int i = 0; i<Math.min(n, segments.length); i++)
        {
            tempSegment[i] = segments[i];
        }
        return tempSegment;
    }


    /**
     * Private helper function that decides whether four points are collinear
     * If yes, return the line segment.
     * If no, return null.
     */
    private LineSegment colinearHelper(Point[] fourPoints)
    {
        double slopes[] = new double[3];
        for(int i=0;i<fourPoints.length-1;i++)
            if(fourPoints[i].compareTo(fourPoints[i+1])>-1)
                return null;
            else
                slopes[i]=fourPoints[i].slopeTo(fourPoints[i+1]);

        for (int i=0;i<2;i++)
            if (slopes[i] != slopes[i + 1])
                return null;

        return new LineSegment(fourPoints[0], fourPoints[3]);
    }

}