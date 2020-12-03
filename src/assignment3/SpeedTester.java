package assignment3;

import RuntimeTester.Visualizer;
import RuntimeTester.benchmark;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class SpeedTester {

    public static final String categoryName = "DogShelter";

    @benchmark(name = "shelter()", expectedEfficiency = "O(log(n))", category = categoryName)
    public static long testShelter(long input) throws FileNotFoundException {
        Random random = new Random();
        File file = new File("src/assignment3/first_names.all.txt");
        Scanner scanner = new Scanner(file);
        String name = "None";
        if (scanner.hasNextLine())
            name = scanner.nextLine();
        Dog d = new Dog("FirstDog", random.nextInt(), random.nextInt(), random.nextInt(), random.nextDouble());
        DogShelter shelter = new DogShelter(d);
        Dog toAdd = new Dog(name, random.nextInt(), random.nextInt(), random.nextInt(), random.nextDouble());
        /* You can use TreePrinter here to show you the tree before the add - just be careful when
           using it for very large trees! */
        long startTime = System.nanoTime();
        shelter.shelter(toAdd);
        long endTime = System.nanoTime();
        /* You can use TreePrinter here to show you the tree after to make sure you're doing this right - just be careful when
           using it for very large trees! */
        return endTime - startTime;
    }

    @benchmark(name = "shelter()", expectedEfficiency = "O(log(n))", category = categoryName)
    public static long testAdopt(long input) throws FileNotFoundException {
        Random random = new Random();
        File file = new File("src/assignment3/first_names.all.txt");
        Scanner scanner = new Scanner(file);
        String name = "None";
        if (scanner.hasNextLine())
            name = scanner.nextLine();
        Dog d = new Dog("FirstDog", random.nextInt(), random.nextInt(), random.nextInt(), random.nextDouble());
        DogShelter shelter = new DogShelter(d);
        Dog toAdopt = new Dog(name, random.nextInt(), random.nextInt(), random.nextInt(), random.nextDouble());
        for (int i = 0; i < input; i++) {
            shelter.shelter(toAdopt);
        }
        /* You can use TreePrinter here to show you the tree before the adoption - just be careful when
           using it for very large trees! */
        long startTime = System.nanoTime();
        shelter.adopt();
        long endTime = System.nanoTime();
        /* You can use TreePrinter here to show you the tree after to make sure you're doing this right - just be careful when
           using it for very large trees! */
        return endTime - startTime;
    }

    @benchmark(name = "findOldest()", expectedEfficiency = "O(log(n))", category = categoryName)
    public static long testFindOldest(long input) {
        Random random = new Random();
        Dog d = new Dog("FirstDog", random.nextInt(), random.nextInt(), random.nextInt(), random.nextDouble());
        DogShelter shelter = new DogShelter(d);
        long startTime = System.nanoTime();
        Dog o = shelter.findOldest();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    @benchmark(name = "findYoungest()", expectedEfficiency = "O(log(n))", category = categoryName)
    public static long testFindYoungest(long input) {
        Random random = new Random();
        Dog d = new Dog("FirstDog", random.nextInt(), random.nextInt(), random.nextInt(), random.nextDouble());
        DogShelter shelter = new DogShelter(d);
        long startTime = System.nanoTime();
        Dog y = shelter.findYoungest();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    @benchmark(name="Shelter", category = categoryName, expectedEfficiency = "O(logn)")
    public static long testShelter(long size){
        // tests shelter on a random shelter of size size
        // DOES NOT TEST CORRECTNESS OF SHELTER()
        Dog A = new Dog("A", 15, 198,5,5.0);
        DogShelter d = new DogShelter(A);
        for (int i = 0; i < size; i++){
            int y = rand.nextInt( Integer.MAX_VALUE );
            int x = rand.nextInt( Integer.MAX_VALUE );
            d.shelter(new Dog("B", y, x,5,5.0));
        }
        long startTime = System.nanoTime();
        d.shelter(new Dog("G", rand.nextInt(), rand.nextInt(), 7, 9.0));
        long endTime = System.nanoTime();
        return endTime-startTime;
    }

    @benchmark(name = "Adopt", category = categoryName, expectedEfficiency = "O(logn)?")
    public static long testAdopt(long size){
        // tests adopt on random shelter of size size
        // DOES NOT TEST CORRECTNESS OF ADOPT()
        Dog A = new Dog("A", rand.nextInt(), rand.nextInt(),5,5.0);
        DogShelter d = new DogShelter(A);
        for (int i = 0; i < size; i++){
            d.shelter(new Dog("B", rand.nextInt(), rand.nextInt(),5,5.0));
        }
        long startTime = System.nanoTime();
        d.adopt(A);
        long endTime = System.nanoTime();
        return endTime-startTime;
    }

    @benchmark(name = "FindOldest", category = categoryName, expectedEfficiency = "O(n)")
    public static long testFindOldest(long size){
        // tests findOldest on random shelter of size size
        // DOES NOT TEST CORRECTNESS OF FINDOLDEST()
        Dog A = new Dog("A", rand.nextInt(), rand.nextInt(),5,5.0);
        DogShelter d = new DogShelter(A);
        for (int i = 0; i < size; i++){
            d.shelter(new Dog("B", rand.nextInt(), rand.nextInt(),5,5.0));
        }
        long startTime = System.nanoTime();
        d.findOldest();
        long endTime = System.nanoTime();
        return endTime-startTime;
    }

    @benchmark(name = "FindYoungest", category = categoryName, expectedEfficiency = "O(n)")
    public static long testFindYoungest(long size){
        // tests findYoungest on random shelter of size size
        // DOES NOT TEST CORRECTNESS OF FINDYOUNGEST
        Dog A = new Dog("A", rand.nextInt(), rand.nextInt(),5,5.0);
        DogShelter d = new DogShelter(A);
        for (int i = 0; i < size; i++){
            d.shelter(new Dog("B", rand.nextInt(), rand.nextInt(),5,5.0));
        }
        long startTime = System.nanoTime();
        d.findYoungest();
        long endTime = System.nanoTime();
        return endTime-startTime;
    }

    @benchmark(name = "FindDogToAdopt", category = categoryName, expectedEfficiency = "O(n)")
    public static long testDogToAdopt(long size){
        // tests findDogToAdopt on a random shelter of size size with a random adoption range
        // DOES NOT TEST CORRECTNESS OF FINDDOGTOADOPT()
        Dog A = new Dog("A", rand.nextInt(), rand.nextInt(),5,5.0);
        DogShelter d = new DogShelter(A);
        for (int i = 0; i < size; i++){
            d.shelter(new Dog("B", rand.nextInt(), rand.nextInt(),5,5.0));
        }
        int y = rand.nextInt();
        int x = rand.nextInt();
        int max = (x>y)?x:y;
        int min = (x>y)?y:x;
        long startTime = System.nanoTime();
        d.findDogToAdopt(min, max);
        long endTime = System.nanoTime();
        return endTime-startTime;
    }

    @benchmark(name = "GetVetExpenses", category = categoryName, expectedEfficiency = "O(n)")
    public static long testGetVetExpenses(long size) {
        // tests getVetExpenses on a random shelter of size size
        // all input dogs have their vet appointment at most 180 days from now
        // the input to getVetExpenses is max integer value should add all dogs to expenses
        // DOES NOT TEST CORRECTNESS OF GETVETEXPENSES()
        Dog A = new Dog("A", rand.nextInt(), rand.nextInt(), rand.nextInt(180), rand.nextDouble());
        DogShelter d = new DogShelter(A);
        for (int i = 0; i < size; i++) {
            d.shelter(new Dog("B", rand.nextInt(), rand.nextInt(), rand.nextInt(180), rand.nextDouble()));
        }
        long startTime = System.nanoTime();
        d.budgetVetExpenses(Integer.MAX_VALUE);
        long endTime = System.nanoTime();
        return endTime-startTime;
    }

    @benchmark(name = "GetVetSchedule", category = categoryName, expectedEfficiency = "O(n)")
    public static long testGetVetSchedule(long size) {
        // tests findDogToAdopt on a random shelter of size size wwith next vet appointment at most 180 days from now
        // DOES NOT TEST CORRECTNESS OF GETVETSCHEDULE
        Dog A = new Dog("A", rand.nextInt(), rand.nextInt(), rand.nextInt(180), 5.0);
        DogShelter d = new DogShelter(A);
        for (int i = 0; i < size; i++) {
            d.shelter(new Dog("B", rand.nextInt(), rand.nextInt(), rand.nextInt(180), 5.0));
        }
        long startTime = System.nanoTime();
        d.getVetSchedule();
        long endTime = System.nanoTime();
        return endTime-startTime;
    }

    public static void main(String[] args) {
        Visualizer.launch(SpeedTester.class);
    }

}
