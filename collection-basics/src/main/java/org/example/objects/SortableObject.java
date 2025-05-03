package org.example.objects;

import java.util.Arrays;
import java.util.Comparator;

public class SortableObject implements Comparable<SortableObject> {
    public final int size;
    public final String name;
    public SortableObject(final int size, final String name) {
        this.size = size;
        this.name = name;
    }
    public int getSize() {
        return size;
    }
    public String getName() {
        return name;
    }

    public static final Comparator<SortableObject> BY_SIZE_THEN_REVERSE_NAME =
            Comparator.comparingInt(SortableObject::getSize)
                    .thenComparing(SortableObject::getName, Comparator.reverseOrder());
    public static final Comparator<SortableObject> BY_NAME_THEN_SIZE =
            Comparator.comparing(SortableObject::getName)
                    .thenComparingInt(SortableObject::getSize);

    /**
     * Compares this object with the specified object for order.
     * Returns:
     *   - a negative integer: if this object is less than the other
     *   - zero:               if they are equal
     *   - a positive integer: if this object is greater than the other
     * Example:
     *   If you have two objects:
     *   <pre>
     *     SortableObject obj1 = new SortableObject(10, "Apple");
     *     SortableObject obj2 = new SortableObject(15, "Banana");
     *   </pre>
     *   <pre>obj1.compareTo(obj2)</pre> will calculate 10 - 15 -> the result is negative and thus obj1 < obj2
     *   <pre>obj2.compareTo(obj1)</pre> will calculate 15 - 10 -> the result is positive and thus obj2 > obj1
     */
    @Override
    public int compareTo(SortableObject other) {
        // *-------------[ONLY REQUIRED FOR NULL SUPPORT]-------------*
        //if (other == null) return -1; // Put null Objects last
        if (other == null) return 1; // Put null Objects first
        // *----------------------------------------------------------*
        int sizeCompare = this.size - other.size; // Use Integer.compare(this.size, other.size) to catch all edge cases!
        if (sizeCompare != 0) return sizeCompare;
        // *----------[ONLY REQUIRED FOR NULL STRING SUPPORT]---------*
        //if (this.name == null) return 1; // Put null Strings last
        //if (other.name == null) return -1;
        if (this.name == null) return -1; // Put null Strings first
        if (other.name == null) return 1;
        // *----------------------------------------------------------*
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return String.format("{size=%d, name=%s}", size,  name == null ? "null" : String.format("'%s'", name));
    }

    public static final Comparator<SortableObject> BASIC_EXAMPLE =
            (o1, o2) -> {
                // Check null objects first and put them after all other entries
                if (o1 == null && o2 == null) return 0;
                if (o1 == null) return 1;  // Put null objects last
                if (o2 == null) return -1; // Put null objects last
                // Now compare by class variables
                if (o1.size != o2.size) {
                    return o1.size - o2.size; // Sort ascending
                }
                if (o1.name == null) return 1;  // Put null strings last
                if (o2.name == null) return -1; // Put null strings last
                return o1.name.compareTo(o2.name); // Sort ascending
            };
    public static final Comparator<SortableObject> BASIC_EXAMPLE_SHORTER_BUT_NO_NULL_CHECKS =
            Comparator.comparing((SortableObject a) -> a.size, Integer::compareTo)
                    .thenComparing((SortableObject a) -> a.name, String::compareTo);
    public static final Comparator<SortableObject> BASIC_EXAMPLE_SHORTEST_BUT_NO_NULL_CHECKS =
            Comparator.comparingInt(SortableObject::getSize).thenComparing(SortableObject::getSize);

    public static final Comparator<SortableObject> BASIC_EXAMPLE_CATCH_NULL_VALUES =
            Comparator.comparing(SortableObject::getSize, Comparator.nullsFirst(Integer::compareTo))
                    .thenComparing(SortableObject::getName, Comparator.nullsLast(String::compareTo));
    public static final Comparator<SortableObject> BASIC_EXAMPLE_CATCH_NULL_VALUES_AND_NULL =
            (o1, o2) -> {
                if (o1 == null && o2 == null) return 0;
                if (o1 == null) return 1;  // Put null objects last
                if (o2 == null) return -1; // Put null objects last
                return BASIC_EXAMPLE_CATCH_NULL_VALUES.compare(o1, o2);  // Use existing comparison
            };

    public static void main(String[] args) {
        var list = new SortableObject[]{
                // Can't contain null!
                //null,
                // Can't contain null values!
                //new SortableObject(10, null),
                new SortableObject(10, "Cde"),
                new SortableObject(10, "Abc"),
                new SortableObject(4, "Smol"),
                new SortableObject(88, "Big"),
                new SortableObject(5, "Same"),
                new SortableObject(5, "Same"),
        };
        System.out.println("Sort via implemented interface:");
        Arrays.sort(list);
        Arrays.stream(list).forEach(System.out::println);

        System.out.println("Sort via comparator BY_NAME_THEN_SIZE:");
        Arrays.sort(list, SortableObject.BY_NAME_THEN_SIZE);
        Arrays.stream(list).forEach(System.out::println);

        System.out.println("Sort via comparator BY_SIZE_THEN_REVERSE_NAME:");
        Arrays.sort(list, SortableObject.BY_SIZE_THEN_REVERSE_NAME);
        Arrays.stream(list).forEach(System.out::println);


        var listWithNull = new SortableObject[]{
                // Can't contain null!
                //null,
                // Can contain null values!
                new SortableObject(10, null),
                new SortableObject(10, "Cde"),
                new SortableObject(10, "Abc"),
                new SortableObject(4, "Smol"),
                new SortableObject(88, "Big"),
        };
        System.out.println("Sort via comparator BASIC_EXAMPLE_CATCH_NULL_VALUES: [NULL Strings]");
        Arrays.sort(listWithNull, SortableObject.BASIC_EXAMPLE_CATCH_NULL_VALUES);
        Arrays.stream(listWithNull).forEach(System.out::println);


        var listWithNulls = new SortableObject[]{
                // Can contain null!
                null,
                // Can contain null values!
                new SortableObject(10, null),
                new SortableObject(10, "Cde"),
                new SortableObject(10, "Abc"),
                new SortableObject(4, "Smol"),
                new SortableObject(88, "Big"),
        };
        System.out.println("Sort via implemented interface: [NULL VALUES]");
        Arrays.sort(listWithNulls);
        Arrays.stream(listWithNulls).forEach(System.out::println);

        System.out.println("Sort via comparator BASIC_EXAMPLE: [NULL VALUES]");
        Arrays.sort(listWithNulls, SortableObject.BASIC_EXAMPLE);
        Arrays.stream(listWithNulls).forEach(System.out::println);

        System.out.println("Sort via comparator BASIC_EXAMPLE_CATCH_NULL_VALUES_AND_NULL: [NULL VALUES]");
        Arrays.sort(listWithNulls, SortableObject.BASIC_EXAMPLE_CATCH_NULL_VALUES_AND_NULL);
        Arrays.stream(listWithNulls).forEach(System.out::println);
    }
}
