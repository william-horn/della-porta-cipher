/*
 * Will Horn
 * 11/9/2024
 */

import java.util.*;

public class DellaPortaCipherNoComments {
  final public static int PORTA_MATRIX_SIZE = 13;

  public static void println(Object message) {
    System.out.println(message);
  }

  public static void print(Object message) {
    System.out.print(message);
  }

  public static String promptMessage(Scanner input, String message) {
    print(message);
    return input.nextLine();
  }

  public static void error(String message) {
    println("\nError: " + message);
  }

  public static char[][] getKeywordPhrasePairs(String phrase, String keyword) 
  {
    // convert keyword and phrase to lowercase to make bytecode-checking easier
    keyword = keyword.toLowerCase();
    phrase = phrase.toLowerCase();

    int keywordLength = keyword.length();
    int phraseLength = phrase.length();

    // The 2D array that contains the phrase letter/keyword letter pairs.
    char[][] keywordPhrasePairs = new char[phraseLength][2];

    for (int phraseIndex = 0, keywordCounter = 0; phraseIndex < phraseLength; phraseIndex++) 
    {
      // Reset the keyword index after the keyword length has been exhausted.
      int keywordIndex = keywordCounter % keywordLength;

      // Grab the next phrase letter & keyword letter
      char phraseLetter = phrase.charAt(phraseIndex);
      char keywordLetter = keyword.charAt(keywordIndex);

      if (!Character.isLetter(phraseLetter))
        keywordLetter = phraseLetter;
      else
        keywordCounter++;

      keywordPhrasePairs[phraseIndex] = new char[] 
      {
        phraseLetter,
        keywordLetter
      };
    }

    return keywordPhrasePairs;
  }

  public static int getPortaRowIndexOf(char character) 
  {
    int bytecode = character;
    int letterIndex = (bytecode - 97);

    return letterIndex/2;
  }

  public static char getPortaCompliment(char phraseLetter, char keywordLetter) 
  {
    // if the keyword is non-alphabetic, then just return the character
    if (!Character.isLetter(keywordLetter)) return keywordLetter;

    // get the row index of the keyword letter
    int portaRowIndex = getPortaRowIndexOf(keywordLetter);

    char encryptedLetter;

    if (phraseLetter < 'n') {
      encryptedLetter = (char) (('a' + PORTA_MATRIX_SIZE) + ((phraseLetter - 'a') + portaRowIndex)%PORTA_MATRIX_SIZE);
    } else {
      encryptedLetter = (char) ('a' + (PORTA_MATRIX_SIZE - (('z' - phraseLetter) + portaRowIndex)%PORTA_MATRIX_SIZE) - 1);
    }

    return encryptedLetter;
  }

  public static String convertPortaCipher(String phrase, String keyword) 
  {
    // create the keyword/phrase letter pairs
    char[][] keywordPhrasePairs = getKeywordPhrasePairs(phrase, keyword);

    // the final encrypted/decrypted result
    String text = "";

    for (char[] row : keywordPhrasePairs) 
    {
      // extract the mapped phrase and keyword letters from each row
      char phraseLetter = row[0];
      char keywordLetter = row[1];

      // get the compliment of a given phrase character based on it's corresponding keyword character
      char encryptedLetter = getPortaCompliment(phraseLetter, keywordLetter);
      text += encryptedLetter;
    }

    return text;
  }

  public static boolean containsExclusivelyLetters(String text) 
  {
    // if the string is blank, invalidate the string
    if (text.trim().equals("")) return false;

    // if the string contains non-alphabet characters, invalidate the string
    for (int i = 0; i < text.length(); i++) 
      if (!Character.isLetter(text.charAt(i))) return false;

    // if all else, validate the string
    return true;
  }
 
  public static void main(String[] args) 
  {
    try (Scanner input = new Scanner(System.in)) 
    {
      // user input parameters
      println("\n------- DELLA PORTA CIPHER ---------------------------\n");
      String phrase = promptMessage(input, "Enter phrase: ");
      String keyword = promptMessage(input, "Enter keyword: ");
      println("\n------------------------------------------------------\n");

      // input validation
      if (!containsExclusivelyLetters(keyword)) {
        error("Keyword must be a single word containing only alphabet characters");
        System.exit(0);
      }

      println("Result: " + convertPortaCipher(phrase, keyword) + "\n");
    }
  }
}
