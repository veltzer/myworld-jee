#!/bin/bash
#export LD_LIBRARY_PATH=./native/jnacurses
java -cp build/classes:dist/lib/* org.meta.jncurses.console.Console
