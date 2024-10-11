/*
 * Author: William J. Horn
 * Written: 10/5/2024
 * 
 * Purpose: Play rock-paper-scissors with a computer.
 * 
 * IMPORTANT:
 * I have prior programming experience. This was an interesting assignment to me, so I
 * apologize if this document is unnecessarily extensive.
 * 
 * I have also written up a similar coding concept to this before, in regards to
 * a Custom Event module I made in Javascript which you can find here: 
 * https://github.com/william-horn/my-web-projects/blob/develop/tools/api/general/js/pseudo-events-2.1.0.js
 * 
 * I also made a YouTube video explaining my concept here: https://youtu.be/0J61XpT3dis
 */
import java.util.*;

public class WilliamHorn_RockPaperScissorsGame {
  /*
   * GAME STATES:
   * 
   * Instead of comparing all combinations of game moves (rock, paper, scissors) manually,
   * it is possible to consider each move as a variable in a system of equations, where:
   * 
   *  R + P = x
   *  P + S = y
   *  R + S = z
   * 
   * The values `x`, `y`, and `z` are arbitrary. However, in this case it would be useful to
   * assign them a value that indicates the dominant game move. This way, we can sum together
   * two game moves and the result will be the winning move.
   * 
   * ex: R + P = P, since Paper beats Rock.
   * 
   * Programmatically, it would make sense to let the result of these equations be equal to 
   * the winning move in it's bytecode form. This way, the results will be numeric values
   * that we can use to solve the system for R, P, and S. Any combination of summing these
   * game moves together thereon, will result in the winning move in bytecode form which
   * we can convert back into a character. So,
   * 
   * R + P = 112 (112 = 'p')
   * P + S = 115 (115 = 's')
   * R + S = 114 (114 = 'r')
   * 
   * Solving the system we get:
   * 
   * R = 55.5
   * P = 56.5
   * S = 58.5
   */
  public static final String[][] GAME_STATES = {
    /*
     * 2D array where:
     * COL 1 = the game move
     * COL 2 = the name of the move in verbose form
     * COL 3 = the numeric value of the game move in the system of equations (a partial bytecode, if you will)
     */
    {"r", "rock", "55.5"},
    {"p", "paper", "56.5"},
    {"s", "scissors", "58.5"}
  };

  /*
   * .getGameState(<String> move): 
   * 
   * Find a game state (array of game move data) that matches the game move passed
   * as the argument
   * 
   * @param <String>move: the single-character string representing which move
   * someone made.
   * 
   * @returns <String[]>gameState: an array of data containing a game state (or game move)
   */
  public static String[] getGameState(String move) {
    for (String[] state : GAME_STATES) {
      if (state[0].equals(move)) return state;
    }

    return null;
  }

  /*
   * VISUAL DISPLAY METHODS:
   * 
   * Each of the methods below are exclusively responsible for displaying
   * text or prompt text to the terminal. They do nothing else. I created 
   * these methods to lessen the clutter in the main logic of the program.
   */

  // display the 'select your move' prompt text
  public static void display__selectMove() {
    System.out.println("------------------------------");
    System.out.print("Select your move to play: ");
  }

  // display the 'valid moves menu' prompt text
  public static void display__moveMenu() {
    System.out.println("Your moves:\n------------------------------");
    System.out.println("Enter \"R\" for Rock\nEnter \"P\" for Paper\nEnter \"S\" for Scissors");
  }

  // display the 'invalid move' prompt text
  public static void display__invalidMove(String move) {
    System.out.print("The move \"" + move + "\" is invalid. Please enter a valid move (R, P, or S): ");
  }

  // display the 'game tied' prompt text
  public static void display__gameTied(String move) {
    System.out.println("------------------------------");
    System.out.print("It's a tie! You both chose " + move + ", go again: ");
  }

  // display the 'chosen game moves' text
  public static void display__chosenRolls(String user, String computer) {
    System.out.println("\n\t> YOU chose: " + user);
    System.out.println("\t> COMPUTER chose: " + computer + "\n");
  }

  // display end-of-game stats between user and computer
  public static void display__endOfGameStats(
    int totalGames,
    int userWins,
    int computerWins,
    int totalTies,
    int mostConsecutiveTies
  ) {
    // losses
    int userLosses = totalGames - userWins;
    int computerLosses = totalGames - computerWins;

    // win rate conversion
    double userWinRate = (userWins/(double) totalGames)*100;
    double computerWinRate = (computerWins/(double) totalGames)*100;

    System.out.println("\n----- END OF GAME STATS ----------------------------------------\n");
    System.out.println("Total Games Played:\t" + totalGames);
    System.out.println("Total Ties:\t\t" + totalTies);
    System.out.println("Highest Tie Streak:\t" + mostConsecutiveTies);
    System.out.println("");

    System.out.println("Your Wins:\t\t" + userWins);
    System.out.println("Your Losses:\t\t" + userLosses);
    System.out.printf("Your Win Rate:\t\t%.1f%%\n", userWinRate);
    System.out.println("");

    System.out.println("Computer Wins:\t\t" + computerWins);
    System.out.println("Computer Losses:\t" + computerLosses);
    System.out.printf("Computer Win Rate:\t%.1f%%\n", computerWinRate);
    System.out.println("\n----------------------------------------------------------------");
  }

