public class NBody {
  public static double readRadius(String fileName){
    In in = new In(fileName);
    int valA = in.readInt();
    double radius = in.readDouble();
    return radius;
  }
  public static Planet[] readPlanets(String fileName){

    In in = new In(fileName);
    int numVal = in.readInt();
    Planet[] newArr = new Planet[numVal];
    in.readDouble();
    int g = 0;
    while (g < numVal){
      int k = 0;
      double[] otherArr = new double[5];
      while (k<5){
        otherArr[k] = in.readDouble();
        k++;
      }
      String newStr = in.readString();
      newArr[g] =  new Planet(otherArr[0], otherArr[1], otherArr[2], otherArr[3], otherArr[4], newStr);
      g++;


    }
    return newArr;
  }
  public static void main(String[] args) {
    if (args.length == 0){
      System.out.println("Please enter arguments.");
      System.exit(0);
    }
    double T = Double.parseDouble(args[0]);
    double dt = Double.parseDouble(args[1]);
    String fileName = args[2];
    Planet[] realArray;
    realArray = readPlanets(fileName);
    double radiusVal = readRadius(fileName);
    StdDraw.setScale(-radiusVal, radiusVal);
    StdDraw.clear();
    StdDraw.picture(0,0,"images/starfield.jpg");
    for(Planet x: realArray){
      x.draw();
    }
    for (double i = 0; i < T; i = i + dt){
      Double[] xForces = new Double[realArray.length];
      Double[] yForces = new Double[realArray.length];
      int x = 0;
      for (Planet h: realArray){
        Double xVal = h.calcNetForceExertedByX(realArray);
        Double yVal = h.calcNetForceExertedByY(realArray);
        xForces[x] = xVal;
        yForces[x] = yVal;
        x++;
      }
      int r = 0;
      for(Planet g: realArray){
        g.update(dt, xForces[r], yForces[r]);
        r++;
      }
      StdDraw.picture(0,0,"images/starfield.jpg");
      for(Planet a: realArray){
        a.draw();
      }
    }
    StdDraw.show(10);
    StdOut.printf("%d\n", realArray.length);
    StdOut.printf("%.2e\n", radiusVal);
    for (int k = 0; k < realArray.length; k++) {
    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
      realArray[k].xxPos, realArray[k].yyPos, realArray[k].xxVel, realArray[k].yyVel, realArray[k].mass, realArray[k].imgFileName);
    }

  }
}
