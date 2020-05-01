package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.introcs.StdRandom;

import java.util.Arrays;

public class Percolation {
   private int[][] percoList;
   private int size;
   private WeightedQuickUnionUF weightUf;
   private int fillCount;
   public Percolation(int N) {
       size = N;
       percoList = new int[N][N];
       weightUf = new WeightedQuickUnionUF(N*N);
       for (int i = 0; i < N; i++) {
           for (int j = 0; j < N; j++) {
               percoList[i][j] = 1;
           }
       }
   }                // create N-by-N grid, with all sites initially blocked
   public void open(int row, int col) throws IndexOutOfBoundsException {      // open the site (row, col) if it is not open already
       if (row >= size || col >= size || col < 0 || row < 0){
           throw new IndexOutOfBoundsException("Index out of bounds");
       }
       percoList[row][col] = 0;
       if (row == size - 1 && col == size - 1) {
           if (isOpen(row - 1, col)) {
               weightUf.union(convert(row, col, size), convert(row - 1, col, size));
           }
           if (isOpen(row, col - 1)) {
               weightUf.union(convert(row, col, size), convert(row, col - 1, size));
           }
       } else if (row == size - 1 && col == 0) {
           if (isOpen(row - 1, col)) {
               weightUf.union(convert(row, col, size), convert(row - 1, col, size));
           }
           if (isOpen(row, col + 1)) {
               weightUf.union(convert(row, col, size), convert(row, col + 1, size));
           }
       } else if (row == 0 && col == 0) {
           if (isOpen(row + 1, col)) {
               weightUf.union(convert(row, col, size), convert(row + 1, col, size));
           }
           if (isOpen(row, col + 1)) {
               weightUf.union(convert(row, col, size), convert(row , col + 1, size));
           }
       } else if (row == 0 && col == size - 1) {
           if (isOpen(row + 1, col)) {
               weightUf.union(convert(row, col, size), convert(row + 1, col, size));
           }
           if (isOpen(row, col - 1)) {
               weightUf.union(convert(row, col, size), convert(row, col - 1, size));
           }
       } else if (col == 0) {
           if (isOpen(row + 1, col)) {
               weightUf.union(convert(row, col, size), convert(row + 1, col, size));
           }
           if (isOpen(row - 1, col)) {
               weightUf.union(convert(row, col, size), convert(row - 1, col, size));
           }
           if (isOpen(row, col + 1)) {
               weightUf.union(convert(row, col, size), convert(row, col + 1, size));
           }
       } else if (col == size - 1) {
           if (isOpen(row + 1, col)) {
               weightUf.union(convert(row, col, size), convert(row + 1, col, size));
           }
           if (isOpen(row - 1, col)) {
               weightUf.union(convert(row, col, size), convert(row - 1, col, size));
           }
           if (isOpen(row, col - 1)) {
               weightUf.union(convert(row, col, size), convert(row, col - 1, size));
           }
       } else if (row == 0) {
           if (isOpen(row, col + 1)) {
               weightUf.union(convert(row, col, size), convert(row, col + 1, size));
           }
           if (isOpen(row, col - 1)) {
               weightUf.union(convert(row, col, size), convert(row, col - 1, size));
           }
           if (isOpen(row + 1, col)) {
               weightUf.union(convert(row, col, size), convert(row + 1, col, size));
           }
       } else if (row == size - 1) {
           if (isOpen(row, col + 1)) {
               weightUf.union(convert(row, col, size), convert(row, col + 1, size));
           }
           if (isOpen(row, col - 1)) {
               weightUf.union(convert(row, col, size), convert(row, col - 1, size));
           }
           if (isOpen(row - 1, col)) {
               weightUf.union(convert(row, col, size), convert(row - 1, col, size));
           }
       } else {
           if (isOpen(row + 1, col)) {
               weightUf.union(convert(row, col, size), convert(row + 1, col, size));
           }
           if (isOpen(row - 1, col)) {
               weightUf.union(convert(row, col, size), convert(row - 1, col, size));
           }
           if (isOpen(row, col + 1)) {
               weightUf.union(convert(row, col, size), convert(row, col + 1, size));
           }
           if (isOpen(row, col - 1)) {
               weightUf.union(convert(row, col, size), convert(row, col - 1, size));
           }
       }
   }
   public boolean isOpen(int row, int col) { // is the site (row, col) open?
       if (row > size || col > size || col < 0 || row < 0){
           throw new IndexOutOfBoundsException("Index out of bounds");
       }
       return percoList[row][col] == 0;
   }
   public boolean isFull(int row, int col) { // is the site (row, col) full?
       for (int m = 0; m < size; m++) {
           if (weightUf.connected(Percolation.convert(row, col, size), m)) {
               return true;
           }
       }
       return false;
       /*if (fillCount > size) {
           return false;
       }
       if (row == 0) {
           return true;
       }
       int[] neighbors = findAdjNeighbors(row, col);
       if (row == 1) {
           //System.out.println("1 value: (" + neighbors[1] + ", " + neighbors[2] + ")");
       }
       if (neighbors[2] != 0) {
           int[] connectVal = convertBack(neighbors[2], size);
           //System.out.println(row +  ", " + col + " (" +  connectVal[0] + " ," + connectVal[1] + ")");
           fillCount++;
           return isFull(connectVal[0], connectVal[1]);
       } else {
           for (int j = 0; j < 2; j++) {
               if (neighbors[j] != 0) {
                   int[] otherArr = convertBack(neighbors[j], size);
                   //System.out.println(row + ", " + col + " (" + otherArr[0] + " ," + otherArr[1] + ")");
                   fillCount++;
                   return isFull(otherArr[0], otherArr[1]);
               }
           }
       }
       if (neighbors[0] == 0 && neighbors[1] == 0 && neighbors[2] == 0) {
           return false;
       }
       return false;*/
   }

