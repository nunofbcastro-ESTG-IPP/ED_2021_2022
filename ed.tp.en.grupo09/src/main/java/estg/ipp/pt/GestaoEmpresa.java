package estg.ipp.pt;

import estg.ipp.pt.DataStructures.ArrayList.ArrayUnorderedList;
import estg.ipp.pt.DataStructures.DoubleLinkedList.DoubleLinkedUnorderedList;
import estg.ipp.pt.DataStructures.Exceptions.EmptyCollectionException;
import estg.ipp.pt.DataStructures.Graphs.Caminho;
import estg.ipp.pt.DataStructures.Graphs.LocalNetwork;
import estg.ipp.pt.DataStructures.Interfaces.QueueADT;
import estg.ipp.pt.DataStructures.Interfaces.StackADT;
import estg.ipp.pt.DataStructures.Interfaces.UnorderedListADT;
import estg.ipp.pt.DataStructures.Queue.LinkedQueue;
import estg.ipp.pt.DataStructures.Stack.LinkedStack;
import estg.ipp.pt.Enums.TipoLocal;
import estg.ipp.pt.Exceptions.IllegalArgumentException;
import estg.ipp.pt.Interfaces.*;

import java.util.Iterator;

public class GestaoEmpresa implements IGestaoEmpresas {
    private final IEmpresa empresa;
    private final UnorderedListADT<IVendedor> vendedores;
    private final LocalNetwork<ILocal> localNetwork;

    public GestaoEmpresa(IEmpresa empresa) throws IllegalArgumentException {
        this.localNetwork = new LocalNetwork<>();
        this.empresa = empresa;
        this.localNetwork.addVertex(empresa);
        this.vendedores = new DoubleLinkedUnorderedList<>();
    }

    @Override
    public IEmpresa getEmpresa() {
        return empresa;
    }

    @Override
    public boolean addVendedor(IVendedor vendedor) {
        if (vendedor != null && !this.vendedores.contains(vendedor)) {
            this.vendedores.addToRear(vendedor);
            return true;
        }

        return false;
    }

    @Override
    public Iterator<IVendedor> getVendedores() {
        return this.vendedores.iterator();
    }

