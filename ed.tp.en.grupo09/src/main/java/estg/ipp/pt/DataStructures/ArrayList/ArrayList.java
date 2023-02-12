package estg.ipp.pt.DataStructures.ArrayList;

import estg.ipp.pt.DataStructures.Interfaces.ListADT;
import estg.ipp.pt.DataStructures.Exceptions.EmptyCollectionException;
import estg.ipp.pt.DataStructures.Exceptions.ElementNotFoundException;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Iterator;

abstract class ArrayList<T> implements ListADT<T> {

    private class ArrayListInterator implements Iterator<T> {

        private int nextPosition;
        private final int expectedModCount;

        {
            nextPosition = 0;
            expectedModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            return this.nextPosition < size();
        }

        @Override
        public T next() {
            this.checkForComodification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (T) arrayList[this.nextPosition++];
        }

        private void checkForComodification() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }

    }

    private final int DEFAULT_CAPACITY = 100;

    protected T[] arrayList;
    protected int size;
    protected int modCount;

    {
        this.size = 0;
        this.modCount = 0;
    }

    public ArrayList() {
        this.arrayList = (T[]) (new Object[this.DEFAULT_CAPACITY]);
    }

    public ArrayList(int capacity) {
        this.arrayList = (T[]) (new Object[capacity]);
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Empty list");
        }
        return removeAux(0);
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Empty list");
        }
        return removeAux(this.size() - 1);
    }

    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Empty list");
        }
        
        int position = search(element);
        
        if(position<0){
            throw new ElementNotFoundException("Value doesn't exist in list.");
        }
        
        return removeAux(position);
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Empty list");
        }
        return arrayList[0];
    }

    @Override
    public T last() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Empty list");
        }
        return arrayList[this.size() - 1];
    }

    @Override
    public boolean contains(T target) {
        return search(target)>0;
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
        return new ArrayListInterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.size(); i++) {
            sb.append(arrayList[i].toString()).append("\n");
        }
        return sb.toString();
    }

    private T removeAux(int position) {
        T tmp = null;
        if (position >= 0 && position < this.size()) {
            tmp = this.arrayList[position];
            this.arrayList[position] = null;
            for (int i = position; i < this.size() - 1; i++) {
                this.arrayList[i] = this.arrayList[i + 1];
            }
            this.arrayList[this.size()-1] = null;
            this.size--;
            this.modCount--;
        }
        return tmp;
    }

    private int search(T element) {
        for (int i = 0; i < this.size(); i++) {
            if (element.equals(this.arrayList[i])) {
                return i;
            }
        }
        return -1;
    }

    protected void expandCapacity() {
        T[] tmp = (T[]) (new Object[(int) Math.ceil(this.size() * (1.5))]);
        System.arraycopy(this.arrayList, 0, tmp, 0, this.size());
        this.arrayList = tmp;
    }

}
