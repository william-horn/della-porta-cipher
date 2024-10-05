
import java.util.*;

public class WilliamHorn_LetterGuessingGame {
  public static void main(String[] args) {

    // create the input object
    try (Scanner input = new Scanner(System.in)) {

      // main game loop
      while (true) {
        // computer generated letter
        String randLetter = "" + (char) (65 + (int) (Math.random()*26));

        // start listening to input from the user
        System.out.println("----------------------------------------------");
        System.out.println("Guess the random letter.\nYou get 10 guesses: ");
        System.out.println("----------------------------------------------");

        // determines whether or not the user guessed the correct letter
        boolean userGuessedCorrectly = false;

        // prompt the user for 10 guesses
        for (int i = 0; i < 10; i++) {
          // collect the guess from the user
          System.out.print("Enter guess: ");
          String userGuess = input.nextLine();

          // handle the user guessing the correct letter:
          if (userGuess.equalsIgnoreCase(randLetter)) {
            System.out.println("\nThe letter " + randLetter + " was correct! You got it in " + i + " guesses.\n");
            userGuessedCorrectly = true;
            break;
          }
        }

        // handle the user guessing the right or wrong answer
        if (!userGuessedCorrectly) {
          System.out.println("Too bad. The letter was: " + randLetter);
        }

        // prompt the user for playing again
        System.out.println("Would you like to play again (y = yes, n = no): ");

        // determine whether or not to start the game back over
        if (input.nextLine().equals("n")) {
          System.out.println("Thanks for playing!");
          break;
        }
      }
    }
  }
}
