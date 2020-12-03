# COMP 250: Assignment 3 - Grand Unified Tester v1.2.1
This repository is meant to act as a bundle of
several tools and testers others have 
shared over the past days and weeks. 
 
Includes:

- Dog.java, and the course-provided template for DogShelter.java - replace this with your own code! 
- [DViz.java](https://github.com/meow10811/C250_Assignment3_Debugger) and related files
    * meow10811's rework of TheBigSasha's tree visualization debugger for last year
- [ZTestCode.java](https://github.com/Lprob1/Assignment3_COMP250)
    * Lprob1's test cases
- TreePrinter.java
    * Prints a binary tree - you just need to temporarily make DogNode implement PrintableNode - just make sure you
      reverse it after!
    * from [michal.kreuzman](https://stackoverflow.com/a/4973083) on StackOverflow
- SpeedTester.java
    * speedtesting functions by huubdejong
    * Requires TheBigSasha's [RuntimeTester](https://github.com/TheBigSasha/RuntimeTester)
- __M E G A T E S T E R !__ Includes **40** tests, from:
    * [jamesxu123's test cases](https://github.com/jamesxu123/COMP-250-A3-Tests)
    * [meow10811's Test Suite](https://github.com/meow10811/AdoptionShelterTestSuite)
    * Alex P's test cases
    * Minitester tests, modified to use JUnit Assertions so they can be run along with all the other tests in IntelliJ
    * a couple other tester methods

    
and more!

## Installation
IntelliJ
1. Click "Get from version control..."
2. Select a local directory and paste the URL of this project

Eclipse
  1. Select "Git -> Projects from Git (with smart import)"
  2. Select "Clone URI"
  3. Paste the URL of this project into "URI"
  4. Select the "master" branch

## Usage

Now should support Eclipse! You should be able to import the project as described above and run Megatester.java by left-clicking.
All other instructions are generally written with IntelliJ in mind, so yeah if things aren't working with Eclipse try IntelliJ.

IntelliJ recommendation: To regain your console output, go to Preferences -> Build, Execution, Deployment -> Build Tools -> Gradle and switch both "Build and run using" and "Run tests using" to "IntelliJ IDEA". (There's probably a better way of dealing with this but I sure don't know it so if you have any tips feel free to let me know please)

* First of all, add your DogShelter code in place of the blank/template DogShelter file.
* If using Eclipse and nothing works, try right clicking the folder `test.java.assignment3` and selecting Refactor > Rename, renaming it to `test.assignment3` without the `java`.
* DViz:
    * Run `DViz.java`.
* __M E G A T E S T E R__:
    * Test cases:
      * (IntelliJ) Right click the folder `assignment3` under `src/test/java` and click "Run Tests in 'assignment3", or
          select "Run..." on Megatester.java through the menu or though Alt-Shift-F10 (or the Mac equivalent Ctrl-R),
          and select the one with orange and green arrows
      * (Eclipse) Open Megatester.java and find a @Test annotation - hover over this and then select "Add JUnit 5 library to the build path". Then, right click your project (likely `COMP_250_A3_W2020...`) in the left sidebar and select Configure > Add Gradle Nature.
      * Alternatively to using the above approaches, uncomment out the lines in `main()` and run the file manually. You may have to add "static" to the method headers.
* SpeedTester:
    * Follow the instructions on [this page](https://github.com/TheBigSasha/RuntimeTester) to download RuntimeTester.jar.
    * (IntelliJ) Go to File > Project Structure > Project Settings > Libraries > '+' and navigate to the location of RuntimeTester.jar.
    * (Eclipse) Go to Project > Properties > Java Build Path > Libraries > Modulepath > Add External JARs and navigate to the location of RuntimeTester.jar.
    * Modify `build.gradle` to point to the location of your RuntimeTester.jar file. I recommend making a folder called `libs/` in your project root and putting the .jar there, for simplicity's sake. 
    Alternatively, find and copy the full file path of your RuntimeTester.jar file and replace the string passed to testImplementation files() in the dependencies within build.gradle.
    * Run `SpeedTester.java`. 
* TreePrinter
    * Make DogNode implement PrintableNode, including its 3 methods. (remove these changes after!)
    * Call `TreePrinter.print(shelter.root)` on the root of the desired shelter.
* Lprob1's tests
    * Run ZTestCode.java like you would normally. These tests don't use JUnit - Lprob1 intended them to be such that
      you determine whether or not the results are correct based on what's printed to the console.

## Contributing
Feel free to fork and make a pull request if you have any tests you think could be added!
