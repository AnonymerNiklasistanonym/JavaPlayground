# JavaPlayground

## TODO

- [ ] Applications
  - [ ] Create JavaFX application
    - [ ] Use a `.fxml` file
    - [ ] Run it
      - [ ] Windows
      - [x] Linux
    - [ ] Package it to a fat `.jar` file that can be run
      - [ ] Linux
      - [x] Windows
  - [ ] Create a binary data type narrowing/widening/cast showcase application
  - [ ] Create a tic-tac-toe application
    - [ ] CLI
    - [ ] JavaFX
  - [ ] Create a Java Spring Webserver
  - [ ] Java Tomcat?
- [ ] Tooling
  - [ ] Automatic formatting
    - [ ] `.java`
    - [ ] `.fxml`
  - [ ] Add JavaDoc export
  - [ ] Add CI/CD

## Setup

### Java

**Linux:**

```sh
# Archlinux
pacman -S jdk-openjdk java-openjfx
```

Archlinux: If multiple versions of Java are installed you might need to switch the default using the tool `archlinux-java`:

```sh
archlinux-java status
# Available Java environments:
#   java-17-openjdk (default)
#   java-24-openjdk
sudo archlinux-java set java-24-openjdk
archlinux-java status
# Available Java environments:
#   java-17-openjdk
#   java-24-openjdk (default)
```

**Windows:**

```powershell
# winget search openjdk
winget install BellSoft.LibericaJDK.23.Full # Liberica JDK 23 Full (64-bit)
# Automatically sets (System) $env:JAVA_HOME to e.g. C:\Program Files\BellSoft\LibericaJDK-23-Full\
```

**Check:**

```sh
java --version
# openjdk 24.0.1 2025-03-18
# OpenJDK Runtime Environment (build 24.0.1)
# OpenJDK 64-Bit Server VM (build 24.0.1, mixed mode, sharing)
```

```powershell
java --version
# openjdk 23.0.2 2025-01-21
# OpenJDK Runtime Environment (build 23.0.2+9)
# OpenJDK 64-Bit Server VM (build 23.0.2+9, mixed mode, sharing)
```

### Maven (requires Java)

*Follow https://maven.apache.org/install.html*

**Linux:**

```sh
# Archlinux
pacman -S maven
```

**Windows:**

