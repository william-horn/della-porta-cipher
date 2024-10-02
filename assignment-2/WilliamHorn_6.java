/*
 * Author: William Horn
 * Written: 9/6/2024
 * 
 * Compilation: javac WilliamHorn_6.java
 * Execution: java WilliamHorn_6
 * 
 * Purpose:
 * Given an input for a number between 0 and 1000, compute the sum of the individual
 * digits in that number.
 * 
 * Example:
 * Input -> 123
 * Output -> 6
 * 
 */
import java.util.*;

public class WilliamHorn_6 {
	// returns the sum of digits in a string converted to a number
    static int getSumOfDigits(String str) {
    	// the variable that stores the sum of all digits
        int sum = 0;
        
        /*
         * Iterate as many times as the input string is long, to 
         * extract each character from the input string individually and convert it
         * to an integer -- adding that to the 'sum'
         */
        for (int i = 0; i < str.length(); i++) {
        	// extract the char as 'i' position in the string
            char d = str.charAt(i);
            
            // check if the extracted character is a valid digit, and if it is, add it to 'sum'
            if (Character.isDigit(d) == true) {
            	sum += Character.getNumericValue(d);
            } else {
              
            	// return -1 to indicate that something went wrong
            	return -1;
            }
        }
        
        // return the sum
        return sum;
    }
    
    public static void main(String[] args) {
    	// ------------- //
    	// SUM OF DIGITS //
    	// ------------- //
    	
    	// create the input object
        Scanner input = new Scanner(System.in);
        
        // prompt the user for an integer value between 0 - 1000
        System.out.print("Enter an integer between 0 and 1000: ");
        String numInput = input.nextLine();
        
        // check if the input length is within the range of 0 and 1000
        if (numInput.length() > 3) {
        	System.out.println("Input must be between 0 and 1000");
        	input.close();
        	return;
        }
        
    	// get the sum of digits between 0 - 1000
        int sumOfDigits = getSumOfDigits(numInput);
        
        // input validation clause - if sumOfDigits() returns less than 0, then the input
        // was not a valid integer.
        if (sumOfDigits < 0) {
        	System.out.println("Invalid input: User did not enter a valid integer");
        	input.close();
        	return;
        }
   
        System.out.println("The sum of digits is: " + sumOfDigits);
        
        // close the input stream at the end of the program
        input.close();
    }
    
    /*
     * I think it's worth noting the interesting math behind the digit sum problem. In general,
     * a number A can be truncated at index k by taking the floor of mod(A, B)/C, where:
     * 
     * 		B = 10^(k + 1)
     * 	and 
     * 		C = 10^k
     * 
     * This operation can be summed with k starting at 0, and completing at
     * floor(log10(A)) -- the magnitude of the number (or how many digits in A).
     * 
     * In other words, the digit sum of any number A is:
     * 	
     * 		∑ (floor(log10(A)); k=0) { (mod(A, 10^(k + 1)) - mod(A, 10^k))/10^k }
     * 
     * or simply:
     * 
     * 		∑ (floor(log10(A)); k=0) { floor(mod(A, 10^(k + 1))/10^k) }
     * 
     * In general, an unoptimized implementation of this formula could look like:


	    static double getSumOfDigits(double num) {
	    	int sum = 0;
	    	
	    	for (int i = 0; i <= Math.floor(Math.log10(num)); i++) {
			  sum += Math.floor(num%Math.pow(10, i + 1)/Math.pow(10, i));
			}
	    	
	    	return sum;
	    }
	    
	    -Will
     */

}
