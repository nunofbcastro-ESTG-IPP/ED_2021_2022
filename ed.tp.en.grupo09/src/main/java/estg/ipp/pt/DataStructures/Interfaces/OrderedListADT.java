package estg.ipp.pt.DataStructures.Interfaces;

import estg.ipp.pt.DataStructures.Exceptions.NonComparableElementException;

public interface OrderedListADT<T> extends ListADT<T> {

    /**
     * Adds the specified element to this list at the proper location
     *
     * @param element the element to be added to this list
     * @throws NonComparableElementException if the element doesn't comparable
     */
    public void add(T element) throws NonComparableElementException;
}
