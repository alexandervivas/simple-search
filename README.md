To run this program, you must have installed java 8 or later and sbt.

- Give the file `simple-search` permissions to execute like this: `chmod +x simple-search`
- Execute it as `./simple-search /Users/alexandervivas/git/alexandervivas/simple-search/src/test/resources/sample-directory`

If this is the first time this is being executed you'll see the program compile and execute unit tests before performing the operations. You could see an output like this:

```
[info] Loading global plugins from /Users/alexandervivas/.sbt/1.0/plugins
[info] Loading settings for project simple-search-build from plugins.sbt ...
[info] Loading project definition from /Users/alexandervivas/git/alexandervivas/simple-search/project
[info] Loading settings for project simple-search from build.sbt ...
[info] Set current project to simple-search (in build file:/Users/alexandervivas/git/alexandervivas/simple-search/)
[info] Updating ...
[info] Done updating.
[info] Compiling 1 Scala source to /Users/alexandervivas/git/alexandervivas/simple-search/target/scala-2.13/classes ...
[warn] there were 5 deprecation warnings (since 2.13.0); re-run with -deprecation for details
[warn] one warning found
[info] Done compiling.
[info] Compiling 1 Scala source to /Users/alexandervivas/git/alexandervivas/simple-search/target/scala-2.13/test-classes ...
[info] Including: scala-library-2.13.0.jar
[info] Done compiling.
ArraySeq(InMemoryFile(sample.txt,HashSet(test, is, This, a, file)))
[info] SimpleSearchTest:
[info] SimpleSearch
[info]   when no arguments present
[info]   - should produce an IllegalArgumentException
[info]   when there are arguments present
[info]   - should return false if there are more than one argument
[info]   - should return true if only one argument is present
[info]   - should is able to read the path in the arguments list
[info]   when a path is given
[info]   - should list all the files contained in that path
[info]   when a filename is given
[info]   - should read all the lines
[info]   - should convert all lines into InMemoryFile structures
[info] Run completed in 371 milliseconds.
[info] Total number of tests run: 7
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 7, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[info] Checking every *.class/*.jar file's SHA-1.
[info] Merging files...
[warn] Merging 'NOTICE' with strategy 'rename'
[warn] Merging 'LICENSE' with strategy 'rename'
[warn] Merging 'META-INF/MANIFEST.MF' with strategy 'discard'
[warn] Strategy 'discard' was applied to a file
[warn] Strategy 'rename' was applied to 2 files
[info] SHA-1: 11f724a1a29b4b0143620d9b643e232a836179e2
[info] Packaging /Users/alexandervivas/git/alexandervivas/simple-search/target/scala-2.13/simple-search-assembly-0.1.jar ...
[info] Done packaging.
[success] Total time: 7 s, completed Oct 11, 2019 6:38:21 PM
```

After this, you can see the search console, like this: 

```
1 files read in directory /Users/alexandervivas/git/alexandervivas/simple-search/src/test/resources/sample-directory
search> file
sample.txt:100%
search> :quit
```
