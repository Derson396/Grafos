package src;

public class Aresta {
    private int peso;
    private Vertice v;//origem
    private Vertice w;//destino
    public Vertice getw() {
        return w;
    }

    public void setw(Vertice w) {
        this.w = w;
    }

    public Aresta(Grafo g,Vertice v,Vertice w, int peso2) {
        setV(v);
        setPeso(peso2);
        setw(w);
        g.arestas.add(this);
    }
 public Aresta(Vertice v,Vertice w, int peso2) {
        setV(v);
        setPeso(peso2);
        setw(w);
    }
    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Vertice getV() {
        return v;
    }

    public void setV(Vertice vizinho) {
        this.v = vizinho;
    }
}
