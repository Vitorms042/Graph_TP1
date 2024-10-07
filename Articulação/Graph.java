/**
 * A classe {@code Graph} representa um grafo não direcionado com vértices 
 * numerados de 0 a <em>V</em> - 1. 
 * 
 * Permite adicionar arestas e iterar sobre os vértices adjacentes. 
 * Fornece métodos para obter o grau de um vértice, o número total de 
 * vértices <em>V</em> e o número de arestas <em>E</em>.
 * 
 * Esta implementação utiliza uma representação por <em>listas de adjacência</em>, 
 * armazenadas em um array indexado de objetos {@link Bag}. O uso de espaço 
 * é &Theta;(<em>E</em> + <em>V</em>), e todas as operações de instância 
 * têm tempo de execução &Theta;(1).
 * 
 * Para mais detalhes, consulte a Seção 4.1 de 
 * <i>Algorithms, 4th Edition</i> por Robert Sedgewick e Kevin Wayne.
 * 
 * @see <a href="https://algs4.cs.princeton.edu/41graph">Seção 4.1</a> 
 * @see <i>Algorithms, 4th Edition</i> por Robert Sedgewick e Kevin Wayne
 */

import java.util.Stack;

public class Graph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    /**
     * Initializes an empty graph with {@code V} vertices and 0 edges.
     * param V the number of vertices
     *
     * @param V number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    @SuppressWarnings("unchecked")
    public Graph(int V) {
        if (V < 0)
            throw new IllegalArgumentException("Number of vertices must be non-negative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

   
    @SuppressWarnings("unchecked")
    public Graph(ForwardStar fs) {
        if (fs == null)
            throw new IllegalArgumentException("argument is null");
        this.V = fs.m;
        if (V < 0)
            throw new IllegalArgumentException("number of vertices in a Graph must be non-negative");
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
        int E = fs.n;
        if (E < 0)
            throw new IllegalArgumentException("number of edges in a Graph must be non-negative");
        int iteradorDestino = 0;
        for (int i = 0; i < V; i++) {
            int repeticoes = fs.saida[i + 1] - fs.saida[i];
            int v = i;
            while (repeticoes > 0) {
                int w = fs.destino[iteradorDestino];
                w--;
                validateVertex(v);
                validateVertex(w);
                addEdge(v, w);
                iteradorDestino++;
                repeticoes--;
            }
        }
    }

   
    @SuppressWarnings("unchecked")
    public Graph(Graph G) {
        this.V = G.V();
        this.E = G.E();
        if (V < 0)
            throw new IllegalArgumentException("Number of vertices must be non-negative");

        // update adjacency lists
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }

        for (int v = 0; v < G.V(); v++) {
           
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }

  
    public int V() {
        return V;
    }

    
    public int E() {
        return E;
    }

  
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }

   
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

  
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

  
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

}