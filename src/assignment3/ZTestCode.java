package assignment3;

// Louis-Philippe Robichaud's tests
public class ZTestCode {

    //create a root only tree
    public static DogShelter createRootTree(Dog d) {
        DogShelter s = new DogShelter(d);
        return s;
    }

    public static DogShelter createRootOlder() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
        DogShelter s = new DogShelter(R);
        s.shelter(P);
        return s;
    }

    public static DogShelter createRootYounger() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 7, 250.0);
        DogShelter s = new DogShelter(R);
        s.shelter(S);
        return s;
    }

    public static DogShelter leftUnbalanced() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog P = new Dog("Poldo", 10, 120, 1, 35.0);
        Dog S = new Dog("Stella", 5, 80, 2, 250.0);

        DogShelter s = new DogShelter(P);
        s.shelter(R);
        s.shelter(S);
        s.shelter(L);
        return s;
    }

    public static DogShelter rightUnbalanced() {
        Dog R = new Dog("Rex", 8, 80, 5, 50.0);
        Dog L = new Dog("Lessie", 3, 120, 9, 25.0);
        Dog P = new Dog("Poldo", 10, 70, 1, 35.0);
        Dog S = new Dog("Stella", 5, 100, 2, 250.0);

        DogShelter s = new DogShelter(P);
        s.shelter(R);
        s.shelter(S);
        s.shelter(L);
        System.out.println(s.root);
        return s;
    }

    public static DogShelter bigTree() {
        Dog R = new Dog("Rex", 8, 70, 5, 50.0);
        Dog L = new Dog("Lessie", 3, 11, 9, 25.0);
        Dog P = new Dog("Poldo", 10, 35, 1, 35.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog F = new Dog("Freud", 6, 85, 8, 250.0);
        Dog U = new Dog("Unicorn", 4, 10, 14, 250.0);
        Dog K = new Dog("Kayleigh", 11, 1, 15, 100.0);
        Dog M = new Dog("Martha", 7, 65, 15, 100.0);

        DogShelter s = new DogShelter(R);
        s.shelter(L);
        s.shelter(P);
        s.shelter(S);
        s.shelter(F);
        s.shelter(U);
        s.shelter(K);
        s.shelter(M);
        return s;
    }

    public static void main(String[] args) {
        //create the shelter
        Dog d = new Dog("Rex", 8, 70, 1, 50.0);
        Dog fake = new Dog("Khan", 100, 100, 6, 60.0);
        /*
         * CHANGE THE NAME OF THE METHOD TO CREATE THE SHELTER HERE
         */
        DogShelter s = rightUnbalanced();
        DogShelter p = new DogShelter(d);
        p.adopt(d);
        for (Dog n : s) {
            p.shelter(n);
        }
        System.out.println(p.root);
        //starting the testing

        System.out.println("Testing Adopt");
        System.out.println("Adopting null");
        p.adopt(null);
        System.out.println(p.root);

        System.out.println("Adopting oldest dog");
        Dog dog = p.adopt();
        System.out.println(p.root);
        p.shelter(dog);

        System.out.println("Adopting unexisting dog");
        p.adopt(fake);
        System.out.println(p.root);
        System.out.println("Adopting all dog in the tree");

        for (Dog n : p) {
            p.adopt(n);
            System.out.println(p.root);
        }

        System.out.println("---------------------------------------------------------");
        for (Dog n : s) {
            p.shelter(n);
        }

        System.out.println("Testing findOldest");
        System.out.println(p.findOldest());

        System.out.println("Testing findYoungest");
        System.out.println(p.findYoungest());

        System.out.println("---------------------------------------------------------");
        System.out.println("Testing findDogToAdopt");
        System.out.println("Testing with impossible ages");
        System.out.println(p.findDogToAdopt(100, 110));
        System.out.println("Testing with ages 0-5");
        System.out.println(p.findDogToAdopt(0, 5));
        System.out.println("Testing with ages 5-10");
        System.out.println(p.findDogToAdopt(5, 10));
        System.out.println("Testing with all ages");
        System.out.println(p.findDogToAdopt(0, 100));

        System.out.println("---------------------------------------------------------");

        System.out.println("Testing budgetVetExpenses");
        System.out.println("Testing for 0 days");
        System.out.println("Expenses are $" + p.budgetVetExpenses(0));
        System.out.println("Testing for 5 days");
        System.out.println("Expenses are $" + p.budgetVetExpenses(5));
        System.out.println("Testing for 7 days");
        System.out.println("Expenses are $" + p.budgetVetExpenses(7));
        System.out.println("Testing for entire tree");
        System.out.println("Expenses are $" + p.budgetVetExpenses(10000));

        System.out.println("---------------------------------------------------------");
        System.out.println("Testing getVetSchedule");
        System.out.println(p.getVetSchedule());
    }

}