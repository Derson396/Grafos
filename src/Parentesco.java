package src;

public class Parentesco {
    private int peso;
    private String vizinho;

    public Parentesco(String string, int peso2) {
        setVizinho(string);
        setPeso(peso2);
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getVizinho() {
        return vizinho;
    }

    public void setVizinho(String vizinho) {
        this.vizinho = vizinho;
    }
}
