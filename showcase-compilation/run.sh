#!/bin/bash

cd src
rm -f *.class */*.class
javac MainAllNoImport.java helper/HelperAllNoPackage.java
cp helper/HelperAllNoPackage.class HelperAllNoPackage.class
java MainAllNoImport > ../MainAllNoImportCopy.log
javac MainAll.java
java MainAll > ../MainAll.log
javac MainSameDir.java # HelperSameDir.java
java MainSameDir > ../MainSameDir.log
cd ..
