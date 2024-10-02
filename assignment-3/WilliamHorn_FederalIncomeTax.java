/*
 * Author: William Horn
 * Written: 9/14/2024
 * 
 * Compilation: javac WilliamHorn_FederalIncomeTax.java
 * Execution: java WilliamHorn_FederalIncomeTax
 * 
 * Purpose:
 * Let the user input their income, and compute the tax they owe on that
 * income based on the federal tax rate of 2018
 * 
 * Example:
 * Input -> 7250
 * Output -> $725.00
 * 
 * Input -> 89395
 * Output -> $15744.30
 * 
 * Note: I've been advised to disclose that I have prior programming experience.
 */

import java.util.*;

class WilliamHorn_FederalIncomeTax {
  public static void main(String[] args) {
    // ----------------------------- //
    // FEDERAL TAX INCOME CALCULATOR //
    // ----------------------------- //

    // Note: I'm using a try-with-resources clause to auto-close the Scanner input stream
    // when it is no longer being used.
    // - instantiate the input object
    // - this should automatically close the input stream after the code block exists.
    try (Scanner input = new Scanner(System.in)) {
      // prompt the user for their income
      System.out.print("Enter your annual income to compute your tax due: ");
      double userIncome = input.nextDouble();

      // handle edge case for when user income is <= 0
      if (userIncome <= 0) {
        System.out.println("Income is less than or equal to $0 - no applicable tax");
        return;
      }

      // track the amount they owe in taxes
      double taxDue = 0.0;

      // - define the tax rate brackets ranging from <lower a_k, upper a_(k + 1)>
      // - it is important to note that order matters in this array, and bracket boundaries
      // - should be sorted from least to greatest.
      /*
       * the sub-arrays denote a tax bracket in the following ways:
       *    - index[0] = the lower boundary of the tax bracket
       *    - index[1] = the offset tax amount to add for that bracket
       *    - index[2] = the tax rate for that bracket
       * 
       *  note: it may be better to use a Map or dictionary-like object for better data structure
       *  in this case. however, to avoid further complexity, i decided to implement a 2D array.
       */
      double[][] bracketIndex = { 
        { 0, 0, 0.1 },
        { 9525, 952.5, 0.12 }, 
        { 38700, 4452.55, 0.22 }, 
        { 82500, 14089.5, 0.24 },
        { 157500, 32089.5, 0.32 },
        { 200000, 45689.5, 0.35 },
        { 500000, 150689.5, 0.37 }
      };

      // assign bracket index length for edge case checking later on, and for loop bounds
      int bracketIndexBounds = bracketIndex.length;

      // iterate over the tax brackets to identify where the user income falls
      for (int i = 0; i < bracketIndexBounds; i++) {
        // the tax bracket array at index i
        double[] lowerBracket = bracketIndex[i];
        double[] upperBracket;

        // the upper & lower boundaries that define a given tax bracket
        int lowerBoundary = (int) lowerBracket[0];
        int upperBoundary;

        // the user's income relative to the lower boundary (what their income must be greater than)
        double userIncomeRelativeToBoundary = userIncome - lowerBoundary;

        // - special case check if the loop is on the last iteration
        // - if so, then there is no upper boundary that comes after the lower boundary
        if (i == bracketIndexBounds - 1) {
          // - apply the tax rate and break out of the loop ensuring the logic below doesn't run
          System.out.println("Your tax bracket is: $500000+");
          taxDue = lowerBracket[1] + lowerBracket[2]*userIncomeRelativeToBoundary;
          break;

        // - if not, then assign the upper bracket/boundary value
        } else {
          upperBracket = bracketIndex[i + 1];
          upperBoundary = (int) upperBracket[0];
        }

        /*
        * finally, compare the income offset from the lower boundary, with the difference 
        * of the upper and lower boundaries. if the income offset is less than the
        * boundary delta, then the income is within that tax bracket.
        */
        if (
          userIncomeRelativeToBoundary > 0 &&
          userIncomeRelativeToBoundary <= upperBoundary - lowerBoundary
        ) {
          System.out.println("Your tax bracket is: $" + lowerBoundary + " - $" + upperBoundary);
          taxDue = lowerBracket[1] + lowerBracket[2]*userIncomeRelativeToBoundary;
          break;
        }
      }

      // render the tax bracket determination in the console
      System.out.printf("Your tax due is: $%.2f", taxDue);

    // if the Scanner input somehow fails:
    } catch (Exception err) {
      System.out.println("Error reading input: " + err.getMessage());
    }
  }
}
