package estg.ipp.pt.Interfaces;

import estg.ipp.pt.DataStructures.Exceptions.EmptyCollectionException;
import estg.ipp.pt.DataStructures.Interfaces.QueueADT;
import estg.ipp.pt.DataStructures.Interfaces.UnorderedListADT;

import java.util.Iterator;

public interface IVendedor {
    /**
     * @return O id
     */
    public int getId();

    /**
     * @return O nome do vendedor
     */
    public String getName();

    /**
     * Define o nome do vendedor
     *
     * @param name Nome do vendedor
     */
    public void setName(String name);

    /**
     * @return a capacidade máxima que o vendedor consegue transportar
     */
    public int getCapacidade();

    /**
     * Define a capacidade máxima que o vendedor consegue transportar
     *
     * @param capacidade Capacidade máxima que o vendedor consegue transportar
     */
    public void setCapacidade(int capacidade);

    /**
     * @return do stock do vendedor
     */
    public int getStock();

    /**
     * Incrementa mercadoria ao stock
     * @param stock quantidade de stock a ser adicionada
     */
    public void setStock(int stock);

    /**
     * @return Mercados que o vendedor tem que visitar
     */
    public Iterator<ILocal> getMercadosToVisit();

    /**
     * Permite adicionar mercados que o vendedor precisa de visitar
     *
     * @param mercado Mercado a ser adicionado
     * @return true se o mercado foi adicionado à fila, false caso contrário.
     */
    public boolean addMercadoToVisit(IMercado mercado);

    /**
     * Remove o mercado da lista de mercados a visitar
     * @param mercado Mercado a ser removido
     * @return true caso tenha sido removido com sucesso, false caso contrário
     */
    public boolean removeMercado(IMercado mercado);

    /**
     * Enquanto tem stock vai satisfazendo os clientes.
     * @param mercado Mercado onde vão ser satisfeitos o maior número de clientes possível
     * @return true caso todos os clientes do mercado tenham sido satisfeitos, false caso contrário
     */
    public boolean satisfazerClientes(IMercado mercado);

    /**
     * @return O próximo mercado a ser visitado pelo vendedor
     * @throws EmptyCollectionException Caso a lista de mercados a visitar esteja vazia.
     */
    public ILocal getCurrentMercadoToVisit() throws EmptyCollectionException;
}
