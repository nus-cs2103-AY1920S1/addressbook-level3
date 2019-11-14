package seedu.address.commons.util;

/**
 * Tuples are a helper class.
 */
public class Tuple<T> {
    private final T zero;
    private final T one;

    public Tuple(T zero, T one) {
        this.zero = zero;
        this.one = one;
    }

    public T getZero() {
        return zero;
    }

    public T getOne() {
        return one;
    }
}
