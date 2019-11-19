import java.util.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.stream.Stream;

public class Viterbi {

    public int [] states;
    public int [] observations;
    public double [] initialStatePr;
    public double [][] transitionPr;
    public double [][] emmissionPr;

    public Viterbi(int [] states, int [] observations, double [] initialStatePr, double [][] transitionPr, double [][]  emmissionPr){

        this.states = states;
        this.observations = observations;
        this.initialStatePr = initialStatePr;
        this.transitionPr = transitionPr;
        this.emmissionPr = emmissionPr;
    }

    public int[] getPath(){
        String [] codons = new String [] {"GCT", "GCC", "GCA", "GCG", "CGT", "CGC", "CGA", "CGG", "AGA", "AGG", "AAT", "AAC", "GAT", "GAC", "TGT", "TGC", "CAA", "CAG", "GAA", "GAG", "GGT", "GGC", "GGA", "GGG", "CAT", "CAC", "ATT", "ATC", "ATA", "ATG", "TTA", "TTG", "CTT", "CTC", "CTA", "CTG", "AAA", "AAG", "ATG", "TTT", "TTC", "CCT", "CCC", "CCA", "CCG", "TCT", "TCC", "TCA", "TCG", "AGT", "AGC", "ACT", "ACC", "ACA", "ACG", "TGG", "TAT", "TAC", "GTT", "GTC", "GTA", "GTG", "TAA", "TGA", "TAG", "TTG", };
        int n = 4;
        HashMap<String, Integer> codonMap = new HashMap<String, Integer>();

        HashMap<Integer, Character> map = new HashMap<Integer, Character>();
        map.put(0,'A');
        map.put(1,'T');
        map.put(2,'C');
        map.put(3,'G');

        for(String s : codons){
            codonMap.put(s, n++);
        }

        int [] path = new int[this.observations.length];

        double [][] trell = new double[this.states.length][this.observations.length];

        for(int i = 0; i < this.observations.length; i++){

            for(int j = 0; j < this.states.length; j++){

                double trans = 0;
                double curr = 0;
                
                if(j == 0){
                    if(i == 0){
                        trans = 1;
                        curr = Math.log(this.emmissionPr[this.states[j]][this.observations[i]]);
                        trell[j][i]  = curr;
                    }
                    else{
                        double max = Double.MAX_VALUE;
                        double [] maxList = new double[4];
                        HashMap<Double, Double> mapList = new HashMap<Double, Double>();
                        for(int k = 0; k < this.states.length; k++){

                            maxList[k] = (trell[k][i-1]);
                            mapList.put(trell[k][i-1], this.transitionPr[this.states[k]][this.states[j]]);
                           
            
                        }
                        Arrays.sort(maxList);
                        max = maxList[3];
                        
                        trans = mapList.get(max);

                        double em = this.emmissionPr[this.states[j]][this.observations[i]];
                        if(em == 0) em = Double.MIN_VALUE;
                        if(trans == 0) trans = Double.MIN_VALUE;
                        curr= ((max) + Math.log(trans) + Math.log(em));
                        trell[j][i]  = curr;
                        System.out.println( "intergenic " +curr + " " + em + " " + trans+ " " + max);
                    }
                }
                else{
                    if(i < 3){
                        trell[j][i] = Math.log(Double.MIN_VALUE);
                    }
                    else{

                        double max = Double.MAX_VALUE;
                        double [] maxList = new double[4];
                        HashMap<Double, Double> mapList = new HashMap<Double, Double>();
                        StringBuilder sb = new StringBuilder();
                        for(int p = i-2; p <= i; p++){
                            sb.append(map.get(this.observations[p]));
                        }
                        
                        for(int k = 0; k < this.states.length; k++){

                            maxList[k] = (trell[k][i-3]);
                            mapList.put(trell[k][i-3], this.transitionPr[this.states[k]][this.states[j]]);
                           

                        }
                        Arrays.sort(maxList);
                        max = maxList[3];
                        trans = mapList.get(max);
                        int codonEmm = codonMap.get(sb.toString());
                       
                        double em = this.emmissionPr[this.states[j]][codonEmm];
                        if(em == 0) em = Double.MIN_VALUE;
                        if(trans == 0) trans = Double.MIN_VALUE;
             
                        curr= ((max) + Math.log(trans) + Math.log(em));
                        System.out.println( this.states[j] + "genic " +curr + " " + em + " " + trans+ " " + max);
                        trell[j][i]  = curr;

                    }
                }

            }
        }

        for(int i = this.observations.length -1; i > -1; i--){
            double max = Math.log(Double.MIN_VALUE)* -Math.log(Double.MIN_VALUE);

            int index = 0;
            for(int j = 0; j < this.states.length; j++){
                if(trell[j][i] > max){
                    max = trell[j][i];
                    index = j;
                }
            }
            path[i] = index;
        }
        return path;
    }

