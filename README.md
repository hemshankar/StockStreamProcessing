Solution
=====

Pre-req 
------
1. Maven
2. Java

Steps to run:
------------
1. Maven: make sure path contains mvn (e.g. export $PATH=$PWD/apache-maven-3.3.9/bin:$PATH)
5. JAVA_HOME environment variable is set (e.g. export JAVA_HOME="$PWD/Java/jdk1.8.0_111/")
6. clone git repo
7. cd to folder that contains pom.xml
8. run `mvn clean install`
9. run `java -jar target\structure_hem-1.0-SNAPSHOT.jar`
10. All supported symbols -> `http://localhost:8080/symbols`
11. Details of specific symbol -> `http://localhost:8080/adxbusd`
    Output:
     `{
    "name": "engbtc",
    "total": 0,
    "median": 0,
    "mrp": 0
    }`