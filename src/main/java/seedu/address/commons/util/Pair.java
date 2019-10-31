package seedu.address.commons.util;

/**
 * Encapsulates a pair, with a head and a tail.
 * @param <H> head of the pair.
 * @param <T> tail of the pair.
 */
public class Pair<H, T> {
    private H head;
    private T tail;

    public Pair(H head, T tail) {
        this.head = head;
        this.tail = tail;
    }

    public H getHead() {
        return head;
    }

    public T getTail() {
        return tail;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || other instanceof Pair && head.equals(((Pair) other).head) && tail.equals(((Pair) other).tail);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", head, tail);
    }
}
