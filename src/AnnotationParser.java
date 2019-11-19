import java.util.*;
import java.io.*;
public class AnnotationParser {
    
    public List<List<Integer>> predictedGenes;
    public List<List<Integer>> annotatedGenes;

    public AnnotationParser(){
        this.predictedGenes = new ArrayList<List<Integer>>();
        this.annotatedGenes = new ArrayList<List<Integer>>();
    }

    public void parse(String genomeAnnotation, String annot) throws RuntimeException{
        try{
            Scanner file = new Scanner(new File(genomeAnnotation));
            while(file.hasNextLine()){
                String currentLine = file.nextLine();
                if(!currentLine.contains("#")){
                    if(currentLine.contains("CDS")){
                        String [] words = currentLine.split("\\s+");
                        List<Integer> startEnd = new ArrayList<Integer>();
                        startEnd.add(Integer.parseInt(words[3]));
                        startEnd.add(Integer.parseInt(words[4]));
                        if(annot == "predicted") this.predictedGenes.add(startEnd);
                        else this.annotatedGenes.add(startEnd);
                        

                    }
                }
               
            }

        }
        catch (FileNotFoundException e){
            System.out.println("file not found");
        }
    }
    
    public static void main(String [] args) throws RuntimeException{
        String predicted = "output.txt";
        String annontaed = "Vibrio_vulnificus.ASM74310v1.37.gff3";

        AnnotationParser obj = new AnnotationParser();
        obj.parse(predicted, "predicted");
        obj.parse(annontaed, "annotated");

        int predBoth = 0;
        int predStart = 0;
        int predEnd = 0;
        int predNone = 0;
        int total = 0;
        
        for( List list : obj.annotatedGenes){

            for(List curr : obj.predictedGenes){
                if((int)list.get(0) == (int)curr.get(0) && (int)list.get(1) == (int)curr.get(1)) predBoth ++;
                else if((int)list.get(0) == (int)curr.get(0) && (int)list.get(1) != (int)curr.get(1)) predStart ++;
                else if((int)list.get(0) != (int)curr.get(0) && (int)list.get(1) ==(int) curr.get(1)) predEnd ++;
                else predNone ++;
            }
            total++;
        }
    
        System.out.println(predBoth);
        System.out.println((predStart*0.5*0.25) / obj.annotatedGenes.size());
        System.out.println((predEnd*0.5*0.25) / obj.annotatedGenes.size());
        System.out.println(obj.annotatedGenes.size());
        System.out.println("\n ");

        predBoth = 0;
        predStart = 0;
        predEnd = 0;
        predNone = 0;
        total = 0;
        
        int [] numGenes = new int [obj.predictedGenes.size()];
        int index = 0;
        for( List list : obj.predictedGenes){
            for(List curr : obj.annotatedGenes){
                if((int)list.get(0) == (int)curr.get(0) && (int)list.get(1) == (int)curr.get(1)) predBoth ++;
                else if((int)list.get(0) == (int)curr.get(0) && (int)list.get(1) != (int)curr.get(1)){
                     predStart ++;
                     numGenes[index++] = (int)list.get(0);
                }
                else if((int)list.get(0) != (int)curr.get(0) && (int)list.get(1) == (int)curr.get(1)){
                     predEnd ++;
                     numGenes[index++] = (int)list.get(1);
                }
                else predNone ++;
            }
            total++;
        }
        System.out.println(predBoth);
        System.out.println((predStart*0.5*0.25) / (obj.predictedGenes.size()*0.5*0.25));
        System.out.println((predEnd*0.5*0.25)/(obj.predictedGenes.size()*0.5*0.25));
        System.out.println(obj.predictedGenes.size());

        int counter = 0;
        
            try(PrintWriter writer = new PrintWriter(new File("annotation.gff3"))) {
                StringBuilder sb1 = new StringBuilder();
                Scanner file = new Scanner(new File("output.txt"));
                while(file.hasNextLine()){
                    String currentLine = file.nextLine();
                    String[] curr = currentLine.split("\\s+");
                    if(curr.length == 1) continue;
                    for(Integer l : numGenes){
                        if(l == 0) continue;
                        if(Integer.parseInt(curr[3]) == l){
                            counter ++;
                            System.out.println(l);
                            sb1.append(currentLine);
                            sb1.append("\n");
                            break;

                        }
                    }
                   
                }
                System.out.println("counter is "+ counter);
                writer.write(sb1.toString());
    
            }
            catch (FileNotFoundException e){
                System.out.println("file not found");
            }

            System.out.println("num of collision: " +index);
    }

}


