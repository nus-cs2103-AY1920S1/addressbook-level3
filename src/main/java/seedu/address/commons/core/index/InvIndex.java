package seedu.address.commons.core.index;

/**
 * Represents a zero-based or one-based index.
 *
 * {@code InvIndex} should be used right from the start (when parsing in a new user input), so that if the current
 * component wants to communicate with another component, it can send an {@code InvIndex} to avoid having to know what
 * base the other component is using for its inventory index. However, after receiving the {@code InvIndex},
 * that component can convert it back to an int if the inventory index will not be passed to a different component
 * again.
 */
public class InvIndex {
    private int zeroBasedIndex;

    /**
     * Index can only be created by calling {@link Index#fromZeroBased(int)} or
     * {@link Index#fromOneBased(int)}.
     */
    private InvIndex(int zeroBasedIndex) {
        if (zeroBasedIndex < 0) {
            throw new IndexOutOfBoundsException();
        }

        this.zeroBasedIndex = zeroBasedIndex;
    }

    public int getZeroBased() {
        return zeroBasedIndex;
    }

    public int getOneBased() {
        return zeroBasedIndex + 1;
    }

    /**
     * Creates a new {@code InvIndex} using a zero-based index.
     */
    public static InvIndex fromZeroBased(int zeroBasedIndex) {
        return new InvIndex(zeroBasedIndex);
    }

    /**
     * Creates a new {@code InvIndex} using a one-based index.
     * @return
     */
    public static InvIndex fromOneBased(int oneBasedIndex) {
        return new InvIndex(oneBasedIndex - 1);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InvIndex // instanceof handles nulls
                && zeroBasedIndex == ((InvIndex) other).zeroBasedIndex); // state check
    }
}
