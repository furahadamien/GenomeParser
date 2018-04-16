package Assignment4;

import java.util.*;



//Student Name : Furaha Damien
//McGill ID    :260754407

public class Genome {

	private String sequence;
	private String species;
	private int numGenes;

	public String getSequence() {
		return this.sequence;
	}

	public String getSpecies() {
		return this.species;
	}

	public int getNumGenes() {
		return this.numGenes;
	}

	public Genome(String sequence, String species) {
		// initializing the attributes

		this.sequence = sequence;
		this.species = species;

	}

	// checking if the nucleotide is a valid nucleotide
	public boolean checkNucleotide(char c) {
		this.sequence = "ACGT";
		boolean bool = false;
		if (c != sequence.charAt(0) || c != sequence.charAt(1) || c != sequence.charAt(2) || c != sequence.charAt(3)) {
			bool = false;
		} else {
			bool = true;
		}
		return bool;
	}
	// declare nucleotideChecker method

	public String toString() {
		String info = "";
		// checking if numGenes has been computed...use -1 if not computed
		if (this.numGenes == -1) {
			info += sequence + "\t" + species;
			System.out.println("the number of genes has not been computed yet");
		} else {
			info += sequence + "\t" + species + "\t" + numGenes;
		}

		return info;
	}

	private String addNucleotide(char c, int index) {
		// adding char c at index specified using substrings
		boolean check = checkNucleotide(c);
		if (check == false) {
			throw new IllegalArgumentException("your nucleotide is not a valid nucleotide");
		}

		String newString = this.sequence.substring(0, index) + c + this.sequence.substring(index, sequence.length());

		return newString;

	}

	private char nucleotideAt(int index) {
		if (index >= this.sequence.length()) {
			throw new IndexOutOfBoundsException("use a smaller index");
		}

		char nucleotide = this.sequence.charAt(index);

		return nucleotide;
	}

	private int length() {
		int length = this.sequence.length();

		return length;
	}

	private boolean isValid(String seq) {
		boolean bool = false;
		for (int i = 0; i < seq.length(); i++) {
			if (seq.charAt(i) == sequence.charAt(0) || seq.charAt(i) == sequence.charAt(1)
					|| seq.charAt(i) == sequence.charAt(2) || seq.charAt(i) == sequence.charAt(3)) {
				bool = true;
			} else {
				bool = false;
			}
		}
		return bool;
	}

	private boolean checkEquality(Genome obj) {
		if (this == obj) { // do i have to use static?
			return true;
		} else {
			return false;
		}
	}

	private static boolean subsequence(String s, String t) {
		int slength = s.length(); // get the length of String s
		int tlength = t.length(); // get the length of String t
		int outCounter = 0; // counter used for s
		int innerCounter = 0; // counter used for t
		while ((innerCounter < tlength) & (outCounter < slength)) { // we only loop when the counters point to the position in the string
																	
			if (t.charAt(innerCounter) == s.charAt(outCounter)) { // counter for t only increments whenever the char pointed is found ins
																	
				innerCounter++;
			}
			outCounter++; // counter for s increments each time
		}
		if (innerCounter >= 1) { // if counter for t is greater than the length of t, all chars are found sequentially in s
									
			return true;
		} else {
			return false;
		}
	}

	private boolean isGene(String subsequence) {
		if (helperMethod(subsequence) == true && checkLength(subsequence) == true && checkCodon(subsequence) == true
				&& checkCodonsIntervene(subsequence) == true) {
			return true;
		} else {
			return false;
		}
	}

	// helper method to check if the DNA string starts with the appropriate sequence
	private static boolean helperMethod(String subsequence) {
		boolean bool;
		System.out.println();
		if (subsequence.substring(0, 3).equals("ATG")) {
			bool = true;
		} else {
			bool = false;
		}
		return bool;
	}

	// helper method to check if the sequence is a multiple of 3
	private boolean checkLength(String subsequence) {
		boolean bool;
		if (subsequence.length() % 3 == 0) {
			bool = true;
		} else {
			bool = false;
		}
		return bool;
	}

	// helper method to check if it ends with a stopCodon
	private boolean checkCodon(String subsequence) {

		// the method returns true if there is a stopCodon at the end
		if (subsequence.substring(subsequence.length() - 3, subsequence.length()).equals("TAG")) {
			return true;
		} else if (subsequence.substring(subsequence.length() - 3, subsequence.length()).equals("TAA")) {

			return true;
		} else if (subsequence.substring(subsequence.length() - 3, subsequence.length()).equals("TGA")) {
			return true;
		} else {
			return false;
		}

	}

	// helper method to check if there are no intervening stopCodons
	private boolean checkCodonsIntervene(String subsequence) {
		String stopcodon = "TAG";
		String stopCodon = "TAA";
		String stoCodon = "TGA";
		// if the sequence has intervening stopCodons the method should retain false
		if (subsequence.substring(3, subsequence.length() - 3).contains(stopcodon) == true) {
			return false;
		} else if (subsequence.substring(3, subsequence.length() - 3).contains(stopCodon) == true) {
			return false;
		} else if (subsequence.substring(3, subsequence.length() - 3).contains(stoCodon) == true) {
			return false;
		} else {
			return true;
		}
	}

