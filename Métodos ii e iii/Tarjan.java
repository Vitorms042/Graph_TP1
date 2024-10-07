import java.io.File;
import java.util.*;

// Classe para encontrar componentes biconectados usando o algoritmo de Tarjan
public class Tarjan {
    private int vertexCount; // Total de vértices
    private LinkedList<Integer> adjacencyList[]; // Representação da lista de adjacência

    static int componentCount = 0, discoveryTime = 0; // Contadores para componentes e tempos de descoberta
    private static LinkedList<LinkedList<Integer>> biconnectedComponents = new LinkedList<>(); // Para armazenar componentes biconectados
    private int currentComponentIndex; // Índice atual do componente sendo processado

    class Connection {
        int startVertex; // Vértice inicial de uma aresta
        int endVertex; // Vértice final de uma aresta

        Connection(int start, int end) {
            this.startVertex = start;
            this.endVertex = end;
        }
    };

    // Construtor para Tarjan
    Tarjan(ForwardStar forwardStar) {
        setupGraph(forwardStar); // Inicializa o grafo a partir do ForwardStar
    }

    Tarjan(int vertices) {
        biconnectedComponents.add(new LinkedList<Integer>());
        currentComponentIndex = 0;
        vertexCount = vertices;
        adjacencyList = new LinkedList[vertices];
        for (int i = 0; i < vertices; ++i)
            adjacencyList[i] = new LinkedList<>();
    }

    // Método para adicionar uma aresta
    void insertEdge(int from, int to) {
        adjacencyList[from].add(to);
    }

    void recordComponent(int vertex, int componentIndex) {
        if (!biconnectedComponents.get(componentIndex).contains(vertex)) {
            biconnectedComponents.get(componentIndex).add(vertex);
        }
    }

    void explore(int currentVertex, int discovery[], int low[], LinkedList<Connection> edgeStack, int parent[]) {
        discovery[currentVertex] = low[currentVertex] = ++discoveryTime; // Define o tempo de descoberta e valor baixo
        int childCount = 0;

        for (int adjacentVertex : adjacencyList[currentVertex]) {
            if (discovery[adjacentVertex] == -1) {
                childCount++;
                parent[adjacentVertex] = currentVertex;
                edgeStack.add(new Connection(currentVertex, adjacentVertex)); // Armazena a aresta

                explore(adjacentVertex, discovery, low, edgeStack, parent); // DFS recursivo

                if (low[currentVertex] > low[adjacentVertex]) {
                    low[currentVertex] = low[adjacentVertex]; // Atualiza o valor baixo
                }

                if ((discovery[currentVertex] == 1 && childCount > 1) || (discovery[currentVertex] > 1 && low[adjacentVertex] >= discovery[currentVertex])) {
                    while (edgeStack.getLast().startVertex != currentVertex || edgeStack.getLast().endVertex != adjacentVertex) {
                        recordComponent(edgeStack.getLast().startVertex, currentComponentIndex);
                        recordComponent(edgeStack.getLast().endVertex, currentComponentIndex);
                        edgeStack.removeLast();
                    }
                    recordComponent(edgeStack.getLast().startVertex, currentComponentIndex);
                    recordComponent(edgeStack.getLast().endVertex, currentComponentIndex);
                    currentComponentIndex++;
                    biconnectedComponents.add(new LinkedList<>());
                    edgeStack.removeLast();
                    componentCount++;
                }
            } else if (adjacentVertex != parent[currentVertex] && discovery[adjacentVertex] < discovery[currentVertex]) {
                if (low[currentVertex] > discovery[adjacentVertex]) {
                    low[currentVertex] = discovery[adjacentVertex]; // Atualiza o valor baixo para a aresta de retorno
                }
                edgeStack.add(new Connection(currentVertex, adjacentVertex)); // Armazena a aresta de retorno
            }
        }
    }

    // Traversal DFS para encontrar componentes biconectados
    void findBiconnectedComponents() {
        long startTime = System.currentTimeMillis();
        int discovery[] = new int[vertexCount];
        int low[] = new int[vertexCount];
        int parent[] = new int[vertexCount];
        LinkedList<Connection> edgeStack = new LinkedList<>();

        Arrays.fill(discovery, -1); // Inicializa o array de descoberta
        Arrays.fill(low, -1); // Inicializa o array de valores baixos
        Arrays.fill(parent, -1); // Inicializa o array de pais

        for (int i = 0; i < vertexCount; i++) {
            if (discovery[i] == -1) {
                explore(i, discovery, low, edgeStack, parent); // Explora vértices não visitados
            }

            int found = 0;

            while (edgeStack.size() > 0) {
                found = 1;
                System.out.print(edgeStack.getLast().startVertex + "--" + edgeStack.getLast().endVertex + " ");
                edgeStack.removeLast(); // Remove arestas da pilha
            }
            if (found == 1) {
                System.out.println(); // Imprime arestas encontradas
                componentCount++;
            }
        }

        System.out.println((System.currentTimeMillis() - startTime) + " ms"); // Tempo de execução
    }

    public void addAllEdges(ForwardStar forwardStar) {
        for (int i = 1; i < forwardStar.saida.length; i++) {
            int successors[] = forwardStar.listaSucessores(i);
            for (int successor : successors) {
                insertEdge(i, successor); // Insere todas as arestas do ForwardStar
            }
        }
    }

    public void setupGraph(ForwardStar forwardStar) {
        Tarjan tarjanGraph = new Tarjan(forwardStar.m + 2);
        tarjanGraph.addAllEdges(forwardStar); // Adiciona arestas do ForwardStar
        tarjanGraph.findBiconnectedComponents(); // Encontra componentes biconectados
        biconnectedComponents.removeLast();
        for (LinkedList<Integer> component : biconnectedComponents) {
            System.out.println(component); // Imprime cada componente biconectado
        }
    }

    public static void main(String[] args) throws Exception {
        ForwardStar forwardStar = new ForwardStar(new File("Metodo i/grafos100vertices")); // Carrega dados do grafo
        initializeTarjan(forwardStar);
    }

    public static void initializeTarjan(ForwardStar forwardStar) {
        new Tarjan(forwardStar); // Cria instância de Tarjan
    }
}
