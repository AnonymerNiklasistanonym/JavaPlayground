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
$ java --version
openjdk 24.0.1 2025-03-18
OpenJDK Runtime Environment (build 24.0.1)
OpenJDK 64-Bit Server VM (build 24.0.1, mixed mode, sharing)
```

```powershell
$ java --version
openjdk 23.0.2 2025-01-21
OpenJDK Runtime Environment (build 23.0.2+9)
OpenJDK 64-Bit Server VM (build 23.0.2+9, mixed mode, sharing)
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
Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
Maven home: /usr/share/java/maven
Java version: 24.0.1, vendor: Arch Linux, runtime: /usr/lib/jvm/java-24-openjdk
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "6.14.0-1-manjaro", arch: "amd64", family: "unix"
```

```powershell
$ mvn --version
Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
Maven home: C:\Users\nikla\Programme\apache-maven-3.9.9
Java version: 23.0.2, vendor: BellSoft, runtime: C:\Program Files\BellSoft\LibericaJDK-23-Full
Default locale: de_DE, platform encoding: UTF-8
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
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
- general-purpose,
- **memory-safe**: prevents common memory issues like segmentation faults, buffer overflows, dangling pointers via automatic garbage collection and strict type checking
  - You can't manually access memory addresses
  - Java automatically reclaims unused memory, reducing the risk of memory leaks and dangling pointers (where memory is freed but still accessed)
    - Memory leaks are NOT impossible!
    - If you hold references to objects you no longer need, they won't be collected
    - JNI (Java Native Interface): When Java calls into native code (e.g., C/C++ libraries), you leave the safety of the JVM
  - Accessing outside the bounds of an array will throw a ArrayIndexOutOfBoundsException, rather than corrupting memory
  - You can't randomly cast unrelated types or access memory as if it were a different type, which avoids undefined behavior
- **object-oriented**: Emphasizes concepts like classes, objects, inheritance, and polymorphism
- **platform independent** and **compiled**: Code is compiled into *bytecode* (optimizing), which runs on any system with a *Java Virtual Machine* (*JVM*)
  - this *bytecode* is more compact and optimized than the Java source code since it removes unnecessary syntax useful for humans like formatting, variable names and comments **TODO**
  - additionally the *bytecode* is only run if it contains no syntax errors or other illegal code constructs
- **statically typed**: Types are checked at compile-time, reducing lots of runtime errors and explicit checks
- **


#### Compilation







*Based on ["How Java REALLY Works: Packages, Jars & Classpath"](https://www.youtube.com/watch?v=zJPFwGs4q9o)*
