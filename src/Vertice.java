package src;

import java.util.*;

public class Vertice implements Comparable<Vertice> {
    private String descricao;
    private int distancia,grau=0;
    private boolean visitado = false;
    private Vertice pai;
    public Map<Vertice, Integer> vizinhos = new HashMap<Vertice, Integer>();
    
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

    public void setPai(Vertice pai) {

        this.pai = pai;
    }

    public Vertice getPai() {

        return this.pai;
    }
    public void setVizinhos(Vertice vizinho,int peso){
        vizinhos.put(vizinho, peso);
        grau++;
    }
    public Integer getVizinhos(Vertice vizinho){
        return vizinhos.get(vizinho);
    }
/**
    public void setVizinhos(Vertice vizinho) {

        this.vizinhos.add(vizinho);
        boolean flag = true;
        for (int i = 0; i < vizinho.getVizinhos().size(); i++) {
            if (vizinho.getVizinhos().get(i) == this) {
                flag = false;
            }
        }
        if (flag) {
            vizinho.setVizinhos(this);
        }
    }

    public List<Vertice> getVizinhos() {

        return this.vizinhos;
    }

    public void setArestas(Aresta aresta) {

        this.arestas.add(aresta);
        addAresta(aresta);

    }

    public void addAresta(Aresta aresta) {
        Aresta w = new Aresta(aresta.getDestino(), aresta.getOrigem());
        w.setPeso(aresta.getPeso());
        aresta.getDestino().arestas.add(w);
    }

    public List<Aresta> getArestas() {

        return arestas;
    }
*/
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

    public Map<Vertice, Integer> getVizinhos() {
        return vizinhos;
    }

    public void setVizinhos(Map<Vertice, Integer> vizinhos) {
        this.vizinhos = vizinhos;
    }
}
