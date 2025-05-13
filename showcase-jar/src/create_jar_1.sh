#!/bin/bash

rm -rf out
mkdir -p out
javac math/* -d out
jar --create --file libMath.jar -C out .
jar cf libMath2.jar -C out .

rm -rf out
mkdir -p out
javac hello_world/* -d out
jar --create --file runnableHelloWorld.jar --main-class=hello_world.HelloWorld -C out .
jar cfe runnableHelloWorld2.jar hello_world.HelloWorld -C out .

cd src_other
javac --class-path ".:../libMath.jar" main/Main.java
java --class-path ".:../libMath.jar" main.Main
