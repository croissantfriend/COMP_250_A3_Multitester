package assignment3;

//import manifold.ext.rt.api.Jailbreak;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Megatester2 {

    DogShelter.DogNode prev;

    boolean isBST(DogShelter.DogNode node) {
        prev = null;
        if (node == null) {
            return true;
        }
        return isBSTHelper(node) && isHeap(node) && validParentRef(node.younger) && validParentRef(node.older);
    }

    boolean isHeap(DogShelter.DogNode root) {
        // base case
        if (root == null) {
            return true;
        }

        // current node has greater value than its left or right child
        if ((root.younger != null && root.younger.d.getDaysAtTheShelter() >= root.d.getDaysAtTheShelter()) ||
                (root.older != null && root.older.d.getDaysAtTheShelter() >= root.d.getDaysAtTheShelter())) {
            return false;
        }

        // check for left and right subtree
        return isHeap(root.younger) && isHeap(root.older);
    }

    boolean isBSTHelper(DogShelter.DogNode node) {
        // traverse the tree in inorder fashion and
        // keep a track of previous node
        if (node != null) {
            if (!isBSTHelper(node.younger))
                return false;

            // allows only distinct values node
            if (prev != null && node.d.getAge() <= prev.d.getAge())
                return false;
            prev = node;
            return isBSTHelper(node.older);
        }
        return true;
    }

    boolean validParentRef(DogShelter.DogNode childRoot) {
        if (childRoot == null) {
            return true;
        }

        if (childRoot.parent == null) {
            return false;
        }

        boolean isChild = childRoot.parent.younger == childRoot || childRoot.parent.older == childRoot;

        if (!isChild) {
            return false;
        }

        return validParentRef(childRoot.younger) && validParentRef(childRoot.older);
    }

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

    DogShelter generateLargeSampleShelter() {
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
        shelter.shelter(P);
        shelter.shelter(B);
        shelter.shelter(C);
        shelter.shelter(A);
        shelter.shelter(D);
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
        Assertions.assertTrue(isBST(shelter.root));
    }

    @Test
    @DisplayName("Should test generateSampleShelter and generateLargeSampleShelter")
    void testShelter2() {
        Assertions.assertAll(
                () -> isBST(generateSampleShelter().root),
                () -> isBST(generateLargeSampleShelter().root)
        );
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
    @DisplayName("Should find the oldest dog in a sub tree")
    void testFindOldestSub() {
        DogShelter shelter = generateLargeSampleShelter();
        Dog d = shelter.root.older.findOldest();
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
        Assertions.assertEquals(P,d);
    }

    @Test
    @DisplayName("Should return null for findOldest in empty tree")
    void testFindOldestNull() {
        DogShelter shelter = new DogShelter(new Dog("asd", 1, 2, 3,4));
        shelter.adopt();
        Dog d = shelter.findOldest();
//        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
        Assertions.assertNull(d);
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
    @DisplayName("Should find the youngest dog in a sub tree")
    void testFindYoungestSub() {
        DogShelter shelter = generateLargeSampleShelter();
        Dog d = shelter.root.older.findYoungest();
        Dog D = new Dog("Daisy", 7, 15, 12, 35.0);
        Assertions.assertEquals(D, d);
    }

    @Test
    @DisplayName("Should return null for findYoungest in empty tree")
    void testFindYoungestNull() {
        DogShelter shelter = new DogShelter(new Dog("asd", 1, 2, 3,4));
        shelter.adopt();
        Dog d = shelter.findYoungest();
//        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
        Assertions.assertNull(d);
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

        Assertions.assertEquals(dogs, expected);
    }

    @Test
    @DisplayName("Should throw exception for DogNode Iterator on no next")
    void testIterator2() {
        DogShelter shelter = generateSampleShelter();
        for (int i = 0; i < 4; i++) {
            shelter.adopt();
        }
        try {
            Iterator<Dog> iterator = shelter.iterator();
            iterator.next();
            Assertions.fail("Did not throw exception");
        } catch (NoSuchElementException e) {
            System.out.println("Successfully thrown exception");
        }
    }

    @Test
    @DisplayName("Should test iterator on large sample shelter")
    void testIterator3() {
        DogShelter largeShelter = generateLargeSampleShelter();
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
        Dog B = new Dog("Bella", 1, 55, 15, 120.0);
        Dog C = new Dog("Cloud", 4, 10, 23, 80.0);
        Dog A = new Dog("Archie", 6, 120, 18, 40.0);
        Dog D = new Dog("Daisy", 7, 15, 12, 35.0);
        ArrayList<Dog> expected = new ArrayList<>();
        expected.add(R);
        expected.add(S);
        expected.add(L);
        expected.add(P);
        expected.add(B);
        expected.add(C);
        expected.add(A);
        expected.add(D);
        expected.sort(Comparator.comparingInt(Dog::getAge));

        ArrayList<Dog> result = new ArrayList<>();
        for (Dog d : largeShelter) {
            result.add(d);
        }
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should find correct vet budget 1")
    void testVetBudget1() {
        DogShelter shelter = generateSampleShelter();
        double budget = shelter.budgetVetExpenses(9);
        Assertions.assertEquals(budget, 325);
    }

    @Test
    @DisplayName("Should find correct vet budget for null tree")
    void testVetBudget2() {
        DogShelter shelter = new DogShelter(new Dog("asd", 1, 2, 3,4));
        shelter.adopt();
        double budget = shelter.budgetVetExpenses(9);
        Assertions.assertEquals(0, budget);
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
        Assertions.assertTrue(isBST(shelter.root));
    }

    @Test
    @DisplayName("Should adopt iteratively all dogs correctly")
    void testAdopt2() {
        DogShelter shelter = generateSampleShelter();
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog Z = new Dog("Z", 4, 200, 10, 25.0);
        Assertions.assertEquals(shelter.adopt(), Z);
        Assertions.assertTrue(isBST(shelter.root));
        Assertions.assertEquals(shelter.adopt(), R);
        Assertions.assertTrue(isBST(shelter.root));
        Assertions.assertEquals(shelter.adopt(), L);
        Assertions.assertTrue(isBST(shelter.root));
        Assertions.assertEquals(shelter.adopt(), S);
        Assertions.assertTrue(isBST(shelter.root));
    }

    @Test
    @DisplayName("Should test adopt with all-left branches")
    void testAdopt3() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 90, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 80, 9, 25.0);
        DogShelter shelter = new DogShelter(R);
        shelter.shelter(S);
        shelter.shelter(L);

        Assertions.assertEquals(shelter.adopt(), R);
        Assertions.assertTrue(isBST(shelter.root));
        Assertions.assertEquals(shelter.adopt(), S);
        Assertions.assertTrue(isBST(shelter.root));
        Assertions.assertEquals(shelter.adopt(), L);
        Assertions.assertTrue(isBST(shelter.root));
        Assertions.assertNull(shelter.root);
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
        Assertions.assertTrue(isBST(shelter.root));
        Assertions.assertEquals(shelter.adopt(), S);
        Assertions.assertTrue(isBST(shelter.root));
        Assertions.assertEquals(shelter.adopt(), L);
        Assertions.assertTrue(isBST(shelter.root));
        Assertions.assertNull(shelter.root);
    }

    @Test
    @DisplayName("Should test adopt from bottom with all-right branches")
    void testAdopt5() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 10, 90, 2, 250.0);
        Dog L = new Dog("Lessie", 12, 80, 9, 25.0);
        DogShelter shelter = new DogShelter(R);
        shelter.shelter(S);
        shelter.shelter(L);

        shelter.adopt(L);
        Assertions.assertEquals(shelter.root.d, R);
        Assertions.assertEquals(shelter.root.older.d, S);
        Assertions.assertNull(shelter.root.younger);
        Assertions.assertEquals(shelter.root.older.parent, shelter.root);
        Assertions.assertTrue(isBST(shelter.root));

        shelter.adopt(S);
        Assertions.assertNull(shelter.root.older);
        Assertions.assertNull(shelter.root.younger);
        Assertions.assertTrue(isBST(shelter.root));

        shelter.adopt(R);
        Assertions.assertNull(shelter.root);
    }

    @Test
    @DisplayName("Should comprehensively test adopt")
    void testAdopt6() {
        DogShelter largeShelter = generateLargeSampleShelter();
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
        Dog B = new Dog("Bella", 1, 55, 15, 120.0);
        Dog C = new Dog("Cloud", 4, 10, 23, 80.0);
        Dog A = new Dog("Archie", 6, 120, 18, 40.0);
        Dog D = new Dog("Daisy", 7, 15, 12, 35.0);

        largeShelter.adopt(A);
        Assertions.assertEquals(R, largeShelter.root.d);
        Assertions.assertEquals(P, largeShelter.root.older.d);
        Assertions.assertTrue(isBST(largeShelter.root));

        largeShelter.adopt(S);
        Assertions.assertEquals(D, largeShelter.root.younger.older.d);
        Assertions.assertEquals(C, largeShelter.root.younger.older.younger.d);
//        System.out.println("hi");
        Assertions.assertTrue(isBST(largeShelter.root));


        largeShelter.adopt(B);
        Assertions.assertNull(largeShelter.root.younger.younger);
        Assertions.assertTrue(isBST(largeShelter.root));


        largeShelter.adopt(C);
        Assertions.assertNull(largeShelter.root.younger.older.younger);
        Assertions.assertTrue(isBST(largeShelter.root));

        largeShelter.adopt(L);
        Assertions.assertEquals(D, largeShelter.root.younger.d);
        Assertions.assertTrue(isBST(largeShelter.root));

    }

    @Test
    @DisplayName("Should comprehensively test adopt #2")
    void testAdopt7() {
        DogShelter largeShelter = generateLargeSampleShelter();
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
        Dog B = new Dog("Bella", 1, 55, 15, 120.0);
        Dog C = new Dog("Cloud", 4, 10, 23, 80.0);
        Dog A = new Dog("Archie", 6, 120, 18, 40.0);
        Dog D = new Dog("Daisy", 7, 15, 12, 35.0);

        largeShelter.adopt(R);
        Assertions.assertEquals(P, largeShelter.root.older.d);
        Assertions.assertTrue(isBST(largeShelter.root));


        largeShelter.adopt(B);
        Assertions.assertNull(largeShelter.root.younger.younger);
        Assertions.assertTrue(isBST(largeShelter.root));
    }

    @Test
    @DisplayName("Should test adopting on non-root node")
    void testAdopt8() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
        Dog B = new Dog("Bella", 1, 55, 15, 120.0);
        Dog C = new Dog("Cloud", 4, 10, 23, 80.0);
        Dog A = new Dog("Archie", 6, 120, 18, 40.0);
        Dog D = new Dog("Daisy", 7, 15, 12, 35.0);

        DogShelter s = new DogShelter(R);
        s.shelter(S);
        s.shelter(L);
        s.shelter(P);
        s.shelter(B);
        s.shelter(C);
        s.shelter(A);
        s.shelter(D);

