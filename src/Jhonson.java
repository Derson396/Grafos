package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Jhonson {
    public static List<Vertice> encontrar(Grafo g, Vertice inicial, int intervalo) throws IOException {
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
        atualizarArestas(g, bellminha);List<Vertice> dijkstrin;
        /*for (Vertice v : vertices) {
            dijkstrin = dijkstrinha(g, v);
            for (Vertice vertice : vertices) {
                vertice.setDistancia(dijkstrin.get(dijkstrin.indexOf(vertice)).getDistancia()
                        - bellminha.get(bellminha.indexOf(v)).getDistancia()
                        + bellminha.get(bellminha.indexOf(vertice)).getDistancia());
            }
        }*/
        dijkstrin=dijkstrinha(g, inicial);
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
        return vertices;
    }

    private static void atualizarArestas(Grafo g, List<Vertice> bellminha) {
        List<Aresta> arestas = g.getArestas();
        for (Aresta aresta : arestas) {
            aresta.setPeso(aresta.getPeso() + aresta.getV().getDistancia()
                    - (bellminha.get(bellminha.indexOf(aresta.getw())).getDistancia()));
        }
    }

    private static List<Vertice> dijkstrinha(Grafo g, Vertice inicial) {
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
                    if (aux.getw().getDistancia() > (atual.getDistancia() + aux.getPeso())) {
                        aux.getw().setDistancia(atual.getDistancia()
                                + aux.getPeso());
                        aux.getw().setPai(atual.getDescricao());
                    }
                }

            }
            menorCaminho.add(atual);
            atual.visitar();
            naoVisitados.remove(atual);

            Collections.sort(naoVisitados);

        }
        return menorCaminho;
    }

    private static List<Vertice> bellminhaFord(Grafo g, Vertice inicial) {
        List<Vertice> vertices = g.getVertices();
        List<Aresta> arestas = g.getArestas();
        int numVertices = vertices.size();
        for (int i = 0; i < numVertices; i++) {
            vertices.get(i).setDistancia(Integer.MAX_VALUE);
        }
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
                    w.setPai(v.getDescricao());
                }
            }
            if (flag) {
                break;
            }
        }
        Grafo h = new Grafo();
        for (Vertice v : vertices) {
            Vertice u = new Vertice(), w = v;
            u.setDescricao(w.getDescricao());
            u.setDistancia(w.getDistancia());
            u.setarestas(w.getarestas());
            h.adicionarVertice(u);
        }
        return h.getVertices();
    }
}