 public static void main(String [] args) throws RuntimeException{

        
        String source = args[0];//Vibrio_vulnificus.ASM74310v1.dna.toplevel.fa;
        List<String> sequences = new ArrayList<String>();

        try{
            Scanner file = new Scanner(new File(source));
            StringBuilder sb = new StringBuilder();
            String currentLine = file.nextLine();

            while(file.hasNextLine()){
                currentLine = file.nextLine();
                if(currentLine.contains(">")){
                    sequences.add(sb.toString());
                    sb = new StringBuilder();
                }
                else sb.append(currentLine);
            }
            source = sb.toString();
        }
        catch(FileNotFoundException e){
            System.out.println("no file found");
        }

        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        map.put('A', 0);
        map.put('T', 1);
        map.put('C', 2);
        map.put('G', 3);
        HashMap<Integer, Character> regionMap = new HashMap<Integer, Character>();
        regionMap.put(0, 'I');
        regionMap.put(1, 'S');
        regionMap.put(2, 'M');
        regionMap.put(3, 'T');


        List<List<Integer>> lis = new ArrayList<List<Integer>>();

        for(int i = 0; i < sequences.size(); i++){
            List<Integer> list = new ArrayList<Integer>();
            for(char c : sequences.get(i).toCharArray()){
                list.add((int)map.get(c));
            }
            lis.add(list);
        }

        int [] states = {0,1,2,3};
        double [] initial = {1,0,0,0};
        double [][] transition = {
            {0.9, 0.1, 0, 0},
            {0,0, 1, 0},
            {0, 0, 0,1},
            {0.9, 0.1, 0,0}
        };

        double[][] emission1 = {
            {0.2663129857804677, 0.26504893809666435, 0.26504893809666435, 0.22632248765502228
            },
            {0.40, 0.25, 0.15, 0.20},
            {0.20, 0.30, 0.35, 0.15},
            {0.30, 0.20, 0.25, 0.25}
        }; 
     
        double[][] emission = {
            {0.2663129857804677, 0.26504893809666435, 0.26504893809666435 ,0.22632248765502228,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0, 0, 0, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.83, 0, 0.3, 0, 0, 0, 0, 0, 0, 0.83, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.14, 0, 0, 0, 0.3},
            {0,0,0,0,0.013315865346839486, 0.012693688594121072, 0.01628884395390119, 0.01839911355848221, 0.007339440714412853, 0.01730389004789799, 0.016715387810146292, 0.0201117031767896, 0.012558990534254197, 0.009096929686009201, 0.014797544005375094, 0.020721051542854026, 0.0028398840955265814, 0.0034460253649275115, 0.015336336244842588, 0.027343706152975305, 0.011391607348741294, 0.01740972423779339, 0.004108290825939639, 0.005325384009736745, 0.0043568408173606556, 0.007536677159217918, 0.005856158507545496, 0.007522245224232181, 0.007341044262744601, 0.013827397264667255, 0.014330911440836282, 0.020150188336751564, 0.01820668775867239, 0.03175346406528366, 0.01661115716858264, 0.03868720705176414, 0.013793722749700537, 0.012339304412804654, 0.009670999988775162, 0.020368270909869358, 0.028175947737152773, 0.046146913891058135, 0.03175346406528366, 0.015883146225968824, 0.014468816597366653, 0.009404810965704911, 0.006762163314983395, 0.01919928417602471, 0.018891402896328995, 0.011250495095547427, 0.009765609340348322, 0.023714876278228464, 0.02297403694896066, 0.011600068631868599, 0.02334926725858981, 0.012132446678009098, 0.014553804658949322, 0.016646435231881106, 0.016000205254186464, 0.03387014786319167, 0.0067862165399596224, 0.011521494763612923, 0.013745616299748083, 0.010763016402695886, 0.014893756905280003, 0.02476359688719198, 0.009369532902406445, 0.02959348446241844, 0.01488734271195301, 0 },
            {0,0,0,0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.63, 0.29, 0.08, 0}
        };

        int i = 0;
        List<List<Integer>> path = new ArrayList<List<Integer>>();
        for(List l : lis){
            StringBuilder sb = new StringBuilder();
            int [] arr = new int[l.size()];
            
            for(int j = 0; j < l.size(); j++){
                arr[j] = (int)l.get(j);
            }
            Viterbi obj = new Viterbi(states, arr, initial, transition, emission);
            int [] pa = (obj.getPath());
            List<Integer> curr = new ArrayList<Integer>();
            for(int k : pa){
                curr.add(k);
            }
            path.add(curr);

        }

        String [] outc = new String[path.size()];

        int k = 0;
        for(List curr : path){
            StringBuilder sb = new StringBuilder();
            for(int p = 0; p < curr.size(); p++){
                sb.append(regionMap.get(curr.get(p)));
            }
           
            outc[k++] = sb.toString();
        }
        try (PrintWriter writer = new PrintWriter(new File("output.txt"))) {
            StringBuilder sb1 = new StringBuilder();

            int ind = 0;
            for( String s: outc){
                
                sb1.append(annotateGenome(s, ind++));
                System.out.println(annotateGenome(s, ind));
                sb1.append("\n");
        
            }
           
            writer.write(sb1.toString());

        }
        catch(FileNotFoundException e){

        }
    }

    public static String annotateGenome(String sequence, int ind){


        StringBuilder sb = new StringBuilder();
        int numGenes = 0;

        int index = 0;
        String formatted = String.format("%05d", ind + 1);

        while(index < sequence.length()){
            int j = index;
            if(sequence.charAt(index) == 'S'){
                
                while(index < sequence.length() && sequence.charAt(index) != 'T'){
                    index ++;
                    if(index < sequence.length() && sequence.charAt(index) == 'T' && index < j+7){
                        index ++;
                    }
                }
                if(index < sequence.length() && sequence.charAt(index) == 'T' && index > j+7){
                    numGenes ++;
                    sb.append("DN38.contig"+formatted+" ena CDS  ");
                    sb.append(j-3);
                    sb.append("   ");
                    sb.append(index-3);
                    sb.append(" . + 0 .");
                    sb.append("\n");
                }

            }
            index ++;
        }

        return sb.toString();
    }
}