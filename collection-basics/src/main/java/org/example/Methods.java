package org.example;

import java.util.Arrays;

public class Methods {
    public static void staticMethod() {
        System.out.println("Static method called.");
    }
    public static void spreadOperator(int... numbers) {
        System.out.println("Sum: " + Arrays.stream(numbers).sum());
    }
    public static void main(String[] args) {
        // TODO Printf
    }
}
