package asteroids;

public class Util {
  public static final double EPSILON = 0.0001;

  public static boolean fuzzyEquals(double x, double y) {
    if (Double.isNaN(x) || Double.isNaN(y))
      return false;
    return Math.abs(x - y) <= EPSILON || Double.valueOf(x).equals(Double.valueOf(y));
  }

  /**
   * x < y
   * @param x
   * @param y
   * @return
   */
  public static boolean fuzzyLessThanOrEqualTo(double x, double y) {
    if (fuzzyEquals(x, y)) {
      return true;
    } else {
      return Double.compare(x, y) < 0;
    }
  }
  
  public static double getEpsilon(){
	  return EPSILON;
  }
}