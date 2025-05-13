# Compilation

The different ways to compile `.java` source files and resolve symbols.

To understand this it is necessary to know the following key concepts:

## Packages/Imports

- A package is a namespace for classes/interfaces/...

```java
// At the top of your .java file:
package com.example.utils;
```

- It can be seen as a folder or module that helps to group related functionality and avoids naming conflicts at the same time
- By importing from a namespace all the classes/interfaces/... in it can be used in the current Java source file

**Single Import**:

```java
import java.util.List;
import java.util.ArrayList;

public class MyApp {
    List<String> items = new ArrayList<>();
}
```

**Wildcard Import**:

```java
import java.util.*;
// ...
```

**Static Import**: (necessary if a static member of a class should be imported)

```java
import static java.lang.Math.PI;
import static java.lang.Math.max;
// -------------------------------
import java.lang.Math;
// ...
double x = Math.sqrt(16);
// -------------------------------
import static java.lang.Math.sqrt;
// ...
double x = sqrt(16);
```

Alternatively you can write down the path in it's full length so that the Java compiler can find it:

```java
public class MyApp {
    java.util.List<String> items = new java.util.ArrayList<>();
}
```

- Java Fully Qualified Classnames: `{package}.{classname}`
  
  File with no package declaration:

  ```java
  public class Calculator {
      public int add(int a, int b) {
          return a + b;
      }
  }
  ```
  
  has the Fully Qualified Classname: `Calculator`.
  Adding at the top:

  ```java
  package math;
  // ...
  ```
  
  changes the Fully Qualified Classname to: `math.Calculator`
  
  > [!Tip]
  >
  > For local classes it is expected to declare packages in relation to the `Main.java` file.
  >
  > This means that `package math;` indicates the following directory structure:
  >
  > ```
  > |
  > +-- Main.java
  > +-- math
  >     |
  >     +-- Calculator.java
  > ```
  >
  > This means that if you use `package math.calculators;` in your `SimpleCalculator.java` file it expects:
  >
  > ```
  > |
  > +-- Main.java
  > +-- math
  >     |
  >     +-- calculators
  >         |
  >         +-- SimpleCalculator.java
  > ```
- `javac`/`java` expect that a directory name is the same as the package name when resolving Symbols (as well as all classes have the same file name as their name in the code)

  Meaning that for example compiling the following where no package names are defined will work:
  
  ```sh
  javac Main.java helper/Helper.java
  ```

  But running the program afterwards using `java Main` will throw an error since the `.class` files are being created where the source `.java` files are located.

  ```sh
  java Main
  # Exception in thread "main" java.lang.NoClassDefFoundError: Helper
  # ...
  ```

  This means that the `.class` file needs to be copied into the same directory as the `Main.class` file:

  ```sh
  cp helper/Helper.class Helper.class
  java Main
  ```

  Assuming you follow these guidelines `java`/`javac` automatically find and compile/load external referenced code by just running `javac Main.java`/`java Main` (assuming `Main.java` is the main file).
