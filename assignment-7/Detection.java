
import java.util.*;

public class Detection {
  /*
   * cardNumberIsValid(<String> cardNumber):
   * 
   * Validate whether the digits in the card number are
   * arithmatically valid
   * 
   * @param: <String>
   */
  public static int computeEventDigits(String cardNumber) {
    int sumEvenDigits = 0;

    // calculate the even digit sum
    for (int i = 2; i < cardNumber.length() + 1; i += 2)
      sumEvenDigits += Character.getNumericValue(cardNumber.charAt(i - 1));

    return sumEvenDigits;
  }

  public static int computeOddDigits(String cardNumber) {
    int sumOddDigits = 0;

    // calculate the odd digit sums
    for (int i = 1; i <= cardNumber.length(); i += 2) {
      int oddDigit = Character.getNumericValue(cardNumber.charAt(i - 1));
      int finalDigit = oddDigit*2;

      // handle subtraction for double-digit numbers
      if (finalDigit >= 10) finalDigit -= 9;

      // add the final digit to the odd sum
      sumOddDigits += finalDigit;
    }

    return sumOddDigits;
  }

  public static boolean cardNumberIsValid(String cardNumber) {
    // keep track of the even/odd digit sums
    int sumOddDigits = computeOddDigits(cardNumber);
    int sumEvenDigits = computeEventDigits(cardNumber);

    return (sumOddDigits + sumEvenDigits) % 10 == 0;
  }

  public static boolean containsAlphabetChar(String cardNumber) {
    boolean containsAlphabet = false;

    for (int i = 0; i < cardNumber.length(); i++) {
      if (Character.isLetter(cardNumber.charAt(i))) {
        containsAlphabet = true;
        break;
      }
    }

    return containsAlphabet;
  }

  public static void main(String[] args) {

    try (Scanner input = new Scanner(System.in)) {
      // String cardNumber = "58667936100244";
      System.out.print("Enter credit card number: ");
      String cardNumber = input.nextLine();

      int cardNumberLength = cardNumber.length();
      
      if (cardNumberLength < 14) {
        System.out.println("Card number length is valid");
      } else {
        System.out.println("Card length is invalid - must be less than 14 digits");
      }

      if (containsAlphabetChar(cardNumber)) {
        System.out.println("Card number is invalid - cannot contain alphabetic characters");
      } else {
        System.out.println("Card number is valid - contains no alhabet characters");
      }

      if (cardNumberIsValid(cardNumber)) {
        System.out.println("Card number is valid!");
      } else {
        System.out.println("Card number is invalid!");
      }
    }
  }
}
