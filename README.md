# COMP 250: Assignment 3 - Grand Unified Tester
This repository is meant to act as a bundle of
the following tools and testers others have 
shared over the past days and weeks. 

Includes:

- Dog.java
- Minitester.java
- SpeedTester.java
    * speedtesting functions written by me, which means they probably have issues - 
    definitely modify and add your own! (Feel free to submit a PR so I can add them as well!)
    * Requires TheBigSasha's [RuntimeTester](https://github.com/TheBigSasha/RuntimeTester)
- [DViz.java](https://github.com/meow10811/C250_Assignment3_Debugger) and related files
    * meow10811's rework of TheBigSasha's tree visualization debugger for last year
- [ZTestCode.java](https://github.com/Lprob1/Assignment3_COMP250)
    * Lprob1's test cases
- [DogShelterTest.java](https://github.com/jamesxu123/COMP-250-A3-Tests)
    * jamesxu123's test cases as well as a couple other tester methods 
- TreePrinter.java
    * Prints a binary tree - you just need to temporarily make DogNode implement
      PrintableNode - just make sure you reverse it after!
    * from [michal.kreuzman](https://stackoverflow.com/a/4973083) on StackOverflow
    
and more!

##Installation
IntelliJ
1. Click "Get from version control..."
2. Select a local directory and paste the URL of this project

Eclipse
  1. Select "Git -> Projects from Git (with smart import)"
  2. Select "Clone URI"
  3. Paste the URL of this project into "URI"
  4. Select the "master" branch

##Usage
* DViz: 
    * Run DViz.java.
* Test cases:
    * (In IntelliJ) right click the folder assignment3 under src and click "Run Tests in 
    'assignment3".
* SpeedTester: 
    * Follow the instructions on 
    [this page](https://github.com/TheBigSasha/RuntimeTester)
    to download RuntimeTester.jar and add it to your project.
    * Run SpeedTester.java.
* TreePrinter.java
    * Make DogNode implement PrintableNode, including its 3 methods. (remove this after!)
    * Call `TreePrinter.print(shelter.root)` on the root of the desired shelter.

