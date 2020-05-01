
public class Planet{
  public Planet(double xP, double yP, double xV, double yV, double m, String img){
      xxPos = xP;
      yyPos = yP;
      xxVel = xV;
      yyVel = yV;
      mass = m;
      imgFileName = img;
    }
    public Planet(Planet p){
      this.xxPos = p.xxPos;
      this.yyPos = p.yyPos;
      this.xxVel = p.xxVel;
      this.yyVel = p.yyVel;
      this.mass = p.mass;
      this.imgFileName = p.imgFileName;
    }
    public double calcDistance(Planet other){
      double ydist = this.yyPos-other.yyPos;
      double xdist = this.xxPos-other.xxPos;
      double returnval =  Math.pow(xdist*xdist + ydist*ydist, 0.5);
      return returnval;
    }
    public double calcForceExertedBy(Planet other){
      double gravVal = 6.67 * Math.pow(10,-11);
      return (gravVal * this.mass * other.mass) / (calcDistance(other) * calcDistance(other));
    }
    public double calcForceExertedByX(Planet other){
      double calVal = calcForceExertedBy(other);
      return calVal * (other.xxPos - this.xxPos)/ calcDistance(other);
    }
    public double calcForceExertedByY(Planet other){
      double calVal = calcForceExertedBy(other);
      return calVal * (other.yyPos - this.yyPos)/ calcDistance(other);
      }
    public double calcNetForceExertedByX(Planet[] others){
      double total = 0;
      for (Planet i: others){
        if (i == this){
          total = total + 0;
        }
        else{
          total = total + calcForceExertedByX(i);
        }
      }
      return total;
    }
    public double calcNetForceExertedByY(Planet[] others){
      double total = 0;
      for (Planet i: others){
        if (i == this){
          total = total + 0;
        }
        else{
          total = total + calcForceExertedByY(i);
        }
      }
      return total;
    }

    public void update(double time, double xForce, double yForce){
      double accX = xForce/this.mass;
      double accY = yForce/this.mass;
      this.xxVel = this.xxVel + time * accX;
      this.yyVel = this.yyVel + time * accY;
      this.xxPos = this.xxPos + time * xxVel;
      this.yyPos = this.yyPos + time * yyVel;
    }

    public void draw(){
      String newString = "images/" + imgFileName;
      StdDraw.picture(xxPos,yyPos,newString);
    }



    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
}
