#!/bin/bash

lscpu > ../../cpuinfo.log
cd jit-example/src
echo "<time>  <compile_id>  <level>  <method> (<bytes>) [optional flags]" > ../../compilation.log
javac Main.java
java -XX:+UnlockDiagnosticVMOptions -XX:+PrintCompilation Main >> ../../compilation.log
java Main > ../../timings.log
cd ../..
