package seedu.address.model.card;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.core.index.Index;

/**
 * Represents a Hint of a String.
 * Consists of a letter and its index in the String.
 * Guarantees: immutable.
 */
public class Hint {

    public final Character letter;
    public final Index index;

    /**
     * Constructs a {@code Hint}.
     *
     * @param letter A character
     * @param index The index of the character in the String
     */
    public Hint(Character letter, Index index) {
        requireAllNonNull(letter, index);
        this.letter = letter;
        this.index = index;
    }

    @Override
    public String toString() {
        return String.format("[letter:%s,index:%d]", letter.toString(), index.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Hint // instanceof handles nulls
                && letter.equals(((Hint) other).letter)
                && index.equals(((Hint) other).index)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, index);
    }
}
