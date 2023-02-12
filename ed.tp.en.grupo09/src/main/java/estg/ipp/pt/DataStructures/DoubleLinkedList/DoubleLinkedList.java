package estg.ipp.pt.DataStructures.DoubleLinkedList;

import estg.ipp.pt.DataStructures.Exceptions.ElementNotFoundException;
import estg.ipp.pt.DataStructures.Exceptions.EmptyCollectionException;
import estg.ipp.pt.DataStructures.Interfaces.ListADT;
import estg.ipp.pt.DataStructures.Nodes.DoubleNode;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class DoubleLinkedList<T> implements ListADT<T> {

    private class DoubleLinkedListInterator implements Iterator<T> {

        private final int expectedModCount;
        private DoubleNode<T> current;

        {
            expectedModCount = modCount;
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            checkForComodification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T tmp = current.getElement();
            current = current.getNext();
            return tmp;
        }

        final void checkForComodification() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }

    }

    protected DoubleNode<T> head;
    protected DoubleNode<T> tail;
    protected int size = 0;
    protected int modCount;

    {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.modCount = 0;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {

        if (this.isEmpty()) {
            throw new EmptyCollectionException("Empty list");
        }

        T tmp = this.head.getElement();

        this.head = this.head.getNext();

        if (this.size() == 1) {
            this.tail = null;
        } else if (this.head != null) {
            this.head.setPrevious(null);
        }

        this.size--;
        this.modCount++;

        return tmp;

    }

    @Override
    public T removeLast() throws EmptyCollectionException {

        if (this.isEmpty()) {
            throw new EmptyCollectionException("Empty list");
        }

        T tmp = this.tail.getElement();

        this.tail = this.tail.getPrevious();

        if (this.size() == 1) {
            this.head = null;
        } else if (this.tail != null) {
            this.tail.setNext(null);
        }

        this.size--;
        this.modCount++;

        return tmp;

    }

    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Empty list");
        }

        DoubleNode<T> node = search(element);

        if (node == null) {
            throw new ElementNotFoundException("Value doesn't exist in list.");
        }

        if (node.equals(this.head)) {
            return this.removeFirst();
        } else if (node.equals(this.tail)) {
            return this.removeLast();
        }

        node.getPrevious().setNext(node.getNext());
        node.getNext().setPrevious(node.getPrevious());

        this.size--;
        this.modCount++;

        return element;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Empty list");
        }
        return head.getElement();
    }

    @Override
    public T last() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Empty list");
        }
        return tail.getElement();
    }

    @Override
    public boolean contains(T target) {
        return search(target) != null;
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
    public Iterator<T> iterator() {
        return new DoubleLinkedListInterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        DoubleNode<T> node = this.head;

        while (node != null) {
            if (node.getElement() != null) {
                sb.append(node.getElement().toString()).append("\n");
                node = node.getNext();
            }
        }

        return sb.toString();
    }

    protected DoubleNode<T> search(T element) {
        DoubleNode<T> node = this.head;
        while (node != null) {
            if (node.getElement().equals(element)) {
                return node;
            }
            node = node.getNext();
        }
        return null;
    }

}
