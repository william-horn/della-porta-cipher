import classes.*;

class First {
  public static String identity = "Original";

  public static void printIdentity() {
    System.out.println("Identity: " + identity);
  }
}

public class ClassTest extends First {
  public static String identity = "Overloaded";

  public static void printIdentity() {
    System.out.println("New Identity: " + identity);
  }

  public static void main(String[] args) {
      Print print = new Print();
      printIdentity();
  }
}
