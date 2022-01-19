#!/usr/bin/env bash
find -name "*.java" > sources.txt
javac -classpath $CLASSPATH:/opt/pi4j/lib/'*' -d bin @sources.txt
