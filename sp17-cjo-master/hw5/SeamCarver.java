import edu.princeton.cs.algs4.Picture;
/**
 * Created by vigneshvasu on 4/26/17.
 */
import java.awt.Color;
public class SeamCarver {
    private Picture newPic;
    private double[][] energy;
    public SeamCarver(Picture picture) {
        newPic = new Picture(picture);
        energy = new double[width()][height()];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                energy[i][j] = energy(i, j);
            }
        }
    }
    public Picture picture()       {
        Picture x = new Picture(newPic);
        return x;
    }
    private void setNewPic(Picture pic) {
        newPic = new Picture(pic);
    }
    // current picture
    public int width() {
        return newPic.width();
    }                         // width of current picture
    public int height()     {
        return newPic.height();
    }                    // height of current picture
    public  double energy(int x, int y) {
        int xSubOne = x - 1;
        int xPlusOne = x + 1;
        int ySubOne = y - 1;
        int yPlusOne = y + 1;
        if (x == width() - 1) {
            xPlusOne = 0;
        }
        if (x == 0) {
            xSubOne = width() - 1;
        }
        if (y == height() - 1) {
            yPlusOne = 0;
        }
        if (y == 0) {
            ySubOne = height() - 1;
        }
        Color newCol = newPic.get(xSubOne, y);
        double redCol = newCol.getRed();
        double greenCol = newCol.getGreen();
        double blueCol = newCol.getBlue();
        Color secCol = newPic.get(xPlusOne, y);
        double redCola = secCol.getRed();
        double greenCola = secCol.getGreen();
        double blueCola = secCol.getBlue();
        double xAnswer = Math.pow(Math.abs(redCol - redCola), 2)  + Math.pow(Math.abs(blueCol - blueCola), 2)
                + Math.pow(Math.abs(greenCol - greenCola), 2);
        Color newColb = newPic.get(x,ySubOne);
        double redColb = newColb.getRed();
        double greenColb = newColb.getGreen();
        double blueColb = newColb.getBlue();
        Color secColc = newPic.get(x,yPlusOne);
        double redColc = secColc.getRed();
        double greenColc = secColc.getGreen();
        double blueColc= secColc.getBlue();
        double yAnswer = Math.pow(Math.abs(redColb - redColc), 2)  + Math.pow(Math.abs(blueColb - blueColc), 2)
                + Math.pow(Math.abs(greenColb - greenColc), 2);
        return xAnswer + yAnswer;


    }        // energy of pixel at column x and row y
    public   int[] findHorizontalSeam() {
        double[][] newEnergy = new double[height()][width()];
        int x = 0;
        int y = 0;
        for (int i = 0; i < width(); i ++) {
            for (int j = 0; j < height(); j ++) {
                newEnergy[j][i] = energy[x][y];
                if (y == height() - 1) {
                    y = 0;
                }
                else {
                    y = y + 1;
                }
            }
            x = x + 1;
        }

        return findVerticalSeam(newEnergy);

    }


    private int[] findVerticalSeam(double[][] arr) {
        double[][] energyArr = arr;
        double[][] minCost = new double[energyArr.length][energyArr[0].length];
        for (int i = 0; i < energyArr.length; i++) {
            minCost[i][0] = energyArr[i][0];
        }
        for (int k = 1; k < energyArr[0].length; k++) {
            for (int j = 0; j < energyArr.length; j++) {
                if (j == 0) {
                    if (minCost[j][k - 1] > minCost[j + 1][k - 1]) {
                        minCost[j][k] = energyArr[j][k] + minCost[j + 1][k - 1];
                    }
                    else {
                        minCost[j][k] = minCost[j][k - 1] + energyArr[j][k];
                    }
                }
                else if (j == energyArr.length - 1) {
                    if (minCost[j][k - 1] > minCost[j - 1][k - 1]) {
                        minCost[j][k] = energyArr[j][k] + minCost[j - 1][k - 1];
                    }
                    else {
                        minCost[j][k] = minCost[j][k - 1] + energyArr[j][k];
                    }
                }
                else {
                    double x = Double.min(minCost[j - 1][k - 1], minCost[j][k - 1]);
                    x = Double.min(x, minCost[j + 1][k - 1]);
                    minCost[j][k] = energyArr[j][k] + x;
                }
            }
        }
        int[] returnArr = new int[energyArr[0].length];
        double otherVal = Double.MAX_VALUE;
        int first = 0;
        for (int a = 0; a < energyArr.length; a++) {
            if (minCost[a][energyArr[0].length - 1] < otherVal) {
                otherVal = minCost[a][energyArr[0].length - 1];
                first = a;
            }
        }
        returnArr[0] = first;
        for (int b = energyArr[0].length - 1; b > -1; b--) {
            int max = 2;
            int min = -1;
            if (first == 0) {
                min = 0;
            }
            if (first == energyArr.length - 1) {
                max = 1;
            }
            double thisVal = Double.MAX_VALUE;
            int damn = 0;
            for (int r = first + min; r < first + max; r++) {
                if (minCost[r][b] < thisVal) {
                    thisVal = minCost[r][b];

                    returnArr[b] = r;
                    damn = r;
                }
            }
            first = damn;
        }
        return returnArr;
    }

    public   int[] findVerticalSeam() {
        return findVerticalSeam(energy);
    }
    public    void removeHorizontalSeam(int[] seam){
        Picture returnPic = SeamRemover.removeHorizontalSeam(this.picture(), seam);
        setNewPic(returnPic);

    } // remove horizontal seam from picture
    public    void removeVerticalSeam(int[] seam)    {
        setNewPic(SeamRemover.removeVerticalSeam(this.picture(), seam));
    }  // remove vertical seam from picture
}
