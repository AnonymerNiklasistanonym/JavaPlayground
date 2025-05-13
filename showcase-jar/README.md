# JAR (Java Archive)

A `.jar` file is a **package file format** used to bundle multiple files into a single archive.

- A compressed archive used to distribute Java applications or libraries
- Executable if it includes a manifest specifying the main class

It contains:

1. **Compiled Java classes** (`.class` files)
2. **A manifest file** (`META-INF/MANIFEST.MF`) with metadata (e.g. main class, version)
3. [OPTIONAL] **Resources**  (images, configuration files, ...)
4. [OPTIONAL] **system libraries** or **other JARs**

## Create

```text
.
|
+-- math
    |
    +-- Calculator.java
```

First we need to create the `.class` files:

```sh
mkdir -p out
javac math/* -d out
```

Then we can create the `.jar` file using the `jar` command:

```sh
jar --create --file libMath.jar -C out .
# Short version:
# jar cf libMath.jar -C out .
```

This will create a `.zip` archive with the extension `.jar` that contains:

- The from `javac` compiled Bytecode `.class` files of all files in the `out` directory (from the files in the `math` directory)

- A file called `MANIFEST.MF` with the following content:

  ```text
  Manifest-Version: 1.0
  Created-By: 23.0.2 (BellSoft)
  ```

### Library (use in a project)

Since this is just a collection of  `javac` compiled Bytecode`.class` files we can embed this into another project.
For this to work we extend the class path (that is per default the root/`src` directory with the `.jar` file):

```sh
javac --class-path ".:../libMath.jar" main/Main.java # Linux
javac --class-path ".;..\libMath.jar" main/Main.java # Windows
# short version:
# javac -cp ".:../libMath.jar" main/Main.java # Linux
```

When running this you need to use the fully qualified class name:

```sh
java --class-path ".:../libMath.jar" main.Main # Linux
java --class-path ".;..\libMath.jar" main.Main # Windows
# short version:
# javac -cp ".:../libMath.jar" main.Main # Linux
```

The `;` or `;` separated list of directories/`.jar` files can be extended multiple times so any amount of `.jar` libraries can be added.

```java
// main/Main.java

package main;
import math.Calculator;

public class Main {
    public static void main(String[] args) {
        var calculator = new Calculator();
        System.out.println("1 + 2 = " + calculator.add(1, 2));
    }
}
```

### Runnable (run it)

To point the `java -jar` command to what file in the archive you want it to run per default you need to add an entry to the `META-INF/MANIFEST.MF` file with the fully qualified class name of your main file:

```text
.
|
+-- hello_world
    |
    +-- HelloWorld.java
```

```java
// Hello World.java

package hello_world;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
```

```sh
mkdir -p out
javac hello_world/* -d out
```

```sh
jar --create --file libMath.jar --main-class=hello_world.HelloWorld -C out .
# Short version:
# jar cfe runnableHelloWorld2.jar hello_world.HelloWorld -C out .
```

This new `META-INF/MANIFEST.MF`:

```test
Manifest-Version: 1.0
Created-By: 23.0.2 (BellSoft)
Main-Class: hello_world.HelloWorld
```
