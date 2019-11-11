package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.parser.ArgumentTokenizer.PrefixPosition;

/**
 * Holds an ordered sequence of {@link CommandArgument}s.
 */
public class ArgumentList extends ArrayList<CommandArgument> {
    private static final ArgumentList EMPTY_LIST = new ArgumentList(List.of());

    public ArgumentList() {
        super();
    }

    private ArgumentList(final List<CommandArgument> list) {
        super(Objects.requireNonNull(list));
    }

    /**
     * Returns an unmodifiable {@link ArgumentList} containing zero {@link CommandArgument}s.
     *
     * @return An empty {@link ArgumentList}.
     */
    public static ArgumentList emptyList() {
        return EMPTY_LIST;
    }

    /**
     * Returns a user-friendly representation of this {@link ArgumentList}.  The string representation consists of a
     * list of the {@link CommandArgument}s in the order they are returned by its iterator. Adjacent elements are
     * separated by the space character.  Elements are converted to strings by {@link String#valueOf(Object)}.
     *
     * @return A user-friendly representation of this collection.
     */
    @Override
    public String toString() {
        final StringJoiner outputString = new StringJoiner(" ");
        for (final CommandArgument commandArgument : this) {
            outputString.add(commandArgument.toString());
        }
        return outputString.toString();
    }

    /**
     * Sorts the {@link CommandArgument}s based on {@link PrefixPosition#getStartPosition()}
     */
    public void sort() {
        sort(Comparator.comparingInt(PrefixPosition::getStartPosition));
    }

    boolean add(final PrefixPosition prefixPosition, final String value) {
        CollectionUtil.requireAllNonNull(prefixPosition, value);

        return add(new CommandArgument(prefixPosition, value));
    }

    /**
     * Finds the index of the {@link CommandArgument} that the {@code caretPosition} would be positioned within.
     *
     * @param caretPosition The position of a caret.
     * @return The index of the {@link CommandArgument} that the {@code caretPosition} would be positioned within.
     */
    public int getClosestIndexAtPosition(final int caretPosition) {
        assert caretPosition >= 0;

        final List<Integer> startPositions = this.stream()
                .map(PrefixPosition::getStartPosition)
                .collect(Collectors.toUnmodifiableList());
        int idx = Collections.binarySearch(startPositions, caretPosition);
        if (idx < -1) {
            // cursor is not exactly on a prefix
            idx = (idx * -1) - 2;
        }
        return idx;
    }

    /**
     * Finds the index of the {@link CommandArgument} that the {@code caretPosition} would be positioned within.
     *
     * @param caretPosition The position of a caret.
     * @return The index of the {@link CommandArgument} that the {@code caretPosition} would be positioned within.
     */
    public CommandArgument getClosestCommandArgumentAtPosition(final int caretPosition) {
        assert caretPosition >= 0;

        final int idx = getClosestIndexAtPosition(caretPosition);
        if (idx == -1) {
            return null;
        } else {
            return get(idx);
        }
    }

    /**
     * Calculates the position of a caret after {@link CommandArgument} of the given {@code index}.
     *
     * @param index The index of the {@link CommandArgument}.
     * @return The position of a caret after {@link CommandArgument} of the given {@code index}.
     * @throws IndexOutOfBoundsException if the {@code index} is invalid.
     */
    public int calculateCaretPositionAfterIndex(final int index) throws IndexOutOfBoundsException {
        Objects.checkIndex(index, size());

        final int numSpacesBetween = index;
        return this.stream().limit(index + 1).mapToInt(commandArgument -> {
            return commandArgument.toString().length();
        }).sum() + numSpacesBetween;
    }

    /**
     * Returns a {@link Stream} containing only the {@link CommandArgument}s that match the given {@code prefix}.
     *
     * @param prefix The desired prefix to match.
     * @return A {@link Stream} containing only the {@link CommandArgument}s that match the given {@code prefix}.
     */
    public Stream<CommandArgument> filterByPrefix(final Prefix prefix) {
        Objects.requireNonNull(prefix);

        return this.stream().filter(commandArgument -> {
            return commandArgument.getPrefix().equals(prefix);
        });
    }

    /**
     * Returns the first {@link CommandArgument} that matches the given {@code prefix} if it exists.
     *
     * @param prefix The desired prefix to match.
     * @return The first {@link CommandArgument} that matches the given {@code prefix} if it exists.
     */
    public Optional<CommandArgument> getFirstOfPrefix(final Prefix prefix) {
        Objects.requireNonNull(prefix);

        return filterByPrefix(prefix).findFirst();
    }

    /**
     * Returns the value of the first {@link CommandArgument} that matches the given {@code prefix} if it exists.
     *
     * @param prefix The desired prefix to match.
     * @return The value of the first {@link CommandArgument} that matches the given {@code prefix} if it exists.
     */
    public Optional<String> getFirstValueOfPrefix(final Prefix prefix) {
        Objects.requireNonNull(prefix);

        return getFirstOfPrefix(prefix).map(CommandArgument::getValue);
    }
}
