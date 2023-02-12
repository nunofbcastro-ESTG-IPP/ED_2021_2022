package estg.ipp.pt.DataStructures.Queue;

import estg.ipp.pt.DataStructures.Exceptions.EmptyCollectionException;
import estg.ipp.pt.DataStructures.Interfaces.QueueADT;
import estg.ipp.pt.DataStructures.Nodes.LinearNode;

public class LinkedQueue<T> implements QueueADT<T> {

    private LinearNode<T> head;
    private LinearNode<T> tail;
    private int size;

    public LinkedQueue() {
        this.head = this.tail = null;
        this.size = 0;
    }

    @Override
    public void enqueue(T element) {
        LinearNode<T> tmp = this.tail;
        this.tail = new LinearNode<>(element);
        this.tail.setNext(null);

        if (tmp != null) {
            tmp.setNext(this.tail);
        }

        if (this.head == null) {
            this.head = this.tail;
        }

        this.size++;
    }

    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Array is empty.");
        }

        T tmp = this.head.getElement();
        this.head = this.head.getNext();        
        this.size--;
        
        if(this.isEmpty()){
            this.tail=null;
        }

        return tmp;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Array is empty.");
        }

        return this.head.getElement();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        LinearNode<T> node = this.head;

        while (node != null) {
            if (node.getElement() != null) {
                sb.append(node.getElement().toString()).append(", ");
            }
            node = node.getNext();
        }

        return sb.toString();
    }

}