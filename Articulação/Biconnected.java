/******************************************************************************
 *  Compilation:  javac Biconnected.java
 *  Execution:    java Biconnected V E
 *  Dependencies: Graph.java GraphGenerator.java
 *
 *  Identify articulation points and print them out.
 *  This can be used to decompose a graph into biconnected components.
 *  Runs in O(E + V) time.
 *
 ******************************************************************************/

 public class Biconnected {
    private int[] low;
    private int[] pre;
    private int cnt;
    private boolean[] articulation;
    private int numrticulation;

    public Biconnected(Graph G) {
        numrticulation=0;
        low = new int[G.V()];
        pre = new int[G.V()];
        articulation = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            low[v] = -1;
        for (int v = 0; v < G.V(); v++)
            pre[v] = -1;

        for (int v = 0; v < G.V(); v++)
            if (pre[v] == -1)
                dfs(G, v, v);
    }

    public int[] getArrayArticulation() {
        int[] articulations = new int[numrticulation];
        int j =0;
        for(int i = 0;i<numrticulation;i++){
            for(;j<low.length;j++){
                if(articulation[j] == true){
                    articulations[i] = (j+1);
                    j++;
                    break;
                }
            }
        }
        return articulations;
    }

    private void dfs(Graph G, int u, int v) {
        int children = 0;
        pre[v] = cnt++;
        low[v] = pre[v];
        for (int w : G.adj(v)) {
            if (pre[w] == -1) {
                children++;
                dfs(G, v, w);

                // update low number
                low[v] = Math.min(low[v], low[w]);

                // non-root of DFS is an articulation point if low[w] >= pre[v]
                if (low[w] >= pre[v] && u != v){
                    articulation[v] = true;
                    numrticulation++;
                }
            }

            // update low number - ignore reverse of edge leading to v
            else if (w != u)
                low[v] = Math.min(low[v], pre[w]);
        }

        // root of DFS is an articulation point if it has more than 1 child
        if (u == v && children > 1){
            articulation[v] = true;
            numrticulation++;
        }
    }

    // is vertex v an articulation point?
    public boolean isArticulation(int v) { return articulation[v]; }

    public void iterarGrafo(Graph G)
    {
            for (int v = 0; v < G.V(); v++)
            if (this.isArticulation(v)) System.out.println(v+1);
    }
}