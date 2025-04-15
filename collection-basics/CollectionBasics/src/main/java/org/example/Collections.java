package org.example;

import java.util.ArrayList;

public class Collections {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("A");list.add("B");list.add("C");list.add("D");list.add("E");
        list.forEach(a -> {
            a += 1;
        });
    }
}
