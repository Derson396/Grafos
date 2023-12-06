package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class BellmanFord {
    public static int[] encontrar(Grafo g, Vertice inicial, int intervalo) throws IOException {
        Instant start = Instant.now();
        List<Vertice> vertices=g.getVertices();
        List<Aresta> arestas=g.getArestas();
        int[] dist = new int[vertices.size()];
        int numVertices=vertices.size();
        for (int i = 0; i < dist.length; i++) {
            dist[i]=Integer.MAX_VALUE;
        }
        dist[Integer.parseInt(inicial.getDescricao().substring(1))]=0;
        for (int i = 1; i < numVertices; i++) {
            boolean flag=true;
            for(Aresta aresta:arestas){
                Vertice w=aresta.getw();
                Vertice v=aresta.getV();
                int peso=aresta.getPeso();
                int wb=Integer.parseInt(w.getDescricao().substring(1));
                int ve=Integer.parseInt(v.getDescricao().substring(1));
                if (dist[ve]!=Integer.MAX_VALUE&&dist[wb]>dist[ve]+peso) {
                    dist[wb]=dist[ve]+peso;
                    flag=false;
                    w.setPai(ve);
                    w.setDistancia(dist[wb]);
                }
            }
            if (flag) {
                break;
            }
        }
        Duration tempo = Duration.between(start, Instant.now());
        File f = new File("src/ResultadoBellmanFord.txt");
        BufferedWriter br = new BufferedWriter(new FileWriter(f, true));
        long millis = tempo.toMillis();
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        br.write("\t Com quantidade de aresta= "
                + intervalo + "\t\tDemorou cerca de: " + millis + " ms, "
                + minutes + " minutos, " + remainingSeconds + " segundos\n");
        br.close();
        return dist;//retornar o vetor ou a list vertices seria o msm resultado porem estou retornando o vetor so pra saida ficar organizada:)
    }
}