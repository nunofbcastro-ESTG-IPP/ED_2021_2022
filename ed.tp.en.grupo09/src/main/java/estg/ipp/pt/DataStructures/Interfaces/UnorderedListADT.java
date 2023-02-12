package estg.ipp.pt.DataStructures.Interfaces;

import estg.ipp.pt.DataStructures.Exceptions.ElementNotFoundException;
import estg.ipp.pt.DataStructures.Exceptions.EmptyCollectionException;

public interface UnorderedListADT<T> extends ListADT<T> {

    
    /**
     * Adds the specified element to this list at the front
     *
     * @param element the element to be added to this list
     */
    public void addToFront(T element);

    /**
     * Adds the specified element to this list at the rear
     *
     * @param element the element to be added to this list
     */
    public void addToRear(T element);

    /**
     * Adds the specified element to this list after a specified element
     *
     * @param element the element to be added to this list
     * @param target the element prior to being added
     * @throws EmptyCollectionException if list is empty
     * @throws ElementNotFoundException if target doesn't exist
     */
    public void addAfter(T element, T target) throws EmptyCollectionException, ElementNotFoundException;

}
