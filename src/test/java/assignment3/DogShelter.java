package assignment3;

import java.util.ArrayList;
import java.util.Iterator;

public class DogShelter implements Iterable<Dog> {
	public DogNode root;

	public DogShelter(Dog d) {
		this.root = new DogNode(d);
	}

	private DogShelter(DogNode dNode) {
		this.root = dNode;
	}


	// add a dog to the shelter
	public void shelter(Dog d) {
        if (root == null)
            root = new DogNode(d);
        else
            root = root.shelter(d);
    }

	// removes the dog who has been at the shelter the longest
	public Dog adopt() {
        if (root == null)
            return null;

        Dog d = root.d;
        root = root.adopt(d);
        return d;
    }

    // overload adopt to remove from the shelter a specific dog
	public void adopt(Dog d) {
		if (root != null)
			root = root.adopt(d);
	}


	// get the oldest dog in the shelter
	public Dog findOldest() {
		if (root == null)
			return null;

        return root.findOldest();
	}

	// get the youngest dog in the shelter
	public Dog findYoungest() {
		if (root == null)
			return null;

        return root.findYoungest();
	}

    // get dog with highest adoption priority with age within the range
	public Dog findDogToAdopt(int minAge, int maxAge) {
		return root.findDogToAdopt(minAge, maxAge);
	}

	// Returns the expected vet cost the shelter has to incur in the next numDays days
	public double budgetVetExpenses(int numDays) {
		if (root == null)
			return 0;

        return root.budgetVetExpenses(numDays);
	}

    // returns a list of list of Dogs. The dogs in the list at index 0 need to see the vet in the next week.
	// The dogs in the list at index i need to see the vet in i weeks. 
	public ArrayList<ArrayList<Dog>> getVetSchedule() {
		if (root == null)
			return new ArrayList<ArrayList<Dog>>();

        return root.getVetSchedule();
	}


	public Iterator<Dog> iterator() {
		return new DogShelterIterator();
	}


	public class DogNode {
		public Dog d;
		public DogNode younger;
        public DogNode older;
        public DogNode parent;

        public DogNode(Dog d) {
            this.d = d;
            this.younger = null;
            this.older = null;
            this.parent = null;
        }

        public DogNode shelter(Dog d) {
            // ADD YOUR CODE HERE
            return null; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }

        public DogNode adopt(Dog d) {
            // ADD YOUR CODE HERE
            return null; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }

        public Dog findOldest() {
            // ADD YOUR CODE HERE
            return null; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }

		public Dog findYoungest() {
            // ADD YOUR CODE HERE
            return null; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }

		public Dog findDogToAdopt(int minAge, int maxAge) {
            // ADD YOUR CODE HERE
            return null; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }

        public double budgetVetExpenses(int numDays) {
            // ADD YOUR CODE HERE
            return 0.0; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }

		public ArrayList<ArrayList<Dog>> getVetSchedule() {
            // ADD YOUR CODE HERE
            return null; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }

		public String toString() {
			String result = this.d.toString() + "\n";
			if (this.younger != null) {
				result += "younger than " + this.d.toString() + " :\n";
				result += this.younger.toString();
			}
			if (this.older != null) {
				result += "older than " + this.d.toString() + " :\n";
				result += this.older.toString();
			}
			/*if (this.parent != null) {
				result += "parent of " + this.d.toString() + " :\n";
				result += this.parent.d.toString() +"\n";
			}*/
			return result;
		}

    }


    private class DogShelterIterator implements Iterator<Dog> {
        // HERE YOU CAN ADD THE FIELDS YOU NEED

        private DogShelterIterator() {
            //YOUR CODE GOES HERE
        }

        public Dog next() {
            //YOUR CODE GOES HERE
            return null; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }

        public boolean hasNext() {
            //YOUR CODE GOES HERE
            return false; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }

    }

}
