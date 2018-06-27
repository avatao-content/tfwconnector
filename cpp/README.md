# TFWConnector - C++

This is a short desciption about how to use C++ TFWConnector.

## Requirements

The JSON parsing of this project uses the `boost` library. To install that library, run `apt-get install libboost-dev` command. This project also has a dependency for dynamic libraries called libzmq and cppzmq. To install them, simply use these snippets:

libzmq:
```text
git clone https://github.com/zeromq/libzmq.git
cd libzmq
mkdir build
cd build
cmake ..
make install
```

cppzmq:
```text
git clone https://github.com/zeromq/cppzmq.git
cd cppzmq
mkdir build
cd build
cmake ..
make install
```

## How to build

When you compile the files, you have to depend on libzmq:
**g++:**
```text
-lzmq
```

**cmake:**
```text
find_package(cppzmq REQUIRED)
include_directories(${cppzmq_INCLUDE_DIR})
```

## Version

This project uses C++11 elements, it is the only project requirement.