	// method to count number of genes in a Genome
	public int findGenes(String sequence) {
		int numGenes = 0;
		for (int i = 0; i < sequence.length() - 2; i++) {
			if (sequence.substring(i, i + 3).equals("ATG")) {
				for (int j = i; j < sequence.length() - 2; j++) {
					if (sequence.substring(j, j + 3).equals("TAG") || sequence.substring(j, j + 3).equals("TGA")
							|| sequence.substring(j, j + 3).equals("TAA")) {
						boolean bool = isGene(sequence.substring(i, j + 3));
						if (bool = true) {
							numGenes++;
							break;
						}
					}
				}
			}

		}
		return numGenes;
	}

	// method to check if one sequence is a circular shift of the other
	private boolean isCircularShift(String s, String t) {
		return (s.length() == t.length()) && (s + s).contains(t);
	}

	// reversing the sequence
	private String reverseComplemented(String sequence) {
		String reverseInterchanged = ""; // reversed string before complementing
		for (int i = sequence.length() - 1; i >= 0; i--) {
			char interchanged = sequence.charAt(i);
			if (interchanged == 'A') { // interchanging A with T

				interchanged = 'T';
			} else if (interchanged == 'C') { // interchanging C with G
				interchanged = 'G';
			} else if (interchanged == 'T') { // interchanging T with A
				interchanged = 'A';
			} else if (interchanged == 'G') { // interchanging G with C
				interchanged = 'C';
			}

			reverseInterchanged += interchanged;

		}
		return reverseInterchanged; // we return the final complemented sequence
	}

	// method to get the longest complemented palindrome
	private String longestComplementedPalindrome(String textString) {
		int length = textString.length(); // get the length of String s
		String longest = ""; // used to store the longest we found so far
		int num = 0; // a number used to store the length of the longest we found so far
		int tempnum; // a number to store the current length
		int leftPointer; // points to a char at the left of the center
		int rightPointer;// points to a char at the Right of the center
		// considering each letter as the center of a possible palindrome of odd length,
		for (int i = 1; i < length; i++) { // i is the position of the center
			leftPointer = i - 1;
			rightPointer = i + 1;
			Boolean isPalin = true;
			tempnum = 1; // the center makes the current length be 1
			while ((leftPointer >= 0) && (rightPointer < length) && (isPalin == true)) {
				isPalin = false;
				if (textString.charAt(leftPointer) == 'A' && textString.charAt(rightPointer) == 'T')
					isPalin = true;
				if (textString.charAt(leftPointer) == 'T' && textString.charAt(rightPointer) == 'A')
					isPalin = true;
				if (textString.charAt(leftPointer) == 'C' && textString.charAt(rightPointer) == 'G')
					isPalin = true;
				if (textString.charAt(leftPointer) == 'G' && textString.charAt(rightPointer) == 'C')
					isPalin = true;
				if (isPalin == true) {
					// update num if tempnum is greater than it
					if (tempnum > num) {
						num = tempnum;
						longest = textString.substring(leftPointer, rightPointer + 1);	// Returns a new string that is a substring of this.
																						// The substring begins at the specified beginIndex and extends to the character at the endIndex-1
																						// Thus the length of the substring is endIndex-beginIndex
					}
					// update other variables for the next while loop
					tempnum += 2;
					leftPointer -= 1;
					rightPointer += 1;
				}
			}
		}

		// considering each letter as the center of a possible palindrome of even length,
		for (int i = 0; i < length - 1; i++) { // i is the position of the left
												// center, thus i + 1 will be
												// the right center
			leftPointer = i;
			rightPointer = i + 1;
			Boolean isPalin = true;
			tempnum = 0;
			while ((leftPointer >= 0) && (rightPointer < length) && (isPalin == true)) {
				isPalin = false;
				if (textString.charAt(leftPointer) == 'A' && textString.charAt(rightPointer) == 'T')
					isPalin = true;
				if (textString.charAt(leftPointer) == 'T' && textString.charAt(rightPointer) == 'A')
					isPalin = true;
				if (textString.charAt(leftPointer) == 'C' && textString.charAt(rightPointer) == 'G')
					isPalin = true;
				if (textString.charAt(leftPointer) == 'G' && textString.charAt(rightPointer) == 'C')
					isPalin = true;
				if (isPalin == true) {
					// update num if tempnum is greater than it
					if (tempnum > num) {
						num = tempnum;
						longest = textString.substring(leftPointer, rightPointer + 1); // Returns a new string that is a substring of this.
																						// The substring begins at the specified beginIndex and extends to the character at the endIndex-1
																						// Thus the length of the substring is endIndex-beginIndex
																						
					}
					// update other variables for the next while loop
					tempnum += 2;
					leftPointer -= 1;
					rightPointer += 1;
				}
			}
		}
		return longest;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}