//        System.out.println("Before: ");
//        System.out.println(s.root);
        String val0 = s.root.toString();
        String expected0 = "Archie(6 , 120)\n" +
                "younger than Archie(6 , 120) :\n" +
                "Lessie(3 , 70)\n" +
                "younger than Lessie(3 , 70) :\n" +
                "Bella(1 , 55)\n" +
                "older than Lessie(3 , 70) :\n" +
                "Stella(5 , 50)\n" +
                "younger than Stella(5 , 50) :\n" +
                "Cloud(4 , 10)\n" +
                "older than Archie(6 , 120) :\n" +
                "Rex(8 , 100)\n" +
                "younger than Rex(8 , 100) :\n" +
                "Daisy(7 , 15)\n" +
                "older than Rex(8 , 100) :\n" +
                "Poldo(10 , 60)\n";
        Assertions.assertEquals(expected0, val0);
        Assertions.assertTrue(isBST(s.root));
/* Displays:
Before:
Archie(6 , 120)
younger than Archie(6 , 120) :
Lessie(3 , 70)
younger than Lessie(3 , 70) :
Bella(1 , 55)
older than Lessie(3 , 70) :
Stella(5 , 50)
younger than Stella(5 , 50) :
Cloud(4 , 10)
older than Archie(6 , 120) :
Rex(8 , 100)
younger than Rex(8 , 100) :
Daisy(7 , 15)
older than Rex(8 , 100) :
Poldo(10 , 60)
*/


        DogShelter.DogNode x = s.root.younger.adopt(L);
        Assertions.assertTrue(isBST(s.root));

