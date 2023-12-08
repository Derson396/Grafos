package src;

import java.util.*;

// Definição da classe Grafo
public class Grafo implements Cloneable {

    // Lista de vértices que compõem o grafo
    private List<Vertice> grafo = new ArrayList<Vertice>();

    // Lista de arestas presentes no grafo
    public List<Aresta> arestas = new ArrayList<>();

    public Grafo clone() throws CloneNotSupportedException {
        return (Grafo) super.clone();
    }

    // Método getter para obter a lista de arestas do grafo
    public List<Aresta> getArestas() {
        return arestas;
    }

    // Método setter para definir a lista de arestas do grafo
    public void setArestas(List<Aresta> arestas) {
        this.arestas = arestas;
    }

    // Método setter para adicionar uma lista de vértices ao grafo
    public void setVertices(List<Vertice> vertices) {
        this.grafo.addAll(vertices);
    }

    // Método para adicionar um novo vértice ao grafo, caso não exista um vértice
    // com a mesma descrição
    public void adicionarVertice(Vertice novoVertice) {
        if (encontrarVertice(novoVertice.getDescricao()) == null) {
            this.grafo.add(novoVertice);
        }
    }

    // Método getter para obter a lista de vértices do grafo
    public List<Vertice> getVertices() {
        return this.grafo;
    }

    // Método que retorna o vértice cuja descrição é igual à procurada.
    public Vertice encontrarVertice(String nome) {
        List<Vertice> vertices=this.getVertices();
        for (Vertice vertice : vertices) {
            if (vertice.getDescricao().equals(nome)) {
                return vertice;
            }
        }
        return null;
    }

    // Método para remover um vértice do grafo, removendo também todas as arestas
    // conectadas a ele
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

    // Método para gerar uma matriz de adjacência do grafo
    public int[][] gerarMatrizAdjacencia() {
        int numVertices = getVertices().size();
        int[][] matrizAdjacencia = new int[numVertices][numVertices];

        // Inicializa a matriz com valores de "infinito" (representado por
        // Integer.MAX_VALUE)
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                matrizAdjacencia[i][j] = Integer.MAX_VALUE;
            }
        }

        // Preenche a matriz com os pesos das arestas
        for (Aresta aresta : getArestas()) {
            int indiceV = Integer.parseInt(aresta.getV().getDescricao().substring(1));
            int indiceW = Integer.parseInt(aresta.getw().getDescricao().substring(1));
            matrizAdjacencia[indiceV][indiceW] = aresta.getPeso();
            matrizAdjacencia[indiceW][indiceV] = aresta.getPeso();
        }

        return matrizAdjacencia;
    }

}
