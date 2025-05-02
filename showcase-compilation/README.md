# Compilation

The different ways to compile `.java` source files and resolve symbols.

To understand this it is necessary to know the following key concepts:

- Packages/Imports
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
 
  - `javac` expects that a directory name is the same as the package name when resolving Symbols

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
  
  changes the Fully Qualified Classname to: `math.Calculator`.

## 1. Name all files

```sh
javac Main.java helper/Helper.java
```