/*
 * Author: William J. Horn
 * Written: 10/18/2024
 * 
 * Purpose: Find largest number in a set and
 * output it's value and quantity of occurences
 * 
 * Input -> "1 2 3 4 7 7 5 7"
 * 
 * Output -> "Largest number is 7 and it appears 3 times"
 */

import java.util.*;

public class WilliamHorn_Exercise_5_41 {
  public static void main(String[] args) {
      try (Scanner input = new Scanner(System.in)) 
      {
        // prompt user for number list input
        // ex: 1 2 3 4
        String numberInput = input.nextLine();
        String[] numberList = numberInput.split(" ");

        // keep track of largest and quantity of largest
        int largestNumber = 0;
        int quantOfLargestNumber = 0;

        // iterate over numberList, all the individual numbers in the list
        for (String numberString : numberList) {
          int number = Integer.parseInt(numberString);

          // if current number is greater than largest, update largest
          // and reset 'quantOfLargest' to 1
          if (number > largestNumber) {
            largestNumber = number;
            quantOfLargestNumber = 1;

          // if current number is equal to largest, increment quantity
          } else if (number == largestNumber) {
            quantOfLargestNumber++;
          }
        }

        System.out.printf(
          "Largest number is %d and it appears %d times", 
          largestNumber,
          quantOfLargestNumber
        );

      } catch(Exception err)
      {
        System.out.println("Error: " + err.getMessage());
      }
  }
}
