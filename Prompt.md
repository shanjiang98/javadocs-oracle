<context>

You are a software testing engineer. You will be provided with Java method description in java.lang.Object class, and your task is to find all features in method descriptions and generate test oracles for all features one by one. You don't need to generate the whole test cases, just focus on test oracles.

</context>

<examples>

<description>

public boolean equals(Object obj)
Indicates whether some other object is "equal to" this one.
The equals method implements an equivalence relation on non-null object references:

It is reflexive: for any non-null reference value x, x.equals(x) should return true.
It is symmetric: for any non-null reference values x and y, x.equals(y) should return true if and only if y.equals(x) returns true.
It is transitive: for any non-null reference values x, y, and z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) should return true.
It is consistent: for any non-null reference values x and y, multiple invocations of x.equals(y) consistently return true or consistently return false, provided no information used in equals comparisons on the objects is modified.
For any non-null reference value x, x.equals(null) should return false.
An equivalence relation partitions the elements it operates on into equivalence classes; all the members of an equivalence class are equal to each other. Members of an equivalence class are substitutable for each other, at least for some purposes.

</description>

<oracle>

For reflexive, the test oracle is:

boolean checkReflexive(Object x) {
     return x != null ? x.equals(x) : true;

}

</oracle>

</examples>

<instruction>

Use the following step-by-step method to generate test oracles. Remember that you need to generate a test oracle that returns a boolean value rather than an entire test case that can be executed. If necessary, you can use the try catch structure in test oracles to catch exception. Test oracles may require some input, you need to determine the input as well, most time the input should be same as class type. No matter in which cases, still return a boolean to indicate whether the feature is satisfied.

Step 1 - Find all features in the Java method description.

Step 2 - Generate test oracles for each of these features.

This is the Java method description you need to deal with:

<context>

You are a software testing engineer. You will be provided with Java method description in java.util.List class, and your task is to find all features in method descriptions and generate test oracles for all features one by one. You don't need to generate the whole test cases, just focus on test oracles.

</context>

<examples>

<description>

public boolean equals(Object obj)
Indicates whether some other object is "equal to" this one.
The equals method implements an equivalence relation on non-null object references:

It is reflexive: for any non-null reference value x, x.equals(x) should return true.
It is symmetric: for any non-null reference values x and y, x.equals(y) should return true if and only if y.equals(x) returns true.
It is transitive: for any non-null reference values x, y, and z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) should return true.
It is consistent: for any non-null reference values x and y, multiple invocations of x.equals(y) consistently return true or consistently return false, provided no information used in equals comparisons on the objects is modified.
For any non-null reference value x, x.equals(null) should return false.
An equivalence relation partitions the elements it operates on into equivalence classes; all the members of an equivalence class are equal to each other. Members of an equivalence class are substitutable for each other, at least for some purposes.

</description>

<oracle>

For reflexive, the test oracle is:

boolean checkReflexive(Object x) {
     return x != null ? x.equals(x) : true;

}

</oracle>

</examples>

<instruction>

Use the following step-by-step method to generate test oracles. Remember that you need to generate a test oracle that returns a boolean value rather than an entire test case that can be executed. If necessary, you can use the try catch structure in test oracles to catch exception. Test oracles may require some input, you need to determine the input as well, most time the input should be same as class type. No matter in which cases, still return a boolean to indicate whether the feature is satisfied.

Step 1 - Find all features in the Java method description.

Step 2 - Generate test oracles for each of these features.

This is the Java method description you need to deal with:



</instruction>
