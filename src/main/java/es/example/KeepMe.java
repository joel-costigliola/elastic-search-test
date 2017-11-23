/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2017 the original author or authors.
 */
package es.example;

public class KeepMe {
	// just here to avoid the test to fail as BootstrapForTesting calls checkJarHell.checkJarHell which expects these directories to exist:
	// - elastic-search-test/build/classes/test/
	// - elastic-search-test/build/resources/test
	// - elastic-search-test/build/classes/main
	// - elastic-search-test/build/resources/main

	// to get that in gradle, one need to put something in
	// - src/main/java
	// - src/main/resources
	// - src/test/java
	// - src/test/resources

	// here's the type of error you might have:

	//@formatter:off
//	com.orchestral.sdp.opentrace.test.integration.OpenTraceSearchIntegrationTest > classMethod FAILED
//	10:06:38.786 [DEBUG] [TestEventLogger]     java.lang.RuntimeException: found jar hell in test classpath
//	10:06:38.786 [DEBUG] [TestEventLogger]         at org.elasticsearch.bootstrap.BootstrapForTesting.<clinit>(BootstrapForTesting.java:91)
//	10:06:38.786 [DEBUG] [TestEventLogger]         at org.elasticsearch.test.ESTestCase.<clinit>(ESTestCase.java:175)
//	10:06:38.786 [DEBUG] [TestEventLogger]         at java.lang.Class.forName0(Native Method)
//	10:06:38.787 [DEBUG] [TestEventLogger]         at java.lang.Class.forName(Class.java:348)
//	10:06:38.787 [DEBUG] [TestEventLogger]         at com.carrotsearch.randomizedtesting.RandomizedRunner$2.run(RandomizedRunner.java:592)
//	10:06:38.787 [DEBUG] [TestEventLogger]
//	10:06:38.787 [DEBUG] [TestEventLogger]         Caused by:
//	10:06:38.787 [DEBUG] [TestEventLogger]         java.nio.file.NoSuchFileException: /home/joelco/orion/smart-data-platform/elastic-search-test/build/classes/main
//	10:06:38.787 [DEBUG] [TestEventLogger]             at sun.nio.fs.UnixException.translateToIOException(UnixException.java:86)
//	10:06:38.787 [DEBUG] [TestEventLogger]             at sun.nio.fs.UnixException.rethrowAsIOException(UnixException.java:102)
//	10:06:38.787 [DEBUG] [TestEventLogger]             at sun.nio.fs.UnixException.rethrowAsIOException(UnixException.java:107)
//	10:06:38.787 [DEBUG] [TestEventLogger]             at sun.nio.fs.UnixFileAttributeViews$Basic.readAttributes(UnixFileAttributeViews.java:55)
//	10:06:38.788 [DEBUG] [TestEventLogger]             at sun.nio.fs.UnixFileSystemProvider.readAttributes(UnixFileSystemProvider.java:144)
//	10:06:38.788 [DEBUG] [TestEventLogger]             at sun.nio.fs.LinuxFileSystemProvider.readAttributes(LinuxFileSystemProvider.java:99)
//	10:06:38.788 [DEBUG] [TestEventLogger]             at java.nio.file.Files.readAttributes(Files.java:1737)
//	10:06:38.788 [DEBUG] [TestEventLogger]             at java.nio.file.FileTreeWalker.getAttributes(FileTreeWalker.java:219)
//	10:06:38.788 [DEBUG] [TestEventLogger]             at java.nio.file.FileTreeWalker.visit(FileTreeWalker.java:276)
//	10:06:38.788 [DEBUG] [TestEventLogger]             at java.nio.file.FileTreeWalker.walk(FileTreeWalker.java:322)
//	10:06:38.788 [DEBUG] [TestEventLogger]             at java.nio.file.Files.walkFileTree(Files.java:2662)
//	10:06:38.788 [DEBUG] [TestEventLogger]             at java.nio.file.Files.walkFileTree(Files.java:2742)
//	10:06:38.788 [DEBUG] [TestEventLogger]             at org.elasticsearch.bootstrap.JarHell.checkJarHell(JarHell.java:201)
//	10:06:38.788 [DEBUG] [TestEventLogger]             at org.elasticsearch.bootstrap.JarHell.checkJarHell(JarHell.java:90)
//	10:06:38.789 [DEBUG] [TestEventLogger]             at org.elasticsearch.bootstrap.BootstrapForTesting.<clinit>(BootstrapForTesting.java:89)
//	10:06:38.789 [DEBUG] [TestEventLogger]             ... 4 more
//	10:06:38.789 [ERROR] [system.err] 10:06:38.789 [ERROR] [system.err]
//	10:06:38.790 [DEBUG] [TestEventLogger]
//	10:06:38.790 [ERROR] [system.err] Exception in thread "Thread-4" java.lang.NoClassDefFoundError: Could not initialize class org.elasticsearch.test.ESTestCase
//	10:06:38.790 [DEBUG] [TestEventLogger] com.orchestral.sdp.opentrace.test.integration.OpenTraceSearchIntegrationTest FAILED
//	10:06:38.790 [ERROR] [system.err] 10:06:38.790 [ERROR] [system.err]     at java.lang.Thread.run(Thread.java:745)
//	10:06:38.791 [ERROR] [system.err] 10:06:38.791 [ERROR] [system.err]     Suppressed: java.lang.IllegalStateException: No context information for thread: Thread[id=15, name=Thread-4, state=RUNNABLE, group=TGRP-OpenTraceSearchIntegrationTest]. Is this thread running under a class com.carrotsearch.randomizedtesting.RandomizedRunner runner context? Add @RunWith(class com.carrotsearch.randomizedtesting.RandomizedRunner.class) to your test class. Make sure your code accesses random contexts within @BeforeClass and @AfterClass boundary (for example, static test class initializers are not permitted to access random contexts).
//	10:06:38.791 [ERROR] [system.err] 10:06:38.791 [ERROR] [system.err]             at com.carrotsearch.randomizedtesting.RandomizedContext.context(RandomizedContext.java:248)
//	10:06:38.791 [ERROR] [system.err] 10:06:38.791 [ERROR] [system.err]             at com.carrotsearch.randomizedtesting.RandomizedContext.current(RandomizedContext.java:134)
//	10:06:38.790 [DEBUG] [TestEventLogger]
//	10:06:38.792 [ERROR] [system.err] 10:06:38.792 [ERROR] [system.err]             at com.carrotsearch.randomizedtesting.RandomizedRunner.augmentStackTrace(RandomizedRunner.java:1848)
//	10:06:38.793 [ERROR] [system.err] 10:06:38.792 [ERROR] [system.err]             at com.carrotsearch.randomizedtesting.RunnerThreadGroup.uncaughtException(RunnerThreadGroup.java:20)
//	10:06:38.793 [ERROR] [system.err] 10:06:38.793 [ERROR] [system.err]             at java.lang.Thread.dispatchUncaughtException(Thread.java:1952)
//	10:06:38.792 [DEBUG] [TestEventLogger] Gradle Test Executor 1 FAILED

}
