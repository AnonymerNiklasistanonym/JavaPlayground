package org.example.objects;

public class ToString {
    private final String name;
    ToString(final String name) {
        this.name = name;
    }

    // Custom string representation
    @Override
    public String toString() {
        return String.format("{ name='%s'}", name);
    }

    public static void main(String[] args) {
        var test = new ToString("test");
        System.out.println(test);
        var testOther = new ToString("test2");
        System.out.println(testOther);
    }
}
