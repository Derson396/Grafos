package src;
import java.util.*;

public class Grafo {

    private List<Vertice> grafo = new ArrayList<Vertice>();
    public List<Aresta> arestas=new ArrayList<>();
    public List<Aresta> getArestas() {
        return arestas;
    }

    public void setArestas(List<Aresta> arestas) {
        this.arestas = arestas;
    }

    public void setVertices(List<Vertice> vertices) {
        this.grafo.addAll(vertices);
    }

    public void adicionarVertice(Vertice novoVertice) {
        if (encontrarVertice(novoVertice.getDescricao()) ==null ) {
            this.grafo.add(novoVertice);
            
        }
    }

    public List<Vertice> getVertices() {

        return this.grafo;
    }

    // Método que retorna o vértice cuja descrição é igual à procurada.
    public Vertice encontrarVertice(String nome) {

        for (int i = 0; i < this.getVertices().size(); i++) {

            if (nome.equalsIgnoreCase(this.getVertices().get(i).getDescricao())) {

                return this.getVertices().get(i);

            }
        }

        return null;

    }

    public void removerVertice(Vertice aux) {
        Iterator<Aresta> iterator = arestas.iterator();
        while (iterator.hasNext()) {
            Aresta aresta = iterator.next();
            if (aresta.getV().equals(aux) || aresta.getw().equals(aux)) {
                iterator.remove();
            }
        }
    
        getVertices().remove(aux);
    }

}