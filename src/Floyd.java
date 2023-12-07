package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class Floyd {
    public static List<Vertice> encontrar(Grafo g, Vertice inicial, int intervalo) throws IOException {
        Instant start = Instant.now();
        int numVertices=g.getVertices().size();
        int[][] distancias = new int[numVertices][numVertices];
        int[][] caminhos = new int[numVertices][numVertices];
        int[][] matrizAdj=gerarMatrizAdjacencia(g);

        // Inicialização das matrizes de distâncias e caminhos
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                distancias[i][j] = matrizAdj[i][j];
                if (i != j && matrizAdj[i][j] != Integer.MAX_VALUE) {
                    caminhos[i][j] = i;
                } else {
                    caminhos[i][j] = -1;
                }
            }
        }

        // Algoritmo de Floyd-Warshall
        for (int k = 0; k < numVertices; k++) {
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (distancias[i][k] != Integer.MAX_VALUE && distancias[k][j] != Integer.MAX_VALUE &&
                            distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        caminhos[i][j] = caminhos[k][j];
                    }
                }
            }
        }
        for (int i = 0; i < numVertices; i++) {
            g.encontrarVertice("v" + i).setDistancia(distancias[Integer.parseInt(inicial.getDescricao().substring(1))][i]);
        }
        Duration tempo = Duration.between(start, Instant.now());
        File f = new File("src/ResultadoFloyd.txt");
        BufferedWriter br = new BufferedWriter(new FileWriter(f, true));
        long millis = tempo.toMillis();
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        br.write("\t Com quantidade de aresta= "
                + intervalo + "\t\tDemorou cerca de: " + millis + " ms, "
                + minutes + " minutos, " + remainingSeconds + " segundos\n");br.close();
        return g.getVertices();
    }
        public static int[][] gerarMatrizAdjacencia(Grafo g) {
            int numVertices = g.getVertices().size();
            int[][] matrizAdjacencia = new int[numVertices][numVertices];
        
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    matrizAdjacencia[i][j] = Integer.MAX_VALUE;
                }
            }
        
        
            for (Aresta aresta : g.getArestas()) {
                int indiceV = Integer.parseInt(aresta.getV().getDescricao().substring(1));
                int indiceW = Integer.parseInt(aresta.getw().getDescricao().substring(1));
                matrizAdjacencia[indiceV][indiceW] = aresta.getPeso();
                matrizAdjacencia[indiceW][indiceV] = aresta.getPeso();
            }
        
            return matrizAdjacencia;
        }
}