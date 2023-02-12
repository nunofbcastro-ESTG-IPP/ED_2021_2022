package estg.ipp.pt.DataStructures.Exceptions;

public class NonComparableElementException extends Exception {

    /**
     * Creates a new instance of <code>NonComparableElementException</code>
     * without detail message.
     */
    public NonComparableElementException() {
    }

    /**
     * Constructs an instance of <code>NonComparableElementException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public NonComparableElementException(String msg) {
        super(msg);
    }
}
