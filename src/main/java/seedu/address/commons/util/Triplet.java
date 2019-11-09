package seedu.address.commons.util;

/**
 * A triplet of 3 values.
 */
public class Triplet<U, V, W> {
    private U u;
    private V v;
    private W w;

    /**
     * Creates a triplet of 3 values.
     */
    public Triplet(U u, V v, W w) {
        this.u = u;
        this.w = w;
        this.v = v;
    }

    /**
     * Gets the first value of this triplet.
     */
    public U getFirst() {
        return u;
    }

    /**
     * Gets the second value of this triplet.
     */
    public V getSecond() {
        return v;
    }

    /**
     * Gets the third value of this triplet.
     */
    public W getThird() {
        return w;
    }
}
