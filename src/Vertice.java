package src;

import java.util.*;

// Definição da classe Vertice, que representa um vértice em um grafo
public class Vertice implements Comparable<Vertice>,Cloneable {
    
    // Atributos da classe
    private String descricao;         // Descrição/nome do vértice
    private int distancia;
    private boolean visitado = false; // Indica se o vértice foi visitado em alguma operação
    public List<Aresta> arestas = new ArrayList<Aresta>(); // Lista de arestas conectadas ao vértice
    public List<Vertice> vizinhos=new ArrayList<Vertice>();
    // Método setter para a descrição do vértice
    public void setDescricao(String nome) {
        this.descricao = nome;
    }

    // Método getter para a descrição do vértice
    public String getDescricao() {
        return descricao;
    }

    // Método getter para a distância do vértice
    public int getDistancia() {
        return distancia;
    }

    // Método setter para a distância do vértice
    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    // Método para marcar o vértice como visitado
    public void visitar() {
        this.visitado = true;
    }

    // Método para verificar se o vértice foi visitado
    public boolean verificarVisita() {
        return visitado;
    }

    // Método para adicionar uma aresta ao vértice, conectando-o a outro vértice
    public void setArestas(Grafo g, Vertice vizinho, int peso) {
        arestas.add(new Aresta(g, this, vizinho, peso));
        vizinho.arestas.add(new Aresta(g, vizinho, this, peso));
    }

    // Método para obter o peso da aresta entre este vértice e um vértice vizinho
    public int getarestas(Vertice vizinho) {
        
    for (Aresta aresta : arestas) {
        if (vizinho.equals(aresta.getw())) {
            return aresta.getPeso();
        }
    }
    return -1;
}


    // Implementação do método de comparação para a interface Comparable
    public int compareTo(Vertice vertice) {
        if (this.getDistancia() < vertice.getDistancia())
            return -1;
        else if (this.getDistancia() == vertice.getDistancia())
            return 0;

        return 1;
    }

    // Implementação do método equals para comparar vértices
    public boolean equals(Object obj) {
        if (obj instanceof Vertice) {
            Vertice vRef = (Vertice) obj;
            if (this.getDescricao().equals(vRef.getDescricao()))
                return true;
        }
        return false;
    }

    // Método getter para o grau do vértice
    public int getGrau() {
        return vizinhos.size();
    }

    // Método getter para verificar se o vértice foi visitado
    public boolean isVisitado() {
        return visitado;
    }

    // Método setter para marcar o vértice como visitado
    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    // Método getter para obter a lista de arestas conectadas ao vértice
    public List<Aresta> getarestas() {
        return arestas;
    }

    // Método setter para definir a lista de arestas conectadas ao vértice
    public void setarestas(List<Aresta> arestas) {
        this.arestas = arestas;
    }
}
