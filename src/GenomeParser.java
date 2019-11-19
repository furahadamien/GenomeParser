import java.util.*;
import java.io.*;

public class GenomeParser{

    public String genomeAnnotation;
    public String genomeSequence;
    public HashMap<String, Integer> codonMap;
    public String[] codons;
    public int allCodon;
    public List<Integer> nucleotideFrequency ;

    List<List<Integer>> genicRegions;
    List<String> frequenceList;


    public GenomeParser(String genomeAnnotation, String genomeSequence){
        this.genomeAnnotation = genomeAnnotation;
        this.genomeSequence = genomeSequence;
        this.genicRegions = new ArrayList<List<Integer>>();
        this.frequenceList = new ArrayList<String>();
        this.codonMap = new HashMap<String, Integer>();
        this.nucleotideFrequency  = new ArrayList<Integer>();
        this.allCodon = 0;
        this.codons = new String[]{"GCT", "GCC", "GCA", "GCG","CGT", "CGC", "CGA", "CGG", "AGA", "AGG",	"AAT", "AAC","GAT", "GAC",
            "TGT", "TGC", "CAA", "CAG", "GAA", "GAG", "GGT", "GGC", "GGA", "GGG", "CAT", "CAC", "ATT", "ATC", "ATA", "ATG",
            "TTA", "TTG", "CTT", "CTC", "CTA", "CTG","AAA", "AAG","ATG","TTT", "TTC","CCT", "CCC", "CCA", "CCG", "TCT", "TCC", "TCA",
            "TCG", "AGT", "AGC","ACT", "ACC", "ACA", "ACG", "TGG", "TAT", "TAC", "GTT", "GTC", "GTA", "GTG", "TAA", "TGA", "TAG", "TTG"};
        for(String codon : this.codons){
            codonMap.put(codon, 0);
        }

    }

    //parsinging gene annotation to get coding and non-coding regions
    public void parse(String genomeAnnotation) throws RuntimeException{
        try{
            Scanner file = new Scanner(new File(genomeAnnotation));
            while(file.hasNextLine()){
                String currentLine = file.nextLine();
                if(!currentLine.contains("#")){
                    if(currentLine.contains("CDS") && currentLine.contains("+")){
                        String [] words = currentLine.split("\\s+");
                        List<Integer> startEnd = new ArrayList<Integer>();
                        startEnd.add(Integer.parseInt(words[3]));
                        startEnd.add(Integer.parseInt(words[4]));
                        startEnd.add(Integer.parseInt(words[0].substring(11,16)));
                        this.genicRegions.add(startEnd);

                    }
                }
               
            }

        }
        catch (FileNotFoundException e){
            System.out.println("file not found");
        }
    }

    //parse genomic sequence to get nucleotide and sequence frequencies
    public void frequencies(String genomeSequence)throws RuntimeException{
        try{
            Scanner file = new Scanner(new File(genomeSequence));
            StringBuilder sb = new StringBuilder();

            while(file.hasNextLine()){
                String currentLine = file.nextLine();

                if(currentLine.contains(">")){
                    frequenceList.add(sb.toString());
                     sb = new StringBuilder();
                }
                else{
                    sb.append(currentLine);
                }
            }
            frequenceList.add(sb.toString());
        }
        catch(FileNotFoundException e){
            System.out.println("no file found");
        }
    }

    public void analyzeSequence(List<List<Integer>> genicRegions, List<String> frequenceList){
        int aCounter = 0;
        int tCounter = 0;
        int cCounter = 0;
        int gCounter = 0;

        //codon frequencies
        for(List<Integer> list : genicRegions){
            String tempString = frequenceList.get(list.get(2));
            for(int i = list.get(0); i <= list.get(1)-2; i+=3){
                String curr = tempString.substring(i, i+3);
                if(this.codonMap.get(curr) != null){
                    
                    int k = this.codonMap.get(curr) + 1;
                    this.codonMap.put(curr, k);
                    this.allCodon ++;
                }
            }
        }
        //nucleotide frequencies
        for(int i = 0; i < this.genicRegions.size()-1; i++){
            if(this.genicRegions.get(i).get(2) == this.genicRegions.get(i+1).get(2)){
                for(int j = this.genicRegions.get(i).get(1); j <= this.genicRegions.get(i+1).get(0); j++){
                    if(frequenceList.get(genicRegions.get(i).get(2)).charAt(j) == 'A') aCounter ++;
                    else if(frequenceList.get(genicRegions.get(i).get(2)).charAt(j) == 'T') tCounter ++;
                    else if(frequenceList.get(genicRegions.get(i).get(2)).charAt(j) == 'C') cCounter ++;
                    else gCounter ++;
                }
            }
        }
        this.nucleotideFrequency.add(aCounter);
        this.nucleotideFrequency.add(tCounter);
        this.nucleotideFrequency.add(tCounter);
        this.nucleotideFrequency.add(gCounter);
        
    }

    public static void main(String [] args){
        String file = "Vibrio_cholerae.GFC_11.37.gff3";
        String sequence = "Vibrio_cholerae.GFC_11.dna.toplevel.fa";

        GenomeParser genome =  new GenomeParser(file, sequence);
        genome.parse(genome.genomeAnnotation);
        genome.frequencies(genome.genomeSequence);

        int genicSum  = 0;
        for(List<Integer> list : genome.genicRegions){
            System.out.println(list.get(0) + " " + list.get(1));
            genicSum += list.get(1) - list.get(0);
        }
        int integenicSum = 0;
        for(int i = 0; i < genome.genicRegions.size() -1; i++){
            if(genome.genicRegions.get(i+1).get(2) != genome.genicRegions.get(i).get(2)) continue;
            integenicSum += genome.genicRegions.get(i+1).get(0) - genome.genicRegions.get(i).get(1);
        }
        System.out.println("average genic is " + genicSum/genome.genicRegions.size());
        System.out.println("average intergenic is " + integenicSum/(genome.genicRegions.size()-1));

        System.out.println(genome.frequenceList.size());
        genome.analyzeSequence(genome.genicRegions, genome.frequenceList);

        List<Integer> counts = genome.nucleotideFrequency;
        System.out.println("number of A nucleotides: " + counts.get(0) + "  frequency: " + (double)(counts.get(0))/(double)(integenicSum));
        System.out.println("number of T nucleotides: " + counts.get(1) + "  frequency: " + (double)(counts.get(1))/(double)(integenicSum));
        System.out.println("number of C nucleotides: " + counts.get(2) + "  frequency: " + (double)(counts.get(2))/(double)(integenicSum));
        System.out.println("number of G nucleotides: " + counts.get(3) + "  frequency: " + (double)(counts.get(3))/(double)(integenicSum));

        try (PrintWriter writer = new PrintWriter(new File("test.csv"))) {
            StringBuilder sb = new StringBuilder();
            for(String codon : genome.codons){
                if(genome.codonMap.get(codon) != 0){
                   if(codon == "ATG" ) sb.append(0.83);
                   else if(codon == "GTG" ) sb.append(0.14);
                    else if(codon == "TTG" ) sb.append(0.3);
                    else sb.append(0);
                    sb.append(", ");
                    System.out.println("codon " + codon + " has frequency of : " + (double)(genome.codonMap.get(codon))/(double)(genome.allCodon ));
                }
            }
            writer.write(sb.toString());

        }
        catch(FileNotFoundException e){

        }
    }
}