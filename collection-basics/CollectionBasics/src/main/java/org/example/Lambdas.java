package org.example;

import java.util.function.Function;

public class Lambdas {
    public static void main(String[] args) {
        Function<Integer, Integer> square = (x) -> {
            return x * x;
        };
        System.out.println(square.apply(2));

        String a = "hello";
        String b = "hello";
        String c = new String("hello");
        String d = "Hello";

        System.out.println(a == b);
        System.out.println(a.equals(b));
        System.out.println(a == d);
        System.out.println(a.equals(d));
        System.out.println(a == c.intern());
        System.out.println(d == c.intern());
    }
}
