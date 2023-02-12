package estg.ipp.pt;

import estg.ipp.pt.DataStructures.ArrayList.ArrayUnorderedList;
import estg.ipp.pt.DataStructures.DoubleLinkedList.DoubleLinkedUnorderedList;
import estg.ipp.pt.DataStructures.Exceptions.EmptyCollectionException;
import estg.ipp.pt.DataStructures.Interfaces.QueueADT;
import estg.ipp.pt.DataStructures.Interfaces.UnorderedListADT;
import estg.ipp.pt.DataStructures.Queue.LinkedQueue;
import estg.ipp.pt.Enums.TipoLocal;
import estg.ipp.pt.Interfaces.ILocal;
import estg.ipp.pt.Interfaces.IMercado;

import java.util.Iterator;

public class Mercado extends Local implements IMercado {
    private final QueueADT<Integer> clientes;

    public Mercado(String nome) {
        super(nome, TipoLocal.MERCADO);
        this.clientes = new LinkedQueue<>();
    }

    @Override
    public boolean addCliente(Integer quantidade){
        if(quantidade!=null && quantidade > 0){
            this.clientes.enqueue(quantidade);
            return true;
        }

        return false;
    }


    @Override
    public Iterator<Integer> getClientes() {
        UnorderedListADT<Integer> clientesList = new ArrayUnorderedList<>();
        while(!clientes.isEmpty()){
            try {
                clientesList.addToRear(clientes.dequeue());
            }catch (EmptyCollectionException ignored) {}
        }
        for (Integer cliente: clientesList) {
            clientes.enqueue(cliente);
        }
        return clientesList.iterator();
    }

    @Override
    public void removeCliente() {
        try {
            this.clientes.dequeue();
        } catch (EmptyCollectionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        Iterator<Integer> clientes = getClientes();
        sb.append(super.toString());
        sb.append("\nClientes: ");
        while(clientes.hasNext()){
            sb.append(clientes.next() + ",");
        }
        return sb.toString();
    }
}
