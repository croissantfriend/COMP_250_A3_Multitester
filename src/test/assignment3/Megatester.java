package assignment3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class Megatester {

    public static void main(String[] args) {
        // Uncomment these if IntelliJ/Gradle testing doesn't work
//        testShelter1();
//        testShelter2();
//        testShelter3();
//        testShelter4();
//        testAdopt1();
//        testAdopt2();
//        testAdopt3();
//        testFindOldest1();
//        testFindYoungest1();
//        testFindDogToAdopt1();
//        testBudgetVetExpenses1();
//        testGetVetSchedule1();
//        testGetVetSchedule2();
//        testDogShelterIterator1();
//        testDogShelterIterator2();
//        testDogShelterIterator3();
    }

    // James Xu's tests
    DogShelter generateSampleShelter() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog Z = new Dog("Z", 4, 200, 10, 25.0);
//        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
//        Dog B = new Dog("Bella", 1, 55, 15, 120.0);
//        Dog C = new Dog("Cloud", 4, 10, 23, 80.0);
//        Dog A = new Dog("Archie", 6, 120, 18, 40.0);
//        Dog D = new Dog("Daisy", 7, 15, 12, 35.0);
        DogShelter shelter = new DogShelter(R);
        shelter.shelter(S);
        shelter.shelter(L);
        shelter.shelter(Z);
        return shelter;
    }

    DogShelter generateSampleShelter2() {
        // From meow10811's Test Suite
        Dog M = new Dog("Max", 11, 0, 2, 100.0);

        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
        Dog B = new Dog("Bella", 1, 55, 15, 120.0);
        Dog C = new Dog("Cloud", 4, 10, 23, 80.0);
        Dog A = new Dog("Archie", 6, 120, 18, 40.0);
        Dog D = new Dog("Daisy", 7, 15, 12, 35.0);

        DogShelter shelter = new DogShelter(M);
        DogShelter.DogNode node = shelter.new DogNode(M);
        return shelter;
    }

    @Test
    void testShelter1() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);

        DogShelter test = new DogShelter(R);

        System.out.print("Testing shelter() #1 [0 tree rotation]... \n");
        test.shelter(S);
//        TreePrinter.print(test.root);
//        test.print();
        test.shelter(P);
//        TreePrinter.print(test.root);
//        test.print();
//        System.out.println(test.root);

//        int d = test.depthOfTree(test.root, 1);
//        //System.out.println(d);
//        test.printLevelOrder(test.root, d);

        StringBuilder errorBuilder = new StringBuilder();
        boolean checkDogs = test.root.d == R && test.root.younger.d == S && test.root.older.d == P;
        if (!checkDogs) {
            errorBuilder.append("Dogs are not assigned correctly. ");
        }
        boolean checkParents = test.root.younger.parent.d == R && test.root.older.parent.d == R;
        if (!checkParents) {
            errorBuilder.append("Parent pointers are not assigned correctly. ");
        }
        boolean checkNulls = test.root.parent == null &&
                test.root.younger.younger == null && test.root.younger.older == null &&
                test.root.older.younger == null && test.root.older.older == null;
        if (!checkNulls) {
            errorBuilder.append("Null values are not assigned correctly.");
        }
        if (!checkDogs || !checkParents || !checkNulls) {
            System.out.println(errorBuilder.toString());
        } else {
            System.out.println("Passed.\n");
        }
        Assertions.assertTrue(checkDogs && checkNulls && checkParents);
    }

    @Test
    void testShelter2() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog P2 = new Dog("Poldo 2", 7, 60, 1, 35.0);

        System.out.print("Testing shelter() #2 [1 (left / right) tree rotation]... \n");

        DogShelter test = new DogShelter(R);
        DogShelter.DogNode nodeS = test.new DogNode(S);
//        System.out.println();
//        TreePrinter.print(test.root);

        test.root.younger = nodeS;
        nodeS.parent = test.root;
//        TreePrinter.print(test.root);

        test.shelter(L);
//        TreePrinter.print(test.root);
        test.shelter(P2);
//        TreePrinter.print(test.root);

//        Iterator<Dog> iter = test.iterator();
//        while (iter.hasNext())
//            System.out.println(iter.next());

//        System.out.println("Starting foreach...");
//        for (Dog d : test) {
//            System.out.println(d);
//        }
//        System.out.println("Done foreach!");

        StringBuilder errorBuilder = new StringBuilder();
        boolean checkDogs = test.root.d == R && test.root.younger.d == L &&
                test.root.younger.older.d == P2 && test.root.younger.older.younger.d == S;
        if (!checkDogs) {
            errorBuilder.append("Dogs are not assigned correctly. ");
        }
        boolean checkParents = test.root.younger.parent.d == R && test.root.younger.older.parent.d == L &&
                test.root.younger.older.younger.parent.d == P2;
        if (!checkParents) {
            errorBuilder.append("Parent pointers are not assigned correctly. ");
        }
        boolean checkNulls = test.root.parent == null && test.root.older == null &&
                test.root.younger.younger == null &&
                test.root.younger.older.older == null &&
                test.root.younger.older.younger.younger == null && test.root.younger.older.younger.older == null;
        if (!checkNulls) {
            errorBuilder.append("Null values are not assigned correctly.");
        }
        if (!checkDogs || !checkParents || !checkNulls) {
            System.out.println(errorBuilder.toString());
        } else {
            System.out.println("Passed.\n");
        }
        Assertions.assertTrue(checkDogs && checkParents && checkNulls);
    }

    @Test
    void testShelter3() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);

        DogShelter test = new DogShelter(L);
        DogShelter.DogNode nodeS = test.new DogNode(S);
        test.root.older = nodeS;
        nodeS.parent = test.root;

        System.out.print("Testing shelter() #3 [2 (left) tree rotations]... \n");
//        TreePrinter.print(test.root);
        test.shelter(R);
//        TreePrinter.print(test.root);

        StringBuilder errorBuilder = new StringBuilder();
        boolean checkDogs = test.root.d == R && test.root.younger.d == L && test.root.younger.older.d == S;
        if (!checkDogs) {
            errorBuilder.append("Dogs are not assigned correctly. ");
        }
        boolean checkParents = test.root.younger.parent.d == R && test.root.younger.older.parent.d == L;
        if (!checkParents) {
            errorBuilder.append("Parent pointers are not assigned correctly. ");
        }
        boolean checkNulls = test.root.parent == null && test.root.older == null &&
                test.root.younger.younger == null &&
                test.root.younger.older.younger == null && test.root.younger.older.older == null;
        if (!checkNulls) {
            errorBuilder.append("Null values are not assigned correctly.");
        }
        if (!checkDogs || !checkParents || !checkNulls) {
            System.out.println(errorBuilder.toString());
        } else {
            System.out.println("Passed.\n");
        }
        Assertions.assertTrue(checkDogs && checkParents && checkNulls);
    }

    @Test
    void testShelter4() {
        Dog R2 = new Dog("Rex 2", 8, 70, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L2 = new Dog("Lessie 2", 3, 80, 9, 25.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);

        DogShelter test = new DogShelter(L2);
        DogShelter.DogNode nodeP = test.new DogNode(P);
        DogShelter.DogNode nodeS = test.new DogNode(S);
        test.root.older = nodeP;
        nodeP.parent = test.root;
        nodeP.younger = nodeS;
        nodeS.parent = nodeP;

        System.out.print("Testing shelter() #4 [2 (left + right) tree rotations]... \n");
//        TreePrinter.print(test.root);
        test.shelter(R2);
//        TreePrinter.print(test.root);

        StringBuilder errorBuilder = new StringBuilder();
        boolean checkDogs = test.root.d == L2 && test.root.older.d == R2 &&
                test.root.older.younger.d == S && test.root.older.older.d == P;
        if (!checkDogs) {
            errorBuilder.append("Dogs are not assigned correctly. ");
        }
        boolean checkParents = test.root.older.parent.d == L2 &&
                test.root.older.younger.parent.d == R2 && test.root.older.older.parent.d == R2;
        if (!checkParents) {
            errorBuilder.append("Parent pointers are not assigned correctly. ");
        }
        boolean checkNulls = test.root.parent == null && test.root.younger == null &&
                test.root.older.younger.younger == null && test.root.older.younger.older == null &&
                test.root.older.older.younger == null && test.root.older.older.older == null;
        if (!checkNulls) {
            errorBuilder.append("Null values are not assigned correctly.");
        }
        if (!checkDogs || !checkParents || !checkNulls) {
            System.out.println(errorBuilder.toString());
        } else {
            System.out.println("Passed.\n");
        }
        Assertions.assertTrue(checkDogs && checkParents && checkNulls);
    }

    @Test
    void testAdopt() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);

        DogShelter test = new DogShelter(R);
        DogShelter.DogNode ll = test.new DogNode(L);
        DogShelter.DogNode rr = test.new DogNode(R);
        DogShelter.DogNode ss = test.new DogNode(S);

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.younger.older = ss;
        test.root.younger.older.parent = ll;


        System.out.print("Testing adopt() #1... \n");
