package estg.ipp.pt.DataStructures.ArrayList;

import estg.ipp.pt.DataStructures.Interfaces.UnorderedListADT;
import estg.ipp.pt.DataStructures.Exceptions.ElementNotFoundException;
import estg.ipp.pt.DataStructures.Exceptions.EmptyCollectionException;

public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {

    public ArrayUnorderedList() {
        super();
    }

    public ArrayUnorderedList(int capacity) {
        super(capacity);
    }

    @Override
    public void addToFront(T element) {

        if (super.size() == super.arrayList.length) {
            super.expandCapacity();
        }

        for (int i = super.size(); i > 0; i--) {
            super.arrayList[i] = super.arrayList[i - 1];
        }

        super.arrayList[0] = element;
        super.size++;
        super.modCount++;

    }

    @Override
    public void addToRear(T element) {

        if (super.size() == super.arrayList.length) {
            super.expandCapacity();
        }

        super.arrayList[super.size] = element;
        super.size++;
        super.modCount++;

    }

    @Override
    public void addAfter(T element, T target) throws EmptyCollectionException, ElementNotFoundException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty list");
        }

        if (super.size() == super.arrayList.length) {
            super.expandCapacity();
        }

        int i;
        for (i = 0; i < super.size(); i++) {
            if (target.equals(arrayList[i])) {
                break;
            }
        }

        if (i == super.size()) {
            throw new ElementNotFoundException("Target not found");
        }

        for (int j = super.size() - 1; j >= 0; j--) {
            super.arrayList[i + 1] = super.arrayList[i];
        }

        super.arrayList[i] = element;
        super.size++;
        super.modCount++;
    }
}
