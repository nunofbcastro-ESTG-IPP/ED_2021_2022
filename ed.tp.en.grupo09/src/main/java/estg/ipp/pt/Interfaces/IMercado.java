package estg.ipp.pt.Interfaces;

import estg.ipp.pt.DataStructures.Interfaces.QueueADT;

import java.util.Iterator;

public interface IMercado extends ILocal{
    /**
     * Adicionar cliente ao mercado
     * @param quantidade Quantidade de mercadoria que o cliente deseja comprar.
     * @return true se o cliente foi adicionado, false caso contrário
     */
    public boolean addCliente(Integer quantidade);

    /**
     * @return a lista de clientes do mercado
     */
    public Iterator<Integer> getClientes();

    /**
     * Remover cliente da fila de clientes
     */
    public void removeCliente();
}
