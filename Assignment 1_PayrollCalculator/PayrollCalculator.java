import java.util.Scanner;

public class PayrollCalculator {

    // tax rate of 20% 
    private static final double TAX_RATE = 0.20;

    public static void main(String[] args) {
        // Scanner reads user input from the keyboard
        Scanner scanner = new Scanner(System.in);

        // ---------- 1. Declare variables ----------
        String name;
        double hourlyWage = 0;
        int hoursWorked = 0;
        double grossSalary;
        double tax;
        double netSalary;

        // ---------- 2. Read user input and handle possible user input errors ----------

        // Name: must not be empty
        System.out.print("Enter employee name: ");
        name = scanner.nextLine().trim();
        while (name.isEmpty()) {
            System.out.print("Name cannot be empty. Please enter employee name: ");
            name = scanner.nextLine().trim();
        }

        // Hourly wage: must be a number greater than 0
        while (hourlyWage <= 0) {
            System.out.print("Enter hourly wage: ");
            if (scanner.hasNextDouble()) {
                hourlyWage = scanner.nextDouble();
                if (hourlyWage <= 0) {
                    System.out.println("Hourly wage must be greater than 0. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number (e.g., 25.50).");
                scanner.next(); // discard the invalid input
            }
        }

        // Hours worked: must be a whole number greater than 0
        while (hoursWorked <= 0) {
            System.out.print("Enter hours worked: ");
            if (scanner.hasNextInt()) {
                hoursWorked = scanner.nextInt();
                if (hoursWorked <= 0) {
                    System.out.println("Hours worked must be greater than 0. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a whole number (e.g., 40).");
                scanner.next(); // discard the invalid input
            }
        }

        // ---------- 3. Perform calculations ----------
        grossSalary = hourlyWage * hoursWorked;   // int is widened to double automatically
        tax = grossSalary * TAX_RATE;
        netSalary = grossSalary - tax;

        // ---------- 4. Print the payroll summary in a structured format ----------
        System.out.println();
        System.out.println("Payroll Summary for " + name);
        System.out.println("-----------------------------------");
        System.out.println("Hours Worked: " + hoursWorked);
        System.out.printf("Hourly Wage: $%.2f%n", hourlyWage);
        System.out.printf("Gross Salary: $%.2f%n", grossSalary);
        System.out.printf("Taxes Deducted: $%.2f%n", tax);
        System.out.printf("Net Salary: $%.2f%n", netSalary);

        // Close the scanner to release resources
        scanner.close();
    }
}