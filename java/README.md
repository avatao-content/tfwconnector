# TFWConnector - Java

This is a short desciption about how to use Java TFWConnector.

## Requirements

The java package requirements can be found in the pom.xml of the project root. This project also has a dependency for dynamic libraries called libzmq and libjzmq. To install them, simply use these snippets:

libzmq:
```text
git clone https://github.com/zeromq/libzmq.git
cd libzmq
mkdir build
cd build
cmake ..
make install
```

libjzmq:
```text
git clone https://github.com/zeromq/jzmq.git
cd jzmq/jzmq-jni
./autogen.sh
./configure
make
make install
```
When you run the final jar file of your project, do not forget to use `-Djava.library.path=/usr/local/lib/` option to access the dynamic library of ZMQ.

## How to build

There are two main options to build this package:
  1. Use `mvn clean compile assembly:single` command to build it with every single dependency into a fat jar file, then use it as a classpath.
  2. You can add this maven project to another maven project as dependency. 

## What is important?

The TFWConnector uses the `ObjectNode` class to parse JSON messages. You have to import first Jackson to use it. The Connector has connect()-close() methods. To use it, you have to connect() first, but never forget to use close() in the end of your operations.