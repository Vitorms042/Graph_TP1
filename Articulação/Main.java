import java.io.*;
import java.util.LinkedList;

public class Main {
    
    /*
    * Dicas Para Executar:
    * Primeiro: Abra o terminal na pasta: ArticulacaoAndTarjan 
    * Segundo: use esse comando: javac -Xlint:unchecked Main.java
    * Terceiro: use: java -Xss256m -Xms6g Main > Art_2_100_g , > NomeDoArquivo, esse arquivo será gerado com a saida (prints) do sistema.
    * -Xss256m -Xms6g -> aumenta capacidade de stack e heap
    */
public static void main(String[] args) throws Exception {
    ForwardStar fs = new ForwardStar(new File("Arquivo_1_100_g"));   //Coloque o nome no new File("nome_arquivo")

        //Selecione qual algoritmo você quer usar (só comentar)

        //Articulacao -> descomente ou comente

        System.out.println("Articulacao");
        usingArticulations(fs);

        //Tarjan -> descomente ou comente

        // System.out.println("Tarjan");
        // usingTarjan(fs);
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