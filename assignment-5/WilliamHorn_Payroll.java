/*
 * Author: William Horn
 * Written: 9/28/2024
 * 
 * Compilation: javac WilliamHorn_Payroll.java
 * Execution: java WilliamHorn_Payroll
 * 
 * Purpose: Compute paycheck for an employee based on tax withholding
 * 
 * Note: I've been advised to disclose that I have prior programming experience.
 */

import java.util.*;

class WilliamHorn_Payroll {
  public static void main(String[] args) {
    // --------------------------- //
    // EMPLOYEE PAYROLL CALCULATOR //
    // --------------------------- //

    // instantiate input stream
    try (Scanner input = new Scanner(System.in)) {

      // prompt the user for employee data
      System.out.print("Enter employee's name: ");
      String employeeName = input.nextLine();

      System.out.print("Enter number of hours worked in a week: ");
      double hoursWorked = input.nextDouble();

      System.out.print("Enter hourly pay rate: ");
      double hourlyPay = input.nextDouble();

      System.out.print("Enter federal tax withholding rate: ");
      double fedTaxRate = input.nextDouble();

      System.out.print("Enter state tax withholding rate: ");
      double stateTaxRate = input.nextDouble();

      System.out.println("------------------------------------------------");

      // compute grossPay and deductions
      double grossPay = hourlyPay*hoursWorked;
      double fedDeduction = grossPay*fedTaxRate;
      double stateDeduction = grossPay*stateTaxRate;

      // display payroll data to the user
      System.out.println("Employee Name: " + employeeName);
      System.out.println("Hours Worked: " + hoursWorked);
      System.out.println("Pay Rate: " + hourlyPay);
      System.out.println("Gross Pay: " + grossPay);
      System.out.println("Deductions: ");
      System.out.printf("\tFederal Withholding (%.1f%%): $%.2f\n", fedTaxRate*100, fedDeduction);
      System.out.printf("\tState Withholding (%.1f%%): $%.2f\n", stateTaxRate*100, stateDeduction);
      System.out.printf("\tTotal Deduction: $%.2f\n", stateDeduction + fedDeduction);
      System.out.printf("Net Pay: $%.2f\n", (grossPay - stateDeduction - fedDeduction));

    } catch (Exception err) {
      System.out.println("Error opening input stream: " + err.getMessage());
    }
  }
}


