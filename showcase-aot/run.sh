#!/bin/bash

cd src
javac MainReflections.java
java MainReflections > ../MainReflections.log
javac MainTypeErasure.java
javap -c MainTypeErasure > ../MainTypeErasure.class.log
java MainTypeErasure > ../MainTypeErasure.log
cd ..
