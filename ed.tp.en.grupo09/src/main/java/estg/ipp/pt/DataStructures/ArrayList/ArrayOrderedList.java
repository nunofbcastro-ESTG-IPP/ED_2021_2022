package estg.ipp.pt.DataStructures.ArrayList;

import estg.ipp.pt.DataStructures.Exceptions.NonComparableElementException;
import estg.ipp.pt.DataStructures.Interfaces.OrderedListADT;

public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T> {

    public ArrayOrderedList() {
        super();
    }

    public ArrayOrderedList(int capacity) {
        super(capacity);
    }

    @Override
    public void add(T element) throws NonComparableElementException {

        if (!(element instanceof Comparable)) {
            throw new NonComparableElementException("Element not extend Comparable");
        }

        if (super.size() == super.arrayList.length) {
            super.expandCapacity();
        }

        Comparable cElement = (Comparable) element;

        int i;
        for (i = super.size() - 1; i >= 0; i--) {
            Comparable cArray = (Comparable) super.arrayList[i];
            
            if (cElement.compareTo(cArray) >= 0) {
                break;
            } 
            super.arrayList[i + 1] = super.arrayList[i];
        }

        super.arrayList[i + 1] = element;
        super.size++;
        super.modCount++;
    }

}
