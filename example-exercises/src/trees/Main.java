package trees;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        if (treeSize < 3) {
            System.err.println("Tree size must be >= 3 for the next tree!");
            System.exit(-1);
        }
        System.out.println("""
                            \\ /
                          -->*<--
                            /_\\
                           /_\\_\\
                          /_/_/_\\
                          /_\\_\\_\\
                         /_/_/_/_\\
                         /_\\_\\_\\_\\
                        /_/_/_/_/_\\
                        /_\\_\\_\\_\\_\\
                       /_/_/_/_/_/_\\
                       /_\\_\\_\\_\\_\\_\\
                      /_/_/_/_/_/_/_\\
                           [___]
                """);

        final int debugLength = 16;
        System.out.printf("%s\\ /%n", " ".repeat(debugLength + treeSize - 1));
        System.out.printf("%s-->*<--%n", " ".repeat(debugLength + treeSize - 3));
        for (int row = 0, underscores = 1; row < treeSize; row++, underscores++) {
            String[] leafs = new String[underscores];
            Arrays.fill(leafs, "_");
            System.out.printf("[row=%3d,_=%3d] %s/%s\\%n", row, underscores, " ".repeat(treeSize - underscores), String.join(row % 2 == 0 ? "\\" : "/", leafs));
            if (row > 0 && row % 2 == 0) {
                underscores -= 1;
            }
        }
        System.out.printf("%s[___]%n", " ".repeat(debugLength + treeSize - 2));

        // Stream version
        final int fixedTreeSize = treeSize / 2 + treeSize % 2 + 1;
        System.out.printf("%s\\ /%n", " ".repeat(fixedTreeSize - 1));
        System.out.printf("%s-->*<--%n", " ".repeat(fixedTreeSize - 3));
        for (int row = 0, underscores = 1; row < treeSize; row++, underscores++) {
            System.out.printf("%s/%s\\%n",
                    " ".repeat(fixedTreeSize - underscores),
                    Stream.generate(() -> "_")
                            .limit(underscores)
                            .collect(Collectors.joining(row % 2 == 0 ? "\\" : "/"))
            );
            if (row > 0 && row % 2 == 0) {
                underscores -= 1;
            }
        }
        System.out.printf("%s[___]%n", " ".repeat(fixedTreeSize - 2));
    }
}
