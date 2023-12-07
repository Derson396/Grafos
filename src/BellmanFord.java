package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class BellmanFord {
    public static List<Vertice> encontrar(Grafo g, Vertice inicial, int intervalo) throws IOException {
        Instant start = Instant.now();
        List<Vertice> vertices=g.getVertices();
        List<Aresta> arestas=g.getArestas();
        int numVertices=vertices.size();
        for (int i = 0; i < numVertices; i++) {
            vertices.get(i).setDistancia(Integer.MAX_VALUE);
        }
        inicial.setDistancia(0);
        for (int i = 1; i < numVertices; i++) {
            boolean flag=true;
            for(Aresta aresta:arestas){
                Vertice w=aresta.getw();
                Vertice v=aresta.getV();
                int peso=aresta.getPeso();
                if (v.getDistancia()!=Integer.MAX_VALUE&&w.getDistancia()>v.getDistancia()+peso) {
                    w.setDistancia(v.getDistancia()+peso);
                    flag=false;
                    w.setPai(v.getDescricao());
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
        return vertices;
    }
}