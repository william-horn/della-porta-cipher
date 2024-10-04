
import java.util.*;


public class WilliamHorn_LetterGuessingGame {
  public static void main(String[] args) {
    
    // control variable determining the game loop
    boolean gameIsRunning = true;

    // main game loop
    while (gameIsRunning) {
      // computer generated letter
      String randLetter = "" + (char) (65 + (int) (Math.random()*26));

      // start listening to input from the user
      try (Scanner input = new Scanner(System.in)) {
        System.out.println("Guess the random letter.\nYou get 10 guesses: ");

        // collect the user guesses
        for (int i = 0; i < 10; i++) {
          System.out.println("Enter guess: ");
          String userGuess = input.nextLine();

          if (userGuess.equals(randLetter)) {
            System.out.println("The letter " + userGuess + " was correct! You got it in " + i + " guesses.");
            break;
          }
        }

        System.out.println("Too bad. The letter was: " + randLetter);
      }
    }
  }
}
