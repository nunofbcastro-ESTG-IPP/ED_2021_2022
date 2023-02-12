package estg.ipp.pt.Interfaces;

import estg.ipp.pt.DataStructures.Graphs.Caminho;
import estg.ipp.pt.Enums.TipoLocal;

import java.util.Iterator;

public interface IGestaoEmpresas {
    /**
     * @return empresa que está a ser gerida
     */
    public IEmpresa getEmpresa();

    /**
     * Adiciona um vendedor à empresa
     * @param vendedor O vendedor a ser adicionado
     * @return true se o vendedor foi adicionado, false caso contrário
     */
    public boolean addVendedor(IVendedor vendedor);

    /**
     * @return a lista de vendedores da empresa
     */
    public Iterator<IVendedor> getVendedores();

    /***
     * Adiciona um mercado à rede
     * @param mercado O mercado a ser adicionado
     */
    public void addMercado(IMercado mercado);

    /**
     * @return a lista de mercados da empresa
     */
    public Iterator<ILocal> getMercados();

    /**
     * Adiciona um armazém à rede
     * @param armazem O armazém a ser adicionado
     */
    public void addArmazem(IArmazem armazem);

    /**
     * @return a lista de armazéns da empresa
     */
    public Iterator<ILocal> getArmazens();

    public ILocal findLocal(String nome, TipoLocal tipoLocal);

    /**
     * Verifica se existe um local com o nome e tipos enviados.
     * @param nome Nome do local
     * @return o local correspondente caso exista, null caso contrário
     */
    public ILocal findLocal(String nome);

    /**
     * Responsável por criar os caminhos entre dois locais
     * @param start Local inicial
     * @param end Local final
     * @param weigth Peso do caminho que, neste caso, se refere à distância entre os dois locais
     */
    public void addCaminho(ILocal start, ILocal end, double weigth);

    /**
     * Responsável por começar a rota de um vendedor.
     * @param vendedor Vendedor de qual vai ser iniciada a rota.
     */
    public void startRota(IVendedor vendedor);

    public void listarMercados();

    public void listarVendedores();

    public void listarArmazens();

    public Iterator<Caminho<ILocal>> getCaminhos();
}