//        System.out.println("\nAfter the adoption: ");
//        System.out.println(x);
        String val = x.toString();
        String expected = "Bella(1 , 55)\n"
                + "older than Bella(1 , 55) :\n"
                + "Stella(5 , 50)\n"
                + "younger than Stella(5 , 50) :\n"
                + "Cloud(4 , 10)\n";
        Assertions.assertEquals(expected, val);
/* Displays
After the adoption:
Bella(1 , 55)
older than Bella(1 , 55) :
Stella(5 , 50)
younger than Stella(5 , 50) :
Cloud(4 , 10)
*/
//
//        System.out.println("\nAfter the adoption: ");
//        System.out.println(s.root);
        String val2 = s.root.toString();
        String expected2 = "Archie(6 , 120)\n"
                + "younger than Archie(6 , 120) :\n"
                + "Bella(1 , 55)\n"
                + "older than Bella(1 , 55) :\n"
                + "Stella(5 , 50)\n"
                + "younger than Stella(5 , 50) :\n"
                + "Cloud(4 , 10)\n"
                + "older than Archie(6 , 120) :\n"
                + "Rex(8 , 100)\n"
                + "younger than Rex(8 , 100) :\n"
                + "Daisy(7 , 15)\n"
                + "older than Rex(8 , 100) :\n"
                + "Poldo(10 , 60)\n";
        Assertions.assertEquals(expected2, val2);
        Assertions.assertTrue(isBST(s.root));
