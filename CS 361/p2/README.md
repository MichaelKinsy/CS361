# Project 2: Nondeterministic Finite Automata

* Author: Michael Kinsy
* Class: CS361 Section 2
* Semester: Fall 2021

## Overview

This java program uses models given NFA's and converts them to DFA's

## Compiling and Using

To compile, execute the following command in the main project directory:
$ javac fa/nfa/NFADriver.java

Run the compiled class with the command:
$ java fa.nfa.NFADriver ./tests/p2tc[Insert 0-3].txt

## Discussion

This project was relatively straight forward to implement with most of the critical concepts being in setting up my getDFA function properly and the data structures that stored and converted objects from my actual nfa class into a dfa object. A lot of my issue was mostly trying to understand how to use the eTransistions correctly as I was traversing the NFA and actually generating new states and transitions for my DFA. I found a lot of inspiration solving this problem once I found the source I listed below as I was able to more concisely understand how the breadth first search was effective for converting my nfa into a dfa. Then once I had the sturcture for this all I needed to do was call my eClosure method on all of the states for a given new transition which also made sense why that was done in a depth first manner. I think that one thing I would have liked to done better on is messing with the ordering of my states in my sets so that when I print them out as a dfa they are prettier. But all in all I thought that this project was actually quite enjoyable.

## Sources used
- https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/
