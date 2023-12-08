package src;

// Definição da classe Aresta
public class Aresta {
    
    // Atributos da classe
    private int peso;        // Peso da aresta
    private Vertice v;       // Vértice de origem
    private Vertice w;       // Vértice de destino
    
    // Método getter para o vértice de destino (w)
    public Vertice getw() {
        return w;
    }

    // Método setter para o vértice de destino (w)
    public void setw(Vertice w) {
        this.w = w;
    }

    // Construtor da classe Aresta com um grafo, vértices de origem e destino, e peso
    public Aresta(Grafo g, Vertice v, Vertice w, int peso2) {
        setV(v);
        setPeso(peso2);
        setw(w);
        g.arestas.add(this);  // Adiciona a aresta à lista de arestas do grafo
    }

    // Construtor da classe Aresta com vértices de origem e destino, e peso
    public Aresta(Vertice v, Vertice w, int peso2) {
        setV(v);
        setPeso(peso2);
        setw(w);
    }

    // Método getter para o peso da aresta
    public int getPeso() {
        return peso;
    }

    // Método setter para o peso da aresta
    public void setPeso(int peso) {
        this.peso = peso;
    }

    // Método getter para o vértice de origem (v)
    public Vertice getV() {
        return v;
    }

    // Método setter para o vértice de origem (v)
    public void setV(Vertice vizinho) {
        this.v = vizinho;
    }
}
