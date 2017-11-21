# Elastic search java test framework examples

The Elastic search lacks some working examples for their [test framework](https://www.elastic.co/guide/en/elasticsearch/reference/current/testing-framework.html). This project tries to fill the gap and provides some.

The setup is not as easy as it looks because the test bootstrapping requires to relax security constraints and is really picky about the classpath (which is a good thing but it fails to report helpful errors unless you execute the build in debug mode).

## gradle


We need to pass `-Dtests.security.manager=false` for the test framework bootstrapping to work.

```
./gradlew clean test -Dtests.security.manager=false
```


## maven

Maven build works out of the box, just run `mvn clean test`.
Note that the `-Dtests.security.manager=false` has been added to the surefire plugin configuration for the tests to pass.

Tested with:
```
~/ ∙ mvn --version
Apache Maven 3.5.2 (138edd61fd100ec658bfa2d307c43b76940a5d7d; 2017-10-18T20:58:13+13:00)
Maven home: /home/joelco/prog/apache-maven-3.5.2
Java version: 1.8.0_66, vendor: Oracle Corporation
Java home: /home/joelco/prog/java/jdk-1-8-66/jre
Default locale: en_NZ, platform encoding: UTF-8
OS name: "linux", version: "4.4.0-98-generic", arch: "amd64", family: "unix"
```
