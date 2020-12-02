package assignment3;

import RuntimeTester.benchmark;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class SpeedTester {

    @benchmark(name = "shelter()", expectedEfficiency = "O(log(n))", category = "DogShelter")
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
        long startTime = System.nanoTime();
//        for (int i = 0; i < input; i++) {
        shelter.shelter(toAdd);
//        }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    @benchmark(name = "shelter()", expectedEfficiency = "O(log(n))", category = "DogShelter")
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
        long startTime = System.nanoTime();
        shelter.adopt();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
}
