/*
 * Author: William Horn
 * Written: 9/6/2024
 * 
 * Compilation: javac WilliamHorn_17.java
 * Execution: java WilliamHorn_17
 * 
 * Purpose:
 * Compute the wind chill index based on a given temperature and wind speed.
 * 
 * Example:
 * Temperature input -> 40
 * Wind speed input -> 5
 * 
 * Output<Wind Chill Index> -> 36.47240485832117
 * 
 */

import java.util.Scanner;

public class WilliamHorn_17 {
    // returns the wind chill index based on temperature and wind speed
    static double calcWindChillIndex(double temp, double speed) {
    	double speedIndex = Math.pow(speed, 0.16);
    	return 35.74 + 0.6215*temp - 35.75*speedIndex + 0.4275*temp*speedIndex;
    }

	public static void main(String[] args) {
    	// --------------- //
    	// TEMP CONVERSION //
    	// --------------- //
		
    	// create the input object
        Scanner input = new Scanner(System.in);
        
        // prompt user for temperature
        System.out.print("Enter temperature between -58 and 41 degrees: ");
        double temp = input.nextDouble();
        
        // input validation for temperature
        if (temp < -58 || temp > 41) {
        	System.out.println("Invalid temperature: Must be between -58 and 41 degrees");
        	input.close();
        	return;
        }
        
        // prompt user for wind speed
        System.out.print("Enter wind speed >= 2 miles per hour: ");
        double speed = input.nextDouble();
        
        // input validation for wind speed
        if (speed < 2) {
        	System.out.println("Invalid wind speed: Must be greater than or equal to 2 degrees");
        	input.close();
        	return;
        }
        
        // compute wind chill index & output
        double windChillIndex = calcWindChillIndex(temp, speed);
        System.out.println("Wind chill index: " + windChillIndex);

        // close the input stream at the end of the program
        input.close();
	}

}
