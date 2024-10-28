

import java.util.*;

public class WilliamHorn_BinaryValues {
  /*
   * promptMessage(<Scanner> input, <String> message):
   * 
   * A combination of printing and inline message with
   * prompting 
   * 
   * @param <Scanner> input: The scanner object to prompt the user input
   * @param <String> message: The message to display before requesting the input
   */
  public static int promptInt(Scanner input, String message) {
    System.out.print(message);
    return input.nextInt();
  }

  public static String promptString(Scanner input, String message) {
    System.out.print(message);
    return input.nextLine();
  }

  public static double binaryToDec(String binaryString, int nonZero) {
    double decValue = 0;

    int len = binaryString.length();
    int magnitude = len - 1;

    for (int i = 0; i < len; i++) {
      int digit = Character.getNumericValue(binaryString.charAt(i));

      if (digit == nonZero) {
        decValue += Math.pow(2, magnitude - i);

      /*
       * `1 - nonZero` will be the binary inverse of 0 or 1, in other words:
       * f(0) -> 1
       * f(1) -> 0
       */
      } else if (digit != (1 - nonZero)) {
        System.out.println("Number enetered must be a valid binary number");
        System.exit(0);
      }
    }

    return decValue;
  }

  public static double twosCompToDec(String binaryValue) {
    int sigBit = Character.getNumericValue(binaryValue.charAt(0));

    switch (sigBit) {
      case 0 -> { return binaryToDec(binaryValue, 1); }
      case 1 -> { return -binaryToDec(binaryValue, 0); }
      default -> { System.out.println("Invalid binary string"); }
    }

    return -909;
  }

  public static void main(String[] args) {
      try (Scanner input = new Scanner(System.in)) {
        int binaryDigits = promptInt(input, "Enter size: ");
        input.nextLine();
        String binaryValue = promptString(input, "Enter binary value: ");

        if (binaryValue.length() > binaryDigits) {
          System.out.println("Binary value cannot exceed size boundary");
          System.exit(0);
        }

        System.out.println(twosCompToDec(binaryValue));

      } catch (Exception err) {
        err.printStackTrace();
      }
  }
}
