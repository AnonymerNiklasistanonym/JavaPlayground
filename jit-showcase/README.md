# JIT Showcase

Use the following commands to not only compile run the `.java` file but also print JVM just-in-time (JIT) compilation activity.
It shows what methods are getting compiled, when, and to what level during the execution of the Java Bytecode.
This for example can showcase the JIT feature of optimizing **hot** code (that is executed repeatedly).

```sh
javac Main.java
# Normal:
java Main
# Additionally print JIT activity:
java -XX:+UnlockDiagnosticVMOptions -XX:+PrintCompilation Main
```

This will generate logs that have the following format:

```text
<time> <compile_id> <level> <method> (<bytes>) [flags/comments]
9     1       3       java.lang.Byte::toUnsignedInt (6 bytes)
9     2       3       java.lang.String::hashCode (60 bytes)
9     3       3       java.lang.Object::<init> (1 bytes)
9     4     n 0       jdk.internal.misc.Unsafe::getReferenceVolatile (native)
9     5     n 0       jdk.internal.vm.Continuation::enterSpecial (native)   (static)
9     6     n 0       jdk.internal.vm.Continuation::doYield (native)   (static)
...
13   22       4       java.lang.Object::<init> (1 bytes)
13   23     n 0       java.lang.invoke.MethodHandle::invokeBasic()L (native)
13    3       3       java.lang.Object::<init> (1 bytes)   made not entrant
...
19   73   !   3       java.lang.ref.ReferenceQueue::poll (58 bytes)
...
25   97       3       Main::computeSomething (26 bytes)
...
```

Fields:

- `time`: Approximate time in milliseconds (or ticks) since the JVM started
- `compile_id`: An internal ID assigned to the compilation
- `level`:
  - `1`: Interpreter or C1 (client compiler, lower-tier)
  - `3`: C1 with profiling (common early tier)
  - `4`: C2 (server compiler, optimized)
  - `n`: Native method
  - `!`: Deoptimization trigger
  - `%`: On-Stack Replacement (OSR)
    - replace an already-running method with a compiled version of itself partway through execution
    - used when a method is long-running (like a loop)
    - JVM decides to optimize it without waiting for the next invocation
- `method`: The class and method being compiled
- `(<bytes>)`: Number of bytecode bytes in the method
- `made not entrant`: JVM decided compiled code shouldn't be used anymore (e.g., because of profiling info changes)
- `(native)`: A method implemented in native code (e.g., from the JDK)
- `(static)`: The method is a static method, declared like

Findings:

- For example Cin the line `25 97 3 Main::computeSomething (26 bytes)` the JVM has compiled an optimized version of the function `computeSomething` and in the upcoming runs the execution becomes 10x faster:

  ```text
  # [APP] Run 010 Time (ns): 023050
  # [APP] Run 011 Time (ns): 028290
  25   97       3       Main::computeSomething (26 bytes)
  25   98       3       java.lang.StringLatin1::indexOfChar (33 bytes)
  # [APP] Run 012 Time (ns): 003040
  # [APP] Run 013 Time (ns): 001810
  ```

- In another version where a longer run is created (by multiplying the constant calls with the current run number) a On-Stack Replacement (OSR) can be seen in the line `26 98 % 4 Main::computeSomething @ 4 (26 bytes) made not entrant` which results in one longer execution but then subsequent consistently faster computations:

  ```text
  # [APP] Run 023 Time/Computation (ns): 000227
  # [APP] Run 024         Time/Computation (ns): 000210
  108  26       3       java.io.PrintStream::ensureOpen (18 bytes)
  26   93       3       Main::computeSomething (26 bytes)   made not entrant
  26   98 %     4       Main::computeSomething @ 4 (26 bytes)   made not entrant
  # ...
  # [APP] Run 025 Time/Computation (ns): 000574
  # [APP] Run 026 Time/Computation (ns): 000134
  # [APP] Run 027 Time/Computation (ns): 000132
  # [APP] Run 028 Time/Computation (ns): 000131
  ```

  - The `Main::computeSomething @ 4` part means that the optimization is started in the method `Main::computeSomething` at the Bytecode index (BCI) 4
  - Each instruction in the Bytecode has a index (this can be visualized by using the built in disassembler `javac Main.java`, `javap -c Main`):

    ```text
    Compiled from "Main.java"
    public class Main {
    public Main();
        Code:
            0: aload_0
            1: invokespecial #1                  // Method java/lang/Object."<init>":()V
            4: return

    public static int computeSomething(int);
        Code:
            0: iconst_0
            1: istore_1
            2: iconst_1
            3: istore_2
            4: iload_2
            5: bipush        100
            7: if_icmpgt     24
            10: iload_1
            11: iload_0
            12: iload_2
            13: imul
            14: iload_2
            15: idiv
            16: iadd
            17: istore_1
            18: iinc          2, 1
            21: goto          4
            24: iload_1
            25: ireturn

    public static void main(java.lang.String[]);
        Code:
            0: iconst_1
            1: istore_1
        ...
    }
    ```

  - For a better visualization a Bytecode viewer like [Konloch/bytecode-viewer](https://github.com/konloch/bytecode-viewer) can be used (it could be that the created `.class` file is unsupported in some viewers since the used major version of Java is too new, but it can help to recompile the Bytecode using an older release like Java 21 which is major version 65 `javac --release 21 Main.java`)
    - The integrated Decompiler creates the connected `.java` source code from it which makes it easier to see what Java commands result in what Bytecode instructions
