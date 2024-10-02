/*
 * Author: William Horn
 * Written: 9/13/2024
 * 
 * Compilation: javac WilliamHorn_ArmstrongNumber.java
 * Execution: java WilliamHorn_ArmstrongNumber
 * 
 * Purpose:
 * Prompt the user for a 3-digit number, and determine whether or not the number
 * they entered is an armstrong number (the sum of cubed digits)
 * 
 * Example:
 * Input -> 123
 * Output -> 123 IS NOT an armstrong number
 * 
 * Input -> 153
 * Output -> 153 IS an armstrong number
 * 
 * Note: I've been advised to disclose that I have prior programming experience.
 */


import java.util.*;

class WilliamHorn_ArmstrongNumbers {
  // method for computing 3-digit armstrong numbers
  static boolean isArmstrongNumber(int num) {
    // keep track of the sum
    int sum = 0;

    // * compute the sum of cubed digits
    // * in this case, we know the magnitude of the number should be 10^2
    // * isolate the digits and sum their cubes
    for (int i = 0; i < 3; i++) {
      sum += Math.pow(Math.floor(num/Math.pow(10, i))%10, 3);
    }

    // evaluates true when the cubed sum is equal to the initial input
    return sum == num;
  }

  public static void main(String[] args) {
      // ----------------- //
      // ARMSTRONG NUMBERS //
      // ----------------- // 
      
      // Note: I'm implementing the "try-with-resources" clause as an updated replacement of 'input.close()'
      // - instantiate the input object
      // - this should automatically close the input stream after the code block exists.
      try (Scanner input = new Scanner(System.in)) {
        // prompt the user to enter a potential aromstrong number
        System.out.print("Enter an integer to determine if it is an armstrong number: ");
        int armstrongInt = input.nextInt();

        // render the result of the armstrong number determination
        if (isArmstrongNumber(armstrongInt)) {
          System.out.println(armstrongInt + " IS an armstrong number");
        } else {
          System.out.println(armstrongInt + " IS NOT an armstrong number");
        }

      // catch a potential error with creating the Scanner object
      } catch (Exception err) {
        System.out.println("Error reading input: " + err.getMessage());
      }
  }
}

/*
 * Aside:
 * 
 * PLEASE NOTE:
 * 
 * This comment block contains personal notes written by me, Will Horn, for the purpose of
 * referencing at some arbitrary point in the future. All notes are relevant within the
 * context of the assignment. These notes are NOT stolen, copied, or plagarized, nor is any 
 * code or otherwise. 
 * 
 * In general, you can represent the sum of cubed digits for any number n,
 * such that n satisfies:
 * 
 *    n = Σ (k=0; floor(log10(n))) { (floor(|n/10^k|) mod 10)^3 }
 * 
 * This makes sense, as it is close in part with the "sum of digits" problem
 * from the last assignment. The shared engine between the two problems is the 
 * mathematical parsing of digits using the mod function, where:
 * 
 *    mod(a, b) = a - b(floor(|a/b|))
 * 
 * It's interesting to note that the mod function itself is a form of digit parsing,
 * or truncation. So, the general sequence of digits of a number can be expressed as:
 * 
 *    (a_k)(k=floor(log10(n)); 0) = floor(|n/10^k|) mod 10
 * 
 * In this case, the sequence begins for the digits from left to right. 
 * 
 * --------------------------------
 * - Finding Any Armstrong Number -
 * --------------------------------
 * 
 * It may be possible that one could find any armstrong number, given some 
 * starting digit n (where n is the significant digit), by creating a system of equations 
 * to narrow down the possible solutions.
 * 
 * For example, given a 3-digit number n, it can be broken down into:
 * 
 *    n = a*10^2 + b*10^1 + c*10^0
 * 
 * We know that a^3 + b^3 + c^3 = n, so:
 * 
 *    a^3 + b^3 + c^3 = a*10^2 + b*10^1 + c*10^0
 *    => b^3 + c^3 = 100*a - a^3
 *    => b^3 + c^3 = a(100 - a^2)
 *    => c^3 = a(100 - a^2) + b(10 - b^2)
 *    => a(100 - a^2) + (10*b - b^2) + c(1 - c^2) = 0
 * 
 * This may or may not lead to an interesting pattern, although I think one does exist regardless.
 * 
 * Note to self: do more research on this later, and try to prove what the
 * largest armstrong number is for a fixed number of digits. 
 * 
 * -Will
 */
