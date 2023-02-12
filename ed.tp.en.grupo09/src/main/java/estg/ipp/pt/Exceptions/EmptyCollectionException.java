package estg.ipp.pt.Exceptions;

public class EmptyCollectionException extends Exception {
    public EmptyCollectionException() {
    }

    public EmptyCollectionException(String msg) {
        super(msg);
    }
}