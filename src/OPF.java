package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class OPF {
    List<Vertice> menorCaminho = new ArrayList<Vertice>();
    Vertice atual = new Vertice();
    Vertice vizinho = new Vertice();
    List<Vertice> naoVisitados = new ArrayList<Vertice>();
    public static List<Vertice> encontrar(Grafo grafo, Vertice v1, int intervalo)
            throws IOException {
        Long time = System.currentTimeMillis();
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
                Parentesco aux = atual.getVizinhos().get(i);
                vizinho = grafo.encontrarVertice(aux.getVizinho());
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
        Long tempo = (System.currentTimeMillis() - time);
        File f = new File("src/ResultadoDjikstra.txt");
        BufferedWriter br = new BufferedWriter(new FileWriter(f, true));
        br.write("Djikstra com quantidade de vertice= " + grafo.getVertices().size() + " e quantidade de aresta= "
                + intervalo + "\t\tDemorou cerca de: " + tempo + " ms, " + ((tempo / 60000) % 60)
                + " minutos, " + ((tempo / 1000) % 60) + " segundos\n");
        br.close();
        return menorCaminho;
    }
}