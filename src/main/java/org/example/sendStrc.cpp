#include <iostream>

// Define a struct in C++ that matches the layout of your Java struct
struct StructArrLibrary {
    int numVals;
    double *vals;
};

// Define the sendStruct function with extern "C"
extern "C" double sendStruct(StructArrLibrary *val) {
    double sum = 0.0;

    for (int i = 0; i < val->numVals; i++) {
        sum += val->vals[i];
    }
    return sum;
}
