package estg.ipp.pt.Interfaces;

public interface IArmazem extends ILocal{

    /**
     * @return capacidade do armazem
     */
    public int getCapacidade();

    /**
     * Permite definir a capacidade máxima do armazém
     * @param capacidade valor referente à capacidade máxima do armazém
     */
    public void setCapacidade(int capacidade);

    /**
     * @return stock atual do armazém
     */
    public int getStock();

    /**
     * Permite definir o stock atual do armazém
     * @param stock valor referente ao stock atual do armazém
     */
    public void setStock(int stock);

    /**
     * Decrementa no stock a quantidade enviada
     * @param stock quantidade de stock a ser decrementada
     */
    public void decremetStock(int stock);
}
