package estg.ipp.pt.DataStructures.Nodes;

/**
 * DoubleNode represents a node in a doubly linked list.
 */
public class DoubleNode<E> {

    private DoubleNode<E> next;
    private E element;
    private DoubleNode<E> previous;

    /**
     * Creates an empty node.
     */
    public DoubleNode() {
        this.next = null;
        this.element = null;
        this.previous = null;
    }

    /**
     * Creates a node storing the specified element.
     *
     * @param elem the element to be stored into the new node
     */
    public DoubleNode(E elem) {
        this.next = null;
        this.element = elem;
        this.previous = null;
    }

    /**
     * Sets the node that follows this one.
     *
     * @param dnode the node to be set as the one to follows the current one
     */
    public void setNext(DoubleNode<E> dnode) {
        this.next = dnode;
    }

    /**
     * Returns the node that follows this one.
     *
     * @return the node that follows the current one
     */
    public DoubleNode<E> getNext() {
        return this.next;
    }

    /**
     * Sets the node that precedes this one.
     *
     * @param dnode the node to be set as the one to precede the current one
     */
    public void setPrevious(DoubleNode<E> dnode) {
        this.previous = dnode;
    }

    /**
     * Returns the node that precedes this one.
     *
     * @return the node that precedes the current one
     */
    public DoubleNode<E> getPrevious() {
        return this.previous;
    }

    /**
     * Returns the element stored in this node.
     *
     * @return the element stored in this node
     */
    public E getElement() {
        return this.element;
    }

    /**
     * Sets the element stored in this node.
     *
     * @param elem the element to be stored in this node
     */
    public void setElement(E elem) {
        this.element = elem;
    }
}
