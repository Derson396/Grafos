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
        int[][] dist = new int[g.getVertices().size()][g.getVertices().size()];
        for (int i = 0; i < g.getVertices().size(); i++) {
            for (int j = 0; j < dist.length; j++) {
                dist[i][j] = Integer.MAX_VALUE;
            }
        }
        dist[Integer.parseInt(inicial.getDescricao().substring(1))][Integer.parseInt(inicial.getDescricao().substring(1))] = 0;
        for (int i = 0; i < dist.length; i++) {
            Vertice atual = g.getVertices().get(i);
            for (Aresta v : atual.getarestas()) {
                dist[i][Integer.parseInt(v.getw().getDescricao().substring(1))] = v.getPeso();
                dist[Integer.parseInt(v.getw().getDescricao().substring(1))][i] = v.getPeso();
            }
        }
        for (int k = 0; k < g.getVertices().size(); k++) {
            for (int i = 0; i < g.getVertices().size(); i++) {
                for (int j = 0; j < g.getVertices().size(); j++) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
        for (int i = 0; i < dist.length; i++) {
            g.encontrarVertice("v" + i).setDistancia(dist[Integer.parseInt(inicial.getDescricao().substring(1))][i]);
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
}