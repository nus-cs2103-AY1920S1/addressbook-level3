package seedu.address.logic;

public abstract class AutoCompleteNode<V> extends Node<V> implements AutoCompleteValueProvider {

    public AutoCompleteNode(V pointer) {
        super(pointer);
    }

}
