package seedu.address.logic.parser;

import java.util.Objects;

import seedu.address.logic.parser.ArgumentTokenizer.PrefixPosition;

/**
 * Represents an argument to a user command (e.g. {@code <prefix>value})
 */
public class CommandArgument extends PrefixPosition {
    private final String value;

    public CommandArgument(final Prefix prefix, final int startPosition, final String value) {
        super(Objects.requireNonNull(prefix), Objects.requireNonNull(startPosition));
        this.value = Objects.requireNonNull(value);
    }

    protected CommandArgument(final PrefixPosition prefixPosition, final String value) {
        this(prefixPosition.getPrefix(), prefixPosition.getStartPosition(), value);
    }

    public String getValue() {
        return value;
    }

    /**
     * Creates a copy of this object with the new object containing the new {@code value}.
     *
     * @param value The value for the new {@link CommandArgument}.
     * @return A copy of this object with the new object containing the new {@code value}.
     */
    public CommandArgument copyWithNewValue(final String value) {
        return new CommandArgument(getPrefix(), getStartPosition(), value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommandArgument that = (CommandArgument) o;
        return super.equals(o) && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    @Override
    public String toString() {
        return String.format("%s%s", getPrefix().toString(), getValue());
    }
}
