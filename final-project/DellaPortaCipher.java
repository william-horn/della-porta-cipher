
import java.util.*;

public class DellaPortaCipher {
  public static void println(Object message) {
    System.out.println(message);
  }

  public static void print(Object message) {
    System.out.print(message);
  }

  /*
   * promptMessage(<Scanner> input, <String> message):
   * 
   * A combination of printing and inline message with
   * prompting 
   * 
   * @param <Scanner> input: The scanner object to prompt the user input
   * @param <String> message: The message to display before requesting the input
   */
  public static String promptMessage(Scanner input, String message) {
    print(message);
    return input.nextLine();
  }

  /*
  * error(<String> message):
  *
  * Display a formatted error message to the screen
  *
  * @param <String> message: The error info to display
  * @return void
  */
  public static void error(String message) {
    println("\nError: " + message);
  }

  public static String[][] getKeywordPhrasePairs(String keyword, String phrase) {
    int keywordLength = keyword.length();
    int phraseLength = phrase.length();

    String[][] keywordPhrasePairs = new String[phraseLength][2];

    for (int phraseIndex = 0, keywordCounter = 0; phraseIndex < phraseLength; phraseIndex++) {
      int keywordIndex = keywordCounter % keywordLength;

      String phraseLetter = phrase.substring(phraseIndex, phraseIndex + 1);
      String keywordLetter = keyword.substring(keywordIndex, keywordIndex + 1);

      if (phraseLetter.equals(" "))
        keywordLetter = " ";
      else
        keywordCounter++;

      keywordPhrasePairs[phraseIndex] = new String[] {
        phraseLetter.toLowerCase(),
        keywordLetter.toLowerCase()
      };
    }

    return keywordPhrasePairs;
  }

  public static int charToPortaRowIndex(char character) {
    int bytecode = character;
    int letterIndex = (bytecode - 97);

    return (int) (Math.ceil(letterIndex/2));
  }

  public static char[] getNextPortaRow(int offset) {
    char[] charCols = new char[13];
    int start = (97 + 13);

    for (int i = 0; i < 13; i++) {
      charCols[i] = (char) (start + (i + offset)%13);
    }

    return charCols;
  }
 
  public static void main(String[] args) {
    try (Scanner input = new Scanner(System.in)) {
      String phrase = promptMessage(input, "Enter phrase: ");
      String keyword = promptMessage(input, "Enter keyword: ");

      String[][] keywordPhrasePairs = getKeywordPhrasePairs(keyword, phrase);

      for (int i = 0; i < keywordPhrasePairs.length; i++) {
        String[] row = keywordPhrasePairs[i];
        if (row[1].equals(" ")) continue;

        char phraseLetter = row[0].charAt(0);
        char keywordLetter = row[1].charAt(0);

        int portaRowIndex = charToPortaRowIndex(keywordLetter);
        char[] portaRow = getNextPortaRow(portaRowIndex);

        println(keywordLetter + " | " + portaRowIndex + ": " + portaRow[0] + portaRow[1] + portaRow[2] + portaRow[3]);
      }

      for (String[] row : keywordPhrasePairs) {
        println(row[0] + " | " + row[1]);
      }

    }
  }
}
