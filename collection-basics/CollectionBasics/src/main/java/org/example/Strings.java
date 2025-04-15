package org.example;

public class Strings {
    public static void main(String[] args) {
        // TODO Printf

        String str = "Hello, World!";
        String str2 = "HELLO, WORLD!";
        String emptyStr = "";
        String spaces = "   padded   ";
        String codeBlock = """
                <html>
                    <p>Keeps indent?</p>
                </html>
                """;
        String[] words = {"Java", "Python", "Go", "Rust"};

        // Length
        System.out.println("Length: " + str.length());

        // charAt
        System.out.println("Char at 0: " + str.charAt(0));

        // substring
        System.out.println("Substring (7, 12): " + str.substring(7, 12));

        // repeat (Java 11+)
        System.out.println("Repeat 3x: " + str.repeat(3));

        // equals
        System.out.println("Equals (case-sensitive): " + str.equals(str2));

        // equalsIgnoreCase
        System.out.println("Equals (ignore case): " + str.equalsIgnoreCase(str2));

        // contains
        System.out.println("Contains 'World': " + str.contains("World"));

        // startsWith / endsWith
        System.out.println("Starts with 'Hello': " + str.startsWith("Hello"));
        System.out.println("Ends with '!': " + str.endsWith("!"));

        // indexOf / lastIndexOf
        System.out.println("Index of 'o': " + str.indexOf('o'));
        System.out.println("Last index of 'o': " + str.lastIndexOf('o'));

        // toUpperCase / toLowerCase
        System.out.println("Uppercase: " + str.toUpperCase());
        System.out.println("Lowercase: " + str.toLowerCase());

        // isEmpty / isBlank (Java 11+)
        System.out.println("Is empty: " + emptyStr.isEmpty());
        System.out.println("Is blank: " + spaces.isBlank());

        // trim / strip (Java 11+)
        System.out.println("Trimmed: '" + spaces.trim() + "'");
        System.out.println("Stripped: '" + spaces.strip() + "'");
        System.out.println("-Leading: '" + spaces.stripLeading() + "'");
        System.out.println("-Trailing: '" + spaces.stripTrailing() + "'");
        System.out.println("-Indent: '" + codeBlock.stripIndent() + "'");

        // replace / replaceAll / replaceFirst
        System.out.println("Replace 'o' with '0': " + str.replace('o', '0'));
        System.out.println("ReplaceAll 'l+': " + str.replaceAll("l+", "*"));
        System.out.println("ReplaceFirst 'l+': " + str.replaceFirst("l+", "*"));

        // split
        String[] parts = str.split(", ");
        System.out.println("Split by ', ':");
        for (String part : parts) {
            System.out.println(" - " + part);
        }

        // join
        String joined = String.join(" | ", "Java", "Python", "Go");
        System.out.println("Joined: " + joined);
        System.out.println("-Array: " + String.join(" | ", words));

        // format
        String formatted = String.format("Name: %s, Age: %d", "Alice", 30);
        System.out.println("Formatted: " + formatted);

        // matches (regex)
        System.out.println("Matches regex '[A-Z].*!': " + str.matches("[A-Z].*!"));

        // intern
        String interned = str.intern();
        System.out.println("Interned string: " + interned);
    }
}
