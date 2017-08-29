import java.util.Arrays;

public class FastCollinearPoints {
    Point[] points;
    public FastCollinearPoints(Point[] points)
    {   // Constructor that prescreens the points input array
        for (int i=0; i<points.length-1; i++)
            for (int j=i+1; j<points.length;j++)
                if (points[i].compareTo(points[j])==0)
                    throw new java.lang.IllegalArgumentException("The input points should all be unique.");
        for (int i=0; i<points.length; i++)
            if (points[i]==null)
                throw new java.lang.IllegalArgumentException("the input points should not be null.");
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
    {   // return all the qualifying the line segments
        LineSegment[] results= new LineSegment[10];
        int num_segments=0;
        double[] slopes;
        Point[] tempPointsArray;
        int pointer;
        int pointer2;
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
            mergeSort(slopes, tempPointsArray, 0, points.length-i-2);

            //Now find out all collinear segments starting with the point[i]
            counter=0;
            pointer=0;
            while (pointer<slopes.length-1)
            {
                slopeValue=slopes[pointer];
                for (pointer2=pointer+1; pointer2<slopes.length; pointer2++)
                {
                    if (slopes[pointer2]!=slopeValue)
                    {
                        pointer = pointer2;
                        counter=0;
                        break;
                    }
                    else
                    {
                        counter++;
                        if (counter>=3)
                        {
                            if (num_segments == results.length)
                                results = resize(results, 2*results.length);
                            results[num_segments]= new LineSegment(tempPointsArray[pointer],tempPointsArray[pointer2]);
                            num_segments++;

                            // this is where i left off, most likely i see bugs around here;
                        }
                    }
                }
            }
        }

        results = resize(results,num_segments);
        return results;
    }

    /**
     * Private helper function, resize the array.
     *
     * */

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
        // question: should I create a static variable pointsCopy and slopesCopy in the FastCollinearPoints class
        // instead of as a local variable? This could save the time spend on recreating the variable in the recursive process

        for (int g=0;g<j-i+1;g++)
        {
            pointsCopy[g]= points[i+g];
            slopesCopy[g]= slopes[i+g];
        }

        int mid = (0+ j-i+1-1)/2;
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