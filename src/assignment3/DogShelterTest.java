package assignment3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

// James Xu's tests
public class DogShelterTest {

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
    @DisplayName("Should test adopt with all-left branches")
    void testAdopt3() {
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

    // From meow10811's Test Suite
    @Test
    void WhenNoMoreElementsIteratorShouldThrowException() {
        DogShelter shelter = generateSampleShelter2();
        Iterator<Dog> shelterIterator = shelter.iterator();

        shelterIterator.next();
        Assertions.assertThrows(NoSuchElementException.class, shelterIterator::next);
    }

}