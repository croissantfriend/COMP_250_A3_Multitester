package assignment3;

import RuntimeTester.Visualizer;
import RuntimeTester.benchmark;

import java.util.Random;

public class SpeedTester {

    public static final String categoryName = "DogShelter";

    public static void main(String[] args) {
        Visualizer.launch(Megatester.class); // Comment this if you don't want to see the visualization
    }

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
}
