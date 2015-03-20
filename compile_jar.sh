#!/bin/bash

javac -verbose *.java

jar cvfm QuiltPrep-$1.jar QuiltPrep.mf *.class

# make executable
chmod +x QuiltPrep-$1.jar
