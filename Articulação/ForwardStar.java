/*
 * /**
 * Esta classe implementa uma estrutura de grafo utilizando a técnica de Forward Star, 
 * que permite uma representação compacta e eficiente dos vértices e arestas de um grafo direcionado. 
 * A classe fornece métodos para a leitura de um arquivo que contém a representação do grafo e 
 * permite consultas sobre os sucessores de cada vértice, facilitando a análise de suas conexões.
 *
 * Referência: 
 * "Algorithms, Part II" por Robert Sedgewick e Kevin Wayne, que aborda estruturas de dados e 
 * algoritmos para grafos, incluindo técnicas de representação eficiente.
 */
 



import java.io.File;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class ForwardStar {
    int m;
    int n;
    int[] saida;
    int[] destino;

    /**
     * Lê o arquivo e gera o Forward Star.
     * 
     * @param arq
     * @throws Exception
     */
    ForwardStar(File arq) throws Exception {
        RandomAccessFile raf = new RandomAccessFile(arq, "r");
        m = raf.readInt();
        n = raf.readInt();
        saida = new int[m + 1];
        destino = new int[n];
        for (int c = 0; c < m + 1; c++) {
            saida[c] = raf.readInt();
        }
        for (int c = 0; c < n; c++) {
            destino[c] = raf.readInt();
        }
        raf.close();
    }

    /**
     * Método para debug inicialmente, imprime os sucessores do vértice seleciano
     * 
     * @param v
     */
    public ForwardStar() {
        m = 11;
        n = 30;
        saida = new int[] { 1, 3, 6, 10, 13, 16, 19, 22, 25, 27, 29, 31 };
        destino = new int[] { 2, 3, 1, 3, 4, 1, 2, 5, 6, 2, 7, 8, 3, 6, 9, 3, 5, 9, 4, 8, 10, 4, 7, 11, 5, 6, 7, 11, 10,
                8 };
    }

    public void busca(int v) {
        int sucessores = saida[v] - saida[v - 1];
        System.out.println("Grau de saída: " + sucessores);
        System.out.print("Lista de Sucessores: ");
        imprimeArray(saida[v - 1] - 1, sucessores);
    }

    public int[] listaSucessores(int v) {
        int sucessores = saida[v] - saida[v - 1];
        int[] resp = new int[sucessores];
        int inicio = saida[v - 1] - 1;
        int c = 0;
        while (sucessores > 0) {
            resp[c] = destino[inicio];
            inicio++;
            c++;
            sucessores--;

        }
        return resp;
    }

    public void imprimeArray(int inicio, int repeticoes) {
        while (repeticoes > 0) {
            System.out.print(destino[inicio] + ", ");
            repeticoes--;
            inicio++;
        }
        System.out.println("\n");
    }

    public void consultas() {
        int v = 1;
        Scanner sc = new Scanner(System.in);
        while (v != 0) {

            System.out.println("Informe o vertice que deseja pesquisar ou 0 caso queira sair:");
            v = Integer.parseInt(sc.nextLine());
            if (v != 0 && v > 0) {
                if (v < m) {
                    busca(v);
                } else {
                    System.out.println("Vértice não existe");
                }
            }
        }

        sc.close();
    }
}