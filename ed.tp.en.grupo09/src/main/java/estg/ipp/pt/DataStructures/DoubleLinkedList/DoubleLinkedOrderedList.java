package estg.ipp.pt.DataStructures.DoubleLinkedList;

import estg.ipp.pt.DataStructures.Exceptions.NonComparableElementException;
import estg.ipp.pt.DataStructures.Interfaces.OrderedListADT;
import estg.ipp.pt.DataStructures.Nodes.DoubleNode;

public class DoubleLinkedOrderedList<T> extends DoubleLinkedList<T> implements OrderedListADT<T> {

    @Override
    public void add(T element) throws NonComparableElementException {

        if (!(element instanceof Comparable)) {
            throw new NonComparableElementException("Element not extend Comparable");
        }

        DoubleNode<T> tmpNew = new DoubleNode<>(element);
        Comparable cElement = (Comparable) element;

        if (super.isEmpty()) {
            super.head = super.tail = tmpNew;
        } else if (cElement.compareTo(super.head.getElement()) <= 0) {
            super.head.setPrevious(tmpNew);
            tmpNew.setNext(super.head);
            tmpNew.setPrevious(null);
            super.head = tmpNew;
        } else if (cElement.compareTo(super.tail.getElement()) >= 0) {
            super.tail.setNext(tmpNew);
            tmpNew.setPrevious(super.tail);
            tmpNew.setNext(null);
            super.tail = tmpNew;
        } else {
            DoubleNode<T> tmpNode = super.head.getNext();
            while ((cElement.compareTo(tmpNode.getElement()) > 0)) {
                tmpNode = tmpNode.getNext();
            }
            tmpNew.setNext(tmpNode);
            tmpNew.setPrevious(tmpNode.getPrevious());
            tmpNode.getPrevious().setNext(tmpNew);
            tmpNode.setPrevious(tmpNew);
        }

        super.size++;
        super.modCount++;
    }

}
