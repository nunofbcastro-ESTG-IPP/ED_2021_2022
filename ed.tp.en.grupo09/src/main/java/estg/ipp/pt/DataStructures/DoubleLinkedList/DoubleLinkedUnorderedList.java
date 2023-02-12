package estg.ipp.pt.DataStructures.DoubleLinkedList;

import estg.ipp.pt.DataStructures.Exceptions.ElementNotFoundException;
import estg.ipp.pt.DataStructures.Exceptions.EmptyCollectionException;
import estg.ipp.pt.DataStructures.Interfaces.UnorderedListADT;
import estg.ipp.pt.DataStructures.Nodes.DoubleNode;

public class DoubleLinkedUnorderedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T> {

    public DoubleLinkedUnorderedList() {
        super();
    }

    @Override
    public void addToFront(T element) {
        DoubleNode<T> tmpNew = new DoubleNode<>(element);

        if (super.isEmpty()) {
            super.head = super.tail = tmpNew;
        } else {
            super.head.setPrevious(tmpNew);
            tmpNew.setNext(super.head);
            tmpNew.setPrevious(null);
            super.head = tmpNew;
        }

        super.size++;
        super.modCount++;
    }

    @Override
    public void addToRear(T element) {
        DoubleNode<T> tmpNew = new DoubleNode<>(element);

        if (super.isEmpty()) {
            super.head = super.tail = tmpNew;
        } else {
            super.tail.setNext(tmpNew);
            tmpNew.setPrevious(super.tail);
            tmpNew.setNext(null);
            super.tail = tmpNew;
        }

        super.size++;
        super.modCount++;
    }

    @Override
    public void addAfter(T element, T target) throws EmptyCollectionException, ElementNotFoundException {
        if(super.isEmpty()){
            throw new EmptyCollectionException("Empty list");
        } 
        
            DoubleNode<T> tmpNew = new DoubleNode<>(element);

            DoubleNode<T> tmpNode = super.search(target);

            if (tmpNode == null) {
                throw new ElementNotFoundException("Target not found");
            }
            
            tmpNew.setPrevious(tmpNode);
            tmpNew.setNext(tmpNode.getNext());
            tmpNode.getNext().setPrevious(tmpNew);
            tmpNode.setNext(tmpNew);
            
            super.size++;
            super.modCount++;
    }

}
