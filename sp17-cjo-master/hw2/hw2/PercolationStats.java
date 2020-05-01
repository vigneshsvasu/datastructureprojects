package hw2;


import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
import hw2.Percolation;
import java.util.Arrays;

public class PercolationStats {
    private int gridSize;
    private int numTests;
    private Percolation newPerc;
    private double[] returnArr;
    public PercolationStats(int N, int T) throws IllegalArgumentException {   // perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N or T is less than 0.");
        }
        gridSize = N;
        numTests = T;
        returnArr = otherStddev();
    }
    public double mean() {                  // sample mean of percolation threshold
        return returnArr[0];
    }

    public int exclude(int[] arr) {
        int k = StdRandom.uniform(gridSize * gridSize - 1);
        for (int l = 0; l < arr.length; l++) {
            if (k == arr[l]) {
                k = StdRandom.uniform(gridSize * gridSize - 1);
                l = 0;
            }
        }
        return k;
    }

    public double stddev() {              // sample standard deviation of percolation threshold
        return returnArr[1];
    }

    public double[] otherStddev() {              // sample standard deviation of percolation threshold
        newPerc = new Percolation(gridSize);
        double average;
        double total = 0;
        double[] testArr = new double[numTests];
        int[] countArray;
        for (int l = 0; l < numTests; l++){
            countArray = new int[gridSize * gridSize - 1];
            for (int i = 0; i < gridSize * gridSize - 1; i++){
                int k = exclude(countArray);
                countArray[i] = k;
                int[] damnArr = new int[2];
                damnArr = Percolation.convertBack(k, gridSize);
                newPerc.open(damnArr[0], damnArr[1]);
                if (newPerc.percolates()){
                    double val = (double) newPerc.numberOfOpenSites() / ((double) gridSize * gridSize);
                    testArr[l] = val;
                    total = total + val;
                    break;
                }
            }
        }
        average = total / (double) numTests;
        double stdDev = StdStats.stddev(testArr);
        double[] finalArr = new double[4];
        finalArr[0] = average;
        finalArr[1] = stdDev;
        finalArr[2] = average - (1.96 * stdDev) / Math.sqrt((double) numTests);
        finalArr[3] = average + (1.96 * stdDev) / Math.sqrt((double) numTests);
        return finalArr;
    }
    public double confidenceLow()           // low  endpoint of 95% confidence interval
    {
        return returnArr[2];
    }
    public double confidenceHigh()          // high endpoint of 95% confidence interval
    {
        return returnArr[3];
    }

    public static void main(String[] args) {
        PercolationStats newStats = new PercolationStats(500, 100);
        System.out.println(newStats.mean());
        System.out.println(newStats.stddev());
        System.out.println(newStats.confidenceLow());
        System.out.println(newStats.confidenceHigh());
    }
}