1. Download the [binary distribution archive](https://maven.apache.org/download.cgi)
2. Extract it into any directory
3. Add the `bin` directory inside the extracted directory to the User Environment Variable `Path`

**Check:**

```sh
mvn --version
# Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
# Maven home: /usr/share/java/maven
# Java version: 24.0.1, vendor: Arch Linux, runtime: /usr/lib/jvm/java-24-openjdk
# Default locale: en_US, platform encoding: UTF-8
# OS name: "linux", version: "6.14.0-1-manjaro", arch: "amd64", family: "unix"
```

```powershell
mvn --version
# Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
# Maven home: C:\Users\nikla\Programme\apache-maven-3.9.9
# Java version: 23.0.2, vendor: BellSoft, runtime: C:\Program Files\BellSoft\LibericaJDK-# 23-Full
# Default locale: de_DE, platform encoding: UTF-8
# OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
```

## Build/Run

**Linux:**

```sh
# Run
make run
# Clean
make clean
# Create JAR
make
```

> [!IMPORTANT]
> To run a JavaFX `.jar` **on Linux** the OpenJFX library path needs to be explicitly set since it only contains the `.class` files and not the necessary platform specific binary files (e.g. `.so`, check with `jar tf path/to/target.jar`):
>
> ```sh
> # openjfx was installed to /usr/lib/jvm/java-24-openjfx
> java --module-path /usr/lib/jvm/java-24-openjfx/lib/ \
>      --add-modules javafx.controls,javafx.fxml \
>      -jar path/to/target.jar
> ```

**Windows:**

```powershell
# Run:
# > JavaFX project:
mvn javafx:run
# Clean
mvn clean
# Create JAR
mvn clean package
```

### Dev Tools

#### Bytecode-Viewer

Download from [Konloch/bytecode-viewer](https://github.com/Konloch/bytecode-viewer/releases).

Analyze `.jar`/`.class` files.

### How does Java work?

Java is a programming language that is:

- **high-level**: abstracts away low-level hardware details, making it easier to write, read, and maintain

  - platform independent: the *Java Virtual Machine* (JVM) that runs Java Programs exists on most platforms

- general-purpose: meaning it is not specialized for one domain (like web development) but can be used to build a wide variety of applications

- **memory-safe**: while e.g. memory leaks, race conditions, etc. can still happen Java prevents common memory issues like

  - segmentation faults: access (arbitrary) memory that you are not allowed to e.g. dereferencing a bad/null pointer

    - You can't manually access memory addresses
    - Null pointer throw a `NullPointerException` if they are dereferenced
      ```java
      String s = null;
      s.length(); // throws NullPointerException
      ```

  - buffer overflows: write more data to a memory buffer than it can hold e.g. a char array with the length 5 is assigned a String with the length 10

    - Accessing or writing out of bounds throws a `ArrayIndexOutOfBoundsException` since this is checked every single time
      ```java
      int[] arr = new int[3];
      arr[10] = 5; // throws ArrayIndexOutOfBoundsException
      ```

  - dangling pointers (*Use-After-Free*): memory that was previously freed/deallocated is used again (e.g. via a pointer)

    - The JVM with its automatic garbage collection frees memory automatically when no reference is left so this can't happen

  - *Double-Free*: memory is deallocated twice

    - See dangling pointers

  - heap overflow: allocate more memory than is available or corrupt memory in the process

    - Running out of memory throws a `OutOfMemoryException` instead of corrupting data

  - type confusion: interpret memory as the wrong type (bad/implicit casts)

    - Java only allows explicit casts
      ```java
      int i = 42;
      float f = (float) i; // must be explicitly casted and only allowed if possible
      ```

    - Memory locations/pointers can't be casted at all

    - If an explicit cast is done between classes that is not possible it checks this at runtime and throws a `ClassCastException` instead of unexpected behavior
      ```java
      Object obj = "Hello, world!";
      Integer num = (Integer) obj; // Allowed since both classes have the superclass
                                   // Object but ClassCastException at runtime since
                                   // the JVM sees that obj actually holds a String
                                   // which can't be casted to an Integer
      ```

  - reading uninitialized memory: Reading from memory that hasn't been initialized

    - Java forces all variables to be initialized before use

- **object-oriented**: Emphasizes concepts like classes, objects, inheritance, and polymorphism

- *Ahead-of-Time* (AoT) compiled: Java source code is compiled into *Bytecode* which runs on any system with a *Java Virtual Machine* (*JVM*)

  - *Bytecode* is optimized for execution compared to the Java source code since it removes unnecessary syntax only useful for humans like comments, formatting, variable names and converts high-level commands into *Bytecode* instructions
  - additionally this will only be successful if the source code passes all **compile-time checks**, including syntax validation and structural correctness

- *Just-In-Time* (JiT) executed: The *Java Virtual Machine* (*JVM*) optimizes the generated *Bytecode* dynamically depending if it's *hot* (repeatedly executed) using a tiered compilation

  1. *Bytecode* Interpretation
  2. Machine Code Compilation
  3. Highly Optimized Profiled Machine Code Compilation

- **statically typed**: Types are checked at compile-time

  - reduces lots of runtime errors (e.g. `String` declared as `int`)
  - making explicit type checks unnecessary
  - more room for optimizations by the compiler (easier reasoning)

#### Compilation

- `javac` compiles a Java file to *Bytecode*

  ```sh
  javac Main.java
  ```

#### Run

- `java` invokes the *Java Virtual Machine* (JVM), a interpreter which runs the *Bytecode*

  ```sh
  # Main.class in this directory
  java Main
  ```

- while the JVM can have slow start times when interpreting *Bytecode* it optimizes repeatedly executed code (*hot* code/bottlenecks) on the fly by profiling the executed *Bytecode* using a tiered approach:

  1. Interpret all *Bytecode* while profiling it (fast but no specific optimizations)
  2. If *hot* code is detected this code is if possible optimized to hardware instructions using basic profiling information from past runs (slow compilation, fast run time)
  3. If this code is further used and identified as *hot* it is if possible optimized to hardware instructions using detailed profiling information (very slow compilation, faster run time)

- *Garbage collector*/collection (GC)

  - Memory allocation

    - When an object is created (`new SomeObject()`), it's allocated in the **heap** memory
    - Each thread has its own **stack**, and each method call gets a **stack frame**, inside of it are:
      - **Local variable array**: Holds method parameters and local variables
      - **Operand stack**: Used for intermediate calculations

  
      - Static variables are stored in the *Metaspace* associated with the class
  
  - Other JVM stored information:
  
    - Class Metadata: Info about the class (methods, fields, inheritance, etc.)
    - Method Tables (*vtable*): For method dispatch, especially with inheritance/polymorphism


  - The GC considers an object *garbage* if there are **no live references** to it from any running part of the code.

    - If an object can't be reached (directly or indirectly) from a root reference (like static variables, local variables on the stack, etc.), it's eligible for collection.

  - The GC periodically scans the heap for unreachable objects and removes them
    - GC Algorithms:
      1. **Serial GC** – Single-threaded, good for small apps.
      2. **Parallel GC** – Multi-threaded, for throughput-oriented applications.
      3. **G1 (Garbage First) GC** – Designed for large heaps with predictable pause times.
      4. **ZGC and Shenandoah** – Low-latency collectors, very short pause times, great for big real-time systems.

- Classes are loaded **on demand** by the JVM *ClassLoader*

  - You can load classes at runtime **TODO**


*Based on ["How Java REALLY Works: Packages, Jars & Classpath"](https://www.youtube.com/watch?v=zJPFwGs4q9o)*

## IDEs

### IntelliJ IDEA Community

**Other**:

- Change SDK/Language level: `File` > `Project Structure`

**Plugins**:

`File` > `Settings` >  `Plugins`:

- `Marketplace`: Search and install plugins like
  - `Project to Zip`: Converts project to `.zip` file without temporary/build files
- `Installed`: Show installed plugins and remove/disable them
  - Makes it possible to disable AI code completion