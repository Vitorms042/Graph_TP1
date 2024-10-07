/**
 * A classe {@code Articulacao} implementa uma estrutura para a análise de grafos não direcionados
 * e a identificação de vértices de articulação. Um vértice de articulação é um ponto crítico,
 * cuja remoção resulta em um aumento no número de componentes conectados no grafo. A classe 
 * permite a adição e remoção de arestas e vértices, além de utilizar a busca em profundidade 
 * (DFS) para explorar o grafo e determinar os componentes conectados resultantes após a 
 * remoção das articulações. Os componentes conectados são armazenados em uma lista de listas, 
 * facilitando o acesso e a manipulação.
 * 
 * REFERÊNCIA: https://www.geeksforgeeks.org
 */


import java.util.*;


class Articulacao {
    private int V;
    private LinkedList<Integer> adj[];
    private boolean visited[];
    private boolean removed[];
    LinkedList<LinkedList<Integer>> componentesConexos;

    @SuppressWarnings("unchecked") Articulacao(int v)
    {
        componentesConexos = new LinkedList<>();
        V = v;
        visited = new boolean[V];
        removed = new boolean[V];
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList<>();
    }

    Articulacao(int numVertices, ForwardStar fs){
        this(numVertices+1);
        addAllEdge(fs);
    }

    void addEdge(int v, int w){
        adj[v].add(w);
    }

    void removeVertice(int v){

        for(int i = 1; i<V;i++)
            removeEdge(i, v);
        
        removed[v] = true;
    }


    void removeEdge(int v, int w){
        int aux= adj[v].indexOf(w);
        if(aux!=-1){
            adj[v].remove(aux);
        }

    }

    void DFSUtil(int v)
    {
        visited[v] = true;

        // System.out.print(v + " ");
        componentesConexos.getLast().add(v);

        Iterator<Integer> i = adj[v].listIterator();
        while (i.hasNext()) {
            int n = i.next();
            if (!visited[n])
                DFSUtil(n);
        }
    }

    void DFS1(int v){
        DFSUtil(v);
    }

    public int allVerticesDiscovery(){
        for(int i = 1; i< V;i++){
            if(removed[i] == false&&visited[i] == false){
                return i;
            }
        }
        return -1;
    }


    public void addAllEdge(ForwardStar fs){
        for(int i = 1; i < fs.saida.length;i++){
            int vet[] = fs.listaSucessores(i);
            for(int j = 0;j < vet.length;j++){
                addEdge(i, vet[j]);
            }
        }
    }

    public boolean isArticualao(int element, int[] vet){
        for(int i =0; i< vet.length;i++){
            if(element==vet[i]){
                return true;
            }
        }
        return false;
    }

    public void inserirArticulacoesNosComponentes(LinkedList<Integer> aux, int articulacao, int[] allArticulacoes, LinkedList<Integer> tratarArticulacoesVizinhas){
        for (Integer element: aux) {
            for(LinkedList<Integer> compC:componentesConexos){
                if(compC.indexOf(element) != -1){

                    if(!isArticualao(element, allArticulacoes)){
                        if(compC.indexOf(articulacao)== -1)
                            compC.add(articulacao);
                    }
                }
                
                if(isArticualao(element, allArticulacoes)){

                    int indexArt = tratarArticulacoesVizinhas.indexOf(articulacao);
                    int indexElement = tratarArticulacoesVizinhas.indexOf(element);

                    if(indexElement==-1 || indexArt==-1 || (indexElement-1 == indexArt)){
                        tratarArticulacoesVizinhas.add(articulacao);
                        tratarArticulacoesVizinhas.add(element);
                    }
                    break;
                }
            }
        }
    }

    public void cloneArticulacoes(LinkedList<Integer> art[], int articulacao,int i ){
        for (int element : adj[articulacao]) 
            art[i].add(element);
    }
    //cria lista de componentes
    public LinkedList<LinkedList<Integer>> getComponentes(int[] articulacoes){
        
        LinkedList<Integer> art[] = new LinkedList[articulacoes.length];
        for(int i =0;i<art.length;i++){
            art[i] = new LinkedList<Integer>();
            cloneArticulacoes(art, articulacoes[i],i);
        }


        for(int i =0; i< articulacoes.length;i++)
            removeVertice(articulacoes[i]);
        
        int teste = allVerticesDiscovery();
        while(teste != -1){
            componentesConexos.add(new LinkedList<Integer>());
            DFS1(teste);
            teste=allVerticesDiscovery();
        }
        LinkedList<Integer> articulacaoVizinha = new LinkedList<>();
        for(int i = 0; i < articulacoes.length;i++){
            inserirArticulacoesNosComponentes(art[i], articulacoes[i], articulacoes,articulacaoVizinha);
        }
        

        Integer vizinhosArt[] = articulacaoVizinha.toArray(new Integer[0]);
        for(int i=0;i<vizinhosArt.length;i+=2){
            for(int j=0; j<componentesConexos.size();j++){
                if(componentesConexos.get(j).indexOf(vizinhosArt[i]) != -1 && componentesConexos.get(j).indexOf(vizinhosArt[i+1]) != -1 ){
                    break;
                }
                if(j ==componentesConexos.size()-1){
                    componentesConexos.add(new LinkedList<Integer>());
                    componentesConexos.getLast().add(vizinhosArt[i]);
                    componentesConexos.getLast().add(vizinhosArt[i+1]);
                }

                
            }
        }

        for(LinkedList<Integer> element: componentesConexos)
            System.out.println(element);
        
        return componentesConexos;
        
        
    }
}