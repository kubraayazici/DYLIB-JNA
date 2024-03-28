#include <stdio.h>

// Define a struct in C that matches the layout of your Java struct
typedef struct {
    int numVals;
    double *vals;
} StructArrLibrary;

double sendStructLib(StructArrLibrary *val) {
    double sum = 0.0;
    printf("**%d ",&val->numVals);
    printf("**%f ",(&val->vals));
    for (int i = 0; i < val->numVals; i++) {
        printf("%f ",val->vals[i]);
        sum += val->vals[i];
    }
    return sum;
}
