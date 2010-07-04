#!/bin/sh

export LD_LIBRARY_PATH=$PWD/rlib:$LD_LIBRARY_PATH
export PATH=$PWD/rlib:$PATH
java -jar dist/meta.jar
