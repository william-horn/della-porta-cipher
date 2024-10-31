/*
 * Author: William J. Horn
 * Written: 10/30/2024
 * 
 * Purpose: Convert between 2s comp in binary to decimal
 * 
 * Input wordSize -> 4
 * Input firstBinaryNumber -> 1001
 * 
 * Output -> 1001 = -7
 * 
 * Input secondBinaryNumber -> 0110
 * 
 * Output -> 0110 = 6
 * Output -> Word size accomodates sum of: -1
 */


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
  public static String promptString(Scanner input, String message) {
    System.out.print(message);
    return input.nextLine();
  }

  /*
   * binaryToDec(<String> binaryString, <int> nonZero):
   * 
   * Convert a binary number to it's decimal equivalent, based on a
   * provided nonZero parameter.
   * 
   * @param <String> binaryString: The binary string 
   * @param <int> nonZero: The number to be counted as the non-zero digit in the binary number
   * @returns <double> decimal: The decimal number converted from binary
   */
  public static double binaryToDec(String binaryString, int nonZero) {
    // the returned decimal value
    double decValue = 0;

    // binary number properties
    int len = binaryString.length();
    int magnitude = len - 1;

    for (int i = 0; i < len; i++) {
      int digit = Character.getNumericValue(binaryString.charAt(i));

      // if the selected digit is the nonZero, then count it's positional value
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

  /*
   * twosCompToDec(<String> binaryValue)
   * 
   * Convert a binary number in 2s comp notation to decimal
   * 
   * @param <String> binaryValue: The 2s comp binary number to convert
   * @returns <double> decimalValue: The converted decimal value from 2s comp
   */
  public static double twosCompToDec(String binaryValue) {
    int sigBit = Character.getNumericValue(binaryValue.charAt(0));

    // if significant bit is 1, return standard conversion. if 0, apply 2s comp logic
    if (sigBit == 0) return binaryToDec(binaryValue, 1); 
    if (sigBit == 1) return -binaryToDec(binaryValue, 0) - 1;
    
    // forcefully return NaN - by this point the entered binaryValue was invaliud
    return Math.sqrt(-1);
  }

  /*
   * sumIsInRange(<double> decimal, <int> wordSize)
   * 
   * Determine whether or not a given converted binary value to decimal
   * falls within the size constraint defined by wordSize.
   * 
   * @param <double> decimal: The converted number
   * @param <int> wordSize: The initial size constraint
   * @returns <boolean> isInRange: Boolean true if it is in range, flase if not
   */
  public static boolean sumIsInRange(double decimal, int wordSize) {
    return decimal >= -Math.pow(2, wordSize - 1)
      && decimal <= Math.pow(2, wordSize - 1) - 1;
  }

  public static void main(String[] args) {
      try (Scanner input = new Scanner(System.in)) {

        // collect word size and first binary number from the user
        int wordSize = Integer.parseInt(promptString(input, "Enter size: "));
        String binaryValue1 = promptString(input, "Enter binary value: ");

        // converted decimal values
        double decValue1 = 0;
        double decValue2 = 0;

        // compare binary number against word size
        if (binaryValue1.length() > wordSize) {
          System.out.println("Binary value cannot exceed size boundary");
          System.exit(0);
        }
        
        // convert the 2s comp binary to decimal
        decValue1 = twosCompToDec(binaryValue1);

        // display first binary number conversion
        System.out.printf("%s = %s\n", binaryValue1, decValue1);

        // collect second binary number
        String binaryValue2 = promptString(input, "Enter second binary value: ");

        // compare second binary number against word size
        if (binaryValue2.length() > wordSize) {
          System.out.println("Binary value cannot exceed size boundary");
          System.exit(0);
        }

        // convert the 2s comp binary to decimal
        decValue2 = twosCompToDec(binaryValue2);

        // display second binary number conversion
        System.out.printf("%s = %s\n", binaryValue2, decValue2);

        // determine if the binary sum accomodates the word size
        double decimalSum = decValue1 + decValue2;
        
        if (!sumIsInRange(decimalSum, wordSize))
          System.out.println("Word size will not accomodate sum");
        else
          System.out.println("Word size accomodates sum of: " + decimalSum);

      } catch (Exception err) {
        err.printStackTrace();
      }
  }
}