    @Override
    public void addMercado(IMercado mercado) {
        if (mercado != null) {
            try {
                this.localNetwork.addVertex(mercado);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Iterator<ILocal> getMercados() {
        return this.getTypeLocal(TipoLocal.MERCADO);
    }

    @Override
    public void addArmazem(IArmazem armazem) {
        if (armazem != null) {
            try {
                this.localNetwork.addVertex(armazem);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Iterator<ILocal> getArmazens() {
        return this.getTypeLocal(TipoLocal.ARMAZEM);
    }

    /**
     * Retorna um iterator para percorrer apenas os locais que são do tipo enviado como parâmetro
     *
     * @param tipoLocal Tipo do local
     * @return Iterator de locais para percorrer os locais do tipo enviado
     */
    private Iterator<ILocal> getTypeLocal(TipoLocal tipoLocal) {
        UnorderedListADT<ILocal> verticesList = new ArrayUnorderedList<>();
        Iterator<ILocal> it = this.localNetwork.getAllVertices();

        ILocal current;
        while (it.hasNext()) {
            current = it.next();
            if (current.getTipo().equals(tipoLocal)) {
                verticesList.addToFront(current);
            }
        }

        return verticesList.iterator();
    }

    @Override
    public ILocal findLocal(String nome, TipoLocal tipoLocal){
        Iterator<ILocal> it = getTypeLocal(tipoLocal);

        ILocal current;
        while(it.hasNext()){
            current = it.next();

            if(current.getNome().equals(nome)){
                return current;
            }
        }

        return null;
    }

    @Override
    public ILocal findLocal(String nome){
        Iterator<ILocal> it = localNetwork.getAllVertices();

        ILocal current;
        while(it.hasNext()){
            current = it.next();

            if(current.getNome().equals(nome)){
                return current;
            }
        }

        return null;
    }

    @Override
    public void addCaminho(ILocal start, ILocal end, double weigth) {
        localNetwork.addEdge(start, end, weigth);
    }

    /**
     * Solicita o cálculo da rota para cada vendedor.
     * Este método, é apenas um método para testes para que, possamos verificar se a rota está a ser bem calculada.
     * Poderá ser apagado posteriormente quando for colocado em prática a restante codificação.
     */
    private QueueADT<ILocal> getRota(IVendedor vendedor) {
        QueueADT<ILocal> rota = null;

        try {
            rota = this.localNetwork.calculaRota(vendedor.getMercadosToVisit(), this.empresa);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return rota;
    }

    private int verifyMercadoriaEmFalta(IMercado mercado, IVendedor vendedor){
        Iterator<Integer> clientesIt = mercado.getClientes();
        int currentClient;

        while (clientesIt.hasNext()){
            currentClient = clientesIt.next();
            if(vendedor.getStock() >= currentClient){
                mercado.removeCliente();
            } else{
                return currentClient - vendedor.getStock();
            }
        }

        return 1;
    }

    public void startRota(IVendedor vendedor) {
        QueueADT<ILocal> rotaVendedor = null, caminhoArmazem;
        StringBuilder sb = new StringBuilder();
        ILocal currentLocal = null;

        rotaVendedor = this.getRota(vendedor);
        int size = rotaVendedor.size();

        for (int i = 0; i < size; i++) {
            try {
                currentLocal = rotaVendedor.dequeue();
                System.out.println("\nO " + vendedor.getName() + " esta neste momento no(a) " + currentLocal.getNome());

            } catch (EmptyCollectionException e) {
                e.printStackTrace();
            }

            try {
                if (currentLocal.getTipo().equals(TipoLocal.MERCADO) && currentLocal.equals(vendedor.getCurrentMercadoToVisit())) {
                    while (!vendedor.satisfazerClientes((IMercado) currentLocal)) {
                        System.out.println("\tO " + vendedor.getName() + " nao possui mercadoria suficiente! A verificar stock em armazens...");
                        caminhoArmazem = getArmazemMaisProximo(currentLocal, vendedor,
                                verifyMercadoriaEmFalta((IMercado) currentLocal, vendedor));
                        if (caminhoArmazem.size() == 0) {
                            System.out.println("\tNeste momento nao existe mercadoria capaz de satisfazer o cliente do(a) " + currentLocal.getNome());
                            break;
                        } else {
                            System.out.println("\tFoi encontrado um armazem capaz de satisfazer o cliente! Vendedor a deslocar-se para o mesmo...");

                            size = caminhoArmazem.size();
                            for (i = 0; i < size; i++) {
                                try {
                                    System.out.println("\t\tO " + vendedor.getName() + " esta neste momento no(a) " + caminhoArmazem.dequeue().getNome());
                                } catch (EmptyCollectionException e) {
                                    e.printStackTrace();
                                }
                            }
                            vendedor.setStock(vendedor.getCapacidade());
                        }
                    }
                    vendedor.removeMercado((IMercado) currentLocal);
                }
            } catch (EmptyCollectionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fim da rota");
    }

    @Override
    public void listarMercados() {
        Iterator<ILocal> mercados = this.getMercados();

        while (mercados.hasNext()){
            System.out.println(mercados.next().toString());
        }
    }

    private QueueADT<ILocal> getArmazemMaisProximo(ILocal startLocal, IVendedor vendedor, int quantidadeEmFalta) {
        Iterator<ILocal> armazensIt = this.getArmazens();
        Iterator<ILocal> caminhoMaisCurtoIt;
        ILocal currentArmazem, armazemMaisProximo = null;
        QueueADT<ILocal> caminhoMaisCurto = new LinkedQueue<>();
        StackADT<ILocal> caminhoDeVolta = new LinkedStack<>();
        double menorCaminho = Double.POSITIVE_INFINITY;

        while (armazensIt.hasNext()) {
            currentArmazem = armazensIt.next();

            double currentArmazemCaminho = this.localNetwork.shortestPathWeight(startLocal, currentArmazem);

            if (currentArmazemCaminho < menorCaminho && ((IArmazem) currentArmazem).getStock() >= quantidadeEmFalta) {
                menorCaminho = currentArmazemCaminho;
                armazemMaisProximo = currentArmazem;
            }
        }

        if(armazemMaisProximo != null){
            int quantidadeToAbastecer = vendedor.getCapacidade() - vendedor.getStock();
            int diff = ((IArmazem)armazemMaisProximo).getStock() - quantidadeToAbastecer;
            if (diff >= 0) {
                vendedor.setStock(quantidadeToAbastecer);
                ((IArmazem) armazemMaisProximo).decremetStock(quantidadeToAbastecer);
            }
            else {
                vendedor.setStock(quantidadeEmFalta);
                ((IArmazem) armazemMaisProximo).decremetStock(quantidadeEmFalta);
            }
        }

        caminhoMaisCurtoIt = this.localNetwork.iteratorShortestPath(startLocal, armazemMaisProximo);
        ILocal currentLocal;
        while (caminhoMaisCurtoIt.hasNext()) {
            currentLocal = caminhoMaisCurtoIt.next();
            caminhoMaisCurto.enqueue(currentLocal);
            caminhoDeVolta.push(currentLocal);
        }

        try {
            caminhoMaisCurto.dequeue();
        } catch (EmptyCollectionException e) {
            e.printStackTrace();
        }

        try {
            caminhoDeVolta.pop();
        } catch (EmptyCollectionException e) {
            e.printStackTrace();
        }

        int size = caminhoDeVolta.size();

        for (int i = 0; i < size; i++){
            try {
                caminhoMaisCurto.enqueue(caminhoDeVolta.pop());
            } catch (EmptyCollectionException e) {
                e.printStackTrace();
            }
        }

        return caminhoMaisCurto;
    }

    @Override
    public void listarVendedores() {
        Iterator<IVendedor> vendedores = this.getVendedores();

        while (vendedores.hasNext()){
            System.out.println(vendedores.next().toString());
        }
    }

    @Override
    public void listarArmazens() {
        Iterator<ILocal> armazens = this.getArmazens();

        while (armazens.hasNext()){
            System.out.println(armazens.next().toString());
        }
    }

    @Override
    public Iterator<Caminho<ILocal>> getCaminhos() {
        return localNetwork.getCaminhos();
    }
}
