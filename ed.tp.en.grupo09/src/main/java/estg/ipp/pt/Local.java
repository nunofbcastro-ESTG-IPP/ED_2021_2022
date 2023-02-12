package estg.ipp.pt;

import estg.ipp.pt.Enums.TipoLocal;
import estg.ipp.pt.Interfaces.ILocal;

public abstract class Local implements ILocal {
    private String nome;
    private TipoLocal tipo;

    public Local(String nome, TipoLocal tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public TipoLocal getTipo() {
        return tipo;
    }

    @Override
    public void setTipo(TipoLocal tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\nNome: ").append(nome);
        sb.append("\nTipo: ").append(tipo);
        return sb.toString();
    }
}
