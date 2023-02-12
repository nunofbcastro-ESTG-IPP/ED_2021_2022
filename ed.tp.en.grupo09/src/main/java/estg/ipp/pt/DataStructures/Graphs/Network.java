package estg.ipp.pt.DataStructures.Graphs;

import estg.ipp.pt.DataStructures.ArrayList.ArrayUnorderedList;
import estg.ipp.pt.DataStructures.Interfaces.NetworkADT;
import estg.ipp.pt.DataStructures.Exceptions.EmptyCollectionException;
import estg.ipp.pt.DataStructures.Interfaces.HeapADT;
import estg.ipp.pt.DataStructures.Queue.LinkedQueue;
import estg.ipp.pt.DataStructures.Stack.LinkedStack;
import estg.ipp.pt.DataStructures.Tree.LinkedHeap;
import estg.ipp.pt.Exceptions.IllegalArgumentException;

import java.util.Iterator;

public class Network<T> extends Graph<T> implements NetworkADT<T> {
    protected double[][] adjMatrix;    // adjacency matrix

    public Network() {
        numVertices = 0;
        this.adjMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    protected void expandCapacity() {
        T[] largerVertices = (T[]) (new Object[vertices.length * 2]);
        double[][] largerAdjMatrix
                = new double[vertices.length * 2][vertices.length * 2];

        for (int i = 0; i < numVertices; i++) {
            System.arraycopy(adjMatrix[i], 0, largerAdjMatrix[i], 0, numVertices);
            largerVertices[i] = vertices[i];
        }

        vertices = largerVertices;
        adjMatrix = largerAdjMatrix;
    }

    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @param weight  the weight
     */
    public void addEdge(T vertex1, T vertex2, double weight) {
        this.addEdge(super.getIndex(vertex1), super.getIndex(vertex2), weight);
    }

    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param index1 the first index
     * @param index2 the second index
     * @param weight the weight
     */
    private void addEdge(int index1, int index2, double weight) {
        if (super.indexIsValid(index1) && super.indexIsValid(index2)) {
            this.adjMatrix[index1][index2] = weight;
            this.adjMatrix[index2][index1] = weight;
        }
    }

    /**
     * Inserts an edge between two vertices of the graph. Assumes a weight of 0.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    @Override
    public void addEdge(T vertex1, T vertex2) {
        this.addEdge(super.getIndex(vertex1), super.getIndex(vertex2), 0);
    }

    /**
     * Adds a vertex to the graph, expanding the capacity of the graph if
     * necessary. It also associates an object with the vertex.
     *
     * @param vertex the vertex to add to the graph
     */
    public void addVertex(T vertex) throws IllegalArgumentException {
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

    /**
     * Removes an edge between two vertices of this graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    public void removeEdge(T vertex1, T vertex2) {
        this.removeEdge(super.getIndex(vertex1), super.getIndex(vertex2));
    }

    /**
     * Removes an edge between two vertices of this graph.
     *
     * @param index1 the first index
     * @param index2 the second index
     */
    public void removeEdge(int index1, int index2) {
        if (super.indexIsValid(index1) && super.indexIsValid(index2)) {
            this.adjMatrix[index1][index2] = Double.POSITIVE_INFINITY;
            this.adjMatrix[index2][index1] = Double.POSITIVE_INFINITY;
        }
    }

    /**
     * Removes a single vertex with the given value from this graph.
     *
     * @param vertex the vertex to be removed from this graph
     */
    @Override
    public void removeVertex(T vertex) {
        this.removeVertex(super.getIndex(vertex));
    }

    /**
     * Removes a single vertex with the given value from this graph.
     *
     * @param index the index
     */
    private void removeVertex(int index) {
        if (this.indexIsValid(index)) {
            this.numVertices--;

            for (int i = index; i < super.size(); i++){
                this.vertices[i] = this.vertices[i+1];
            }

            for (int i = index; i < super.size(); i++){
                for (int j = 0; j <= super.size(); j++){
                    this.adjMatrix[i][j] = this.adjMatrix[i+1][j];
                }
            }

            for (int i = index; i < super.size(); i++){
                for (int j = 0; j < super.size(); j++){
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
    public Iterator<T> iteratorBFS(T startVertex) {
        return this.iteratorBFS(super.getIndex(startVertex));
    }

    /**
     * Returns an iterator that performs a breadth first search traversal
     * starting at the given index.
     *
     * @param startIndex the index to begin the search from
     * @return an iterator that performs a breadth first traversal
     */
    public Iterator<T> iteratorBFS(int startIndex) {
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();

        if (!super.indexIsValid(startIndex))
            return resultList.iterator();

        boolean[] visited = new boolean[super.size()];
        for (int i = 0; i < super.size(); i++) {
            visited[i] = false;
        }


        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            try {
                x = traversalQueue.dequeue();
                resultList.addToRear(super.vertices[x]);
                /**
                 * Find all vertices adjacent to x that have not been visited
                 * and queue them up
                 */
                for (int i = 0; i < super.size(); i++) {
                    if ((this.adjMatrix[x][i] < Double.POSITIVE_INFINITY) && !visited[i]) {
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
    public Iterator<T> iteratorDFS(T startVertex) {
        return this.iteratorDFS(super.getIndex(startVertex));
    }

    /**
     * Returns an iterator that performs a depth first search traversal starting
     * at the given index.
     *
     * @param startIndex the index to begin the search traversal from
     * @return an iterator that performs a depth first traversal
     */
    public Iterator<T> iteratorDFS(int startIndex) {
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();
        boolean[] visited = new boolean[super.size()];

        if (!super.indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        for (int i = 0; i < super.size(); i++) {
            visited[i] = false;
        }

        traversalStack.push(startIndex);
        resultList.addToRear(super.vertices[startIndex]);
        visited[startIndex] = true;

        while (!traversalStack.isEmpty()) {
            try {
                x = traversalStack.peek();
                found = false;
                /**
                 * Find a vertex adjacent to x that has not been visited and
                 * push it on the stack
                 */
                for (int i = 0; (i < super.size()) && !found; i++) {
                    if ((this.adjMatrix[x][i] < Double.POSITIVE_INFINITY) && !visited[i]) {
                        traversalStack.push(i);
                        resultList.addToRear(super.vertices[i]);
                        visited[i] = true;
                        found = true;
                    }
                }
                if (!found && !traversalStack.isEmpty())
                    traversalStack.pop();
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
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        return iteratorShortestPath(getIndex(startVertex),
                getIndex(targetVertex));
    }

    public Iterator<T> iteratorShortestPath(int startIndex, int targetIndex) {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<>();
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex))
            return templist.iterator();

        Iterator<Integer> it = iteratorShortestPathIndices(startIndex,
                targetIndex);
        while (it.hasNext())
            templist.addToRear(vertices[(it.next()).intValue()]);
        return templist.iterator();
    }

    protected Iterator<Integer> iteratorShortestPathIndices(int startIndex, int targetIndex) {
        int index;
        double weight;
        int[] predecessor = new int[numVertices];
        HeapADT<Double> traversalMinHeap = new LinkedHeap<>();
        ArrayUnorderedList<Integer> resultList =
                new ArrayUnorderedList<>();
        LinkedStack<Integer> stack = new LinkedStack<>();

        int[] pathIndex = new int[numVertices];
        double[] pathWeight = new double[numVertices];
        for (int i = 0; i < numVertices; i++)
            pathWeight[i] = Double.POSITIVE_INFINITY;

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++)
            visited[i] = false;

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex) ||
                (startIndex == targetIndex) || isEmpty())
            return resultList.iterator();

        pathWeight[startIndex] = 0;
        predecessor[startIndex] = -1;
        visited[startIndex] = true;
        weight = 0;

        /** Update the pathWeight for each vertex except the
         startVertex. Notice that all vertices not adjacent
         to the startVertex  will have a pathWeight of
         infinity for now. */
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                pathWeight[i] = pathWeight[startIndex] +
                        adjMatrix[startIndex][i];
                predecessor[i] = startIndex;
                traversalMinHeap.addElement(pathWeight[i]);
            }
        }

        do {
            try {
                weight = traversalMinHeap.removeMin();
                traversalMinHeap = new LinkedHeap<>();
                if (weight == Double.POSITIVE_INFINITY)  // no possible path
                    return resultList.iterator();
                else {
                    index = getIndexOfAdjVertexWithWeightOf(visited, pathWeight, weight);
                    visited[index] = true;
                }

                /** Update the pathWeight for each vertex that has has not been
                 visited and is adjacent to the last vertex that was visited.
                 Also, add each unvisited vertex to the heap. */
                for (int i = 0; i < numVertices; i++) {
                    if (!visited[i]) {
                        if ((adjMatrix[index][i] < Double.POSITIVE_INFINITY) && (pathWeight[index] + adjMatrix[index][i]) < pathWeight[i]) {
                            pathWeight[i] = pathWeight[index] + adjMatrix[index][i];
                            predecessor[i] = index;
                        }
                        traversalMinHeap.addElement(pathWeight[i]);
                    }
                }
            } catch (EmptyCollectionException ignored) {
            }
        } while (!traversalMinHeap.isEmpty() && !visited[targetIndex]);

        index = targetIndex;
        stack.push(index);
        do {
            index = predecessor[index];
            stack.push(index);
        } while (index != startIndex);

        while (!stack.isEmpty()) {
            try {
                resultList.addToRear((stack.pop()));
            } catch (EmptyCollectionException ignored) {
            }
        }


        return resultList.iterator();
    }

    protected int getIndexOfAdjVertexWithWeightOf(boolean[] visited, double[] pathWeight, double weight) {
        for (int i = 0; i < numVertices; i++)
            if ((pathWeight[i] == weight) && !visited[i])
                for (int j = 0; j < numVertices; j++)
                    if ((adjMatrix[i][j] < Double.POSITIVE_INFINITY) &&
                            visited[j])
                        return i;

        return -1;  // should never get to here
    }

    public double shortestPathWeight(int startIndex, int targetIndex) {
        double result = 0;
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex))
            return Double.POSITIVE_INFINITY;

        int index1, index2;
        Iterator<Integer> it = iteratorShortestPathIndices(startIndex,
                targetIndex);

        if (it.hasNext())
            index1 = ((Integer) it.next()).intValue();
        else
            return Double.POSITIVE_INFINITY;

        while (it.hasNext()) {
            index2 = (it.next()).intValue();
            result += adjMatrix[index1][index2];
            index1 = index2;
        }

        return result;
    }

    /**
     * Returns the weight of the shortest path in this network.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return the weight of the shortest path in this network
     */
    public double shortestPathWeight(T vertex1, T vertex2) {
        return shortestPathWeight(super.getIndex(vertex1), super.getIndex(vertex2));
    }

    /**
     * Returns a minimum spanning tree of the network.
     *
     * @return a minimum spanning tree of the network
     */
    public Network mstNetwork() {
        int x, y;
        int index;
        double weight;
        int[] edge = new int[2];
        HeapADT<Double> minHeap = new LinkedHeap<>();
        Network<T> resultGraph = new Network<>();
        if (super.isEmpty() || !super.isConnected()) {
            return resultGraph;
        }
        resultGraph.adjMatrix = new double[super.numVertices][super.numVertices];
        for (int i = 0; i < super.numVertices; i++) {
            for (int j = 0; j < super.numVertices; j++) {
                resultGraph.adjMatrix[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        resultGraph.vertices = (T[]) (new Object[super.numVertices]);
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        edge[0] = 0;
        resultGraph.vertices[0] = this.vertices[0];
        resultGraph.numVertices++;
        visited[0] = true;
        /**
         * Add all edges, which are adjacent to the starting vertex, to the heap
         */
        for (int i = 0; i < super.numVertices; i++) {
            minHeap.addElement(this.adjMatrix[0][i]);
        }
        while ((resultGraph.size() < this.size()) && !minHeap.isEmpty()) {
            /**
             * Get the edge with the smallest weight that has exactly one vertex
             * already in the resultGraph
             */
            do {
                try {
                    weight = (minHeap.removeMin());
                    edge = getEdgeWithWeightOf(weight, visited);
                } catch (EmptyCollectionException ignored) {
                }
            } while (!super.indexIsValid(edge[0]) || !super.indexIsValid(edge[1]));
            x = edge[0];
            y = edge[1];
            if (!visited[x]) {
                index = x;
            } else {
                index = y;
            }
            /**
             * Add the new edge and vertex to the resultGraph
             */
            resultGraph.vertices[index] = this.vertices[index];
            visited[index] = true;
            resultGraph.numVertices++;
            resultGraph.adjMatrix[x][y] = this.adjMatrix[x][y];
            resultGraph.adjMatrix[y][x] = this.adjMatrix[y][x];
            /**
             * Add all edges, that are adjacent to the newly added vertex, to
             * the heap
             */
            for (int i = 0; i < super.numVertices; i++) {
                if (!visited[i] && (this.adjMatrix[i][index] < Double.POSITIVE_INFINITY)) {
                    edge[0] = index;
                    edge[1] = i;
                    minHeap.addElement(this.adjMatrix[index][i]);
                }
            }
        }
        return resultGraph;
    }

    protected int[] getEdgeWithWeightOf(double weight, boolean[] visited) {
        int[] edge = new int[2];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if ((adjMatrix[i][j] == weight) && (visited[i] ^ visited[j])) {
                    edge[0] = i;
                    edge[1] = j;
                    return edge;
                }
            }
        }

        /**
         * Will only get to here if a valid edge is not found
         */
        edge[0] = -1;
        edge[1] = -1;
        return edge;
    }

}
