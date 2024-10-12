/*
 * Author: William Horn
 * Written: 10/12/2024
 * 
 * Compilation: javac WilliamHorn_Detection.java
 * Execution: java WilliamHorn_Detection
 * 
 * Purpose: Run input validation on a credit card number that the 
 * user inputs
 * 
 * Example:
 * --
 * Input -> 58667936100244
 * 
 * Output ->
 * Card number length is valid
 * Card number is valid - contains no alphabet characters
 * Odd sum = 25
 * Even sum = 35
 * Card number is valid!
 * --
 * 
 * Note: I've been advised to disclose that I have prior programming experience.
 */

import java.util.*;

public class WilliamHorn_Detection {
  /*
   * computeEvenDigits(<String> cardNumber):
   * 
   * Compute the evenly-positioned digits in the cardNumber sum
   * 
   * @param <String> cardNumber: The card number
   * @returns <int> sumOfDigits: The sum of the evenly-positioned digits
   */
  public static int computeEvenDigits(String cardNumber) {
    int sumEvenDigits = 0;

    // calculate the even digit sum
    for (int i = 1; i < cardNumber.length(); i += 2)
      sumEvenDigits += Character.getNumericValue(cardNumber.charAt(i));

    return sumEvenDigits;
  }

  /*
   * computeOddDigits(<String> cardNumber):
   * 
   * Compute the oddly-positioned digits in the cardNumber sum
   * 
   * @param <String> cardNumber: The card number
   * @returns <int> sumOfDigits: The sum of the oddly-positioned digits
   */
  public static int computeOddDigits(String cardNumber) {
    int sumOddDigits = 0;

    // calculate the odd digit sums
    for (int i = 0; i < cardNumber.length(); i += 2) {
      int oddDigit = Character.getNumericValue(cardNumber.charAt(i));
      int finalDigit = oddDigit*2;

      // handle subtraction for double-digit numbers
      if (finalDigit >= 10) finalDigit -= 9;

      // add the final digit to the odd sum
      sumOddDigits += finalDigit;
    }

    return sumOddDigits;
  }

  /*
   * cardNumberIsValid(<String> cardNumber):
   * 
   * Determine whether or not the card number is valid
   * based on the digit-placement sums. Both even and
   * odd sums will be added together, and if their sum
   * is a multiple of 10, then the validation passes.
   * 
   * @param <String> cardNumber: The card number
   * @returns <boolean> isValidCardNumber: The boolean representing the valid
   * card number (true = valid)
   */
  public static boolean cardNumberIsValid(String cardNumber) {
    // keep track of the even/odd digit sums
    int sumOddDigits = computeOddDigits(cardNumber);
    int sumEvenDigits = computeEvenDigits(cardNumber);

    // check for multiple of 10
    return (sumOddDigits + sumEvenDigits) % 10 == 0;
  }

  /*
   * containsAlphabetChar(<String> cardNumber):
   * 
   * Determine whether or not `cardNumber` contains a alphabetical
   * character.
   * 
   * @param <String> cardNumber: The user input of the card number
   * @returns <boolean> containsAlphabetical: Boolean representing whether 
   * or not the string contains an alphabet character (true = yes)
   */
  public static boolean containsAlphabetChar(String cardNumber) {
    for (int i = 0; i < cardNumber.length(); i++)
      if (Character.isLetter(cardNumber.charAt(i))) return true;

    return false;
  }

  /*
   * isValidCardLength(<String> cardNumber):
   * 
   * Checks valid credit card length
   * 
   * @param <String> cardNumber
   * @returns <boolean> isValidLength
   */
  public static boolean isValidCardLength(String cardNumber) {
    return cardNumber.length() <= 14;
  }

  public static void main(String[] args) {

    try (Scanner input = new Scanner(System.in)) {
      // ex: "58667936100244";
      System.out.print("Enter credit card number: ");
      String cardNumber = input.nextLine();
      
      /*
       * INPUT VALIDATION: Length check
       */
      if (isValidCardLength(cardNumber))
        System.out.println("Card number length is valid");
      else
        System.out.println("Card length is invalid - must be less than 14 digits");

      /*
       * INPUT VALIDATION: Contains alphabetical character
       */
      if (containsAlphabetChar(cardNumber))
        System.out.println("Card number is invalid - cannot contain alphabetic characters");
      else
        System.out.println("Card number is valid - contains no alphabet characters");

        
      // display the sums
      System.out.println("Odd sum = " + computeOddDigits(cardNumber));
      System.out.println("Even sum = " + computeEvenDigits(cardNumber));

      /*
       * INPUT VALIDATION: Checks valid card number algorithm
       */
      if (cardNumberIsValid(cardNumber))
        System.out.println("Card number is valid!");
      else
        System.out.println("Card number is invalid!");
    }
  }
}
