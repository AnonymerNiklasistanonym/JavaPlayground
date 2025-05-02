# AoT

Ahead of time compilation to Bytecode.

## Type Erasure for generic code

```java
public class MainTypeErasure {
    public <T> void print(T anything) {
        System.out.println(anything);
    }

    public <T extends Number> void printNumber(T number) {
        System.out.println(number.doubleValue());
    }

    public static void main(String[] args) {
        var example = new MainTypeErasure();
        // Calls the method using java.lang.Object
        example.print(42);
        // Calls the method using java.lang.Number
        example.printNumber(42);
    }
}
```

In the compiled Bytecode the calls are converted as described in the comments:

```txt
...
  public static void main(java.lang.String[]);
    Code:
       0: new           #28                 // class MainTypeErasure
       3: dup
       4: invokespecial #30                 // Method "<init>":()V
       7: astore_1
       8: aload_1
       9: bipush        42
      11: invokestatic  #31                 // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
      14: invokevirtual #37                 // Method print:(Ljava/lang/Object;)V
      17: aload_1
      18: bipush        42
      20: invokestatic  #31                 // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
      23: invokevirtual #40                 // Method printNumber:(Ljava/lang/Number;)V
      26: return
...
```

## Reflections

You can get type information of a class field at runtime using reflections.

```java
class Box<T> {
    public String name;
    public T value;
}

abstract class TypeReference<T> {
    Type type;

    public TypeReference() {
        Type superClass = getClass().getGenericSuperclass();
        this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public Type getType() {
        return this.type;
    }
}

public class MainReflections {
    public static void main(String[] args) throws Exception {
        Field fieldName = Box.class.getField("name");
        System.out.println(fieldName.getType().getTypeName());  // Outputs: java.lang.String
        
        Field field = Box.class.getField("value");
        System.out.println(field.getGenericType());  // Output: T

        TypeReference<java.util.List<String>> ref = new TypeReference<>() {};
        System.out.println(ref.getType()); // Output: java.util.List<java.lang.String>
    }
}
```
