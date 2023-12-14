package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Jhonson {
    public static List<Vertice> encontrar(Grafo g, Vertice inicial, int intervalo)
            throws IOException, CloneNotSupportedException {
        Instant start = Instant.now();
        List<Vertice> vertices = g.getVertices();
        for (Vertice v : vertices) {
            v.setDistancia(Integer.MAX_VALUE);
        }
        inicial.setDistancia(0);
        Vertice aux = new Vertice();
        aux.setDescricao("v?");
        g.adicionarVertice(aux);
        for (Vertice vertice : vertices) {
            aux.setArestas(g, vertice, 0);
        }
        List<Vertice> bellminha = bellminhaFord(g, aux);
        g.removerVertice(aux);
        atualizarArestas(g, bellminha);
        List<Vertice> dijkstrin;

        dijkstrin = dijkstrinha(g, inicial);
        for (Vertice vertice : vertices) {
            vertice.setDistancia(dijkstrin.get(dijkstrin.indexOf(vertice)).getDistancia()
                    - bellminha.get(bellminha.indexOf(inicial)).getDistancia()
                    + bellminha.get(bellminha.indexOf(vertice)).getDistancia());
        }
        Duration tempo = Duration.between(start, Instant.now());
        File f = new File("src/ResultadoJhonson.txt");
        long millis = tempo.toMillis();
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        BufferedWriter br = new BufferedWriter(new FileWriter(f, true));
        br.write("\t Com quantidade de aresta= "
                + intervalo + "\t\tDemorou cerca de: " + millis + " ms, "
                + minutes + " minutos, " + remainingSeconds + " segundos\n");
        br.close();
        return g.getVertices();
    }

    private static void atualizarArestas(Grafo g, List<Vertice> bellminha) {
        List<Aresta> arestas = g.getArestas();
        for (Aresta aresta : arestas) {
            aresta.setPeso(aresta.getPeso() + aresta.getV().getDistancia()
                    - (bellminha.get(bellminha.indexOf(aresta.getw())).getDistancia()));
        }
    }

    private static List<Vertice> dijkstrinha(Grafo g, Vertice inicial) {
        Vertice atual = new Vertice();
        List<Vertice> naoVisitados = new ArrayList<Vertice>();
        List<Vertice> vertices = g.getVertices();
        inicial.setDistancia(0);
        Collections.sort(naoVisitados);

        while (!naoVisitados.isEmpty()) {
            atual = naoVisitados.get(0);
            for (Aresta aux : atual.getarestas()) {
                if (!aux.getw().verificarVisita()) {
                    if (aux.getw().getDistancia() > (atual.getDistancia() + aux.getPeso())) {
                        aux.getw().setDistancia(atual.getDistancia()
                                + aux.getPeso());
                    }
                }

            }
            atual.visitar();
            naoVisitados.remove(atual);

            Collections.sort(naoVisitados);

        }
        return vertices;
    }

    private static List<Vertice> bellminhaFord(Grafo g, Vertice inicial) throws CloneNotSupportedException {
        Grafo h = new Grafo();
        h = g.clone();
        List<Vertice> vertices = h.getVertices();
        List<Aresta> arestas = h.getArestas();
        int numVertices = vertices.size();
        inicial.setDistancia(0);
        for (int i = 1; i < numVertices; i++) {
            boolean flag = true;
            for (Aresta aresta : arestas) {
                Vertice w = aresta.getw();
                Vertice v = aresta.getV();
                int peso = aresta.getPeso();
                if (v.getDistancia() != Integer.MAX_VALUE && w.getDistancia() > v.getDistancia() + peso) {
                    w.setDistancia(v.getDistancia() + peso);
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
        
        return h.getVertices();
    }
}