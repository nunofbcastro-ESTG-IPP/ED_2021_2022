package estg.ipp.pt.DataStructures.Stack;

import estg.ipp.pt.DataStructures.Exceptions.EmptyCollectionException;
import estg.ipp.pt.DataStructures.Interfaces.StackADT;
import estg.ipp.pt.DataStructures.Nodes.LinearNode;

public class LinkedStack<T> implements StackADT<T> {

    private LinearNode<T> head;
    private int top;

    public LinkedStack() {
        this.head = null;
        top = 0;
    }

    @Override
    public void push(T element) {
        LinearNode<T> tmp = this.head;
        this.head = new LinearNode<>(element);
        this.head.setNext(tmp);
        this.top++;
    }

    @Override
    public T pop() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("DataStructures/Stack");
        }
        T tmp = head.getElement();
        this.head=this.head.getNext();
        this.top--;
        return tmp;
    }

    @Override
    public T peek() throws EmptyCollectionException {        
        if (isEmpty()) {
            throw new EmptyCollectionException("DataStructures/Stack");
        }
        return head.getElement();
    }

    @Override
    public boolean isEmpty() {
        return top == 0;
    }

    @Override
    public int size() {
        return top;
    }
    
}
