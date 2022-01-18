#!/usr/bin/env bash
find -name "*.java" > sources.txt
javac -d bin @sources.txt