  /*
   * VISUAL PROMPT DISPLAY METHODS:
   * 
   * All of the methods below will prompt the user for some input.
   */

  // prompt the user to play the game again
  public static boolean prompt__playAgain(Scanner input) {
    System.out.println("------------------------------");
    System.out.print("Would you like to play again? (y/n): ");
    String answer = input.nextLine();

    System.out.println("");
    return answer.equals("y");
  }

  /*
   * PROGRAM LOGIC BEGINS:
   * 
   * All program logic happens here.
   */
  public static void main(String[] args) {

    // running game stats
    int totalGames = 0;
    int userWins = 0;
    int computerWins = 0;
    int totalTies = 0;
    int mostConsecutiveTies = 0;

    // open the input stream
    try (Scanner input = new Scanner(System.in)) {

      /*
       * MAIN GAME LOOP:
       * 
       * This loop controls the entire game cycle. There may be other loops
       * nested within this one to control re-prompting the user, but this loop
       * will restart everything.
       */
      while (true) {
        // text prompt for game instructions
        display__moveMenu();
        display__selectMove();

        // collect user roll, and generate computer roll (convert all to lower-case)
        String[] computerRoll = null; // determine after tie-checking validation
        String[] userRoll = null; // determine after input validation

        // keep track of consecutive ties
        int consecutiveTies = 0;

        /*
         * GAME-ROLL LOOP:
         * 
         * This loop is responsible for handling the determination of `userRoll` and `computerRoll` 
         * variables. It will only initialize both of these variables when the following is true:
         * 
         *  1) User input is a valid move
         *  2) User roll and computer roll are not equal
         * 
         * Until these conditions are met, the game roll loop will continue re-prompting the user
         * for a new move.
         */
        while (userRoll == null || computerRoll == null) {
          // collect user input
          String userEntry = input.nextLine().toLowerCase();
          userRoll = getGameState(userEntry);

          /*
           * INPUT VALIDATION:
           * 
           * We can check for input validation by determining whether or not the user input
           * is found inside the 2D game state array. If it is not found, it returns null, in 
           * which case we can skip back to the start of the game roll loop.
           */
          if (userRoll == null) {
            display__invalidMove(userEntry);
            continue;
          }

          // generate the computer roll (`computerRollIndex` just to avoid in-line ugliness)
          int computerRollIndex = (int) (Math.random()*GAME_STATES.length);
          computerRoll = GAME_STATES[computerRollIndex];

          /*
           * TIE-CHECKING:
           * 
           * Compare the validated user roll with the computer roll. If the rolls are equal, then 
           * the game is tied, and both the user and computer should re-roll. 
           */
          if (computerRoll[0].equals(userRoll[0])) {
            totalTies++;
            consecutiveTies++;
            display__gameTied(computerRoll[1]);

            // reset both rolls so the game roll loop continues
            computerRoll = null;
            userRoll = null;
          }
        }

        // increment total games 
        totalGames++;

        // update most consecutive ties if the record was beat
        if (consecutiveTies > mostConsecutiveTies)
          mostConsecutiveTies = consecutiveTies;

        // display what both user and computer have rolled
        display__chosenRolls(
          userRoll[1], 
          computerRoll[1]
        );
        
        /*
         * GAME RESULT DETERMINATION:
         * 
         * As stated in the explanation above `GAME_STATES`, we can parse out the
         * values associated with the variables in the system and add them together.
         * Their result will be the winning result out of the user and computer. 
         */
        char gameResult = (char) (
          Double.parseDouble(userRoll[2]) + Double.parseDouble(computerRoll[2])
        );

        // if the winning result (userRoll[0].charAt(0)) is equal to the gameResult, then
        // the user won.
        if (userRoll[0].charAt(0) == gameResult) {
          userWins++;
          System.out.println("YOU are the winner!");

        // otherwise, the computer won.
        } else {
          computerWins++;
          System.out.println("COMPUTER is the winner! Better luck next time!");
        }

        // break out of main loop cycle if user chooses not to play again
        if (!prompt__playAgain(input)) break;
      }

      // display end of game running-stats
      display__endOfGameStats(
        totalGames, 
        userWins, 
        computerWins, 
        totalTies,
        mostConsecutiveTies
      );

      // end of game message
      System.out.println("\nThanks for playing!");

    // handle scanner error
    } catch(Exception err) {
      System.out.println("Error with scanner input: " + err.getMessage());
    }
  }
}
