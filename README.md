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

> [!IMPORTANT]
> To run a JavaFX `.jar` the OpenJFX library path needs to be explicitly set since it only contains the `.class` files and not the necessary platform specific binary files (e.g. `.so`, check with `jar tf path/to/target.jar`):
>
> ```sh
> # openjfx was installed to /usr/lib/jvm/java-24-openjfx
> java --module-path /usr/lib/jvm/java-24-openjfx/lib/ \
>      --add-modules javafx.controls,javafx.fxml \
>      -jar path/to/target.jar
> ```

### Dev Tools

#### Bytecode-Viewer

Download from [Konloch/bytecode-viewer](https://github.com/Konloch/bytecode-viewer/releases).

Analyze `.jar`/`.class` files.
