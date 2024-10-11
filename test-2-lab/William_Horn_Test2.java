/*
 * Author: William Horn
 * Written: 10/11/2024
 * 
 * Compilation: javac William_Horn_Test2.java
 * Execution: java William_Horn_Test2
 * 
 * Purpose:
 * Simulate dice rolls between 2 die until the simulation results
 * in snake-eyes. Display number of simulations and dice roll results.
 * 
 * Example:
 * 
 * Output -> 
 * Die 1  Die 2
 * 2      5
 * 1      3
 * 4      2
 * 1      1
 * Number of simulations: 4
 */

public class William_Horn_Test2 {
  /*
   * getDiceRoll(<int> sides):
   * 
   * Return a random number from 1 to `sides`
   * 
   * @param <int> sides: The upper-boundary for the RNG
   * @returns: <int> roll: The generated dice roll
   */
  public static int getDiceRoll(int sides) {
    return (int) (1 + Math.random()*sides);
  }

  public static void main(String[] args) {
    // the two dice states
    int dieOne = 0;
    int dieTwo = 0;

    // track total # of rolls
    int numRolls = 0;

    // display dice roll table
    System.out.println("Roll\tDie 1\tDie 2");

    /*
     * DICE ROLL LOOP
     * 
     * Since the only equation for checking a snake-eyes roll is: dieOne + dieTwo = 2,
     * we can simply add the two rolls together and check if their sum is 2 for the
     * loop condition. While the sum is not 2, then keep rolling.
     */
    while (dieOne + dieTwo != 2) {
      // generate the dice rolls
      dieOne = getDiceRoll(6);
      dieTwo = getDiceRoll(6);

      // increment the roll counter
      numRolls++;

      // display the dice rolls
      System.out.printf("%d\t%d\t%d\n", numRolls, dieOne, dieTwo);
    }

    // display the total dice roll simulations
    System.out.println("Number of rolls before snake-eyes: " + numRolls);
  }
}
