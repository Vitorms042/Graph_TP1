// Para a implementação desse método foi utilizado como referência o vídeo tutorial: https://www.youtube.com/watch?v=dmp7DIQl9fc.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DisjointPathsFinder {

    static int numVertices; 
    static List<Integer>[] adjacencyList; 

    static boolean findAugmentingPath(int[][] residualGraph, int source, int sink, int[] parent) {
        boolean[] visited = new boolean[numVertices];
        visited[source] = true;
        parent[source] = -1;

        Stack<Integer> stack = new Stack<>();
        stack.push(source);

        while (!stack.isEmpty()) {
            int currentVertex = stack.pop();

            for (int neighbor = 0; neighbor < numVertices; neighbor++) {
                if (!visited[neighbor] && residualGraph[currentVertex][neighbor] > 0) {
                    visited[neighbor] = true;
                    parent[neighbor] = currentVertex;
                    stack.push(neighbor);

                    if (neighbor == sink) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    static List<List<Integer>> findDisjointPaths(int source, int sink) {
        List<Integer> visitedVertices = new ArrayList<>();
        int[][] residualGraph = buildResidualGraph();

        int[] parent = new int[numVertices];
        List<List<Integer>> disjointPaths = new ArrayList<>();

        while (findAugmentingPath(residualGraph, source, sink, parent)) {
            List<Integer> path = new ArrayList<>();
            int currentVertex = sink;

            while (currentVertex != -1) {
                if (visitedVertices.contains(currentVertex) && currentVertex != source && currentVertex != sink) {
                    return Collections.emptyList();
                }

                visitedVertices.add(currentVertex);
                path.add(currentVertex);

                int parentVertex = parent[currentVertex];
                if (parentVertex != -1) {
                    residualGraph[parentVertex][currentVertex] = 0;
                    residualGraph[currentVertex][parentVertex] = 1; 
                }
                currentVertex = parentVertex;
            }

            Collections.reverse(path); // IMPORTANTE: NÂO APAGUE, È NECESSÀRIO REVERTER O CAMINHO PARA FICAR NA ORDEM CORRETA
            disjointPaths.add(path);
        }
        return disjointPaths;
    }

    static int[][] buildResidualGraph() {
        int[][] residualGraph = new int[numVertices][numVertices];
        for (int u = 0; u < numVertices; u++) {
            for (int v : adjacencyList[u]) {
                residualGraph[u][v] = 1;
            }
        }
        return residualGraph;
    }

    static void identifyGraphBlocks(List<List<Integer>> paths) {
        List<Set<Integer>> vertexSets = new ArrayList<>();
        Set<Integer> currentSet = new HashSet<>();

        // Agrupa caminhos em conjuntos
        for (int i = 0; i < paths.size(); i++) {
            List<Integer> path = paths.get(i);
            if (i > 0 && !path.get(path.size() - 1).equals(paths.get(i - 1).get(paths.get(i - 1).size() - 1))) {
                vertexSets.add(currentSet);
                currentSet = new HashSet<>();
            }
            currentSet.addAll(path);
        }
        vertexSets.add(currentSet);

        for (int i = 0; i < vertexSets.size(); i++) {
            for (int j = i + 1; j < vertexSets.size(); j++) {
                Set<Integer> intersection = new HashSet<>(vertexSets.get(i));
                intersection.retainAll(vertexSets.get(j));

                if (intersection.size() > 1) {
                    vertexSets.get(i).addAll(vertexSets.get(j));
                    vertexSets.set(j, new HashSet<>()); 
                }
            }
        }

        vertexSets.removeIf(Set::isEmpty);
        vertexSets.forEach(block -> System.out.println(new ArrayList<>(block)));
    }

    static void loadGraphFromFile(String filename) {
        List<List<Integer>> edges = new ArrayList<>();
        String vertexEdgeInfo = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            vertexEdgeInfo = reader.readLine().trim().replaceAll("\\s+", " ");

            String line;
            while ((line = reader.readLine()) != null) {
                List<Integer> edge = Arrays.stream(line.trim().split("\\s+"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                edges.add(edge);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        initializeGraph(vertexEdgeInfo, edges);
    }

    static void initializeGraph(String vertexEdgeInfo, List<List<Integer>> edges) {
        List<Integer> vertexEdgeData = Arrays.stream(vertexEdgeInfo.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        numVertices = vertexEdgeData.get(0);
        adjacencyList = new ArrayList[numVertices];

        for (int i = 0; i < numVertices; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (List<Integer> edge : edges) {
            int u = edge.get(0) - 1;
            int v = edge.get(1) - 1;
            adjacencyList[u].add(v);
            adjacencyList[v].add(u);
        }

        List<List<Integer>> disjointPaths = findAllDisjointPaths();
        identifyGraphBlocks(disjointPaths);
    }

    static List<List<Integer>> findAllDisjointPaths() {
        List<List<Integer>> disjointPaths = new ArrayList<>();

        for (int u = 0; u < numVertices - 1; u++) {
            for (int v = u + 1; v < numVertices; v++) {
                List<List<Integer>> paths = findDisjointPaths(u, v);

                if (!paths.isEmpty()) {
                    disjointPaths.addAll(paths);
                }
            }
        }

        return disjointPaths;
    }

    static void generateRandomGraph(int numVertices, int numEdges) {
        DisjointPathsFinder.numVertices = numVertices;
        adjacencyList = new ArrayList[numVertices];

        for (int i = 0; i < numVertices; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        Random random = new Random();
        int edgeCount = 0;

        while (edgeCount < numEdges) {
            int u = random.nextInt(numVertices);
            int v = random.nextInt(numVertices);

            if (u != v && !adjacencyList[u].contains(v)) {
                adjacencyList[u].add(v);
                adjacencyList[v].add(u);
                edgeCount++;
            }
        }

        List<List<Integer>> disjointPaths = findAllDisjointPaths();
        identifyGraphBlocks(disjointPaths);
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // loadGraphFromFile("graph-test-ex-tp01.txt"); // É possivel entrar com um grafo já existente caso seja necessário, dai só é preciso passar o nome do mesmo para ser usado
        generateRandomGraph(100, 2000); // Essa função cria um grafo aleatório onde o primeiro parametro é a quantidade de grafos e o segundo é a quantidade de arestas

        long endTime = System.currentTimeMillis();
        System.out.println("Tempo de execução: " + (endTime - startTime) + " ms");
    }
}
