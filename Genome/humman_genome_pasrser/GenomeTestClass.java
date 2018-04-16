package Assignment4;

public class GenomeTestClass {
	
	public static void main(String []arg){
		Genome B = new Genome("ACGT","ELEPHANT");
		System.out.println(B);
		char c='g';
		int index = 2;
		String sequence = "abcdef";
		String s=sequence.substring(0, index)+c+sequence.substring(index, sequence.length());
	    System.out.println(s);
	    String sc = "taaccghgg";
	    String t = "aac";
	    boolean bool = subsequence(sc,t);
	    System.out.println(bool);
	    System.out.println(longestComplementedPalindrome("GACACGGTTTTA"));
	    System.out.println(longestComplementedPalindrome("GCATGC"));
	   
	}
	private static boolean subsequence(String s, String t) { 
	    int slength = s.length(); // get the length of String s
	    int tlength = t.length(); // get the length of String t
	    int outCounter = 0;       // counter used for s
	    int innerCounter = 0;     // counter used for t
	    while ((innerCounter < tlength) & (outCounter < slength)){ // we only loop when the counters point to position in the string
	        if (t.charAt(innerCounter) == s.charAt(outCounter)){   // counter for t only increments whenever the char pointed is found in S
	          innerCounter++;
	        }
	        outCounter++;                                          // counter for s increments each time 
	    }
	    if (innerCounter > tlength) {                              // if counter for t is greater than the length of t, all chars are found sequentially in s
	      return true;
	    }else{
	      return false;
	    }
	}
	private static String longestComplementedPalindrome(String s) { 
	    int length = s.length(); // get the length of String s
	    String longest =""; // used to store the longest we found so far
	    int num = 0; // a number used to store the length of the longest we found so far
	    int tempnum; // a number to store the current length
	    int leftPointer; // points to a char at the left of the center 
	    int rightPointer;// points to a char at the Right of the center 
	    // considering each letter as the center of a possible palindrome of odd length,\
	    for (int i = 1; i < length; i++){ // i is the position of the center 
	      leftPointer = i - 1;
	      rightPointer = i + 1;
	      Boolean isPalin = true;
	      tempnum = 1; // the center makes the current length be 1
	      while ((leftPointer >= 0) && (rightPointer < length) && (isPalin == true)){
	        isPalin = false;
	        if (s.charAt(leftPointer) == 'A' && s.charAt(rightPointer)== 'T') isPalin = true;
	        if (s.charAt(leftPointer) == 'T' && s.charAt(rightPointer)== 'A') isPalin = true;
	        if (s.charAt(leftPointer) == 'C' && s.charAt(rightPointer)== 'G') isPalin = true;
	        if (s.charAt(leftPointer) == 'G' && s.charAt(rightPointer)== 'C') isPalin = true;
	        if (isPalin == true){
	          //update num if tempnum is greater than it
	          if (tempnum > num) {
	            num = tempnum;
	            longest = s.substring(leftPointer, rightPointer + 1); //Returns a new string that is a substring of this string. The substring begins at the specified beginIndex and extends to the character at index endIndex - 1. Thus the length of the substring is endIndex-beginIndex.
	          }
	          //update other variables for the next while loop
	          tempnum += 2;
	          leftPointer -= 1;
	          rightPointer += 1;
	        }
	      }
	    }
	        

	// considering each letter as the center of a possible palindrome of even length,\
	    for (int i = 0; i < length - 1; i++){ // i is the position of the left center, thus i + 1 will be the right center 
	      leftPointer = i;
	      rightPointer = i + 1;
	      Boolean isPalin = true;
	      tempnum = 0; 
	      while ((leftPointer >= 0) && (rightPointer < length) && (isPalin == true)){
	        isPalin = false;
	        if (s.charAt(leftPointer) == 'A' && s.charAt(rightPointer)== 'T') isPalin = true;
	        if (s.charAt(leftPointer) == 'T' && s.charAt(rightPointer)== 'A') isPalin = true;
	        if (s.charAt(leftPointer) == 'C' && s.charAt(rightPointer)== 'G') isPalin = true;
	        if (s.charAt(leftPointer) == 'G' && s.charAt(rightPointer)== 'C') isPalin = true;
	        if (isPalin == true){
	          //update num if tempnum is greater than it
	          if (tempnum > num) {
	            num = tempnum;
	            longest = s.substring(leftPointer, rightPointer + 1); //Returns a new string that is a substring of this string. The substring begins at the specified beginIndex and extends to the character at index endIndex - 1. Thus the length of the substring is endIndex-beginIndex.
	          }
	          //update other variables for the next while loop
	          tempnum += 2;
	          leftPointer -= 1;
	          rightPointer += 1;
	        }
	      }
	    }
	    return longest;
	  }
  
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
