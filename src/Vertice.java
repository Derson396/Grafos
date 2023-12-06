package src;

import java.util.*;

public class Vertice implements Comparable<Vertice> {
    private String descricao;
    private int distancia, grau = 0;
    private boolean visitado = false;
    public List<Aresta> arestas = new ArrayList<Aresta>();
    private boolean jaArestado=false;
    public int pai;
    public int getPai() {
        return pai;
    }

    public void setPai(int pai) {
        this.pai = pai;
    }

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

    public void setArestas(Grafo g,Vertice vizinho, int peso) {
        arestas.add(new Aresta(g,this,vizinho, peso));
            vizinho.arestas.add(new Aresta(g,vizinho,this, peso));
    }

    public int getarestas(Vertice vizinho) {
        for (int i = 0; i < arestas.size(); i++) {
            if (vizinho.getDescricao().equals(arestas.get(i).getV().getDescricao())) {
                return arestas.get(i).getPeso();
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

    public List<Aresta> getarestas() {
        return arestas;
    }

    public void setarestas(List<Aresta> arestas) {
        this.arestas = arestas;
    }
}
