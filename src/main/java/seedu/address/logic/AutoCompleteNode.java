package seedu.address.logic;

/**
 * Represents a {@code Node} that implements {@code AutoCompleteValueProvider}.
 */
public abstract class AutoCompleteNode<V> extends Node<V> implements AutoCompleteValueProvider {

    public AutoCompleteNode(V pointer) {
        super(pointer);
    }

}
