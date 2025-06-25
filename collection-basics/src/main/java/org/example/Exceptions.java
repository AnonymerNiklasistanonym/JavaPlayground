package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class Exceptions {
    public static int divide(int a, int b) {
        try {
            return a / b;
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException: " + e.getMessage());
            return 0;
        } finally {
            // Is always run, even when a return is triggered in either try or catch
            System.out.println("Cleanup something...");
        }
    }
    public static void main(String[] args) {

        try {
            // Dividing by 0 throws ArithmeticException because unable to divide by 0
            var result = 1 / 0;
        } catch (ArithmeticException e) {
            // Can be caught
            System.out.println("ArithmeticException was thrown: " + e.getMessage());
        }

        // With an additional finally block resources like a file writer can be closed
        // automatically if either try runs successful or catch caught an exception
        FileWriter writer = null;
        final String filePath = "hello_world.txt";
        final String message = "Hello World";
        try {
            writer = new FileWriter(filePath);
            writer.write(message);
            System.out.println("Message written to file.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();  // Must be inside its own try-catch
                } catch (IOException e) {
                    System.err.println("Error closing FileWriter: " + e.getMessage());
                }
            }
        }

        // Try-with-resources block which automatically cleans/closes the file writer
        // so no finally necessary
        // > This only works if the class implements AutoCloseable
        try (FileWriter writerAutoCloseable = new FileWriter(filePath)) {
            writerAutoCloseable.write("Hello World");
            System.out.println("Message written to file.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

        // Example that the finally block will be triggered even if a return statement
        // is in the try or catch
        final var result = divide(1, 0);

        // Unchecked / Checked exceptions
        // TODO
        // RuntimeExceptions do not need to be caught since it's an unchecked Exception
        // IOException do need to be caught since they are a checked Exception

        // Multiple exceptions
        // TODO
        // If multiple exceptions can be thrown they must be listed from the most specific
        // exception to the most generic one (e.g. FileNotFoundError -> ... -> IOException)
    }
}
