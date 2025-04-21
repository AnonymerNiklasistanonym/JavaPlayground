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
