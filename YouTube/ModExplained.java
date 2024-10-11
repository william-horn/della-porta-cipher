
public class ModExplained {
  public static double mod(double a, double b) {
    return a - (a*Math.floor(Math.abs(a/b))/Math.abs(a/b));
  }

  public static void main(String[] args) {
    System.out.println(-5.4%2);
    System.out.print(mod(-5.4, 2));
  }
}
