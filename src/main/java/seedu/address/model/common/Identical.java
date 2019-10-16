package seedu.address.model.common;

public interface Identical<T> extends Comparable<T> {

    /**
     * Returns true if {@code other} is similar to current instance.
     * This defines a weaker notion of equality between two instances.
     */
    boolean isSameAs(T other);
}
