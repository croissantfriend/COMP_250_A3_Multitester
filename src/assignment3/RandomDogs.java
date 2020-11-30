package assignment3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RandomDogs extends DogShelter {
    Random rand;
    ArrayList<String> names = new ArrayList<>();
    File Lastnames = new File("src/COMP250_A3_W2020/Put Your java files here.txt");

    public RandomDogs(long seed) {
        super(new Dog("trick to access inner methods of cattree :) Ignore me", 1, 10, 243, 0));
        rand = new Random(seed);
        try {
            Scanner scanner = new Scanner(Lastnames);
            while (scanner.hasNextLine()) {
                names.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            try {
                Lastnames = new File("supportfiles/last_names.all.txt");
                Scanner scanner = new Scanner(Lastnames);
                while (scanner.hasNextLine()) {
                    names.add(scanner.nextLine());
                }
            } catch (FileNotFoundException f) {
                try {
                    Lastnames = new File("last_names.all.txt");
                    Scanner scanner = new Scanner(Lastnames);
                    while (scanner.hasNextLine()) {
                        names.add(scanner.nextLine());
                    }
                } catch (FileNotFoundException g) {
                    try {
                        Lastnames = new File("Put Your java files here.txt");
                        Scanner scanner = new Scanner(Lastnames);
                        while (scanner.hasNextLine()) {
                            names.add(scanner.nextLine());
                        }
                    } catch (FileNotFoundException h) {
                        try {
                            Lastnames = new File("supportfiles\first_names.all.txt");
                            Scanner scanner = new Scanner(Lastnames);
                            while (scanner.hasNextLine()) {
                                names.add(scanner.nextLine());
                            }
                        } catch (FileNotFoundException i) {

                        }
                    }
                }
            }
        }
    }

    public RandomDogs() {
        super(new Dog("trick to access inner methods of cattree :) Ignore me", 1, 10, 243, 0));
        rand = new Random();
        try {
            Scanner scanner = new Scanner(Lastnames);
            while (scanner.hasNextLine()) {
                names.add(scanner.nextLine());
            }
        } catch (FileNotFoundException ignored) {
        }
    }

    public int nextInt(int i) {
        return rand.nextInt(Math.abs(i));
    }

    public Dog nextDog() {
        return new Dog(nextName(), 1 + rand.nextInt(100), rand.nextInt(365), rand.nextInt(365), rand.nextInt(20000) / 100.0);
    }

    public DogNode nextDogNode() {
        return new DogNode(nextDog());
    }

    public DogShelter nextDogShelter(int numNodes) {
        DogShelter output = new DogShelter(nextDog());
        if (numNodes > 1) {
            for (int i = 0; i < numNodes; i++) {
                output.shelter(nextDog());
            }
        }
        return output;
    }

    public DogShelter nextDogShelter() {
        DogShelter output = new DogShelter(nextDog());
        int numNodes = 1 + rand.nextInt(25);
        if (numNodes > 1) {
            for (int i = 0; i < numNodes; i++) {
                output.shelter(nextDog());
            }
        }
        return output;
    }

    public String nextName() {
        try {
            int numNames = rand.nextInt(names.size());
            String output = names.get(numNames);
            names.remove(numNames);
            return output;

        } catch (Exception e) {

            int nameLength = rand.nextInt(11);
            nameLength += 1;
            char[] nameOutChar = new char[nameLength];
            for (int i = 0; i < nameLength; i++) {
                if (nameLength <= 4) {
                    nameOutChar[i] = (char) rand.nextInt(18500);
                    nameOutChar[i] += (char) rand.nextInt(5000);
                } else {
                    int nameNumber = rand.nextInt(90 - 65) + 65;
                    nameOutChar[i] = (char) nameNumber;
                    if (i == nameLength - 3) {
                        nameOutChar[i + 1] = (char) 85;
                    }
                }
            }
            //System.out.println(nameOut);
            return String.valueOf(nameOutChar);
        }


    }

    public DViz nextDViz() {
        DViz viz = new DViz(nextDog());
        int numNodes = 1 + rand.nextInt(2);
        if (numNodes > 1) {
            for (int i = 0; i < numNodes; i++) {
                viz.shelter(nextDog());
            }
        }
        return viz;
    }


}
