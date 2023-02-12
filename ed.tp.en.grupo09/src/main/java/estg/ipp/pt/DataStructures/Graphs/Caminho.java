package estg.ipp.pt.DataStructures.Graphs;

public class Caminho<T> {
    private T de;
    private T para;
    private Double distancia;

    public Caminho(T de, T para, Double distancia) {
        if(de == null || para==null || distancia==Double.POSITIVE_INFINITY){
            throw new IllegalArgumentException("Dados invalidos");
        }
        this.de = de;
        this.para = para;
        this.distancia = distancia;
    }

    public T getDe() {
        return de;
    }

    public T getPara() {
        return para;
    }

    public double getDistancia() {
        return distancia;
    }
}
