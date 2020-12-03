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

    public static void main(String[] args) {
        Visualizer.launch(SpeedTester.class);
    }

}
