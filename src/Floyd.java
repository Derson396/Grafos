package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class Floyd {
    public static List<Vertice> encontrar(Grafo g, Vertice inicial, int intervalo, int[][] matrizAdj)
            throws IOException {
        Instant start = Instant.now();
        int numVertices = g.getVertices().size();
        int[][] distancias = new int[numVertices][numVertices];
        // Inicialização das matrizes de distâncias e caminhos
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                distancias[i][j] = matrizAdj[i][j];
            }
            distancias[i][i]=0;
        }

        // Algoritmo de Floyd-Warshall
        for (int k = 0; k < numVertices; k++) {
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (distancias[i][k] != Integer.MAX_VALUE && distancias[k][j] != Integer.MAX_VALUE &&
                            distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        
                        
                    }
                }
            }
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
                + minutes + " minutos, " + remainingSeconds + " segundos\n");
        br.close();
        for (int i = 0; i < distancias.length; i++) {
            g.encontrarVertice("v" + i)
                                .setDistancia(distancias[Integer.parseInt(inicial.getDescricao().substring(1))][i]);
        }

        inicial.setDistancia(0);
        return g.getVertices();
    }
}