//        TreePrinter.print(test.root);
        test.adopt(R);
//        TreePrinter.print(test.root);

        boolean dogs = test.root.d == L && test.root.older.d == S;
        boolean nulls = test.root.parent == null && test.root.younger == null && test.root.older.younger == null && test.root.older.older == null;
        boolean parents = test.root.older.parent.d == L;

        if (!(dogs && nulls && parents)) {
            if (!dogs) System.out.println("Dogs are not assigned correctly");
            else if (!nulls) System.out.println("Null values are not assigned correctly");
            else if (!parents) System.out.println("Parent pointers are not assigned correctly");
        } else {
            System.out.println("Passed.\n");
        }
        Assertions.assertTrue(dogs && nulls && parents);
    }

    @Test
    void testAdopt0() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);

        DogShelter test = new DogShelter(R);
        DogShelter.DogNode ll = test.new DogNode(L);
        DogShelter.DogNode rr = test.new DogNode(R);
        DogShelter.DogNode ss = test.new DogNode(S);
        DogShelter.DogNode pp = test.new DogNode(P);

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.older = pp;
        test.root.older.parent = test.root;
        test.root.younger.older = ss;
        test.root.younger.older.parent = ll;

        System.out.print("Testing adopt() #2... \n");
////        TreePrinter.print(test.root);
        test.adopt(R);
//////        TreePrinter.print(test.root);

