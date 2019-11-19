import java.util.*;

public class CodonFrequency {

    HashMap<String, Integer> codonMap;
    String[] codons;

    public CodonFrequency(HashMap<String, Integer> codonMap){
        this.codonMap = new HashMap<String, Integer>();
        this.codons = new String[]{"GCT", "GCC", "GCA", "GCG","CGT", "CGC", "CGA", "CGG", "AGA", "AGG",	"AAT", "AAC","GAT", "GAC",
            "TGT", "TGC", "CAA", "CAG", "GAA", "GAG", "GGT", "GGC", "GGA", "GGG", "CAT", "CAC", "ATT", "ATC", "ATA", "ATG",
            "TTA", "TTG", "CTT", "CTC", "CTA", "CTG","AAA", "AAG","ATG","TTT", "TTC","CCT", "CCC", "CCA", "CCG", "TCT", "TCC", "TCA",
            "TCG", "AGT", "AGC","ACT", "ACC", "ACA", "ACG", "TGG", "TAT", "TAC", "GTT", "GTC", "GTA", "GTG", "TAA", "TGA", "TAG"};
        for(String codon : this.codons){
            codonMap.put(codon, 0);
        }
    }
}