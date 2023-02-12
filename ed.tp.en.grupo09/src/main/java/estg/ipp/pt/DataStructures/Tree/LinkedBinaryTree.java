package estg.ipp.pt.DataStructures.Tree;

import estg.ipp.pt.DataStructures.ArrayList.ArrayUnorderedList;
import estg.ipp.pt.DataStructures.Exceptions.ElementNotFoundException;
import estg.ipp.pt.DataStructures.Exceptions.EmptyCollectionException;
import estg.ipp.pt.DataStructures.Interfaces.QueueADT;
import estg.ipp.pt.DataStructures.Interfaces.BinaryTreeADT;
import estg.ipp.pt.DataStructures.Queue.LinkedQueue;

import java.util.Iterator;

/**
 * LinkedBinaryTree implements the BinaryTreeADT interface
 *
 */
public class LinkedBinaryTree<T> implements BinaryTreeADT<T> {

    protected int count;
    protected BinaryTreeNode<T> root;

    /**
     * Creates an empty binary tree.
     */
    public LinkedBinaryTree() {
        count = 0;
        root = null;
    }

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param element the element that will become the root of the new binary
     * tree
     */
    public LinkedBinaryTree(T element) {
        count = 1;
        root = new BinaryTreeNode<T>(element);
    }

    @Override
    public T getRoot() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Empty Collection");
        }
        return this.root.element;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public boolean contains(T targetElement) {
        try {
            this.find(targetElement);
            return true;
        } catch (ElementNotFoundException e) {
            return false;
        }
    }

    /**
     * Returns a reference to the specified target element if it is found in
     * this binary tree. Throws a NoSuchElementException if the specified target
     * element is not found in the binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @return a reference to the specified target
     * @throws ElementNotFoundException if an element not found exception occurs
     */
    @Override
    public T find(T targetElement) throws ElementNotFoundException {
        BinaryTreeNode<T> current = this.findAgain(targetElement, this.root);

        if (current == null) {
            throw new ElementNotFoundException("binary tree");
        }

        return (current.element);
    }

    /**
     * Returns a reference to the specified target element if it is found in
     * this binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @param next the element to begin searching from
     */
    private BinaryTreeNode<T> findAgain(T targetElement, BinaryTreeNode<T> next) {
        if (next == null) {
            return null;
        }

        if (next.element.equals(targetElement)) {
            return next;
        }

        BinaryTreeNode<T> temp = this.findAgain(targetElement, next.left);

        if (temp == null) {
            temp = this.findAgain(targetElement, next.right);
        }

        return temp;
    }

    /**
     * Performs an inorder traversal on this binary tree by calling an
     * overloaded, recursive inorder method that starts with the root.
     *
     * @return an in order iterator over this binary tree
     */
    @Override
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();
        this.inorder(this.root, tempList);

        return tempList.iterator();
    }

    /**
     * Performs a recursive inorder traversal.
     *
     * @param node the node to be used as the root for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    private void inorder(BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) {
        if (node != null) {
            this.inorder(node.left, tempList);
            tempList.addToRear(node.element);
            this.inorder(node.right, tempList);
        }
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        this.preorder(this.root, tempList);

        return tempList.iterator();
    }

    private void preorder(BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) {
        if (node != null) {
            tempList.addToRear(node.element);
            this.preorder(node.left, tempList);
            this.preorder(node.right, tempList);
        }
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        this.postorder(this.root, tempList);

        return tempList.iterator();
    }

    private void postorder(BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) {
        if (node != null) {
            this.postorder(node.left, tempList);
            this.postorder(node.right, tempList);
            tempList.addToRear(node.element);
        }
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        QueueADT<BinaryTreeNode<T>> tempQueue = new LinkedQueue<>();
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();

        if (!this.isEmpty()) {
            tempQueue.enqueue(this.root);
        }

        while (!tempQueue.isEmpty()) {
            BinaryTreeNode<T> temp = null;
            try {
                temp = tempQueue.dequeue();
            } catch (EmptyCollectionException e) {
            }
            if (temp != null) {
                tempList.addToRear(temp.element);
                tempQueue.enqueue(temp.left);
                tempQueue.enqueue(temp.right);
            }
        }

        return tempList.iterator();
    }

}