//        test.printInOrder(test.root);

        boolean dogs = test.root.d == L && test.root.older.d == P && test.root.older.younger.d == S;
        boolean nulls = test.root.parent == null && test.root.younger == null && test.root.older.older == null && test.root.older.younger.younger == null && test.root.older.younger.older == null;
        boolean parents = test.root.older.parent.d == L && test.root.older.younger.parent.d == P;

        if (!(dogs && nulls && parents)) {
            if (!dogs) System.out.println("Dogs are not assigned correctly");
            else if (!nulls) System.out.println("Null values are not assigned correctly");
            else if (!parents) System.out.println("Parent pointers are not assigned correctly");
        } else {
            System.out.println("Passed.\n");
        }
        Assertions.assertTrue(dogs && nulls && parents);
    }

    @Test
    @DisplayName("Should adopt root correctly")
    void testAdopt1() {
        DogShelter shelter = generateSampleShelter();
        Dog Z = new Dog("Z", 4, 200, 10, 25.0);
        Assertions.assertEquals(shelter.adopt(), Z);
    }

    @Test
    @DisplayName("Should adopt iteratively all dogs correctly")
    void testAdopt2() {
        DogShelter shelter = generateSampleShelter();
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog Z = new Dog("Z", 4, 200, 10, 25.0);
//        TreePrinter.print(shelter.root);
        Assertions.assertEquals(shelter.adopt(), Z);
//        TreePrinter.print(shelter.root);
        Assertions.assertEquals(shelter.adopt(), R);
//        TreePrinter.print(shelter.root);
        Assertions.assertEquals(shelter.adopt(), L);
//        TreePrinter.print(shelter.root);
        Assertions.assertEquals(shelter.adopt(), S);
//        TreePrinter.print(shelter.root);
    }

    @Test
    void testAdopt3() {
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
        Dog B = new Dog("Bella", 1, 55, 15, 120.0);
        Dog C = new Dog("Cloud", 4, 10, 23, 80.0);
        Dog A = new Dog("Archie", 6, 120, 18, 40.0);
        Dog D = new Dog("Daisy", 7, 15, 12, 35.0);

        DogShelter test = new DogShelter(A);

        DogShelter.DogNode ll = test.new DogNode(L);
        DogShelter.DogNode rr = test.new DogNode(R);
        DogShelter.DogNode ss = test.new DogNode(S);
        DogShelter.DogNode pp = test.new DogNode(P);
        DogShelter.DogNode bb = test.new DogNode(B);
        DogShelter.DogNode cc = test.new DogNode(C);
        DogShelter.DogNode aa = test.new DogNode(A);
        DogShelter.DogNode dd = test.new DogNode(D);

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.older = rr;
        test.root.older.parent = test.root;

        test.root.younger.younger = bb;
        test.root.younger.younger.parent = ll;
        test.root.younger.older = ss;
        test.root.younger.older.parent = ll;

        test.root.younger.older.younger = cc;
        test.root.younger.older.younger.parent = ss;

        test.root.older.younger = dd;
        test.root.older.younger.parent = rr;
        test.root.older.older = pp;
        test.root.older.older.parent = rr;

        System.out.print("Testing adopt() #3... \n");
////        TreePrinter.print(test.root);
        Dog a = test.adopt();
//        TreePrinter.print(test.root);

        boolean dogs = test.root.d == R && test.root.younger.d == L && test.root.older.d == P &&
                test.root.younger.younger.d == B && test.root.younger.older.d == S && test.root.younger.older.younger.d == C &&
                test.root.younger.older.older.d == D;

        boolean nulls = test.root.parent == null && test.root.older.younger == null && test.root.older.older == null &&
                test.root.younger.younger.younger == null && test.root.younger.younger.older == null &&
                test.root.younger.older.younger.younger == null && test.root.younger.older.younger.older == null &&
                test.root.younger.older.older.younger == null && test.root.younger.older.older.older == null;

        boolean parents = test.root.younger.parent.d == R &&
                test.root.older.parent.d == R &&
                test.root.younger.younger.parent.d == L &&
                test.root.younger.older.parent.d == L &&
                test.root.younger.older.younger.parent.d == S &&
                test.root.younger.older.older.parent.d == S;
        boolean ret = a == A;

        if (!(dogs && nulls && parents && ret)) {
            if (!dogs) System.out.println("Dogs are not assigned correctly");
            else if (!nulls) System.out.println("Null values are not assigned correctly");
            else if (!parents) System.out.println("Parent pointers are not assigned correctly");
            else if (!ret) System.out.println("The method returned incorrect value");
        } else {
            System.out.println("Passed.\n");
        }
        Assertions.assertTrue(dogs && nulls && parents && ret);
    }

    @Test
    @DisplayName("Should test adopt with all-right branches")
    void testAdopt4() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 10, 90, 2, 250.0);
        Dog L = new Dog("Lessie", 12, 80, 9, 25.0);
        DogShelter shelter = new DogShelter(R);
        shelter.shelter(S);
        shelter.shelter(L);

        Assertions.assertEquals(shelter.adopt(), R);
        Assertions.assertEquals(shelter.adopt(), S);
        Assertions.assertEquals(shelter.adopt(), L);
    }

    @Test
    @DisplayName("Should test adopt with all-left branches")
    void testAdopt5() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 90, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 80, 9, 25.0);
        DogShelter shelter = new DogShelter(R);
        shelter.shelter(S);
        shelter.shelter(L);

        Assertions.assertEquals(shelter.adopt(), R);
        Assertions.assertEquals(shelter.adopt(), S);
        Assertions.assertEquals(shelter.adopt(), L);
    }

    @Test
    void testFindOldest1() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 150, 0, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 1, 25.0);

        DogShelter test = new DogShelter(S);
        DogShelter.DogNode ll = test.new DogNode(L);
        DogShelter.DogNode rr = test.new DogNode(R);
        DogShelter.DogNode ss = test.new DogNode(S);

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.older = rr;
        test.root.older.parent = test.root;

        System.out.print("Testing findOldest() #1... ");

        Dog d = test.findOldest();


        if (!(d.equals(R))) {
            System.out.println("The dog found was not the oldest.");
        } else {
            System.out.println("Passed.\n");
        }
        Assertions.assertEquals(d, R, "The dog found was not the oldest.");
    }

    @Test
    void testFindYoungest1() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 0, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 1, 25.0);

        DogShelter test = new DogShelter(S);
        DogShelter.DogNode ll = test.new DogNode(L);
        DogShelter.DogNode rr = test.new DogNode(R);
        DogShelter.DogNode ss = test.new DogNode(S);

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.older = rr;
        test.root.older.parent = test.root;

        System.out.print("Testing findYoungest() #1... ");

        Dog d = test.findYoungest();


        if (!(d.equals(L))) {
            System.out.println("The dog found was not the youngest.");
        } else {
            System.out.println("Passed.\n");
        }
        Assertions.assertEquals(d, L, "The dog found was not the youngest.");
    }

    @Test
    void testFindDogToAdopt1() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 0, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 1, 25.0);

        DogShelter test = new DogShelter(R);
        DogShelter.DogNode ll = test.new DogNode(L);
        DogShelter.DogNode rr = test.new DogNode(R);
        DogShelter.DogNode ss = test.new DogNode(S);

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.younger.older = ss;
        test.root.younger.older.parent = ll;

        System.out.print("Testing findDogToAdopt() #1... ");

        Dog d = test.findDogToAdopt(3, 7);


        if (!(d.equals(L))) {
            if (!(d.getAge() <= 7 && d.getAge() >= 3)) {
                System.out.println("The dog found was not the correct age range.");
            }
            System.out.println("The dog found did not have the highest priority within the age range.");
        } else {
            System.out.println("Passed.\n");
        }
        Assertions.assertEquals(d, L, (!(d.getAge() <= 7 && d.getAge() >= 3) ? "The dog found was not the correct age range." :
                "The dog found did not have the highest priority within the age range."));
    }

    @Test
    void testBudgetVetExpenses1() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
        Dog B = new Dog("Bella", 1, 55, 15, 120.0);
        Dog C = new Dog("Cloud", 4, 10, 23, 80.0);
        Dog A = new Dog("Archie", 6, 120, 18, 40.0);
        Dog D = new Dog("Daisy", 7, 15, 12, 35.0);

        DogShelter test = new DogShelter(A);
        DogShelter.DogNode nodeL = test.new DogNode(L);
        DogShelter.DogNode nodeR = test.new DogNode(R);
        DogShelter.DogNode nodeB = test.new DogNode(B);
        DogShelter.DogNode nodeS = test.new DogNode(S);
        DogShelter.DogNode nodeD = test.new DogNode(D);
        DogShelter.DogNode nodeP = test.new DogNode(P);
        DogShelter.DogNode nodeC = test.new DogNode(C);
        test.root.younger = nodeL;
        nodeL.parent = test.root;
        nodeL.younger = nodeB;
        nodeB.parent = nodeL;
        nodeL.older = nodeS;
        nodeS.parent = nodeL;
        nodeS.younger = nodeC;
        nodeC.parent = nodeS;
        test.root.older = nodeR;
        nodeR.parent = test.root;
        nodeR.younger = nodeD;
        nodeD.parent = nodeR;
        nodeR.older = nodeP;
        nodeP.parent = nodeR;

        System.out.print("Testing budgetVetExpenses() #1... ");
        double result = test.budgetVetExpenses(19);

        double expected = 555.0;
        if (result != expected) {
            System.out.println("Incorrect amount of dollars (expected: " +
                    expected + ", received: " + result + ")");
        } else {
            System.out.println("Passed.\n");
        }
        Assertions.assertEquals(expected, result, "Incorrect amount of dollars");
    }

    @Test
    void testGetVetSchedule1() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 0, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 1, 25.0);

        DogShelter test = new DogShelter(R);
        DogShelter.DogNode ll = test.new DogNode(L);
        DogShelter.DogNode rr = test.new DogNode(R);
        DogShelter.DogNode ss = test.new DogNode(S);

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.younger.older = ss;
        test.root.younger.older.parent = ll;

        System.out.print("Testing getVetSchedule() #1... ");

        ArrayList<ArrayList<Dog>> ald = test.getVetSchedule();

        boolean arlSizes = ald.size() == 1 && ald.get(0).size() == 3;
        boolean arlDogs = ald.get(0).get(0) == L && ald.get(0).get(1) == S && ald.get(0).get(2) == R;


        if (!(arlSizes && arlDogs)) {
            if (!arlSizes) System.out.println("At least one ArrayList has wrong number of elements");
            else if (!arlDogs) System.out.println("Incorrect elements in ArrayList 0");
        } else {
            System.out.println("Passed.\n");
        }
        Assertions.assertTrue(arlSizes && arlDogs);
    }

    @Test
    void testGetVetSchedule2() {
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
        Dog B = new Dog("Bella", 1, 55, 15, 120.0);
        Dog C = new Dog("Cloud", 4, 10, 23, 80.0);
        Dog A = new Dog("Archie", 6, 120, 18, 40.0);
        Dog D = new Dog("Daisy", 7, 15, 12, 35.0);

        DogShelter test = new DogShelter(A);

        DogShelter.DogNode ll = test.new DogNode(L);
        DogShelter.DogNode rr = test.new DogNode(R);
        DogShelter.DogNode ss = test.new DogNode(S);
        DogShelter.DogNode pp = test.new DogNode(P);
        DogShelter.DogNode bb = test.new DogNode(B);
        DogShelter.DogNode cc = test.new DogNode(C);
        DogShelter.DogNode aa = test.new DogNode(A);
        DogShelter.DogNode dd = test.new DogNode(D);

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.older = rr;
        test.root.older.parent = test.root;

        test.root.younger.younger = bb;
        test.root.younger.younger.parent = ll;
        test.root.younger.older = ss;
        test.root.younger.older.parent = ll;

        test.root.younger.older.younger = cc;
        test.root.younger.older.younger.parent = ss;

        test.root.older.younger = dd;
        test.root.older.younger.parent = rr;
        test.root.older.older = pp;
        test.root.older.older.parent = rr;

        System.out.print("Testing getVetSchedule() #2... ");

        ArrayList<ArrayList<Dog>> ald = test.getVetSchedule();

        boolean arlSizes = ald.size() == 4 && ald.get(0).size() == 3 && ald.get(1).size() == 2 && ald.get(2).size() == 2 && ald.get(3).size() == 1;
        boolean arlDogs1 = ald.get(0).get(0) == S && ald.get(0).get(1) == R && ald.get(0).get(2) == P;
        boolean arlDogs2 = ald.get(1).get(0) == L && ald.get(1).get(1) == D;
        boolean arlDogs3 = ald.get(2).get(0) == B && ald.get(2).get(1) == A;
        boolean arlDogs4 = ald.get(3).get(0) == C;


        if (!(arlSizes && arlDogs1 && arlDogs2 && arlDogs3 && arlDogs4)) {
            if (!arlSizes) System.out.println("At least one ArrayList has wrong number of elements");
            else if (!arlDogs1) System.out.println("Incorrect elements in ArrayList 0");
            else if (!arlDogs2) System.out.println("Incorrect elements in ArrayList 1");
            else if (!arlDogs3) System.out.println("Incorrect elements in ArrayList 2");
            else if (!arlDogs4) System.out.println("Incorrect elements in ArrayList 3");
        } else {
            System.out.println("Passed.\n");
        }
        Assertions.assertTrue(arlDogs1 && arlDogs2 && arlDogs3 && arlDogs3 && arlDogs4, "Incorrect elements in ArrayList");
        Assertions.assertTrue(arlSizes, "At least one ArrayList has wrong number of elements");
    }

    @Test
    void testDogShelterIterator1() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 0, 250.0);
        Dog L = new Dog("Lessie", 3, 40, 1, 25.0);

        DogShelter test = new DogShelter(R);
        DogShelter.DogNode ll = test.new DogNode(L);
        DogShelter.DogNode rr = test.new DogNode(R);
        DogShelter.DogNode ss = test.new DogNode(S);

        test.root.younger = ss;
        test.root.younger.parent = test.root;
        test.root.younger.younger = ll;
        test.root.younger.younger.parent = ss;

        ArrayList<Dog> expected = new ArrayList<>();
        expected.add(L);
        expected.add(S);
        expected.add(R);

        ArrayList<Dog> actual = new ArrayList<>();

        System.out.print("Testing DogShelterIterator #1... \n");

