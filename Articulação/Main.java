
import java.io.*;
import java.util.LinkedList;

public class Main {
    
public static void main(String[] args) throws Exception {
    ForwardStar fs = new ForwardStar(new File("Arquivo_1_100_g"));  

   
        System.out.println("Articulacao");
        usingArticulations(fs);

    }

   

    public static LinkedList<LinkedList<Integer>> usingArticulations(ForwardStar fs) {
        long init = System.currentTimeMillis();
        Biconnected biConnected = new Biconnected(new Graph(fs));
        int array[] = biConnected.getArrayArticulation();
        Articulacao dfs = new Articulacao(fs.saida.length - 1, fs);
        var ret = dfs.getComponentes(array);
        System.out.println((System.currentTimeMillis() - init) + " ms");
        return ret;
    }
}