package estg.ipp.pt.DataStructures.Queue;

public class LinearNode<T> {
    private LinearNode<T> next = null;
    private T element;

    public LinearNode() {
        this.element = null;
    }

    public LinearNode(T elem) {
        this.element = elem;
    }

    public LinearNode<T> getNext() {
        return this.next;
    }

    public void setNext(LinearNode<T> node) {
        this.next = node;
    }

    public T getElement() {
        return this.element;
    }

    public void setElement(T elem) {
        this.element = elem;
    }
}

