package org.example;

public class Casting {
    public static void main(String[] args) {
        byte a = Byte.MAX_VALUE;
        a++;
        System.out.println(a);
        byte b = Byte.MAX_VALUE;
        b = (byte) (b + 1);
        System.out.println(b);
        byte c = 84;
        byte d = 69;
        System.out.println(c + d);
        System.out.println((byte)(c + d));

        // TODO Casting rules
    }
}
