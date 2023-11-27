package src;

import java.util.*;

public class Dijstra {

    // Atributos usados na funcao encontrarMenorCaminho

    // Lista que guarda os vertices pertencentes ao menor caminho encontrado
    List<Vertice> menorCaminho = new ArrayList<Vertice>();

    // Variavel que guarda o vertice que esta sendo visitado
    Vertice atual = new Vertice();

    // Variavel que marca o vizinho do vertice atualmente visitado
    Vertice vizinho = new Vertice();

    // Lista dos vertices que ainda nao foram visitados
    List<Vertice> naoVisitados = new ArrayList<Vertice>();

    // Algoritmo de Dijkstra
    public static List<Vertice> encontrarMenorCaminhoDijkstra(Grafo grafo, Vertice v1) {
        Long time = System.currentTimeMillis();
        // Lista que guarda os vertices pertencentes ao menor caminho encontrado
        List<Vertice> menorCaminho = new ArrayList<Vertice>();

        // Variavel que guarda o vertice que esta sendo visitado
        Vertice atual = new Vertice();

        // Variavel que marca o vizinho do vertice atualmente visitado
        Vertice[] vizinho;

        // Lista dos vertices que ainda nao foram visitados
        List<Vertice> naoVisitados = new ArrayList<Vertice>();
        // Adiciona a origem na lista do menor caminho
        
        // Colocando a distancias iniciais
        for (int i = 0; i < grafo.getVertices().size(); i++) {

            // Vertice atual tem distancia zero, e todos os outros,
            // 9999999("infinita")
            if (grafo.getVertices().get(i).getDescricao()
                    .equals(v1.getDescricao())) {

                grafo.getVertices().get(i).setDistancia(0);

            } else {

                grafo.getVertices().get(i).setDistancia(9999999);

            }
            // Insere o vertice na lista de vertices nao visitados
            naoVisitados.add(grafo.getVertices().get(i));
        }

        Collections.sort(naoVisitados);

        // O algoritmo continua ate que todos os vertices sejam visitados
        while (!naoVisitados.isEmpty()) {

            // Toma-se sempre o vertice com menor distancia, que eh o primeiro
            // da
            // lista

            atual = naoVisitados.get(0);

            /*
             * Para cada vizinho (cada aresta), calcula-se a sua possivel
             * distancia, somando a distancia do vertice atual com a da aresta
             * correspondente. Se essa distancia for menor que a distancia do
             * vizinho, esta eh atualizada.
             */
            for (int i = 0; i < atual.getGrau(); i++) {
                Set<Vertice> chaveSet = atual.vizinhos.keySet();
                vizinho=null;
                vizinho = chaveSet.toArray(new Vertice[0]);
                for (int j = 0; j < vizinho.length; j++) {
                    
                
                if (!vizinho[j].verificarVisita()) {

                    // Comparando a distância do vizinho com a possível
                    // distância
                    if (vizinho[j].getDistancia() > (atual.getDistancia() + atual.getVizinhos(vizinho[j]))) {

                        vizinho[j].setDistancia(atual.getDistancia()
                                + atual.getVizinhos(vizinho[j]));
                        vizinho[j].setPai(atual);

                    }
                }}

            }
            // Marca o vertice atual como visitado e o retira da lista de nao
            // visitados
            menorCaminho.add(atual);
            atual.visitar();
            naoVisitados.remove(atual);
            /*
             * Ordena a lista, para que o vertice com menor distancia fique na
             * primeira posicao
             */

            Collections.sort(naoVisitados);

        }
        System.out.println("\t Demorou exatamente: " + (System.currentTimeMillis() - time)+" milessegundos");
        return menorCaminho;
    }
}