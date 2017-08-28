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
        Arrays.sort(points); //Takes O(nlogn) time
        this.points = points;
    }
    public  int numberOfSegments()
    {   // the number of line segments
        LineSegment[] results = segments();
        return results.length;
    }

    public LineSegment[] segments()
    {   // the line segments
        LineSegment[] results= new LineSegment[10];
        double[] slopes;
        Point[] tempPointsArray;
        int pointer;
        int counter;
        double slopeValue;
        for (int i = 0; i < points.length - 1; i++)
        {   //Loop: for each starting point points[i]
            slopes = new double[points.length-i-1];
            tempPointsArray= new Point[points.length-i-1];
            for (int j = i+1; j<points.length;j++)
            {
                //Iteratively calculate all slopes from point[j] to point[i]
                //Iteratively copy all the points from point[i+1] to tempPointArray[]
                // Takes O(n) time
                slopes[j - (i + 1)] = points[i].slopeTo(points[j]);
                tempPointsArray[j - (i + 1)] = points[j];
            }
            //Sort the slopes array and its corresponding Points array non-destructively
            // Merge-sort is a good choice because it is non-destructive and fast. How about its space usage?
            for (int k=0; k<points.length;k++) // copy the points array
            mergeSort(slopes, tempPointsArray, 0, points.length-i-2);
            counter=0;
            for (pointer=0;pointer<slopes.length;pointer++)
            {
                slopeValue=slopes[pointer];

                for ()


            }




        }

        ///the following line is invalid
        return results;

    }

    /**
     * mergeSort is a private helper function that
     * sorts the slopes array as well as its corresponding points array
     *
     * */
    private void mergeSort(double[] slopes, Point[] points, int i, int j)
    {   //When first called, i =0, j=N-1
        if (i==j)   return; //base case of recursive function;
        int mid = (i+j)/2;
        mergeSort(slopes, points, i, mid);
        mergeSort(slopes,points,mid+1,j);
        merge(slopes,points,i,j);
    }

    private void merge (double[] slopes, Point[] points, int i, int j)
    {
        if (j<=i) return; // this line is redundant, but i still kept it here for safety.
        Point[] pointsCopy = new Point[j-i+1];
        double[] slopesCopy = new double[j-i+1];

        for (int g=0;g<j-i+1;g++)
        {
            pointsCopy[g]= points[i+g];
            slopesCopy[g]= slopes[i+g];
        }

        int mid = (j-i+1)/2;
        int first_ref =0;
        int second_ref=mid+1;

        for (int n=0;n<(j-i+1);n++)
        {
            if (first_ref>mid)
            {
                points[i + n] = pointsCopy[second_ref];
                slopes[i+n] = slopesCopy[second_ref];
                second_ref++;
            }
            else if (second_ref>j-i+1-1)
            {
                points[i+n]= pointsCopy[first_ref];
                slopes[i+n]= slopesCopy[first_ref];
                first_ref++;
            }
            else
            {
                if (slopes[first_ref]<=slopes[second_ref])
                {
                    points[i+n]= pointsCopy[first_ref];
                    slopes[i+n]= slopesCopy[first_ref];
                    first_ref++;
                }
                else
                {
                    points[i+n]=pointsCopy[second_ref];
                    slopes[i+n]=slopesCopy[second_ref];
                    second_ref++;
                }
            }
        }
    }


}