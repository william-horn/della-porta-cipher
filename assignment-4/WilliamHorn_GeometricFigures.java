/*
 * Author: William Horn
 * Written: 9/20/2024
 * 
 * Compilation: javac WilliamHorn_GeometricFigures.java
 * Execution: java WilliamHorn_GeometricFigures
 * 
 * Purpose:
 * Display a menu of geometric shapes to compute the area of based on
 * the dimensions the user inputs
 * 
 * Example:
 * Menu item input -> 2
 * Output -> You chose Cylinder
 * Radius input -> 2
 * Height input -> 4
 * Output -> Volume of the Cylinder: 78.956...
 * 
 * Note: I've been advised to disclose that I have prior programming experience.
 */

import java.util.Scanner;

class WilliamHorn_GeometricFigures {
  final static double PI = Math.PI;

  public static void main(String[] args) {
      try (Scanner input = new Scanner(System.in)) {

        // prompt the user for a geometric shape to compute the volume of
        System.out.println("Select a geometric figure to compute the volume of: ");
        System.out.println("\t1) Sphere");
        System.out.println("\t2) Cylinder");
        System.out.println("\t3) Cone");
        System.out.print("\nSelect your choice (1-3): ");
        int shapeChoice = input.nextInt();

        // switch cases for different menu items
        // !note: converted to rule switch (not in submitted assignment)
        switch (shapeChoice) {
          // Shape choice: Sphere
          case 1 -> {
            System.out.println("\nYou choose: Sphere");
            System.out.print("Enter the value of the radius: ");
            double radius = input.nextDouble();

            double volume = (4*PI*Math.pow(radius, 3))/3;

            System.out.println("Volume of the Sphere: " + volume);
          }

          // Shape choice: Cylinder
          case 2 -> {
            System.out.println("\nYou choose: Cylinder");
            System.out.print("Enter the value of the radius: ");
            double radius = input.nextDouble();

            System.out.print("Enter the value of the height: ");
            double height = input.nextDouble();

            double volume = Math.pow(PI, 2)*radius*height;

            System.out.println("Volume of the Cylinder: " + volume);
            break;
          }

          // Shape choice: Cone
          case 3 -> {
            System.out.println("\nYou choose: Cone");
            System.out.print("Enter the value of the radius: ");
            double radius = input.nextDouble();

            System.out.print("Enter the value of the height: ");
            double height = input.nextDouble();

            double volume = (Math.pow(radius, 2)*PI*height)/3;

            System.out.println("Volume of the Cone: " + volume);
            break;
          }

          // Case for invalid menu item
          default -> {
            System.out.println("Your entry of: " + shapeChoice + " is not a valid menu item.");
          }
        }

      // Catch if error opening input stream
      } catch(Exception err) {
        System.out.println("Error opening input stream: " + err.getMessage());
      }
  }
}
