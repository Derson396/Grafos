package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class OPF {
    List<Vertice> menorCaminho = new ArrayList<Vertice>();
    Vertice atual = new Vertice();
    Vertice vizinho = new Vertice();
    List<Vertice> naoVisitados = new ArrayList<Vertice>();
    public static List<Vertice> encontrarOPF(Grafo grafo, Vertice v1, int intervalo) throws IOException {
        Long time = System.currentTimeMillis();
        List<Vertice> menorCaminho = new ArrayList<Vertice>();
        Vertice atual = new Vertice();
        Vertice[] vizinho;
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
                Set<String> chaveSet = atual.vizinhos.keySet();
                String[] chave = chaveSet.toString().split(",");
                for (int j = 0; j < chave.length; j++) {
                    for (int j2 = 0; j2 < chave[j].length(); j2++) {
                        if (chave[j].charAt(j2) == '[' || chave[j].charAt(j2) == ' ' || chave[j].charAt(j2) == ']') {
                            StringBuilder a = new StringBuilder(chave[j]);
                            a.deleteCharAt(j2);
                            chave[j] = new String(a);
                        }
                    }
                }
                vizinho = new Vertice[chave.length];
                for (int j = 0; j < chave.length; j++) {
                    vizinho[j] = grafo.encontrarVertice(chave[j]);
                }
                for (int j = 0; j < vizinho.length; j++) {
                    if (!vizinho[j].verificarVisita()) {
                        if (vizinho[j].getDistancia() > (atual.getDistancia() + atual.getVizinhos(vizinho[j]))) {
                            vizinho[j].setDistancia(atual.getDistancia()
                                    + atual.getVizinhos(vizinho[j]));//modificar aqui
                            vizinho[j].setPai(atual);
                        }
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
        BufferedWriter br = new BufferedWriter(new FileWriter(f));
        br.write("OPF com quantidade de vertice= " + grafo.getVertices().size() + " e quantidade de aresta= "
                + intervalo + " Demorou cerca de: " + tempo + " ms, " + ((tempo / 1000) % 60)
                + " segundos, " + ((tempo / 60000) % 60) + " minutos");
        br.close();
        return menorCaminho;
    }
}