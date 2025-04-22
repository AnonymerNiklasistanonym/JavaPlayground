package org.example;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Cli {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter weekday index to get name: ");
            // Get next int
            int dateIndex = scanner.nextInt();
            System.out.println(switch (dateIndex) {
                case 1 -> "Monday";
                case 2 -> "Tuesday";
                case 3 -> "Wednesday";
                case 4 -> "Thursday";
                case 5 -> "Friday";
                case 6 -> "Saturday";
                case 7 -> "Sunday";
                default -> "Invalid weekday";
            });

            System.out.print("Enter string: ");
            // Get next line
            String input = scanner.next();
            System.out.printf("'%s'%n", input);

            System.out.print("Enter float: ");
            // Get next float
            System.out.println(scanner.nextFloat());

            System.out.print("Enter bool: ");
            // Get next bool
            System.out.println(scanner.nextBoolean());

        } catch (NoSuchElementException | IllegalStateException e) {
            System.err.println("⚠️ Invalid input or scanner error.");
        }
    }
}