//        TreePrinter.print(test.root);
//        test.printInOrder(test.root);

        boolean error = false;
        for (Dog d : test) {
//            System.out.println(d);
            actual.add(d);
        }
        if (actual.isEmpty() || actual.size() < expected.size()) {
            error = true;
            System.out.println("Did not visit all of the dogs in the shelter.");
        }
        if (actual.size() > expected.size()) {
            error = true;
            System.out.println("Some dogs were visited multiple times.");
        }
        for (int i = 0; i < 3; i++) {
            if (!(actual.get(i).equals(expected.get(i)))) {
                error = true;
                System.out.println("Dogs were visited in the wrong order");
            }
        }

        if (!error) {
            System.out.println("Passed.\n");
        }
        Assertions.assertFalse(error);
    }

    @Test
    void testDogShelterIterator2() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 0, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 1, 25.0);
        Dog F = new Dog("Fred", 6, 120, 10, 100.0);

        DogShelter test = new DogShelter(F);
        DogShelter.DogNode ll = test.new DogNode(L);
        DogShelter.DogNode rr = test.new DogNode(R);
        DogShelter.DogNode ss = test.new DogNode(S);
        DogShelter.DogNode ff = test.new DogNode(F);

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.younger.older = ss;
        test.root.younger.older.parent = ll;
        test.root.older = rr;
        test.root.older.parent = test.root;

        ArrayList<Dog> expected = new ArrayList<>();
        expected.add(L);
        expected.add(S);
        expected.add(F);
        expected.add(R);


        ArrayList<Dog> actual = new ArrayList<>();

        System.out.print("Testing DogShelterIterator #2... ");

        boolean error = false;
        for (Dog d : test) {
            actual.add(d);
        }
        if (actual.isEmpty() || actual.size() < expected.size()) {
            error = true;
            System.out.println("Did not visit all of the dogs in the shelter.");
        }
        if (actual.size() > expected.size()) {
            error = true;
            System.out.println("Some dogs were visited multiple times.");
        }
        for (int i = 0; i < 4; i++) {
            if (!(actual.get(i).equals(expected.get(i)))) {
                error = true;
                System.out.println("Dogs were visited in the wrong order");
            }
        }

        if (!error) {
            System.out.println("Passed.\n");
        }
        Assertions.assertFalse(error);
    }

    @Test
    void testDogShelterIterator3() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);

        DogShelter test = new DogShelter(R);

        ArrayList<Dog> actual = new ArrayList<>();

        System.out.print("Testing DogShelterIterator #3, contains only a root node... ");

        boolean error = false;
        for (Dog d : test) {
            actual.add(d);
        }
        if (actual.isEmpty()) {
            error = true;
            System.out.println("Did not visit any dog in the shelter.");
        }
        if (actual.size() > 1) {
            error = true;
            System.out.println("Some dogs were visited multiple times.");
        }
        if (!(actual.get(0).equals(R))) {
            error = true;
            System.out.println("Incorrect dog was visited.");
        }

        if (!error) {
            System.out.println("Passed.\n");
        }
        Assertions.assertFalse(error);
    }

    // Louis-Philippe Robichaud's tests // TODO

    @Test
    @DisplayName("Should test adding to shelter")
    void testAddToShelter() throws NoSuchFieldException, IllegalAccessException {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog Z = new Dog("Z", 4, 200, 9, 25.0);
//        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
//        Dog B = new Dog("Bella", 1, 55, 15, 120.0);
//        Dog C = new Dog("Cloud", 4, 10, 23, 80.0);
//        Dog A = new Dog("Archie", 6, 120, 18, 40.0);
//        Dog D = new Dog("Daisy", 7, 15, 12, 35.0);
        DogShelter shelter = new DogShelter(R);
        shelter.shelter(S);
        shelter.shelter(L);
        shelter.shelter(Z);
//        shelter.shelter(P);
//        shelter.shelter(B);
//        shelter.shelter(C);
//        shelter.shelter(A);
//        shelter.shelter(D);

        DogShelter.DogNode root = shelter.root;

        Field LNode = DogShelter.DogNode.class.getDeclaredField("younger");
        LNode.setAccessible(true);

        Field RNode = DogShelter.DogNode.class.getDeclaredField("older");
        RNode.setAccessible(true);

        Field dog = DogShelter.DogNode.class.getDeclaredField("d");
        dog.setAccessible(true);

        DogShelter.DogNode LTest = (DogShelter.DogNode) LNode.get(root);
        Dog l = (Dog) dog.get(LTest);
        Assertions.assertEquals(L.toString(), l.toString());

        DogShelter.DogNode RTest = (DogShelter.DogNode) RNode.get(root);
        Dog r = (Dog) dog.get(RTest);
        Assertions.assertEquals(R.toString(), r.toString());

        DogShelter.DogNode STest = (DogShelter.DogNode) LNode.get(RTest);
        Dog s = (Dog) dog.get(STest);
        Assertions.assertEquals(S.toString(), s.toString());
    }

    @Test
    @DisplayName("Should test adding to shelter")
    void testAddToShelter2() {
        Dog P = new Dog("Poldo", 10, 1, 1, 1.0);
        Dog R = new Dog("Rex", 8, 1, 1, 1.0);
        Dog S = new Dog("Stella", 5, 1, 1, 1.0);

        DogShelter dogs = new DogShelter(S);

        dogs.shelter(P);
        dogs.shelter(R);

        Assertions.assertEquals(dogs.root.d, S, "Error in test binary insertion: root should be Stella");
        Assertions.assertEquals(dogs.root.older.d, P, "Error in test binary insertion: root.older should be Polda");
        Assertions.assertEquals(dogs.root.older.younger.d, R, "Error in test binary insertion: root.older.younger should be Rex");

        dogs = new DogShelter(P);
        dogs.shelter(S);
        dogs.shelter(R);

        Assertions.assertEquals(dogs.root.d, P, "Error in test binary insertion: root should be Poldo");
        Assertions.assertEquals(dogs.root.younger.d, S, "Error in test binary insertion: root.younger should be Stella");
        Assertions.assertEquals(dogs.root.younger.older.d, R, "Error in test binary insertion: root.younger.older should be Rex");
    }

    @Test
    @DisplayName("Should find the oldest dog in a shelter")
    void testFindOldest() {
        DogShelter shelter = generateSampleShelter();
        Dog d = shelter.findOldest();
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Assertions.assertEquals(d.toString(), R.toString());
    }

    @Test
    @DisplayName("Should find the youngest dog in a shelter")
    void testFindYoungest() {
        DogShelter shelter = generateSampleShelter();
        Dog d = shelter.findYoungest();
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Assertions.assertEquals(d.toString(), L.toString());
    }

    @Test
    @DisplayName("Should test DogNode iterator for a simple Shelter")
    void testIterator1() {
        DogShelter shelter = generateSampleShelter();
        ArrayList<Dog> dogs = new ArrayList<>();
        for (Dog d : shelter) {
            dogs.add(d);
        }
        ArrayList<Dog> expected = new ArrayList<>();
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog Z = new Dog("Z", 4, 200, 9, 25.0);
        expected.add(L);
        expected.add(Z);
        expected.add(S);
        expected.add(R);

        Assertions.assertTrue(expected.containsAll(dogs));
    }

    @Test
    @DisplayName("Should find correct vet budget 1")
    void testVetBudget1() {
        DogShelter shelter = generateSampleShelter();
        double budget = shelter.budgetVetExpenses(9);
        Assertions.assertEquals(budget, 325);
    }

    @Test
    @DisplayName("Should give correct vet schedule 1")
    void testVetSchedule1() {
        DogShelter shelter = generateSampleShelter();
        ArrayList<ArrayList<Dog>> list = shelter.getVetSchedule();

        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog Z = new Dog("Z", 4, 200, 10, 25.0);

        ArrayList<Dog> week1 = list.get(0);

        ArrayList<Dog> week2 = list.get(1);

        Assertions.assertAll(
                () -> Assertions.assertEquals(week1.get(0), S),
                () -> Assertions.assertEquals(week1.get(1), R),
                () -> Assertions.assertEquals(week2.get(0), L),
                () -> Assertions.assertEquals(week2.get(1), Z));
    }

    @Test
    @DisplayName("Should return empty schedule for empty shelter")
    void testVetSchedule2() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        DogShelter shelter = new DogShelter(R);
        shelter.root = null;
        Assertions.assertEquals(shelter.getVetSchedule().size(), 0);
    }

    @Test
    @DisplayName("Should do a full-scale test per A3 examples")
    void fullScale1() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
        Dog B = new Dog("Bella", 1, 55, 15, 120.0);
        Dog C = new Dog("Cloud", 4, 10, 23, 80.0);
        Dog A = new Dog("Archie", 6, 120, 18, 40.0);
        Dog D = new Dog("Daisy", 7, 15, 12, 35.0);

        DogShelter shelter = new DogShelter(R);
        shelter.shelter(S);
        shelter.shelter(L);
        shelter.adopt(R);

        Assertions.assertEquals(shelter.root.d, L);

        shelter.shelter(R);
        shelter.shelter(P);
        shelter.adopt(R);

        Assertions.assertEquals(shelter.root.d, L);

        shelter.shelter(R);
        shelter.shelter(B);
        shelter.shelter(C);
        shelter.shelter(A);
        shelter.shelter(D);

        Assertions.assertAll(
                () -> Assertions.assertEquals(shelter.findOldest(), P),
                () -> Assertions.assertEquals(shelter.findYoungest(), B),
                () -> Assertions.assertEquals(shelter.findDogToAdopt(3, 5), L),
                () -> Assertions.assertEquals(shelter.budgetVetExpenses(15), 515.0),
                () -> {
                    ArrayList<ArrayList<Dog>> schedule = shelter.getVetSchedule();
                    Assertions.assertAll(
                            () -> Assertions.assertEquals(schedule.size(), 4),
                            () -> Assertions.assertEquals(schedule.get(0).get(0), S),
                            () -> Assertions.assertEquals(schedule.get(0).get(1), R),
                            () -> Assertions.assertEquals(schedule.get(0).get(2), P),
                            () -> Assertions.assertEquals(schedule.get(1).get(0), L),
                            () -> Assertions.assertEquals(schedule.get(1).get(1), D),
                            () -> Assertions.assertEquals(schedule.get(2).get(0), B),
                            () -> Assertions.assertEquals(schedule.get(2).get(1), A),
                            () -> Assertions.assertEquals(schedule.get(3).get(0), C)
                    );
                }
        );

        Assertions.assertEquals(shelter.adopt(), A);
        DogShelter.DogNode root = shelter.root;
        Assertions.assertAll(
                () -> Assertions.assertEquals(root.d, R),
                () -> Assertions.assertNull(root.parent),
                () -> Assertions.assertEquals(root.younger.d, L),
                () -> Assertions.assertEquals(root.older.d, P),
                () -> Assertions.assertTrue(root.younger.parent == root && root.older.parent == root),
                () -> Assertions.assertEquals(root.younger.younger.d, B),
                () -> Assertions.assertEquals(root.younger.older.d, S),
                () -> Assertions.assertTrue(root.younger.younger.parent == root.younger && root.younger.older.parent == root.younger),
                () -> Assertions.assertEquals(root.younger.older.younger.d, C),
                () -> Assertions.assertEquals(root.younger.older.older.d, D),
                () -> Assertions.assertEquals(root.younger.older.older.parent, root.younger.older),
                () -> Assertions.assertEquals(root.younger.older.younger.parent, root.younger.older)
        );

        Assertions.assertEquals(shelter.adopt(), R);
        Assertions.assertEquals(shelter.adopt(), L);
        Assertions.assertEquals(shelter.adopt(), P);
        Assertions.assertEquals(shelter.adopt(), B);
        Assertions.assertEquals(shelter.adopt(), S);
        Assertions.assertEquals(shelter.adopt(), D);
        Assertions.assertEquals(shelter.adopt(), C);
        Assertions.assertNull(shelter.root);
    }

    // From meow10811's Test Suite

    // The shelter
    DogShelter shelter;
    // My test 🐶
    Dog max = new Dog("Max", 11, 0, 2, 100.0);
    Dog neptune = new Dog("Neptune", 12, 3, 250, 0.0);
    Dog izzie = new Dog("Izzie", 6, 5, 15, 150.0);

    @Test
    void IterateFunctionalityTest() {
        DogShelter shelter = generateSampleShelter2();
        Iterator<Dog> shelterIterator = shelter.iterator();

        Assertions.assertEquals(shelterIterator.hasNext(), true, "Iterator should haveNext()");
        Assertions.assertEquals(shelterIterator.next().getClass(), Dog.class, "Iterator should return root object of type DogNode");
        Assertions.assertEquals(shelterIterator.hasNext(), false, "Iterator should no longer haveNext");
    }

    @Test
    void WhenNoMoreElementsIteratorShouldThrowException() {
        DogShelter shelter = generateSampleShelter2();
        Iterator<Dog> shelterIterator = shelter.iterator();

        shelterIterator.next();
        Assertions.assertThrows(NoSuchElementException.class, shelterIterator::next);
    }

    @Test
        // Neptune is older than Max but has been in the shelter for longer
    void ShelterOneYoungerDogLowerPriority() {
        // Setup
        shelter = new DogShelter(neptune);

        // Test
        shelter.shelter(max);

        // Assert
        assertAll("Sheltering a younger dog with lower priority",
                () -> assertEquals(neptune, shelter.root.d, "Root node shouldn't change"),
                () -> assertNotNull(shelter.root.younger, "Root node should have a younger dog"),
                () -> assertEquals(max, shelter.root.younger.d, "New dog should be younger")
        );
    }

    @Test
        // Neptune is older than Izzie but Izzie has been in the shelter for longer
    void ShelterOneYoungerDogHigherPriority() {
        // Setup
        shelter = new DogShelter(neptune);

        // Test
        shelter.shelter(izzie);

        // Assert
        assertAll("Sheltering a younger dog with higher priority priority",
                () -> assertEquals(izzie, shelter.root.d, "Root node should change"),
                () -> assertNotNull(shelter.root.older, "Root node should have an older dog"),
                () -> assertEquals(neptune, shelter.root.older.d, "Original dog should be older")
        );
    }

    @Test
        // Izzie is younger than Max but has been in the shelter for longer
    void ShelterOneOlderDogLowerPriority() {
        // Setup
        shelter = new DogShelter(izzie);

        // Test
        shelter.shelter(max);

        // Assert
        assertAll("Sheltering an older dog with lower priority",
                () -> assertEquals(izzie, shelter.root.d, "Root node shouldn't change"),
                () -> assertNotNull(shelter.root.older, "Root node should have an older dog"),
                () -> assertEquals(max, shelter.root.older.d, "New dog should be older")
        );
    }

    @Test
        // Max is younger than Neptune but hasn't been in the shelter for as long
    void ShelterOneOlderDogHigherPriority() {
        // Setup
        shelter = new DogShelter(max);

        // Test
        shelter.shelter(neptune);

        // Assert
        assertAll("Sheltering an older dog with higher priority",
                () -> assertEquals(neptune, shelter.root.d, "Root node should change"),
                () -> assertNotNull(shelter.root.younger, "Root node should have a younger dog"),
                () -> assertEquals(max, shelter.root.younger.d, "Original dog should be younger")
        );
    }

    // Alex P's tests

        @Test
        public void getVetScheduleMegaTester(){
            Dog R = new Dog("Rex", 8, 100, 5, 50.0);
            Dog S = new Dog("Stella", 5, 50, 15, 250.0);
            Dog L = new Dog("Lessie", 3, 151, 0, 25.0);
            Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
            Dog B = new Dog("Bella", 11, 150, 48, 120.0);
            Dog C = new Dog("Cloud", 4, 10, 12, 80.0);
            Dog A = new Dog("Archie", 6, 120, 7, 40.0);
            Dog D = new Dog("Daisy", 7, 15, 12, 35.0);
            Dog J = new Dog("Jesse", 8, 58, 12, 50.0);

            DogShelter test = new DogShelter(R);

            test.shelter(S);
            test.shelter(L);
            test.shelter(P);
            test.shelter(B);
            test.shelter(C);


            System.out.print("\n");
            System.out.print("\n");
            System.out.print("~~~~~~~~~~~~~~Case 1: empty weeks in between~~~~~~~~~~~~~~~~~\n");


            ArrayList< ArrayList< Dog > > ald =  test.getVetSchedule();

            boolean arlSizes = ald.size() == 7 && ald.get( 0 ).size() == 3 && ald.get( 1 ).size() == 1 && ald.get( 2 ).size() == 1
                    && ald.get( 3 ).size() == 0 && ald.get( 4 ).size() == 0 && ald.get( 5 ).size() == 0 && ald.get( 6 ).size() == 1;
            boolean arlDogs1 = ald.get( 0 ).get( 0 ) == L && ald.get( 0 ).get( 1 ) == R && ald.get( 0 ).get( 2 ) == P;
            boolean arlDogs2 = ald.get( 1 ).get( 0 ) == C;
            boolean arlDogs3 = ald.get( 2 ).get( 0 ) == S;
            boolean arlDogs4 = ald.get( 6 ).get( 0 ) == B;


            if( !( arlSizes && arlDogs1 && arlDogs2 && arlDogs3 && arlDogs4) )
            {
//                if( !arlSizes ) System.out.println( "At least one ArrayList has wrong number of elements" );
//                else if( !arlDogs1 ) System.out.println( "Incorrect elements in ArrayList 0" );
//                else if( !arlDogs2 ) System.out.println( "Incorrect elements in ArrayList 1" );
//                else if( !arlDogs3 ) System.out.println( "Incorrect elements in ArrayList 2" );
//                else if( !arlDogs4 ) System.out.println( "Incorrect elements in ArrayList 6" );
            } else {
                System.out.println("Passed case 1.");
            }
            assertTrue(arlSizes, "At least one ArrayList has wrong number of elements");
            assertTrue(arlDogs1, "Incorrect elements in ArrayList 0");
            assertTrue(arlDogs2, "Incorrect elements in ArrayList 1");
            assertTrue(arlDogs3, "Incorrect elements in ArrayList 1");
            assertTrue(arlDogs4, "Incorrect elements in ArrayList 6");

            test.shelter(A);
            test.shelter(D);
            test.shelter(J);


            System.out.print("\n");
            System.out.print("\n");
            System.out.print("~~~~~~~~~~~~~~Case 2: dogs with same dates~~~~~~~~~~~~~~~~~\n");


            ArrayList< ArrayList< Dog > > ald2 =  test.getVetSchedule();

            boolean arlSizes2 = ald2.size() == 7 && ald2.get( 0 ).size() == 3 && ald2.get( 1 ).size() == 4 && ald2.get( 2 ).size() == 1
                    && ald2.get( 3 ).size() == 0 && ald2.get( 4 ).size() == 0 && ald2.get( 5 ).size() == 0 && ald2.get( 6 ).size() == 1;
            boolean arlDogs12 = ald2.get( 0 ).get( 0 ) == L && ald2.get( 0 ).get( 1 ) == R && ald2.get( 0 ).get( 2 ) == P;
            boolean arlDogs22 = ald2.get( 1 ).get( 0 ) == C && ald2.get( 1 ).get( 1 ) == A && ald2.get( 1 ).get( 2 ) == D && ald2.get( 1 ).get( 3 ) == J;
            boolean arlDogs32 = ald2.get( 2 ).get( 0 ) == S;
            boolean arlDogs42 = ald2.get( 6 ).get( 0 ) == B;


            if( !( arlSizes2 && arlDogs12 && arlDogs22 && arlDogs32 && arlDogs42) )
            {
//                if( !arlSizes2 ) System.out.println( "At least one ArrayList has wrong number of elements" );
//                else if( !arlDogs12 ) System.out.println( "Incorrect elements in ArrayList 0" );
//                else if( !arlDogs22 ) System.out.println( "Incorrect elements in ArrayList 1" );
//                else if( !arlDogs32 ) System.out.println( "Incorrect elements in ArrayList 2" );
//                else if( !arlDogs42 ) System.out.println( "Incorrect elements in ArrayList 6" );
            } else {
                System.out.println("Passed case 2.");
            }
            assertTrue(arlSizes2, "At least one ArrayList has wrong number of elements");
            assertTrue(arlDogs12, "Incorrect elements in ArrayList 0");
            assertTrue(arlDogs22, "Incorrect elements in ArrayList 1");
            assertTrue(arlDogs32, "Incorrect elements in ArrayList 2");
            assertTrue(arlDogs42, "Incorrect elements in ArrayList 6");
        }
