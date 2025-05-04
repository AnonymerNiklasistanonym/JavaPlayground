package org.example.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamableObject implements Iterable<ExampleObject> {
    private final List<ExampleObject> data = new ArrayList<>();

    @Override
    public Iterator<ExampleObject> iterator() {
        return data.iterator();
    }

    // No standardized interface but a way to
    public Stream<ExampleObject> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public void add(final ExampleObject obj) {
        data.add(obj);
    }

    public static void main(String[] args) {
        var streamable = new StreamableObject();
        streamable.add(new ExampleObject("A", 10));
        streamable.add(new ExampleObject("B", 10));
        streamable.add(new ExampleObject("Z", 1));

        streamable.stream().forEach(System.out::println);
    }
}
