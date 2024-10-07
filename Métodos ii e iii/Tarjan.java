
// A Java program to find biconnected components in a given
// undirected Tarjan
import java.io.File;
import java.util.*;

// This class represents a directed Tarjan using adjacency
// list representation
public class Tarjan {
  private int V; // No. of vertices & Edges respectively
  private LinkedList<Integer> adj[]; // Adjacency List

  // Count is number of biconnected components. time is
  // used to find discovery times
  static int count = 0, time = 0;
  private static LinkedList<LinkedList<Integer>> componentes = new LinkedList<>();
  private int auxiliar_comp;

  class Edge {
    int u;
    int v;

    Edge(int u, int v) {
      this.u = u;
      this.v = v;
    }
  };

  Tarjan(ForwardStar fs) {
    initialize(fs);
  }

  // Constructor
  Tarjan(int v) {
    componentes.add(new LinkedList<Integer>());
    auxiliar_comp = 0;
    V = v;
    // E = 0;
    adj = new LinkedList[v];
    for (int i = 0; i < v; ++i)
      adj[i] = new LinkedList<>();
  }

  // Function to add an edge into the Tarjan
  void addEdge(int v, int w) {
    adj[v].add(w);
    // E++;
  }

  // A recursive function that finds and prints strongly connected
  // components using DFS traversal
  // u --> The vertex to be visited next
  // disc[] --> Stores discovery times of visited vertices
  // low[] -- >> earliest visited vertex (the vertex with minimum
  // discovery time) that can be reached from subtree
  // rooted with current vertex
  // *st -- >> To store visited edges
  void addComponent(int comp, int index) {
    if (!componentes.get(index).contains(comp)) {
      componentes.get(index).add(comp);
    }
  }

  void BCCUtil(int u, int disc[], int low[], LinkedList<Edge> st,
      int parent[]) {

    // Initialize discovery time and low value
    disc[u] = low[u] = ++time;
    int children = 0;

    // Go through all vertices adjacent to this
    Iterator<Integer> it = adj[u].iterator();
    while (it.hasNext()) {
      int v = it.next(); // v is current adjacent of 'u'

      // If v is not visited yet, then recur for it
      if (disc[v] == -1) {
        children++;
        parent[v] = u;

        // store the edge in stack
        st.add(new Edge(u, v));
        BCCUtil(v, disc, low, st, parent);

        // Check if the subtree rooted with 'v' has a
        // connection to one of the ancestors of 'u'
        // Case 1 -- per Strongly Connected Components Article
        if (low[u] > low[v])
          low[u] = low[v];

        // If u is an articulation point,
        // pop all edges from stack till u -- v
        if ((disc[u] == 1 && children > 1) || (disc[u] > 1 && low[v] >= disc[u])) {
          while (st.getLast().u != u || st.getLast().v != v) {
            addComponent(st.getLast().u, auxiliar_comp);
            addComponent(st.getLast().v, auxiliar_comp);
            st.removeLast();
          }
          addComponent(st.getLast().u, auxiliar_comp);
          addComponent(st.getLast().v, auxiliar_comp);
          auxiliar_comp++;
          componentes.add(new LinkedList<>());
          st.removeLast();

          count++;
        }
      }

      // Update low value of 'u' only if 'v' is still in stack
      // (i.e. it's a back edge, not cross edge).
      // Case 2 -- per Strongly Connected Components Article
      else if (v != parent[u] && disc[v] < disc[u]) {
        if (low[u] > disc[v])
          low[u] = disc[v];

        st.add(new Edge(u, v));
      }
    }
  }

  // The function to do DFS traversal. It uses BCCUtil()
  void BCC() {
    long init = System.currentTimeMillis();
    int disc[] = new int[V];
    int low[] = new int[V];
    int parent[] = new int[V];
    LinkedList<Edge> st = new LinkedList<Edge>();

    // Initialize disc and low, and parent arrays
    for (int i = 0; i < V; i++) {
      disc[i] = -1;
      low[i] = -1;
      parent[i] = -1;
    }

    for (int i = 0; i < V; i++) {
      if (disc[i] == -1)
        BCCUtil(i, disc, low, st, parent);

      int j = 0;

      // If stack is not empty, pop all edges from stack
      while (st.size() > 0) {
        j = 1;
        System.out.print(st.getLast().u + "--" + st.getLast().v + " ");
        st.removeLast();
      }
      if (j == 1) {
        System.out.println();
        count++;
      }
    }

    System.out.println((System.currentTimeMillis() - init) + " ms");
  }

  public void addAllEdge(ForwardStar fs) {
    for (int i = 1; i < fs.saida.length; i++) {
      int vet[] = fs.listaSucessores(i);
      for (int j = 0; j < vet.length; j++) {
        addEdge(i, vet[j]);
      }
    }
  }

  public void initialize(ForwardStar forwardStar) {
    
    Tarjan g = new Tarjan(forwardStar.m + 2);
    g.addAllEdge(forwardStar);

    g.BCC();
    componentes.removeLast();
    for (LinkedList<Integer> element : componentes) {
      System.out.println(element);
    }

  }


  public static void main(String[] args) throws Exception {
    ForwardStar fs = new ForwardStar(new File("Metodo i/grafos100vertices"));  

        
        usingTarjan(fs);
    }

    public static void usingTarjan(ForwardStar fs) {
        new Tarjan(fs);
    }


}


