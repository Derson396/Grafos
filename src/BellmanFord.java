package src;

import java.util.*;

public class BellmanFord {
    public List<Vertice> encontrar(Grafo g, Vertice inicial) {
        List<Vertice> menorCaminho = new ArrayList<Vertice>();
        for (int i = 0; i < g.getVertices().size(); i++) {
            if (g.getVertices().get(i).getDescricao().equals(inicial.getDescricao())) {
                g.getVertices().get(i).setDistancia(0);
            } else {
                g.getVertices().get(i).setDistancia(Integer.MAX_VALUE);
            }
        }
        for (int i = 0; i < g.getVertices().size() - 1; i++) {
            for (int j = 0; i < g.getVertices().size(); i++) {
                Vertice atual=g.getVertices().get(j);
                Set<String> chaveSet = atual.vizinhos.keySet();
                String[] chave = chaveSet.toString().split(",");
                for (int j2 = 0; j2 < chave.length; j2++) {
                    for (int j3 = 0; j3 < chave[j3].length(); j3++) {
                        if (chave[j2].charAt(j3) == '[' || chave[j2].charAt(j3) == ' ' || chave[j2].charAt(j3) == ']') {
                            StringBuilder a = new StringBuilder(chave[j2]);
                            a.deleteCharAt(j3);
                            chave[j2] = new String(a);
                        }
                    }
                }
                Vertice[] vizinhos = new Vertice[chave.length];
                for (int j2 = 0; j2 < chave.length; j2++) {
                    vizinhos[j2] = g.encontrarVertice(chave[j2]);

                }
            }
        }
        return menorCaminho;
    }
}
