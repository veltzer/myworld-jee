#!/bin/sh

export LD_LIBRARY_PATH=$PWD/rlib:$LD_LIBRARY_PATH
MAIN_CLASS=net.veltzer.examples.JnotifyListenerImpl
MAIN_CLASS=net.veltzer.jdmt.Jdmt
MAIN_CLASS=net.veltzer.readline.Readline
java -cp classes:rlib/* $MAIN_CLASS
