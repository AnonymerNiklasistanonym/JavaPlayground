package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.*;

/**
 * Java Streams
 *
 * Processing data sequences without for-loops in a function-style is possible with the Java Stream API.
 * 2 important characteristics of streams:
 *
 * <ul>
 *     <li>Streams are lazy evaluated, meaning that intermediate operations won’t be executed until a terminal operation is invoked</li>
 *     <li>After executing the terminal operation, the stream closes automatically, and it won’t be possible to reuse it</li>
 * </ul>
 *
 * <a href="https://bell-sw.com/blog/a-guide-to-java-stream-api/">Source Bellsoft</a>
 */
public class Streams {
    public static void main(String[] args) {
        // 1. Create Streams
        // 1.1 Finite Streams
        //     > .stream() / Stream<T>:
        //     [from Arrays]
        int[] numbers = {1, 2, 3, 4};
        IntStream numbersStream = Arrays.stream(numbers);
        numbersStream.forEach(System.out::println);
        //     [from Collections]
        List<String> countries = Arrays.asList("Germany", "France", "Italy");
        Stream<String> countriesStream = countries.stream();
        countriesStream.forEach(System.out::println);
        //     [from static Stream]
        DoubleStream doubleStream = DoubleStream.of(4.5, 6.7, 1.2);
        doubleStream.forEach(System.out::println);
        //     [from file Stream]
        try (Stream<String> fileLinesStream = Files.lines(Paths.get("src", "main", "res", "lines.txt"))) {
            fileLinesStream.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 1.2 Infinite Streams
        //     > .generate() / .iterate()
        //       requires a stop condition e.g. limit()
        Random random = new Random();
        IntStream.generate(random::nextInt).limit(10).forEach(System.out::println);

        // 2. Parallelize Stream Operations
        int sum = IntStream.range(1, 99)
                .parallel()
                .map(i -> i * 10)
                .reduce(0, Integer::sum);
        System.out.printf("Sum of 1..99 of n * 10: %d%n", sum);
        // Careful if reduce is ran in parallel but the operation is not associative (like String concatenation)
        String result = IntStream.range(1, 50).parallel().mapToObj(i -> String.format("%d", i))
                .reduce("", (a, b) ->  String.format("(a='%s',b='%s')", a, b));
        String resultStable = IntStream.range(1, 50).parallel().mapToObj(i -> String.format("%d", i))
                .collect(Collectors.joining(",", "[", "]"));
        System.out.printf("Concatenate stream of strings: '%s'%n%svs '%s' %n", result, " ".repeat(28), resultStable);

        // 3. Filtering
        IntStream.range(0, 11).filter(n -> n % 2 == 0).forEach(System.out::println);

        // 4. Map

        // 5. FlatMap

        // 6. Sort

        // 7. Distinct

        // 8. Skip

        // 9. Limit

        // 10. Collect

        // 11. Reduce

        // 12. Find

        // 13. Match

        // 14. count()/average()/min()/max()
    }
}
