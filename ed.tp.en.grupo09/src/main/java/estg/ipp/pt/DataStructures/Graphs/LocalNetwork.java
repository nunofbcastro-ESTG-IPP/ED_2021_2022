package estg.ipp.pt.DataStructures.Graphs;

import estg.ipp.pt.DataStructures.ArrayList.ArrayUnorderedList;
import estg.ipp.pt.DataStructures.Interfaces.LocalNetworkADT;
import estg.ipp.pt.DataStructures.Interfaces.QueueADT;
import estg.ipp.pt.DataStructures.Interfaces.UnorderedListADT;
import estg.ipp.pt.DataStructures.Queue.LinkedQueue;
import estg.ipp.pt.Exceptions.IllegalArgumentException;

import java.util.Iterator;

public class LocalNetwork<T> extends Network<T> implements LocalNetworkADT<T> {

    @Override
    public Iterator<T> getAllVertices(){
        UnorderedListADT<T> verticesList = new ArrayUnorderedList<>();

        for(int i = 0; i < super.vertices.length; i++){
            if(this.vertices[i] != null) {
                verticesList.addToFront(super.vertices[i]);
            }
        }

        return verticesList.iterator();
    }

    @Override
    public boolean containsVertex(T vertex) {
        Iterator<T> it = getAllVertices();

        while (it.hasNext()){
            if(it.next().equals(vertex)){
                return true;
            }
        }

        return false;
    }

    @Override
    public void addVertex(T vertex) throws IllegalArgumentException {
        if(vertex == null){
            throw new IllegalArgumentException("O vértice não pode ser nulo");
        }

        if(!containsVertex(vertex)){
            if (super.size() == super.vertices.length) {
                this.expandCapacity();
            }

            super.vertices[super.size()] = vertex;

            for (int i = 0; i <= this.size(); i++) {
                this.adjMatrix[numVertices][i] = Double.POSITIVE_INFINITY;
                this.adjMatrix[i][numVertices] = Double.POSITIVE_INFINITY;
            }
            super.numVertices++;
        }
    }

    @Override
    public QueueADT<T> calculaRota (Iterator<T> locais, T startLocal) throws IllegalArgumentException {
        if(locais == null){
            throw new IllegalArgumentException("Os locais a visitar não podem ser nulos!");
        }

        Iterator<T> locaisIt, shortestPathForLocais, shortestPathBackToBase;
        //Queue que armazenará os vértices a serem visitados
        QueueADT<T> caminho = new LinkedQueue<>();
        T current, startLocalShortestPath = startLocal;

        locaisIt = locais;

        //Inserir o ponto de partida na Queue
        caminho.enqueue(startLocalShortestPath);

        /**
         * Para cada local a ser visitado pelo vendedor, é calculado o caminho mais curto desde o local onde o mesmo
         * se encontra de momento até ao local a ser visitado.
         */
        while(locaisIt.hasNext()){
            shortestPathForLocais = this.iteratorShortestPath(startLocalShortestPath, locaisIt.next());
            while (shortestPathForLocais.hasNext()){
                current = shortestPathForLocais.next();
                if(!current.equals(startLocalShortestPath)){
                    caminho.enqueue(current);
                }

                startLocalShortestPath = current;
            }
        }

        /**
         * Depois de calculada a rota para visitar todos os respetivos locais, é necessário voltar ao local de partida
         * e, por isso mesmo é então calculado o caminho mais curto desde o local onde o vendedor se encontra até ao
         * local de partida que, deverá ser a empresa.
         */
        shortestPathBackToBase = this.iteratorShortestPath(startLocal, startLocalShortestPath);
        while (shortestPathBackToBase.hasNext()){
            current = shortestPathBackToBase.next();

            if(!current.equals(startLocalShortestPath)){
                caminho.enqueue(current);
            }
        }

        return caminho;
    }

    @Override
    public Iterator<Caminho<T>> getCaminhos(){
        UnorderedListADT<Caminho<T>> caminhos = new ArrayUnorderedList();

        for(int i = 0; i < super.size(); i++){
            for(int j = i; j < super.size(); j++){
                try{
                    caminhos.addToRear(new Caminho<T>(this.vertices[i],this.vertices[j],this.adjMatrix[i][j]));
                }catch (Exception ignored){
                }
            }
        }

        return caminhos.iterator();
    }
}
