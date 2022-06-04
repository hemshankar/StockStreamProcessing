Solution
=====

Pre-req
------
1. Maven
2. Java

Steps to run:
------------
1. Maven: make sure path contains mvn (e.g. export $PATH=$PWD/apache-maven-3.3.9/bin:$PATH)
2. JAVA_HOME environment variable is set (e.g. export JAVA_HOME="$PWD/Java/jdk1.8.0_111/")
3. clone git repo
4. cd to folder that contains pom.xml
5. run `mvn clean install`
6. run `java -jar target\structure_hem-1.0-SNAPSHOT.jar`
7. All supported symbols -> `http://localhost:8080/symbols`
8. Details of specific symbol -> `http://localhost:8080/adxbusd`
   Output:
   `{
   "name": "engbtc",
   "total": 0,
   "median": 0,
   "mrp": 0
   }`


Approach
=======

Trie for no collision hashing
-----
Using Trie, we can give a unique index to each symbol.
Since to calculate a hash a String all the characters of a string needs to be processed, the complexity of this approach is same as that of hashing i.e. O(N) where N = length of String.
And we avoid any collision, and save space.


Min/Max Priority Queues for Infinite Median
----
Idea is to use a
1. maxHeap: containing all the elements smaller than or equal to Median
2. minHeap: containing all the elements greater than Median

So when the new element arrives based on its value it is either pushed in the maxPQ (left) or minPQ(right), and the priority queues are adjusted such that both the queues are almost equal.