/* Displays
After the adoption:
Archie(6 , 120)
younger than Archie(6 , 120) :
Bella(1 , 55)
older than Bella(1 , 55) :
Stella(5 , 50)
younger than Stella(5 , 50) :
Cloud(4 , 10)
older than Archie(6 , 120) :
Rex(8 , 100)
younger than Rex(8 , 100) :
Daisy(7 , 15)
older than Rex(8 , 100) :
Poldo(10 , 60)
*/
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
        Assertions.assertTrue(isBST(shelter.root));

        shelter.shelter(R);
        shelter.shelter(P);
        shelter.adopt(R);

        Assertions.assertEquals(shelter.root.d, L);
        Assertions.assertTrue(isBST(shelter.root));

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
                },
                () -> Assertions.assertTrue(isBST(shelter.root))

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
                () -> Assertions.assertEquals(root.younger.older.younger.parent, root.younger.older),
                () -> Assertions.assertTrue(isBST(shelter.root))
        );

        Assertions.assertEquals(shelter.adopt(), R);
        Assertions.assertTrue(isBST(shelter.root));

        Assertions.assertEquals(shelter.adopt(), L);
        Assertions.assertTrue(isBST(shelter.root));

        Assertions.assertEquals(shelter.adopt(), P);
        Assertions.assertTrue(isBST(shelter.root));

        Assertions.assertEquals(shelter.adopt(), B);
        Assertions.assertTrue(isBST(shelter.root));

        Assertions.assertEquals(shelter.adopt(), S);
        Assertions.assertTrue(isBST(shelter.root));

        Assertions.assertEquals(shelter.adopt(), D);
        Assertions.assertTrue(isBST(shelter.root));

        Assertions.assertEquals(shelter.adopt(), C);
        Assertions.assertTrue(isBST(shelter.root));

        Assertions.assertNull(shelter.root);
        Assertions.assertTrue(isBST(shelter.root));
    }
}