package assignment3;

import RuntimeTester.benchmark;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class Megatester {

    public static final String categoryName = "DogShelter";

    // The shelter
    DogShelter shelter;

    // Speedtests
    @benchmark(name = "Shelter", category = categoryName, expectedEfficiency = "O(logn)")
    public static long testShelter(long size) {
        // tests shelter on a random shelter of size size
        // DOES NOT TEST CORRECTNESS OF SHELTER()
        Random rand = new Random();
        Dog A = new Dog("A", 15, 198, 5, 5.0);
        DogShelter d = new DogShelter(A);
        for (int i = 0; i < size; i++) {
            int y = rand.nextInt(Integer.MAX_VALUE);
            int x = rand.nextInt(Integer.MAX_VALUE);
            d.shelter(new Dog("B", y, x, 5, 5.0));
        }
        long startTime = System.nanoTime();
        d.shelter(new Dog("G", rand.nextInt(), rand.nextInt(), 7, 9.0));
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    @benchmark(name = "Adopt", category = categoryName, expectedEfficiency = "O(logn)?")
    public static long testAdopt(long size) {
        // tests adopt on random shelter of size size
        // DOES NOT TEST CORRECTNESS OF ADOPT()
        Random rand = new Random();
        Dog A = new Dog("A", rand.nextInt(), rand.nextInt(), 5, 5.0);
        DogShelter d = new DogShelter(A);
        for (int i = 0; i < size; i++) {
            d.shelter(new Dog("B", rand.nextInt(), rand.nextInt(), 5, 5.0));
        }
        long startTime = System.nanoTime();
        d.adopt(A);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    @benchmark(name = "FindOldest", category = categoryName, expectedEfficiency = "O(logn)")
    public static long testFindOldest(long size) {
        // tests findOldest on random shelter of size size
        // DOES NOT TEST CORRECTNESS OF FINDOLDEST()
        Random rand = new Random();
        Dog A = new Dog("A", rand.nextInt(), rand.nextInt(), 5, 5.0);
        DogShelter d = new DogShelter(A);
        for (int i = 0; i < size; i++) {
            d.shelter(new Dog("B", rand.nextInt(), rand.nextInt(), 5, 5.0));
        }
        long startTime = System.nanoTime();
        d.findOldest();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    @benchmark(name = "FindYoungest", category = categoryName, expectedEfficiency = "O(logn)")
    public static long testFindYoungest(long size) {
        // tests findYoungest on random shelter of size size
        // DOES NOT TEST CORRECTNESS OF FINDYOUNGEST
        Random rand = new Random();
        Dog A = new Dog("A", rand.nextInt(), rand.nextInt(), 5, 5.0);
        DogShelter d = new DogShelter(A);
        for (int i = 0; i < size; i++) {
            d.shelter(new Dog("B", rand.nextInt(), rand.nextInt(), 5, 5.0));
        }
        long startTime = System.nanoTime();
        d.findYoungest();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    // My test 🐶
    Dog max = new Dog("Max", 11, 0, 2, 100.0);

    @benchmark(name = "GetVetExpenses", category = categoryName, expectedEfficiency = "O(n)")
    public static long testGetVetExpenses(long size) {
        // tests getVetExpenses on a random shelter of size size
        // all input dogs have their vet appointment at most 180 days from now
        // the input to getVetExpenses is max integer value should add all dogs to expenses
        // DOES NOT TEST CORRECTNESS OF GETVETEXPENSES()
        Random rand = new Random();
        Dog A = new Dog("A", rand.nextInt(), rand.nextInt(), rand.nextInt(180), rand.nextDouble());
        DogShelter d = new DogShelter(A);
        for (int i = 0; i < size; i++) {
            d.shelter(new Dog("B", rand.nextInt(), rand.nextInt(), rand.nextInt(180), rand.nextDouble()));
        }
        long startTime = System.nanoTime();
        d.budgetVetExpenses(Integer.MAX_VALUE);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    @benchmark(name = "GetVetSchedule", category = categoryName, expectedEfficiency = "O(n)")
    public static long testGetVetSchedule(long size) {
        // tests findDogToAdopt on a random shelter of size size wwith next vet appointment at most 180 days from now
        // DOES NOT TEST CORRECTNESS OF GETVETSCHEDULE
        Random rand = new Random();
        Dog A = new Dog("A", rand.nextInt(), rand.nextInt(), rand.nextInt(180), 5.0);
        DogShelter d = new DogShelter(A);
        for (int i = 0; i < size; i++) {
            d.shelter(new Dog("B", rand.nextInt(), rand.nextInt(), rand.nextInt(180), 5.0));
        }
        long startTime = System.nanoTime();
        d.getVetSchedule();
        long endTime = System.nanoTime();
        return endTime - startTime;
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

    // Louis-Philippe Robichaud's tests // TODO

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

    Dog neptune = new Dog("Neptune", 12, 3, 250, 0.0);
    Dog izzie = new Dog("Izzie", 6, 5, 15, 150.0);

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
        // Visualizer.launch(Megatester.class); // Comment this if you don't want to see the visualization
    }

    @benchmark(name = "FindDogToAdopt", category = categoryName, expectedEfficiency = "O(logn)")
    public static long testDogToAdopt(long size) {
        // tests findDogToAdopt on a random shelter of size size with a random adoption range
        // DOES NOT TEST CORRECTNESS OF FINDDOGTOADOPT()
        Random rand = new Random();
        Dog A = new Dog("A", rand.nextInt(), rand.nextInt(), 5, 5.0);
        DogShelter d = new DogShelter(A);
        for (int i = 0; i < size; i++) {
            d.shelter(new Dog("B", rand.nextInt(), rand.nextInt(), 5, 5.0));
        }
        int y = rand.nextInt();
        int x = rand.nextInt();
        int max = Math.max(x, y);
        int min = Math.min(x, y);
        long startTime = System.nanoTime();
        d.findDogToAdopt(min, max);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    @AfterEach
    void destroyShelter() {
        shelter = null;
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
}