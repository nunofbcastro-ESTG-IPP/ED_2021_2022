package estg.ipp.pt.DataStructures.Graphs;

import estg.ipp.pt.DataStructures.ArrayList.ArrayUnorderedList;
import estg.ipp.pt.DataStructures.Interfaces.GraphADT;
import estg.ipp.pt.DataStructures.Exceptions.EmptyCollectionException;
import estg.ipp.pt.DataStructures.Stack.LinkedStack;
import estg.ipp.pt.DataStructures.Queue.LinkedQueue;
import estg.ipp.pt.Exceptions.IllegalArgumentException;

import java.util.Iterator;

/**
 * Graph represents an adjacency matrix implementation of a graph.
 */
public class Graph<T> implements GraphADT<T> {

    protected final int DEFAULT_CAPACITY = 10;
    protected int numVertices; // number of vertices in the graph
    protected boolean[][] adjMatrix; // adjacency matrix
    protected T[] vertices; // values of vertices

    /**
     * Creates an empty graph.
     */
    public Graph() {
        this.numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    protected void expandCapacity() {
        T[] newVertices = (T[]) new Object[this.vertices.length * 2];
        System.arraycopy(this.vertices, 0, newVertices, 0, this.vertices.length);
        boolean[][] newAdjMatrix = new boolean[this.adjMatrix.length * 2][this.adjMatrix.length * 2];

        for (int j = 0; j < this.adjMatrix.length; j++) {
            System.arraycopy(this.adjMatrix[j], 0, newAdjMatrix[j], 0, this.adjMatrix.length);
        }

        this.vertices = newVertices;
        this.adjMatrix = newAdjMatrix;
    }

    protected boolean indexIsValid(int index) {
        return index >= 0 && index <= this.size();
    }

    protected int getIndex(T vertex) {
        for (int i = 0; i < this.size(); i++) {
            if (this.vertices[i].equals(vertex)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    public void addEdge(T vertex1, T vertex2) {
        this.addEdge(this.getIndex(vertex1), this.getIndex(vertex2));
    }

    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param index1 the first index
     * @param index2 the second index
     */
    private void addEdge(int index1, int index2) {
        if (this.indexIsValid(index1) && this.indexIsValid(index2)) {
            this.adjMatrix[index1][index2] = true;
            this.adjMatrix[index2][index1] = true;
        }
    }

    /**
     * Adds a vertex to the graph, expanding the capacity of the graph if
     * necessary. It also associates an object with the vertex.
     *
     * @param vertex the vertex to add to the graph
     */
    public void addVertex(T vertex) throws IllegalArgumentException {
        if (this.size() == this.vertices.length) {
            expandCapacity();
        }
        this.vertices[this.size()] = vertex;
        for (int i = 0; i <= this.size(); i++) {
            this.adjMatrix[this.size()][i] = false;
            this.adjMatrix[i][this.size()] = false;
        }
        this.numVertices++;
    }

    /**
     * Removes an edge between two vertices of this graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    @Override
    public void removeEdge(T vertex1, T vertex2) {
        this.removeEdge(this.getIndex(vertex1), this.getIndex(vertex2));
    }

    /**
     * Removes an edge between two vertices of this graph.
     *
     * @param index1 the first index
     * @param index2 the second index
     */
    private void removeEdge(int index1, int index2) {
        if (this.indexIsValid(index1) && this.indexIsValid(index2)) {
            this.adjMatrix[index1][index2] = false;
            this.adjMatrix[index2][index1] = false;
        }
    }

    /**
     * Removes a single vertex with the given value from this graph.
     *
     * @param vertex the vertex to be removed from this graph
     */
    @Override
    public void removeVertex(T vertex) {
        this.removeVertex(this.getIndex(vertex));
    }

    /**
     * Removes a single vertex with the given value from this graph.
     *
     * @param index the index
     */
    private void removeVertex(int index) {
        if (this.indexIsValid(index)) {
            this.numVertices--;

            for (int i = index; i < this.size(); i++){
                this.vertices[i] = this.vertices[i+1];
            }

            for (int i = index; i < this.size(); i++){
                for (int j = 0; j <= this.size(); j++){
                    this.adjMatrix[i][j] = this.adjMatrix[i+1][j];
                }
            }

            for (int i = index; i < this.size(); i++){
                for (int j = 0; j < this.size(); j++){
                    this.adjMatrix[j][i] = this.adjMatrix[j][i+1];
                }
            }
        }
    }

    /**
     * Returns a breadth first iterator starting with the given vertex.
     *
     * @param startVertex the starting vertex
     * @return a breadth first iterator beginning at the given vertex
     */
    @Override
    public Iterator<T> iteratorBFS(T startVertex) {
        return this.iteratorBFS(getIndex(startVertex));
    }

    /**
     * Returns an iterator that performs a breadth first search traversal
     * starting at the given index.
     *
     * @param startIndex the index to begin the search from
     * @return an iterator that performs a breadth first traversal
     */
    private Iterator<T> iteratorBFS(int startIndex) {
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();

        if (!this.indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[this.size()];
        for (int i = 0; i < this.size(); i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            try {
                x = traversalQueue.dequeue();
                resultList.addToRear(this.vertices[x]);
                /**
                 * Find all vertices adjacent to x that have not been visited
                 * and queue them up
                 */
                for (int i = 0; i < this.size(); i++) {
                    if (this.adjMatrix[x][i] && !visited[i]) {
                        traversalQueue.enqueue(i);
                        visited[i] = true;
                    }
                }
            } catch (EmptyCollectionException ignored) {
            }
        }
        return resultList.iterator();
    }

    /**
     * Returns a depth first iterator starting with the given vertex.
     *
     * @param startVertex the starting vertex
     * @return a depth first iterator starting at the given vertex
     */
    @Override
    public Iterator<T> iteratorDFS(T startVertex) {
        return this.iteratorDFS(this.getIndex(startVertex));
    }

    /**
     * Returns an iterator that performs a depth first search traversal starting
     * at the given index.
     *
     * @param startIndex the index to begin the search traversal from
     * @return an iterator that performs a depth first traversal
     */
    private Iterator<T> iteratorDFS(int startIndex) {
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();
        boolean[] visited = new boolean[this.size()];

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        for (int i = 0; i < this.size(); i++) {
            visited[i] = false;
        }

        traversalStack.push(startIndex);
        resultList.addToRear(this.vertices[startIndex]);
        visited[startIndex] = true;

        while (!traversalStack.isEmpty()) {
            try {
                x = traversalStack.peek();

                found = false;
                /**
                 * Find a vertex adjacent to x that has not been visited and
                 * push it on the stack
                 */
                for (int i = 0; (i < this.size()) && !found; i++) {
                    if (this.adjMatrix[x][i] && !visited[i]) {
                        traversalStack.push(i);
                        resultList.addToRear(this.vertices[i]);
                        visited[i] = true;
                        found = true;
                    }
                }
                if (!found && !traversalStack.isEmpty()) {
                    traversalStack.pop();
                }
            } catch (EmptyCollectionException ignored) {
            }

        }
        return resultList.iterator();
    }

    /**
     * Returns an iterator that contains the shortest path between the two
     * vertices.
     *
     * @param startVertex  the starting vertex
     * @param targetVertex the ending vertex
     * @return an iterator that contains the shortest path between the two
     * vertices
     */
    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        return this.iteratorShortestPath(this.getIndex(startVertex), this.getIndex(targetVertex));
    }

    private Iterator<Integer> iteratorShortestPathIndices(int startIndex, int targetIndex) {
        int index = startIndex;
        int[] pathLength = new int[this.size()];
        int[] predecessor = new int[this.size()];
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<Integer> resultList = new ArrayUnorderedList<>();

        if (!this.indexIsValid(startIndex) || !this.indexIsValid(targetIndex) || (startIndex == targetIndex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[this.size()];
        for (int i = 0; i < this.size(); i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;
        pathLength[startIndex] = 0;
        predecessor[startIndex] = -1;

        while (!traversalQueue.isEmpty() && (index != targetIndex)) {
            try {
                index = traversalQueue.dequeue();

                /**
                 * Update the pathLength for each unvisited vertex adjacent to
                 * the vertex at the current index.
                 */
                for (int i = 0; i < this.size(); i++) {
                    if (adjMatrix[index][i] && !visited[i]) {
                        pathLength[i] = pathLength[index] + 1;
                        predecessor[i] = index;
                        traversalQueue.enqueue(i);
                        visited[i] = true;
                    }
                }
            } catch (EmptyCollectionException e) {
            }
        }
        if (index != targetIndex) // no path must have been found
        {
            return resultList.iterator();
        }

        LinkedStack<Integer> stack = new LinkedStack<>();
        stack.push(index);
        do {
            index = predecessor[index];
            stack.push(index);
        } while (index != startIndex);

        while (!stack.isEmpty()) {
            try {
                resultList.addToRear(stack.pop());
            } catch (EmptyCollectionException ignored) {
            }
        }

        return resultList.iterator();
    }

    private Iterator<T> iteratorShortestPath(int startIndex, int targetIndex) {
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();
        if (!this.indexIsValid(startIndex) || !this.indexIsValid(targetIndex)) {
            return resultList.iterator();
        }

        Iterator<Integer> it = this.iteratorShortestPathIndices(startIndex, targetIndex);
        while (it.hasNext()) {
            resultList.addToRear(this.vertices[it.next()]);
        }
        return resultList.iterator();
    }

    /**
     * Returns true if this graph is empty, false otherwise.
     *
     * @return true if this graph is empty
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Returns true if this graph is connected, false otherwise.
     *
     * @return true if this graph is connected
     */
    @Override
    public boolean isConnected() {
        if (this.isEmpty()) {
            return false;
        }

        Iterator<T> it = this.iteratorBFS(0);
        int count = 0;

        while (it.hasNext()) {
            it.next();
            count++;
        }

        return (count == this.size());
    }

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the integer number of vertices in this graph
     */
    @Override
    public int size() {
        return this.numVertices;
    }

}
