package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Floyd {
    public static List<Vertice> encontrar(Grafo g, Vertice inicial, int intervalo) throws IOException {
        Long time = System.currentTimeMillis();
        int[][] dist = new int[g.getVertices().size()][g.getVertices().size()];
        for (int i = 0; i < g.getVertices().size(); i++) {
            for (int j = 0; j < dist.length; j++) {
                dist[i][j] = Integer.MAX_VALUE;
            }
        }
        dist[inicial.getDescricao().charAt(1)][inicial.getDescricao().charAt(1)] = 0;
        for (int i = 0; i < dist.length; i++) {
            Vertice atual = g.getVertices().get(i);
            for (Parentesco v : atual.getVizinhos()) {
                dist[i][v.getVizinho().charAt(1)] = v.getPeso();
                dist[v.getVizinho().charAt(1)][i] = v.getPeso();
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
            g.encontrarVertice("v" + i).setDistancia(dist[inicial.getDescricao().charAt(1)][i]);
        }
        Long tempo = (System.currentTimeMillis() - time);
        File f = new File("src/ResultadoJhonson.txt");
        BufferedWriter br = new BufferedWriter(new FileWriter(f, true));
        br.write("Jhonson com quantidade de vertice= " + g.getVertices().size() + " e quantidade de aresta= "
                + intervalo + "\t\tDemorou cerca de: " + tempo + " ms, " + ((tempo / 60000) % 60)
                + " minutos, " + ((tempo / 1000) % 60) + " segundos\n");
        br.close();
        return g.getVertices();
    }
}