# TOFIX

Findings from a code scan on 2026-10-04.

## Medium

- `scripts/javac_build.py:55-58` - the build only compiles `src/test/java`; nothing ever runs the JUnit tests, so they cannot fail CI. Running them with JUnitCore shows 8 tests, 1 failure: `src/test/java/org/meta/jncurses/JncursesTest.java` needs the native `libjnacurses.so` (`src/main/java/org/meta/jncurses/Ncurses.java:15`), which is not in the repo or any declared package. Add a test step to the build and fix or remove the native-library test.
- `scripts/run_default.sh:3` - all `scripts/run_*.sh` launchers use the old NetBeans/ant outputs (`dist/meta.jar`, `build/classes:dist/lib/*`, e.g. `scripts/run_mp3tags.sh:2`), but the build now writes `out/classes` and no jar or `dist/lib` exists. Point them at `out/classes` plus the classpath `scripts/javac_build.py` computes (or emit a classpath file from the build).
- `src/main/java/org/meta/richfunction/impl/Tweet.java:41-61` - built on twitter4j 2.0.10 (`libs/jars/twitter4j-2.0.10.jar`), which talks to Twitter API v1 endpoints that were shut down years ago, with empty consumer key/secret in `src/main/java/org/meta/conf/Conf.java:30-33`; the command cannot work. Remove the command and the jar (and `libs/download/twitter4j-2.0.10.zip`) or port it to a current API.

## Low

- `src/main/java/org/meta/conf/Conf.java:35` - the Twitter token file is `"~/.twitter.token"`, and `Tweet.java:109,122` opens it with `FileOutputStream`/`FileInputStream`, which do not expand `~`; it resolves to a literal `./~/.twitter.token`. Use `System.getProperty("user.home")`. The same file hardcodes `/home/mark/links/...` at line 43.
- `scripts/netbeans.sh:2` - launches `/home/mark/install/netbeans-6.9`, a 2010 IDE install the README no longer mentions (the NetBeans build was replaced by `scripts/javac_build.py`); delete it.
- `libs/download/jetty-6.1.22.zip` - this ~27 MB `libs/download/` tree (jetty 6 zip, twitter4j zip, `mp3_examples/`) is used by neither the build nor the sources (the README only says `libs/` holds third-party jars); drop it, or document why it is kept.
- `src/test/java/org/meta/util/PakcageScannerTest.java` - typo in the test class/file name ("Pakcage"); rename to `PackageScannerTest`.
