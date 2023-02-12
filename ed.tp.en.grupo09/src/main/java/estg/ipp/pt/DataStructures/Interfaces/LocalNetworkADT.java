package estg.ipp.pt.DataStructures.Interfaces;

import estg.ipp.pt.DataStructures.Exceptions.ElementNotFoundException;
import estg.ipp.pt.DataStructures.Graphs.Caminho;
import estg.ipp.pt.Exceptions.IllegalArgumentException;

import java.util.Iterator;

public interface LocalNetworkADT<T> extends NetworkADT<T>{
    /**
     * @return o iterator para percorrer todos os vértices
     */
    public Iterator<T> getAllVertices();

    /**
     * Verifica se o vértice já foi adicionado
     * @param vertex Vértice a ser verificado
     * @return true se já existe, false caso contrário
     * @throws IllegalArgumentException Caso o vértice enviado seja null
     * @throws IllegalArgumentException se o vertex for null
     */
    public boolean containsVertex(T vertex) throws IllegalArgumentException;

    /**
     * Calcula a rota mais curta consoante os locais a serem visitados.
     * @param locais Locais a serem visitados
     * @param startLocal Local onde se dá ínicio à rota
     * @return Rota a realizar para o caminho mais curto
     * @throws IllegalArgumentException se os locias ou o startLocal for null
     */
    public QueueADT<T> calculaRota (Iterator<T> locais, T startLocal) throws IllegalArgumentException;

    /**
     * @return o iterator com todas os caminhos do grafo
     */
    public Iterator<Caminho<T>> getCaminhos();
}
