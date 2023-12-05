package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Jhonson {
    public static List<Vertice> encontrar(Grafo g, Vertice inicial, int intervalo) throws IOException {
        Long time = System.currentTimeMillis();
        for (int i = 0; i < g.getVertices().size(); i++) {
            if (g.getVertices().get(i).getDescricao().equals(inicial.getDescricao())) {
                g.getVertices().get(i).setDistancia(0);
            } else {
                g.getVertices().get(i).setDistancia(Integer.MAX_VALUE);
            }
        }
        Vertice aux = new Vertice();
        aux.setDescricao("v?");
        aux.setVizinhos(inicial, 0);
        g.adicionarVertice(aux);
        List<Vertice> bellminha = bellminhaFord(g, aux);
        // grego aq
        g.removerVertice(aux);
        List<Vertice> resultado;
        for (int i = 0; i < g.getVertices().size(); i++) {
            List<Vertice>dijkstrin= dijkstrinha(g,i);
            for (int j = 0; j < g.getVertices().size(); j++) {
                
            }
        }
        Long tempo = (System.currentTimeMillis() - time);
        File f = new File("src/ResultadoJhonson.txt");
        BufferedWriter br = new BufferedWriter(new FileWriter(f, true));
        br.write("Jhonson com quantidade de vertice= " + g.getVertices().size() + " e quantidade de aresta= "
                + intervalo + "\tDemorou cerca de: " + tempo + " ms, " + ((tempo / 60000) % 60)
                + "ou minutos, " + ((tempo / 1000) % 60) + " segundos\n");
        br.close();
        return g.getVertices();
    }

    private static List<Vertice> dijkstrinha(Grafo g, int i) {
        return null;
    }

    private static List<Vertice> bellminhaFord(Grafo g, Vertice inicial) {
        for (int i = 0; i < g.getVertices().size(); i++) {
            if (g.getVertices().get(i).getDescricao().equals(inicial.getDescricao())) {
                g.getVertices().get(i).setDistancia(0);
            } else {
                g.getVertices().get(i).setDistancia(Integer.MAX_VALUE);
            }
        }
        for (int i = 0; i < g.getVertices().size() - 1; i++) {
            for (int j = 0; j < g.getVertices().size(); j++) {
                Vertice atual = g.getVertices().get(j);
                for (int k = 0; k < atual.getVizinhos().size(); k++) {
                    Parentesco aux = atual.getVizinhos().get(k);
                    Vertice vizinho = g.encontrarVertice(aux.getVizinho());

                    if (atual.getDistancia() + aux.getPeso() < vizinho.getDistancia()) {
                        vizinho.setDistancia(atual.getDistancia() + aux.getPeso());
                    }
                }
            }
        }
        Grafo h = new Grafo();
        for (int i = 0; i < g.getVertices().size(); i++) {
            Vertice u = new Vertice(), v = g.getVertices().get(i);
            u.setDescricao(v.getDescricao());
            u.setDistancia(v.getDistancia());
            u.setVizinhos(v.getVizinhos());
            h.adicionarVertice(u);
        }
        return h.getVertices();
    }
}