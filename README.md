# COMP 250: Assignment 3 - Grand Unified Tester v1.1
This repository is meant to act as a bundle of
several tools and testers others have 
shared over the past days and weeks. 

Includes:

- Dog.java, and the course-provided template for DogShelter.java - replace this with your own code!
    * 
- [DViz.java](https://github.com/meow10811/C250_Assignment3_Debugger) and related files
    * meow10811's rework of TheBigSasha's tree visualization debugger for last year
- [ZTestCode.java](https://github.com/Lprob1/Assignment3_COMP250)
    * Lprob1's test cases
- TreePrinter.java
    * Prints a binary tree - you just need to temporarily make DogNode implement
      PrintableNode - just make sure you reverse it after!
    * from [michal.kreuzman](https://stackoverflow.com/a/4973083) on StackOverflow
 - __M E G A T E S T E R !__ Includes:
   * SpeedTester.java
     * speedtesting functions by huubdejong 
     * Requires TheBigSasha's [RuntimeTester](https://github.com/TheBigSasha/RuntimeTester)
   * [DogShelterTest.java](https://github.com/jamesxu123/COMP-250-A3-Tests)
     * jamesxu123's test cases as well as a couple other tester methods
   * Minitester tests, modified to use JUnit Assertions so they can be run along with all the other tests in IntelliJ
    
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
All instructions are written with IntelliJ in mind; also not sure about JUnit's compatibility with Eclipse out-of-the-box, so yeah try IntelliJ.
* First of all, add your DogShelter code in place of the blank/template DogShelter file.
* DViz: 
    * Run `DViz.java`.
* __M E G A T E S T E R__:
    * Test cases:
       * (In IntelliJ) right click the folder `assignment3` under `src` and click "Run Tests in 'assignment3", or select "Run..." on Megatester.java through the menu or though Alt-Shift-F10 (or the Mac equivalent), and select the one with orange and green arrows
       * Alternatively, comment out `Visualizer.launch(Megatester.java)` in `main()`, uncomment the rest of `main()`, and run Megatester.java like you normally would
     * SpeedTester: 
       * Follow the instructions on [this page](https://github.com/TheBigSasha/RuntimeTester) to download RuntimeTester.jar and add it to your project.
       * Run Megatester.java like you normally would
* TreePrinter
    * Make DogNode implement PrintableNode, including its 3 methods. (remove this after!)
    * Call `TreePrinter.print(shelter.root)` on the root of the desired shelter.
* Lprop1's tests
    * Run ZTestCode.java like you would normally. These tests don't user JUnit - Lprob1 intended them to be such that you determine whether or not the results are correct based on what's printed to the console.

## Contributing
Feel free to fork and make a pull request if you have any tests you think could be added!
