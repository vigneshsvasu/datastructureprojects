public class LeapYear {
    public static void main(String[] args) {
        for (String s: args){
          int yval = (Integer) s;
          boolean isLeapYear = ((yval % 4 == 0) && (yval % 100 != 0) || (yval % 400 == 0));

          if (isLeapYear)
          {
              println(yval + " is a leap year.");
          }
          else
              println(yval + " is not a leap year.");
          }
    }
}
