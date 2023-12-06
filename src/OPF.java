package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class OPF {
    List<Vertice> menorCaminho = new ArrayList<Vertice>();
    Vertice vizinho = new Vertice();
    List<Vertice> naoVisitados = new ArrayList<Vertice>();

    public static List<Vertice> encontrar(Grafo grafo, Vertice v1, int intervalo)
            throws IOException {
        Instant start = Instant.now();
        List<Vertice> menorCaminho = new ArrayList<Vertice>();
        Vertice atual = new Vertice();
        Vertice vizinho;
        List<Vertice> naoVisitados = new ArrayList<Vertice>();
        for (int i = 0; i < grafo.getVertices().size(); i++) {
            if (grafo.getVertices().get(i).getDescricao()
                    .equals(v1.getDescricao())) {
                grafo.getVertices().get(i).setDistancia(0);
            } else {
                grafo.getVertices().get(i).setDistancia(Integer.MAX_VALUE);
            }
            naoVisitados.add(grafo.getVertices().get(i));
        }
        Collections.sort(naoVisitados);
        while (!naoVisitados.isEmpty()) {
            atual = naoVisitados.get(0);
            for (int i = 0; i < atual.getGrau(); i++) {
                Aresta aux = atual.getarestas().get(i);
                vizinho = aux.getV();
                if (!vizinho.verificarVisita()) {
                    if (vizinho.getDistancia() > (atual.getDistancia() + aux.getPeso())) {
                        vizinho.setDistancia(Math.max(atual.getDistancia(), aux.getPeso()));
                    }
                }

            }
            menorCaminho.add(atual);
            atual.visitar();
            naoVisitados.remove(atual);
            Collections.sort(naoVisitados);
        }
        Duration tempo = Duration.between(start, Instant.now());
        File f = new File("src/ResultadoOPF.txt");
        BufferedWriter br = new BufferedWriter(new FileWriter(f, true));
        long millis = tempo.toMillis();
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        br.write("\t Com quantidade de aresta= "
                + intervalo + "\t\tDemorou cerca de: " + millis + " ms, "
                + minutes + " minutos, " + remainingSeconds + " segundos\n");
        br.close();
        return menorCaminho;
    }
}