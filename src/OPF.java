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
    Vertice atual = new Vertice();
    Vertice vizinho = new Vertice();
    List<Vertice> naoVisitados = new ArrayList<Vertice>();

    public static List<Vertice> encontrar(Grafo g, Vertice inicial, int intervalo)
            throws IOException {
        Instant start = Instant.now();
        List<Vertice> menorCaminho = new ArrayList<Vertice>();
        Vertice atual = new Vertice();
        List<Vertice> naoVisitados = new ArrayList<Vertice>();
        List<Vertice> vertices = g.getVertices();
        for (int i = 0; i < vertices.size(); i++) {
            vertices.get(i).setDistancia(Integer.MAX_VALUE);
            naoVisitados.add(vertices.get(i));
        }
        inicial.setDistancia(0);
        Collections.sort(naoVisitados);

        while (!naoVisitados.isEmpty()) {
            atual = naoVisitados.get(0);
            for (Aresta aux : atual.getarestas()) {
                if (!aux.getw().verificarVisita()) {
                    //Distancia da extremidade da aresta>max(distancia do vertice atual, peso da aresta)
                    if (aux.getw().getDistancia() > Math.max(atual.getDistancia(), aux.getPeso())) {
                        aux.getw().setDistancia(Math.max(atual.getDistancia(), aux.getPeso()));
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