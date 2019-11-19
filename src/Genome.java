import java.util.*;

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
		this.sequence = sequence;
		this.species = species;

	}

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

	public String toString() {
		String info = "";
		if (this.numGenes == -1) {
			info += sequence + "\t" + species;
			System.out.println("the number of genes has not been computed yet");
		} else {
			info += sequence + "\t" + species + "\t" + numGenes;
		}

		return info;
	}

	private String addNucleotide(char c, int index) {
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
		if (this == obj) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean subsequence(String s, String t) {
		int slength = s.length(); 
		int tlength = t.length(); 
		int outCounter = 0; 
		int innerCounter = 0; 
		while ((innerCounter < tlength) & (outCounter < slength)) {
																	
			if (t.charAt(innerCounter) == s.charAt(outCounter)) { 
																	
				innerCounter++;
			}
			outCounter++;
		}
		if (innerCounter >= 1) { 
									
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

	private boolean checkLength(String subsequence) {
		boolean bool;
		if (subsequence.length() % 3 == 0) {
			bool = true;
		} else {
			bool = false;
		}
		return bool;
	}

	private boolean checkCodon(String subsequence) {
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

	private boolean checkCodonsIntervene(String subsequence) {
		String stopcodon = "TAG";
		String stopCodon = "TAA";
		String stoCodon = "TGA";

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

	private boolean isCircularShift(String s, String t) {
		return (s.length() == t.length()) && (s + s).contains(t);
	}

	private String reverseComplemented(String sequence) {
		String reverseInterchanged = ""; 
		for (int i = sequence.length() - 1; i >= 0; i--) {
			char interchanged = sequence.charAt(i);
			if (interchanged == 'A') {

				interchanged = 'T';
			} else if (interchanged == 'C') {
				interchanged = 'G';
			} else if (interchanged == 'T') { 
				interchanged = 'A';
			} else if (interchanged == 'G') { 
				interchanged = 'C';
			}

			reverseInterchanged += interchanged;

		}
		return reverseInterchanged;
	}

	private String longestComplementedPalindrome(String textString) {
		int length = textString.length(); 
		String longest = ""; 
		int num = 0; 
		int tempnum; 
		int leftPointer; 
		int rightPointer;
		for (int i = 1; i < length; i++) { 
			leftPointer = i - 1;
			rightPointer = i + 1;
			Boolean isPalin = true;
			tempnum = 1; 
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
					if (tempnum > num) {
						num = tempnum;
						longest = textString.substring(leftPointer, rightPointer + 1);	
					}
					tempnum += 2;
					leftPointer -= 1;
					rightPointer += 1;
				}
			}
		}

		for (int i = 0; i < length - 1; i++) { 
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
					if (tempnum > num) {
						num = tempnum;
						longest = textString.substring(leftPointer, rightPointer + 1); // Returns a new string that is a substring of this.
																						// The substring begins at the specified beginIndex and extends to the character at the endIndex-1
																						// Thus the length of the substring is endIndex-beginIndex
																						
					}
					tempnum += 2;
					leftPointer -= 1;
					rightPointer += 1;
				}
			}
		}
		return longest;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}