//
//    public static void main(String[] args) {
//        shelterMegaTester();
//
//        System.out.print("\n");
//        System.out.print("\n");
//        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
//        System.out.print("\n");
//        System.out.print("\n");
//
//        findDogToAdoptMegaTester();
//
//        System.out.print("\n");
//        System.out.print("\n");
//        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
//        System.out.print("\n");
//        System.out.print("\n");
//
//        adoptMegaTester();
//
//        System.out.print("\n");
//        System.out.print("\n");
//        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
//        System.out.print("\n");
//        System.out.print("\n");
//
//        getVetScheduleMegaTester();
//
//    }

    @Test
    public void shelterMegaTester(){
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 151, 9, 25.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
        Dog B = new Dog("Bella", 11, 150, 15, 120.0);
        Dog C = new Dog("Cloud", 4, 10, 23, 80.0);
        Dog A = new Dog("Archie", 6, 120, 18, 40.0);
        Dog D = new Dog("Daisy", 7, 15, 12, 35.0);

        DogShelter test = new DogShelter(D);

        System.out.print("Testing shelter Mega Tester. There will be multiple mini tests in order to cover the different possible case scenarios\n");
        System.out.print("If you don't succeed in all tests, go into the debugger for the specific test you failed\n");

        System.out.print("\n");
        System.out.print("\n");
        System.out.print("~~~~~~~~~~~~~~Testing basic tree building~~~~~~~~~~~~~~~~~\n");

        test.shelter(C);

        StringBuilder errorBuilder = new StringBuilder();
        boolean checkDogs = test.root.d == D && test.root.younger.d == C;
//        if (!checkDogs) {
//            errorBuilder.append("Dogs are not assigned correctly. ");
//        }
        assertTrue(checkDogs, "Dogs are not assigned correctly. ");
        boolean checkParents = test.root.younger.parent.d == D;
//        if (!checkParents) {
//            errorBuilder.append("Parent pointers are not assigned correctly. ");
//        }
        assertTrue(checkParents, "Parent pointers are not assigned correctly. ");
        boolean checkNulls = test.root.parent == null &&
                test.root.younger.younger == null && test.root.younger.older == null;
//        if (!checkNulls) {
//            errorBuilder.append("Null values are not assigned correctly.");
//        }
        assertTrue(checkNulls,"Null values are not assigned correctly.");
        if (!checkDogs || !checkParents || !checkNulls) {
            System.out.println(errorBuilder.toString());
        } else {
            System.out.println("Passed first build case.");
        }

        test.shelter(R);

        StringBuilder errorBuilder2 = new StringBuilder();
        boolean checkDogs2 = test.root.d == R && test.root.younger.d == D && test.root.younger.younger.d == C;
//        if (!checkDogs2) {
//            errorBuilder2.append("Dogs are not assigned correctly. ");
//        }
        assertTrue(checkDogs2, "Dogs are not assigned correctly. ");
        boolean checkParents2 = test.root.younger.parent.d == R && test.root.younger.younger.parent.d == D;
//        if (!checkParents2) {
//            errorBuilder2.append("Parent pointers are not assigned correctly. ");
//        }
        assertTrue(checkParents2, "Parent pointers are not assigned correctly. ");
        boolean checkNulls2 = test.root.parent == null && test.root.older == null &&
                test.root.younger.older == null && test.root.younger.younger.older == null &&
                test.root.younger.younger.younger == null;
//        if (!checkNulls2) {
//            errorBuilder2.append("Null values are not assigned correctly.");
//        }
        assertTrue(checkNulls2, "Null values are not assigned correctly.");
        if (!checkDogs2 || !checkParents2 || !checkNulls2) {
            System.out.println(errorBuilder2.toString());
        } else {
            System.out.println("Passed second build case.");
        }

        System.out.print("\nBasic tests done, let's try something harder\n");

        test.shelter(P);
        test.shelter(A);
        test.shelter(S);

        StringBuilder errorBuilder3 = new StringBuilder();
        boolean checkDogs3 = test.root.d == A && test.root.younger.d == S && test.root.younger.younger.d == C &&
                test.root.older.d == R && test.root.older.younger.d == D && test.root.older.older.d == P;
//        if (!checkDogs3) {
//            errorBuilder3.append("Dogs are not assigned correctly. ");
//        }
        assertTrue(checkDogs3, "Dogs are not assigned correctly. ");
        boolean checkParents3 = test.root.younger.parent.d == A && test.root.younger.younger.parent.d == S &&
                test.root.older.parent.d == A && test.root.older.younger.parent.d == R &&
                test.root.older.older.parent.d == R;
//        if (!checkParents3) {
//            errorBuilder3.append("Parent pointers are not assigned correctly. ");
//        }
        assertTrue(checkParents3, "Parent pointers are not assigned correctly. ");
        boolean checkNulls3 = test.root.parent == null && test.root.younger.older == null &&
                test.root.younger.younger.older == null && test.root.older.younger.older == null &&
                test.root.older.younger.younger == null && test.root.older.older.younger == null &&
                test.root.older.older.older == null;
//        if (!checkNulls3) {
//            errorBuilder3.append("Null values are not assigned correctly.");
//        }
        assertTrue(checkNulls3, "Null values are not assigned correctly.");
        if (!checkDogs3 || !checkParents3 || !checkNulls3) {
            System.out.println(errorBuilder3.toString());
        } else {
            System.out.println("\nPassed third build case.");
        }


        System.out.print("~~~~~~~~~~~~~~Testing first hardest case~~~~~~~~~~~~~~~~~\n");

        test.shelter(B);

        StringBuilder errorBuilder4 = new StringBuilder();
        boolean checkDogs4 = test.root.d == B && test.root.younger.d == A && test.root.younger.younger.d == S &&
                test.root.younger.younger.younger.d == C && test.root.younger.older.d == R &&
                test.root.younger.older.older.d == P && test.root.younger.older.younger.d == D;
//        if (!checkDogs4) {
//            errorBuilder4.append("Dogs are not assigned correctly. ");
//        }
        assertTrue(checkDogs4, "Dogs are not assigned correctly. ");
        boolean checkParents4 = test.root.younger.parent.d == B && test.root.younger.younger.parent.d == A &&
                test.root.younger.younger.younger.parent.d == S && test.root.younger.older.parent.d == A &&
                test.root.younger.older.younger.parent.d == R &&  test.root.younger.older.older.parent.d == R;
//        if (!checkParents4) {
//            errorBuilder4.append("Parent pointers are not assigned correctly. ");
//        }
        assertTrue(checkParents4, "Parent pointers are not assigned correctly. ");
        boolean checkNulls4 = test.root.parent == null && test.root.older == null &&
                test.root.younger.younger.older == null && test.root.younger.younger.younger.older == null &&
                test.root.younger.older.older.younger == null && test.root.younger.older.older.older == null &&
                test.root.younger.older.younger.younger == null && test.root.younger.older.younger.older == null;
//        if (!checkNulls4) {
//            errorBuilder4.append("Null values are not assigned correctly.");
//        }
        assertTrue(checkNulls4, "Null values are not assigned correctly.");
        if (!checkDogs4 || !checkParents4 || !checkNulls4) {
            System.out.println(errorBuilder4.toString());
        } else {
            System.out.println("Passed fourth build case.");
        }

        System.out.print("\nGreat job! that was the hardest case so far which required the most rotations!\n");

        System.out.print("~~~~~~~~~~~~~~Testing hardest case~~~~~~~~~~~~~~~~~\n");

        test.shelter(L);

        StringBuilder errorBuilder5 = new StringBuilder();
        boolean checkDogs5 = test.root.d == L && test.root.older.d == B &&
                test.root.older.younger.d == A && test.root.older.younger.younger.d == S &&
                test.root.older.younger.younger.younger.d == C &&  test.root.older.younger.older.d == R &&
                test.root.older.younger.older.younger.d == D && test.root.older.younger.older.older.d == P;
//        if (!checkDogs5) {
//            errorBuilder5.append("Dogs are not assigned correctly. ");
//        }
        assertTrue(checkDogs5, "Dogs are not assigned correctly. ");
        boolean checkParents5 = test.root.older.parent.d == L && test.root.older.younger.parent.d == B &&
                test.root.older.younger.younger.parent.d == A && test.root.older.younger.older.parent.d == A &&
                test.root.older.younger.younger.younger.parent.d == S && test.root.older.younger.older.younger.parent.d == R
                && test.root.older.younger.older.older.parent.d == R;
//        if (!checkParents5) {
//            errorBuilder5.append("Parent pointers are not assigned correctly. ");
//        }
        assertTrue(checkParents5, "Parent pointers are not assigned correctly. ");
        boolean checkNulls5 = test.root.parent == null && test.root.younger == null && test.root.older.older == null &&
                test.root.older.younger.younger.older == null && test.root.older.younger.younger.younger.older == null
                && test.root.older.younger.older.younger.younger == null && test.root.older.younger.older.younger.older == null
                && test.root.older.younger.older.older.younger == null && test.root.older.younger.older.older.older == null;
//        if (!checkNulls5) {
//            errorBuilder5.append("Null values are not assigned correctly.");
//        }
        assertTrue(checkNulls5, "Null values are not assigned correctly.");
        if (!checkDogs5 || !checkParents5 || !checkNulls5) {
            System.out.println(errorBuilder5.toString());
        } else {
            System.out.println("\nPassed fifth and final build case.");
        }

        System.out.print("\nThis last case involved plenty of right and left rotations so I think it should be a good test\n");




    }

    @Test
    public void findDogToAdoptMegaTester(){
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 151, 9, 25.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
        Dog B = new Dog("Bella", 11, 150, 15, 120.0);
        Dog C = new Dog("Cloud", 4, 10, 23, 80.0);
        Dog A = new Dog("Archie", 6, 120, 18, 40.0);
        Dog D = new Dog("Daisy", 7, 15, 12, 35.0);

        DogShelter test = new DogShelter(D);

        test.shelter(R);
        test.shelter(B);
        test.shelter(A);
        test.shelter(S);

        System.out.print("Testing DogToAdopt Mega Tester. There will be multiple mini tests in order to cover the different possible case scenarios\n");
        System.out.print("If you don't succeed in all tests, go into the debugger for the specific test you failed\n");

        System.out.print("\n");
        System.out.print("\n");
        System.out.print("~~~~~~~~~~~~~~Case 1: minAge = maxAge~~~~~~~~~~~~~~~~~\n");

        Dog d = test.findDogToAdopt(5,5);
        if(!(d.equals(S))) {
            if(!(d.getAge() >=5 && d.getAge() <=5)) {
//                System.out.println("The dog found was not the correct age range.");
            }
//            System.out.println("The dog found did not have the highest priority within the age range.");
        } else {
            System.out.println("Passed case 1.");
        }
        assertTrue(d.getAge()==5, "The dog found was not the correct age range.");
        assertTrue(d.equals(S), "The dog found did not have the highest priority within the age range.");

        System.out.print("\n");
        System.out.print("\n");
        System.out.print("~~~~~~~~~~~~~~Case 2: no such dog exists (age too big)~~~~~~~~~~~~~~~~~\n");

        Dog a = test.findDogToAdopt(12,16);

        if(!(a == null)) {
//            System.out.println("You found a dog when you shouldn't have.");
        } else {
            System.out.println("Passed case 2.");
        }
        assertNull(a, "You found a dog when you shouldn't have.");

        System.out.print("\n");
        System.out.print("\n");
        System.out.print("~~~~~~~~~~~~~~Case 3: no such dog exists (age too small)~~~~~~~~~~~~~~~~~\n");

        Dog b = test.findDogToAdopt(1,4);

        if(!(b == null)) {
//            System.out.println("You found a dog when you shouldn't have.");
        } else {
            System.out.println("Passed case 3.");
        }
        assertNull(b, "You found a dog when you shouldn't have.");

        System.out.print("\n");
        System.out.print("\n");
        System.out.print("~~~~~~~~~~~~~~Case 4: Age == maxAge~~~~~~~~~~~~~~~~~\n");

        Dog c = test.findDogToAdopt(1,5);

        if(!(c.equals(S))) {
            if(!(c.getAge() >=1 && c.getAge()<=5)) {
//                System.out.println("The dog found was not the correct age range.");
            }
//            System.out.println("The dog found did not have the highest priority within the age range.");
        } else {
            System.out.println("Passed case 4.");
        }
        assertTrue(c.getAge() >= 1 || c.getAge() <= 5, "The dog found was not the correct age range.");
        assertTrue(c.equals(S), "The dog found did not have the highest priority within the age range.");

        System.out.print("\n");
        System.out.print("\n");
        System.out.print("~~~~~~~~~~~~~~Case 5: Age == minAge~~~~~~~~~~~~~~~~~\n");

        Dog e = test.findDogToAdopt(8,10);

        if(!(e.equals(R))) {
            if(!(e.getAge() >=8 && e.getAge() <=10)) {
//                System.out.println("The dog found was not the correct age range.");
            }
//            System.out.println("The dog found did not have the highest priority within the age range.");
        } else {
            System.out.println("Passed case 5.");
        }
//        System.out.println(e);
        assertTrue(e.getAge() >= 8 && e.getAge() <= 10, "The dog found was not the correct age range.");
        assertTrue(e.equals(R), "The dog found did not have the highest priority within the age range.");


        System.out.print("\n");
        System.out.print("\n");
        System.out.print("~~~~~~~~~~~~~~Case 6: Find highest priority~~~~~~~~~~~~~~~~~\n");

        Dog f = test.findDogToAdopt(4, 12);

        if(!(f.equals(B))) {
            if(!(f.getAge() >=4 && f.getAge() <=12)) {
                System.out.println("The dog found was not the correct age range.");
            }
//            System.out.println("The dog found did not have the highest priority within the age range.");
        } else {
            System.out.println("Passed case 6.");
        }
        assertTrue(f.getAge() >=4 && f.getAge() <=12, "The dog found was not the correct age range.");
        assertTrue(f.equals(B), "The dog found did not have the highest priority within the age range.");

        System.out.print("\n");
        System.out.print("\n");
        System.out.print("~~~~~~~~~~~~~~Case 7: Find highest priority~~~~~~~~~~~~~~~~~\n");

        Dog g = test.findDogToAdopt(4, 10);

        if(!(g.equals(A))) {
            if(!(g.getAge() >=4 && g.getAge() <=10)) {
//                System.out.println("The dog found was not the correct age range.");
            }
//            System.out.println("The dog found did not have the highest priority within the age range.");
        } else {
            System.out.println("Passed case 7.");
        }
        assertTrue(g.getAge() >=4 && g.getAge() <=10, "The dog found was not the correct age range.");
        assertTrue(g.equals(A), "The dog found did not have the highest priority within the age range.");
        System.out.print("\nThat should have covered most of the edge cases, great job if you passed them all! :)\n");
    }

    @Test
    public void adoptMegaTester(){
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 151, 9, 25.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
        Dog B = new Dog("Bella", 11, 150, 15, 120.0);
        Dog C = new Dog("Cloud", 4, 10, 23, 80.0);
        Dog A = new Dog("Archie", 6, 120, 18, 40.0);
        Dog D = new Dog("Daisy", 7, 15, 12, 35.0);
        Dog J = new Dog("Jesse", 8, 58, 5, 50.0);
        Dog M = new Dog("Monica", 12, 59, 5, 50.0);
        Dog N = new Dog("Nina", 11, 55, 5, 50.0);
        Dog O = new Dog("Ollie", 14, 57, 5, 50.0);
        Dog LO = new Dog("Louigi", 13, 54, 5, 50.0);

        DogShelter test = new DogShelter(D);

        test.shelter(C);
        test.shelter(B);


        System.out.print("Testing Adopt Mega Tester. There will be multiple mini tests in order to cover the different possible case scenarios\n");
        System.out.print("If you don't succeed in all tests, go into the debugger for the specific test you failed\n");

        System.out.print("\n");
        System.out.print("\n");
        System.out.print("~~~~~~~~~~~~~~Case 1: adopting root~~~~~~~~~~~~~~~~~\n");

        Dog a = test.adopt();

        boolean dogs = test.root.d == D && test.root.younger.d == C;

        boolean nulls = test.root.parent == null && test.root.older== null && test.root.younger.older == null &&
                test.root.younger.younger == null;

        boolean parents = test.root.younger.parent.d == D;

        boolean ret = a == B;

        if( !( dogs && nulls && parents && ret ) )
        {
//            if( !dogs ) System.out.println( "Dogs are not assigned correctly" );
//            else if( !nulls ) System.out.println( "Null values are not assigned correctly" );
//            else if( !parents ) System.out.println( "Parent pointers are not assigned correctly" );
//            else if( !ret ) System.out.println( "The method returned incorrect value" );
        } else {
            System.out.println("Passed case 1.");
        }
        assertTrue(dogs, "Dogs are not assigned correctly");
        assertTrue(nulls, "Null values are not assigned correctly");
        assertTrue(parents, "Parent pointers are not assigned correctly");
        assertTrue(ret, "The method returned incorrect value");

        test.shelter(A);
        test.shelter(L);

        System.out.print("\n");
        System.out.print("\n");
        System.out.print("~~~~~~~~~~~~~~Case 2: adopting root~~~~~~~~~~~~~~~~~\n");

        Dog b = test.adopt();

        boolean dogs2 = test.root.d == A && test.root.younger.d == C && test.root.older.d == D;

        boolean nulls2 = test.root.parent == null && test.root.older.younger== null && test.root.older.older== null &&
                test.root.younger.older == null && test.root.younger.younger == null;

        boolean parents2 = test.root.younger.parent.d == A && test.root.older.parent.d == A;

        boolean ret2 = b == L;

        if( !( dogs2 && nulls2 && parents2 && ret2 ) )
        {
//            if( !dogs2 ) System.out.println( "Dogs are not assigned correctly" );
//            else if( !nulls2 ) System.out.println( "Null values are not assigned correctly" );
//            else if( !parents2 ) System.out.println( "Parent pointers are not assigned correctly" );
//            else if( !ret2 ) System.out.println( "The method returned incorrect value" );
        } else {
            System.out.println("Passed case 2.");
        }
        assertTrue(dogs2, "Dogs are not assigned correctly");
        assertTrue(nulls2, "Null values are not assigned correctly");
        assertTrue(parents2, "Parent pointers are not assigned correctly");
        assertTrue(ret2, "The method returned incorrect value");

        test.shelter(S);
        test.shelter(R);
        test.shelter(P);

        System.out.print("\n");
        System.out.print("\n");
        System.out.print("~~~~~~~~~~~~~~Case 3: adopting a specific dog~~~~~~~~~~~~~~~~~\n");

        test.adopt(R);

        boolean dogs3 = test.root.d == A && test.root.younger.d == S && test.root.younger.younger.d == C &&
                test.root.older.d == P && test.root.older.younger.d == D;

        boolean nulls3 = test.root.parent == null && test.root.older.older== null && test.root.older.younger.younger == null
                && test.root.older.younger.older == null && test.root.younger.older == null &&
                test.root.younger.younger.younger == null && test.root.younger.younger.older == null;

        boolean parents3 = test.root.younger.parent.d == A && test.root.older.parent.d == A &&
                test.root.younger.younger.parent.d == S && test.root.older.younger.parent.d == P;

        if( !( dogs3 && nulls3 && parents3) )
        {
//            if( !dogs3 ) System.out.println( "Dogs are not assigned correctly" );
//            else if( !nulls3 ) System.out.println( "Null values are not assigned correctly" );
//            else if( !parents3 ) System.out.println( "Parent pointers are not assigned correctly" );
        } else {
            System.out.println("Passed case 3.");
        }
        assertTrue(dogs3, "Dogs are not assigned correctly");
        assertTrue(nulls3, "Null values are not assigned correctly");
        assertTrue(parents3, "Parent pointers are not assigned correctly");

        test.shelter(J);
        test.shelter(M);
        test.shelter(N);
        test.shelter(O);
        test.shelter(LO);

        System.out.print("\n");
        System.out.print("\n");
        System.out.print("~~~~~~~~~~~~~~Case 4: adopting a specific dog~~~~~~~~~~~~~~~~~\n");

        test.adopt(P);

        boolean dogs4 = test.root.d == A && test.root.younger.d == S && test.root.younger.younger.d == C &&
                test.root.older.d == M && test.root.older.younger.d == J && test.root.older.older.d == O &&
                test.root.older.younger.younger.d == D && test.root.older.younger.older.d == N &&
                test.root.older.older.younger.d == LO;

        boolean nulls4 = test.root.parent == null && test.root.older.older.older == null &&
                test.root.older.younger.younger.younger == null && test.root.older.younger.younger.older == null &&
                test.root.older.younger.older.younger == null && test.root.older.younger.older.older == null &&
                test.root.older.older.younger.younger == null && test.root.older.older.younger.older == null &&
                test.root.younger.younger.younger == null && test.root.younger.younger.older ==  null &&
                test.root.younger.older == null;

        boolean parents4 = test.root.younger.parent.d == A && test.root.older.parent.d == A &&
                test.root.younger.younger.parent.d == S && test.root.older.younger.parent.d == M &&
                test.root.older.older.parent.d == M && test.root.older.younger.younger.parent.d == J &&
                test.root.older.younger.older.parent.d == J && test.root.older.older.younger.parent.d == O;

        if( !( dogs4 && nulls4 && parents4) )
        {
//            if( !dogs4 ) System.out.println( "Dogs are not assigned correctly" );
//            else if( !nulls4 ) System.out.println( "Null values are not assigned correctly" );
//            else if( !parents4 ) System.out.println( "Parent pointers are not assigned correctly" );
        } else {
            System.out.println("Passed case 4.");
        }
        assertTrue(dogs4, "Dogs are not assigned correctly");
        assertTrue(nulls4, "Null values are not assigned correctly");
        assertTrue(parents4, "Parent pointers are not assigned correctly");

        System.out.print("\nIf you passed all the tests, congrats! :)\n");
        System.out.print("\nThis last case involved plenty of right and left rotations so I think it should be a good test\n");
    }

    @Test
    public void dogIteratorExceptionCheck(){
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 15, 250.0);
        Dog L = new Dog("Lessie", 3, 151, 0, 25.0);


        DogShelter test = new DogShelter(R);

        test.shelter(S);
        test.shelter(L);

        System.out.print("\n");
        System.out.print("\n");
        System.out.print("~~~~~~~~~~~~~~Raising NoSuchElementException~~~~~~~~~~~~~~~~~\n");


        Iterator<Dog> d1 = test.iterator();

        d1.next();
        d1.next();
        d1.next();

        boolean didntWork = true;

        try {
            d1.next();
        }
        catch (NoSuchElementException e){
            System.out.println("Great! your code raised an exception as it should!");
            didntWork = false;
        }
        if(didntWork){
//            System.out.println("Hmm your code didn't raise the NoSuchElementException...");
        }
        assertFalse(didntWork, "Hmm your code didn't raise the NoSuchElementException...");
    }

}