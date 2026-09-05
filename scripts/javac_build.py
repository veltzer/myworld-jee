#!/usr/bin/env python

""" Compile the application and its tests, replacing the NetBeans/ant build.

The NetBeans project resolved its classpath from jars the Debian packages
install under /usr/share/java plus the jars checked in under libs/jars, and
compiled src/main/java with -Xlint:unchecked; tests compiled against the
result plus junit. This does the same into out/. """

import glob
import os
import subprocess
import sys

SYSTEM_JARS = [
    "commons-io", "commons-lang", "jna", "trove", "gnu-getopt", "trilead-ssh2",
    "tritonus_alsa", "tritonus_aos", "tritonus_cdda", "tritonus_core",
    "tritonus_dsp", "tritonus_esd", "tritonus_fluidsynth", "tritonus_gsm",
    "tritonus_javasequencer", "tritonus_mp3", "tritonus_pvorbis",
    "tritonus_remaining", "tritonus_share", "tritonus_src", "tritonus_vorbis",
    "mp3spi", "jl",
]
JAVA_DIR = "/usr/share/java"
MAIN_OUT = os.path.join("out", "classes")
TEST_OUT = os.path.join("out", "test-classes")


def run(args):
    """ run a command, aborting the build on failure """
    ret = subprocess.call(args)
    if ret != 0:
        sys.exit(ret)


def classpath(extra):
    """ the jars the sources compile against """
    jars = [os.path.join(JAVA_DIR, f"{name}.jar") for name in SYSTEM_JARS]
    jars += sorted(glob.glob(os.path.join("libs", "jars", "*.jar")))
    missing = [jar for jar in jars if not os.path.exists(jar)]
    if missing:
        sys.exit(f"missing jars (install the packages in rsconstruct.toml): {missing}")
    return os.pathsep.join(jars + extra)


def sources(root):
    """ every java file under root """
    return sorted(glob.glob(os.path.join(root, "**", "*.java"), recursive=True))


def main():
    """ main entry point """
    os.makedirs(MAIN_OUT, exist_ok=True)
    run(["javac", "-Xlint:unchecked", "-cp", classpath([]), "-d", MAIN_OUT]
        + sources(os.path.join("src", "main", "java")))
    os.makedirs(TEST_OUT, exist_ok=True)
    junit = os.path.join(JAVA_DIR, "junit4.jar")
    run(["javac", "-Xlint:unchecked", "-cp", classpath([MAIN_OUT, junit]),
         "-d", TEST_OUT] + sources(os.path.join("src", "test", "java")))


if __name__ == "__main__":
    main()
