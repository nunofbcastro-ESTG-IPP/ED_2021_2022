package estg.ipp.pt.DataStructures.Queue;

import estg.ipp.pt.DataStructures.Exceptions.EmptyCollectionException;
import estg.ipp.pt.DataStructures.Interfaces.QueueADT;

public class CircularArrayQueue<T> implements QueueADT<T> {

    private final int DEFAULT_CAPACITY = 100;

    private T[] queue;
    private int count;
    private int front;
    private int rear;

    {
        this.queue = null;
        this.count = 0;
        this.rear = 0;
        this.front = 0;
    }

    public CircularArrayQueue() {
        this.queue = (T[]) (new Object[this.DEFAULT_CAPACITY]);
    }

    public CircularArrayQueue(int initialCapacity) {
        this.queue = (T[]) (new Object[initialCapacity]);
    }

    @Override
    public void enqueue(T element) {
        if (this.size() == this.queue.length) {
            this.expandCapacity();
        }
        this.queue[rear] = element;
        this.rear = (this.rear + 1) % this.queue.length;
        this.count++;
    }

    @Override
    public T dequeue() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Array is empty.");
        }

        T tmp = this.queue[front];
        this.queue[front] = null;
        this.count--;
        this.front = (front + 1) % queue.length;
        return tmp;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Array is empty.");
        }

        return this.queue[front];
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public int size() {
        return this.count;
    }

    private void expandCapacity() {
        T[] tmp = (T[]) (new Object[(int) Math.ceil(this.size() * (1.5))]);
        int i = 0;

        try {
            while (this.size() != 0) {
                tmp[i] = this.dequeue();
                i++;
            }
            front = 0;
            this.count = this.rear = this.queue.length;
            this.queue = tmp;
        } catch (EmptyCollectionException e) {
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Array size: ").append(this.queue.length).append("\n");
        sb.append("Number of elements: ").append(this.size()).append("\n");
        for (T queue1 : queue) {
            if (queue1 != null) {
                sb.append(queue1.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}