   public int[] findAdjNeighbors(int row, int col) {
       int val = convert(row, col, size);
       int[] neighbors = new int[3];
       for (int k : neighbors) {
           neighbors[k] = 0;
       }
       if (row == size - 1 && col == size - 1) {
           if (weightUf.connected(val, val - 1)) {
               neighbors[0] = val - 1;
           }
           int test = convert(row - 1, col, size);
           if (weightUf.connected(val, test)) {
               neighbors[2] = convert(row - 1, col, size);
           }
           return neighbors;
       } else if (row == size - 1 && col == 0) {
           if (weightUf.connected(val, val + 1)) {
               neighbors[1] = val + 1;
           }
           int test = convert(row - 1, col, size);
           if (weightUf.connected(val, test)) {
               neighbors[2] = convert(row - 1, col, size);
           }
           return neighbors;
       } else {
           if (weightUf.connected(val, val - 1)) {
               neighbors[0] = val - 1;
           }
           if (weightUf.connected(val, val + 1)) {
               neighbors[1] = val + 1;
           }
           int test = convert(row - 1, col, size);
           if (weightUf.connected(val, test)) {
               neighbors[2] = convert(row - 1, col, size);
           }
           return neighbors;
       }
   }
   public int numberOfOpenSites() {           // number of open sites
       int N = size;
       int count = 0;
       for (int i = 0; i < N; i++) {
           for (int j = 0; j < N; j++) {
               if (percoList[i][j] == 0) {
                   count++;
               }
           }
       }
       return count;
   }
   public boolean percolates() {           // does the system percolate?
       for (int m = 0; m < size; m++) {
               if (isFull(size - 1, m)) {
                   return true;
               }
       }
       return false;
   }

   public static void main(String[] args) {   // unit testing (not required)
       Percolation newPerc = new Percolation(5);
       int[] countArray = new int[5*5 - 1];
       for (int i = 0; i < 5 * 5 - 1; i++) {
           int k = newPerc.exclude(countArray);
           countArray[i] = k;
           int[] damnArr = new int[2];
           damnArr = Percolation.convertBack(k, 5);
           newPerc.open(damnArr[0], damnArr[1]);
       }
       System.out.println(newPerc.isFull(4,4));
       System.out.println(newPerc.percolates());
   }

    public int exclude(int[] arr) {
        int k = StdRandom.uniform(5 * 5 - 1);
        for (int l = 0; l < arr.length; l++) {
            if (k == arr[l]) {
                k = StdRandom.uniform(5 * 5 - 1);
                l = 0;
            }
        }
        return k;
    }

   public static int convert(int row, int col, int size) {
       return size * row + col;
   }

   public static int[] convertBack(int val, int size) {
       int[] values = new int[2];
       int col = val % size;
       values[0] = (val - col) / size;
       values[1] = col;
       return values;
   }
}
