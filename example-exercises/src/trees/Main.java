package trees;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int treeSize = -1;
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter size of tree (>0):");
            treeSize = sc.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Invalid input! Please enter a whole number.");
            System.exit(-1);
        } catch (NoSuchElementException | IllegalStateException e) {
            System.err.println("Error reading input: " + e.getMessage());
            System.exit(-1);
        }

        if (treeSize <= 0) {
            System.err.println("Invalid tree size: " + treeSize);
            System.exit(-1);
        }

        for (int i = 1; i <= treeSize; i++) {
            System.out.println("*".repeat(i));
        }
        System.out.println("*");

        System.out.printf("%n");

        for (int i = 1; i <= treeSize; i++) {
            System.out.println(" ".repeat(treeSize - i) + "*".repeat(i));
        }
        System.out.println(" ".repeat(treeSize - 1) + "*".repeat(1));

        System.out.printf("%n");

        for (int i = 1; i <= treeSize; i++) {
            System.out.println(" ".repeat(treeSize - i) + "*".repeat(i * 2 - 1));
        }
        System.out.println(" ".repeat(treeSize - 1) + "*".repeat(1));
    }
}
