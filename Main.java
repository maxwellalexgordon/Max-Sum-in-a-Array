import java.util.ArrayList;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        //generate a list
        Random rand = new Random();
        int n = 10;  //length of test data
        int[] testData = new int[n];

        for (int i = 0; i < n; i++) {
            if (rand.nextBoolean()) {
                testData[i] = -1 * (rand.nextInt(10));
            } else {
                testData[i] = (rand.nextInt(10));
            }
        }


        //brute
        // maxSumArray result = Brute(testData);

        //divid and concer
        //maxSumArray result = divideConcur(testData, 0, testData.length - 1);

        //quick
        maxSumArray result = Kadane(testData);

        //test bench
        System.out.println(result.sum);
        System.out.println(result.start);
        System.out.println(result.end);
        for (int i = 0; i <testData.length ; i++) {
            System.out.print(testData[i]+ " ");
        }


    }

    public static maxSumArray betterMax(maxSumArray a, maxSumArray c) {
        if (a.sum > c.sum) {
            return a;
        } else {
            return c;
        }
    }

    public static int sum(ArrayList<Integer> arr) {
        int sum = 0;
        for (int temp : arr) {
            sum += temp;
        }
        return sum;
    }


    public static maxSumArray Brute(int[] arr) {
        //max
        maxSumArray max = new maxSumArray(0, 0, 0);

        //keep tracks of max array, and (first,last) indecies
        ArrayList<Integer> current = new ArrayList<Integer>();

        for (int i = 0; i < arr.length; i++) {
            current.clear();
            for (int j = i; j < arr.length; j++) {
                //update current
                current.add(arr[j]);
                //compare to max
                if (sum(current) > max.sum) {
                    //if true copy array over & update indecise
                    max = new maxSumArray(i, j, sum(current));


                }
            }
        }

        return max;
    }

    public static maxSumArray maxCrossingArray(int[] arr, int low, int high, int middle) {
        maxSumArray max = new maxSumArray(middle, middle, 0);

        //left add until less
        int leftSum = Integer.MIN_VALUE;
        int tempSum = 0; //needed to keep running tally, even if you dont use it
        for (int i = middle; i >= low; i--) {
            tempSum += arr[i];
            if (tempSum > leftSum) {
                leftSum = tempSum;
                max.start = i;
            }
        }

        //right
        int rightSum =Integer.MIN_VALUE;
        tempSum = 0;  //running tally set back to zero
        for (int j = middle + 1; j <= high; j++) {
            tempSum += arr[j];
            if (tempSum > rightSum) {
                rightSum = tempSum;
                max.end = j;
            }
        }
        max.sum = leftSum + rightSum;
        return max;
    }


    public static maxSumArray divideConcur(int[] arr, int low, int high) {
        //base case
        if (low == high) {
            return new maxSumArray(low, high, arr[0]);
        }
        //find mid point
        int mid = (high + low) / 2;

        //takes the max{self made function: max(a,max(b,c)) }
        maxSumArray max = betterMax(divideConcur(arr, low, mid), betterMax(divideConcur(arr, mid + 1, high), maxCrossingArray(arr, low, high, mid)));
        return max;
    }


    public static maxSumArray Kadane(int[] arr){
        int maxSumSoFar = 0;
        int sum = 0;
        int maxStartUntilNow = 0;

        maxSumArray max = new maxSumArray(0,0,0);

        //loop through array
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];

           //compare and update if neccisary
            if(maxSumSoFar < sum){
                maxSumSoFar = sum;
                max.sum = maxSumSoFar;
                max.end = i;
                max.start = maxStartUntilNow;
            }
            //reset if below zero
            if(sum < 0){
                sum = 0;
                maxStartUntilNow = i + 1;
            }
        }
            return max;
    }
}


