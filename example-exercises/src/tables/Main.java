package tables;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static String generateTableCSS(final int lines) {
        return """
                    :root {
                        --border_width: 0.1rem;
                        --border_style_normal: solid #ccc;
                        --border_style_thick: solid #444;
                        --border_style_light: solid #ddd;
                    }
                    table {
                        border-collapse: collapse;
                        width: auto;
                        margin: 0.5rem;
                        font-family: sans-serif;
                        font-size: 1rem;
                        border: var(--border_width) var(--border_style_normal);
                    }
                    th, td {
                        padding: 0.5rem 1rem;
                        text-align: center;
                        border-right: var(--border_width) var(--border_style_light);
                    }
                    th {
                        background-color: #f5f5f5;
                        font-weight: 600;
                    }
                    th:nth-child(2n):not(:last-child),
                    td:nth-child(2n):not(:last-child) {
                        border-right: calc(var(--border_width) * 2) var(--border_style_thick);
                    }
                    th {
                        border-bottom: calc(var(--border_width) * 2) var(--border_style_thick);
                    }
                    tr:nth-child(%sn + 1):not(:last-child) td {
                        border-bottom: calc(var(--border_width) * 2) var(--border_style_thick);
                    }
                """.formatted(lines);
    }

    public static void printTable(final int columns, final int rows, final int lines, final boolean html, final boolean debug) {
        // Get max length of each value for output
        final int maxLengthCol = String.valueOf(columns).length();
        final int maxLengthRow = String.valueOf(rows).length();
        final int maxLengthLine = String.valueOf(lines).length();
        final BigInteger maxN = BigInteger.valueOf(columns).multiply(BigInteger.valueOf(rows)).multiply(BigInteger.valueOf(lines));
        final BigInteger maxNSquared = maxN.multiply(maxN);
        final String nStr = html ? """
                    <math display="inline" displaystyle="true">
                        <mi>n</mi>
                    </math>
                """ : "n";
        final String nSquaredStr = html ? """
                    <math display="inline" displaystyle="true">
                        <msup>
                            <mi>n</mi>
                            <mn>2</mn>
                        </msup>
                    </math>
                """ : "n^2";
        final int maxLengthNStr = nStr.length();
        final int maxLengthNSquaredStr = nSquaredStr.length();
        final int maxLengthN = Math.max(String.valueOf(maxN).length(), maxLengthNStr);
        final int maxLengthNSquared = Math.max(String.valueOf(maxNSquared).length(), maxLengthNSquaredStr);
        final int maxLengthDebug = 2 + 2 + (4 + maxLengthCol + 4 + maxLengthRow + 5 + maxLengthLine);

        // Generate template strings
        String templateHeader = "%" + maxLengthN + "s | %" + maxLengthNSquared + "s";
        String templateCell = "%" + maxLengthN + "d | %" + maxLengthNSquared + "d";
        String cellSeparator = " ".repeat(6);
        String cellSeparatorLine = "-".repeat(6);
        String tableEdgeL = " ";
        String tableEdgeR = tableEdgeL;
        String tableEdgeLine = "-";
        String lineSeparator = "-".repeat(maxLengthN + 1) + "+" + "-".repeat(maxLengthNSquared + 1);
        if (debug) {
            templateHeader = "%" + maxLengthDebug + "s %" + maxLengthN + "s";
            templateCell = "[col=%" + maxLengthCol + "d,row=%" + maxLengthRow + "d,line=%" + maxLengthLine + "d] %" + maxLengthN + "d";
            tableEdgeL = " ";
            tableEdgeR = tableEdgeL;
            tableEdgeLine = "#";
            cellSeparatorLine = "+".repeat(3);
            cellSeparator = " ".repeat(3);
            lineSeparator = "-".repeat(maxLengthDebug + 1 + maxLengthN);
        } else if (html) {
            templateHeader = "<th>%s</th><th>%s</th>";
            templateCell = "<td>%d</td><td>%d</td>";
            cellSeparator = "";
            cellSeparatorLine = "";
            tableEdgeL = "<tr>";
            tableEdgeR = "</tr>";
            tableEdgeLine = "";
            lineSeparator = "";
        }

        if (!debug && html) {
            System.out.printf("""
                    <!DOCTYPE html>
                    <html>
                        <head>
                            <title>Sophisticated HTML square table</title>
                            <style>%s</style>
                        </head>
                    <body>
                        <table>
                    """.formatted(generateTableCSS(lines)));
        }

        // Header
        System.out.print(tableEdgeL);
        for (int col = 0; col < columns; col++) {
            if (debug) {
                System.out.printf(templateHeader, "", nStr);
            } else {
                System.out.printf(templateHeader, nStr, nSquaredStr);
            }
            if (col != columns - 1) {
                System.out.print(cellSeparator);
            }
        }
        System.out.printf("%s%n%s", tableEdgeR, tableEdgeLine);
        for (int col = 0; col < columns; col++) {
            System.out.print(lineSeparator);
            if (col != columns - 1) {
                System.out.print(cellSeparatorLine);
            }
        }
        System.out.printf("%s%n", tableEdgeLine);

        // Body
        final BigInteger linesBig = BigInteger.valueOf(lines);
        for (int row = 0; row < rows; row++) {
            final BigInteger rowLinesBig = BigInteger.valueOf(row).multiply(linesBig);
            for (int line = 0; line < lines; line++) {
                final BigInteger lineBig = BigInteger.valueOf(line);
                System.out.print(tableEdgeL);
                for (int col = 0; col < columns; col++) {
                    //final int n = row * lines + line + col * columns * lines;
                    final BigInteger n = rowLinesBig.add(lineBig).add(BigInteger.valueOf(col).multiply(BigInteger.valueOf(columns).multiply(linesBig)));
                    if (debug) {
                        System.out.printf(templateCell, col, row, line, n);
                    } else {
                        //long nSquared = (long) Math.pow(n, 2);
                        final BigInteger nSquared = n.multiply(n);
                        System.out.printf(templateCell, n, nSquared);
                    }
                    if (col == columns - 1) {
                        System.out.print(tableEdgeR);
                    } else {
                        System.out.print(cellSeparator);
                    }
                }
                System.out.printf("%n");
            }
            if (row != rows - 1) {
                String[] helper = new String[columns];
                Arrays.fill(helper, lineSeparator);
                System.out.println(tableEdgeLine + String.join(cellSeparatorLine, helper) + tableEdgeLine);
            }
        }
        if (!debug && html) {
            System.out.print("""
                        </body>
                    </html>
                    """);
        }
    }

    public static void main(String[] args) {
        printTable(3, 3, 2, false, true);
        System.out.printf("%n");
        printTable(3, 3, 2, false, false);
        System.out.printf("%n");
        printTable(3, 3, 3, false, false);
        System.out.printf("%n");

        int columns = -1;
        int rows = -1;
        int lines = -1;
        boolean debug = false;
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter columns (>0):");
            columns = sc.nextInt();
            System.out.print("Enter rows (>0):");
            rows = sc.nextInt();
            System.out.print("Enter lines (>0):");
            lines = sc.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Invalid input! Please enter a whole number.");
            System.exit(-1);
        } catch (NoSuchElementException | IllegalStateException e) {
            System.err.println("Error reading input: " + e.getMessage());
            System.exit(-1);
        }

        if (columns <= 0) {
            System.err.println("Invalid column size: " + columns);
            System.exit(-1);
        }
        if (rows <= 0) {
            System.err.println("Invalid rows size: " + rows);
            System.exit(-1);
        }
        if (lines <= 0) {
            System.err.println("Invalid lines size: " + lines);
            System.exit(-1);
        }

        printTable(columns, rows, lines, false, debug);
        System.out.printf("%n");

        // Funny: Watch the System.out output and forward it to a file
        Path desktopPath = Paths.get(System.getProperty("user.home"), "Desktop");
        File file = desktopPath.resolve("table.html").toFile();
        // Save the original System.out to restore later
        PrintStream originalOut = System.out;
        // Create a PrintStream to write to a file
        try (PrintStream fileOut = new PrintStream(file)) {
            // Redirect System.out to both console and file
            System.setOut(new PrintStream(System.out) {
                @Override
                public void print(String s) {
                    originalOut.print(s);
                    fileOut.print(s);
                }

                @Override
                public void println(String s) {
                    originalOut.println(s);
                    fileOut.println(s);
                }
            });

            printTable(columns, rows, lines, true, debug);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            // Restore the original System.out after the try block
            System.setOut(originalOut);
        }

        System.out.println("Not catched in file");
    }
}
