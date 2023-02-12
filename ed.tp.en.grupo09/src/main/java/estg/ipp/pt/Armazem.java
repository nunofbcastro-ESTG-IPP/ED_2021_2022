package estg.ipp.pt;

import estg.ipp.pt.Enums.TipoLocal;
import estg.ipp.pt.Interfaces.IArmazem;

public class Armazem extends Local implements IArmazem {
    private int capacidade;
    private int stock;

    public Armazem(String nome, int capacidade, int stock) {
        super(nome, TipoLocal.ARMAZEM);
        this.capacidade = capacidade;
        this.stock = stock;
    }

    @Override
    public int getCapacidade() {
        return capacidade;
    }

    @Override
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    @Override
    public int getStock() {
        return stock;
    }

    @Override
    public void setStock(int stock) {
        if(stock > 0 && (this.stock + stock <= this.capacidade)){
            this.stock += stock;
        }
    }

    public void decremetStock(int stock){
        if(stock > 0 && (this.stock - stock > 0))
        this.stock -= stock;
    }
}
