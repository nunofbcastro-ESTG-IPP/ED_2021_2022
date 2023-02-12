package estg.ipp.pt;

import estg.ipp.pt.DataStructures.DoubleLinkedList.DoubleLinkedUnorderedList;
import estg.ipp.pt.DataStructures.Exceptions.ElementNotFoundException;
import estg.ipp.pt.DataStructures.Exceptions.EmptyCollectionException;
import estg.ipp.pt.DataStructures.Interfaces.QueueADT;
import estg.ipp.pt.DataStructures.Interfaces.UnorderedListADT;
import estg.ipp.pt.Interfaces.ILocal;
import estg.ipp.pt.Interfaces.IMercado;
import estg.ipp.pt.Interfaces.IVendedor;

import java.util.Iterator;

public class Vendedor implements IVendedor {
    private final int id;
    private String name;
    private int capacidade;
    private int stock;
    private final UnorderedListADT<ILocal> mercadosToVisit;
    private static int actualIdVendedor;

    static {
        actualIdVendedor = 1;
    }

    public Vendedor(String name, int capacidade) {
        this.id = actualIdVendedor;
        this.name = name;
        this.capacidade = capacidade;
        this.stock = 0;
        this.mercadosToVisit = new DoubleLinkedUnorderedList<>();
        actualIdVendedor += 1;
    }

    public Vendedor(String name, int capacidade, int stock) {
        this.id = actualIdVendedor;
        this.name = name;
        this.capacidade = capacidade;
        this.stock = stock;
        this.mercadosToVisit = new DoubleLinkedUnorderedList<>();
        actualIdVendedor += 1;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getCapacidade() {
        return this.capacidade;
    }

    @Override
    public void setCapacidade(int capacidade) {
        if(capacidade > 0){
            this.capacidade = capacidade;
        }
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

    @Override
    public boolean addMercadoToVisit(IMercado mercado) {
        if (mercado!=null && !mercadosToVisit.contains(mercado)) {
            this.mercadosToVisit.addToRear(mercado);
            return true;
        }

        return false;
    }

    @Override
    public boolean removeMercado(IMercado mercado) {
        if(mercado != null && mercadosToVisit.contains(mercado)){
            try {
                mercadosToVisit.remove(mercado);
                return true;
            } catch (EmptyCollectionException e) {
                e.printStackTrace();
            } catch (ElementNotFoundException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean satisfazerClientes(IMercado mercado) {
        int currentClient;

        Iterator<Integer> clientesIt = mercado.getClientes();

        while (clientesIt.hasNext()){
            currentClient = clientesIt.next();
            if(this.stock >= currentClient && currentClient <= getCapacidade()){
                this.stock -= currentClient;
                System.out.println("\t" + this.name + " satisfez o cliente com uma quantidade de mercadoria de " + currentClient);
                mercado.removeCliente();
            } else{
                return false;
            }
        }

        return true;
    }

    @Override
    public Iterator<ILocal> getMercadosToVisit() {
        return mercadosToVisit.iterator();
    }

    @Override
    public ILocal getCurrentMercadoToVisit() throws EmptyCollectionException {
        return mercadosToVisit.first();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        Iterator<ILocal> mercados = getMercadosToVisit();
        sb.append("\nid: ").append(id);
        sb.append("\nName: ").append(name);
        sb.append("\nCapacidade: ").append(capacidade);
        sb.append("\nStock: ").append(stock);
        sb.append("\nMercados ").append(mercadosToVisit);
        while (mercados.hasNext()){
            sb.append(mercados.next());
        }
        return sb.toString();
    }
}
