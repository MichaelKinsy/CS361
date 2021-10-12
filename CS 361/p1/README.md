# Project 1: Deterministic Finite Automata

* Author: Michael Kinsy
* Class: CS361 Section 2 
* Semester: Fall 2021

## Overview

This java program models a deterministic finite automaton using packages, java collections, interfaces, and abstract classes.

## Compiling and Using

* Compilation
   - To compile run: javac fa/dfa/DFADriver.java frotm the top directory of this project
* Using
   - To use run: java fa.dfa.DFADriver 'path to input file'

## Discussion
 This project was relatively straight forward to implement with most of the critical concepts being in setting up my DFAState properly and the data structures that stored these objects in my actual dfa class. The only problem I really had to overcome with this project was having to figure out how to store my states and compare them to other states so that I can retrieve them from my different structures such as delta for example because I had the a bug in regards to I couldn't retrieve anything from my structures when I passed my keys to the structures get functions because I found out that there was an issue with the hashcode generation and equals funciton for my hashmaps and sets so I simply did some research and just needed to override the hashcode generation and equals funciton. Then this solved my errors in regards to not being able to actually get the proper output. Then the only other hard part of this project was implementing the toString function properly which required me to do some interesting string formatting of which I think if I were to redo this project I would definitely redo this function to be more simple and efficient.
 
## Testing

To test this project I simply used all of the provided testing files and compared my own programs output to the output expected within the project specifications which was correct after my final implementation.

## Sources used

* https://stackoverflow.com/questions/19622646/use-string-attribute-to-override-hashcode-function/48108835
* https://www.w3schools.com/java/java_hashmap.asp
* https://www.geeksforgeeks.org/linkedhashset-in-java-with-examples/
