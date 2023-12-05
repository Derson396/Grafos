package src;

import java.util.*;

public class Vertice implements Comparable<Vertice> {
    private String descricao;
    private int distancia, grau = 0;
    private boolean visitado = false;
    public List<Parentesco> vizinhos = new ArrayList<Parentesco>();

    public void setDescricao(String nome) {

        this.descricao = nome;
    }

    public String getDescricao() {

        return descricao;

    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public void visitar() {

        this.visitado = true;
    }

    public boolean verificarVisita() {

        return visitado;
    }

    public void setVizinhos(Vertice vizinho, int peso) {

        vizinhos.add(new Parentesco(vizinho.getDescricao(), peso));
        vizinho.vizinhos.add(new Parentesco(this.getDescricao(), peso));
        vizinho.setGrau(vizinho.getGrau() + 1);
        grau++;
    }

    public int getVizinhos(Vertice vizinho) {
        for (int i = 0; i < vizinhos.size(); i++) {
            if (vizinho.getDescricao().equals(vizinhos.get(i).getVizinho())) {
                return vizinhos.get(i).getPeso();
            }
        }
        return -1;
    }

    public int compareTo(Vertice vertice) {
        if (this.getDistancia() < vertice.getDistancia())
            return -1;
        else if (this.getDistancia() == vertice.getDistancia())
            return 0;

        return 1;

    }

    public boolean equals(Object obj) {
        if (obj instanceof Vertice) {
            Vertice vRef = (Vertice) obj;
            if (this.getDescricao().equals(vRef.getDescricao()))
                return true;
        }
        return false;
    }

    public int getGrau() {
        return grau;
    }

    public void setGrau(int grau) {
        this.grau = grau;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public List<Parentesco> getVizinhos() {
        return vizinhos;
    }

    public void setVizinhos(List<Parentesco> vizinhos) {
        this.vizinhos = vizinhos;
    }
}
