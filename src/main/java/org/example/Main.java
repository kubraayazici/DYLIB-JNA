package org.example;

import com.sun.jna.*;
import java.util.Arrays;
import java.util.List;

public class Main {
    public interface PrintLibrary extends Library {
        PrintLibrary INSTANCE = Native.load("printLib", PrintLibrary.class);
        void printString(String str);
    }

    public interface SumLibrary extends Library {
        SumLibrary INSTANCE = Native.load("sumLib", SumLibrary.class);
        int sum(int a, int b);
    }

    public interface SendStructLibrary extends Library {
        SendStructLibrary INSTANCE = Native.load("sendStrc", SendStructLibrary.class);

        // Define a method declaration that matches the C++ function
        double sendStruct(StructArrLibrary.ByReference val);
    }

    // Define a Java structure to match the layout of the C++ struct
    public static class StructArrLibrary extends com.sun.jna.Structure {
        public static class ByReference extends StructArrLibrary implements com.sun.jna.Structure.ByReference {}

        public int numVals;
        public Pointer vals;

        // Define the field order to match the C++ struct
        @Override
        protected java.util.List<String> getFieldOrder() {
            return java.util.Arrays.asList("numVals", "vals");
        }
    }

    public static void main(String[] args) {
        System.setProperty("jna.library.path", "/Users/sunbul/Documents/Workspace/Projects/DllJna/src/main/java/org/example");

        PrintLibrary printLibrary = PrintLibrary.INSTANCE;
        SumLibrary sumLibrary = SumLibrary.INSTANCE;
        SendStructLibrary library = SendStructLibrary.INSTANCE;

        int result = sumLibrary.sum(5, 7);
        System.out.println("Sum result: " + result);

        printLibrary.printString("Hello from Java!");

        // Create a Java structure and populate it with data
        StructArrLibrary.ByReference struct = new StructArrLibrary.ByReference();
        struct.numVals = 3;
        // Allocate memory for the array of double values
        Memory valsMemory = new Memory(struct.numVals * Native.getNativeSize(Double.TYPE));

        // Populate the array with values (for example)
        double[] values = {1.0, 2.0, 8.6986};
        valsMemory.write(0, values, 0, values.length);

        // Set the vals field to point to the allocated memory
        struct.vals = valsMemory;
        // Call the C++ function
        double sum = library.sendStruct(struct);
        System.out.println("Sum of values: " + sum);

    }
}