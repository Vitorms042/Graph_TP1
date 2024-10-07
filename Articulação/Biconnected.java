/**
 * Esta classe implementa um algoritmo para identificar e exibir pontos de articulação em um grafo.
 * Os pontos de articulação são vértices cuja remoção aumenta o número de componentes conectados 
 * no grafo. Esta implementação pode ser utilizada para decompor um grafo em componentes biconectados.
 * 
 * O algoritmo executa em tempo O(E + V), onde E representa o número de arestas e V o número de 
 * vértices do grafo.
 * 
 * A implementação baseia-se em conceitos fundamentais de teoria de grafos, particularmente na 
 * busca em profundidade (DFS) e na análise de conexões de vértices.
 */


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

               
                low[v] = Math.min(low[v], low[w]);

               
                if (low[w] >= pre[v] && u != v){
                    articulation[v] = true;
                    numrticulation++;
                }
            }

        
            else if (w != u)
                low[v] = Math.min(low[v], pre[w]);
        }

        
        if (u == v && children > 1){
            articulation[v] = true;
            numrticulation++;
        }
    }

    public boolean isArticulation(int v) { return articulation[v]; }

    public void iterarGrafo(Graph G)
    {
            for (int v = 0; v < G.V(); v++)
            if (this.isArticulation(v)) System.out.println(v+1);
    }
}