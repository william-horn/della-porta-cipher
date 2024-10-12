

/*
 * Input validation checks:
 * > UserId must be greater than or equal to 10 characters
 * 
 * Input validation parsing:
 * > UserId cannot have any alphabet characters
 * 
 * > Must sum all evenly-positioned digits in the UserId together. This
 * sum must be equal to or greater than 20.
 * 
 * > Must sum all oddly-positioned digits in the UserId together. This
 * sum must be equal to or greater than 15.
 */

public class InputValidation {
  public static void main(String[] args) {
    String userId = "3111111276";

    if (userId.length() < 10) {
      System.out.println("UserId is invalid - too short");
    } else {
      System.out.println("UserId length is valid");
    }


    boolean hasAlphabetical = false;

    for (int i = 0; i < userId.length(); i++) {
      char character = userId.charAt(i);

      if (Character.isLetter(character)) {
        hasAlphabetical = true;
        break;
      }
    }

    if (hasAlphabetical) {
      System.out.println("UserId is invalid - cannot contain alphabetical characters");
    } else {
      System.out.println("UserId is valid - does not contain any alphabetical characters");
    }

    // evenly-positioned digits
    int sumOfEvenDigits = 0;

    for (int i = 1; i < userId.length(); i += 2) {
      char character = userId.charAt(i);
      int digit = Character.getNumericValue(character);

      sumOfEvenDigits += digit;
    }


    // oddly-positioned digits
    int sumOfOddDigits = 0;

    for (int i = 0; i < userId.length(); i += 2) {
      char character = userId.charAt(i);
      int digit = Character.getNumericValue(character);

      sumOfOddDigits += digit;
    }

    if (sumOfEvenDigits >= 20) {
      System.out.println("UserId is valid (correct even sum)");
    } else {
      System.out.println("UserId is invalid - even sums are incorrect");
    }

    if (sumOfOddDigits >= 15) {
      System.out.println("UserId is valid (correct odd sum)");
    } else {
      System.out.println("UserId is invalid - odd sums are incorrect");
    }

    System.out.println("Sum of evenly positioned digits: " + sumOfEvenDigits);
    System.out.println("Sum of oddly positioned digits: " + sumOfOddDigits);

  }
}


