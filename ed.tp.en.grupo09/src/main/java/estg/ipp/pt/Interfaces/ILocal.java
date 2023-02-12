package estg.ipp.pt.Interfaces;

import estg.ipp.pt.Enums.TipoLocal;

public interface ILocal {
    /**
     * @return o nome do local
     */
    public String getNome();

    /***
     * Define o nome do local
     * @param nome o nome do local
     */
    public void setNome(String nome);

    /**
     * @return o tipo do local
     */
    public TipoLocal getTipo();

    /**
     * Define o tipo do local
     * @param tipo Tipo do local
     */
    public void setTipo(TipoLocal tipo);
}
