package seedu.address.logic.commands.suggestions;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.AddToGroupCommand;
import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;

/**
 * Represents a source of suggestions with hidden internal logic.
 */
public abstract class Suggester {
    protected static final ObservableList<String> NO_SUGGESTIONS = FXCollections.emptyObservableList();
    static final Map<String, Class<? extends Suggester>> SUGGESTER_MAP = Map.ofEntries(
            Map.entry(AddToGroupCommand.COMMAND_WORD, AddToGroupCommandSuggester.class)
    );

    protected static <T> ObservableList<T> createUnmodifiableObservableList(final List<T> list) {
        return FXCollections.unmodifiableObservableList(FXCollections.observableList(list));
    }

    /**
     * Creates a {@link Suggester} based on the given {@code commandWord}. (e.g. {@link AddToGroupCommand#COMMAND_WORD})
     *
     * @param commandWord The command keyword entered by the user.
     * @return A new {@link Suggester}.
     */
    public static Suggester createSuggester(final String commandWord) {
        Objects.requireNonNull(commandWord);

        final Class<? extends Suggester> suggesterClass = SUGGESTER_MAP.get(commandWord);
        if (suggesterClass == null) {
            return null;
        }

        try {
            return suggesterClass.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            return null;
        }
    }

    /**
     * Gets the supported {@link Prefix}es for a given {@code commandWord} (e.g. {@link AddToGroupCommand#COMMAND_WORD}
     * supports {@link AddToGroupCommandSuggester#SUPPORTED_PREFIXES}).
     *
     * @param commandWord The command keyword entered by the user.
     * @return The list of supported {@link Prefix}es for the given {@code commandWord}.
     */
    public static List<Prefix> getCommandPrefixes(final String commandWord) {
        Objects.requireNonNull(commandWord);

        final Class<? extends Suggester> suggesterClass = SUGGESTER_MAP.get(commandWord);
        if (suggesterClass == null) {
            return null;
        }

        try {
            return (List<Prefix>) suggesterClass.getField("SUPPORTED_PREFIXES").get(null);
        } catch (ReflectiveOperationException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            return null;
        }
    }

    /**
     * Gets a list of person names contained within the {@link Model} that match the {@link CommandArgument#getValue()}.
     * Requires the {@code commandArgument} to be of type {@link CliSyntax#PREFIX_NAME}.
     *
     * @param model           The {@link Model} containing the {@link seedu.address.model.person.Person}s to look
     *                        through.
     * @param commandArgument The {@link CommandArgument} of type {@link CliSyntax#PREFIX_NAME} containing the name to
     *                        search for.
     * @return A list of person names that match the search string (from {@link CommandArgument#getValue()}).
     */
    static List<String> getPersonNameSuggestions(final Model model, final CommandArgument commandArgument) {
        CollectionUtil.requireAllNonNull(model, commandArgument);

        final Prefix prefix = commandArgument.getPrefix();
        assert prefix.equals(CliSyntax.PREFIX_NAME);

        final String personNameInput = commandArgument.getValue();
        return model.personSuggester(personNameInput);
    }

    /**
     * Gets a list of group names contained within the {@link Model} that match the {@link CommandArgument#getValue()}.
     * Requires the {@code commandArgument} to be of type {@link CliSyntax#PREFIX_GROUPNAME}.
     *
     * @param model           The {@link Model} containing the {@link seedu.address.model.group.Group}s to look through.
     * @param commandArgument The {@link CommandArgument} of type {@link CliSyntax#PREFIX_GROUPNAME} containing the name
     *                        to search for.
     * @return A list of group names that match the search string (from {@link CommandArgument#getValue()}).
     */
    static List<String> getGroupNameSuggestions(final Model model, final CommandArgument commandArgument) {
        CollectionUtil.requireAllNonNull(model, commandArgument);

        final Prefix prefix = commandArgument.getPrefix();
        assert prefix.equals(CliSyntax.PREFIX_GROUPNAME);

        final String groupNameInput = commandArgument.getValue();
        return model.groupSuggester(groupNameInput);
    }

    /**
     * Gets suggestions for a specific {@link CommandArgument} within the {@link ArgumentList}.
     *
     * @param model           The {@link Model} containing data that {@link Suggester}s might look through to provide
     *                        suggestions.
     * @param arguments       The {@link ArgumentList} representing all the arguments/{@link Prefix}es and their values.
     * @param commandArgument The specific {@link CommandArgument} to get suggestions for.
     * @return A list of String suggestions for the given {@code commandArgument}.
     */
    public final ObservableList<String> getSuggestions(
            final Model model, final ArgumentList arguments, final CommandArgument commandArgument) {
        CollectionUtil.requireAllNonNull(model, arguments, commandArgument);
        assert arguments.contains(commandArgument) : "provided commandArgument is not in arguments";

        final List<String> suggestions = provideSuggestions(model, arguments, commandArgument);

        if (suggestions == null || suggestions.isEmpty()) {
            return NO_SUGGESTIONS;
        } else if (suggestions instanceof ObservableList) {
            return (ObservableList<String>) suggestions;
        } else {
            return createUnmodifiableObservableList(suggestions);
        }
    }

    /**
     * Gets suggestions for a specific {@link CommandArgument} within the {@link ArgumentList}.
     * <p>
     * Implementing classes can return null to signal there are no suggestions at the point of query.
     * Implementing classes can decide to only return values when certain {@link Prefix}es are present with valid
     * values.
     *
     * @param model           The {@link Model} containing data that {@link Suggester}s might look through to provide
     *                        suggestions.
     * @param arguments       The {@link ArgumentList} representing all the arguments/{@link Prefix}es and their values.
     * @param commandArgument The specific {@link CommandArgument} to get suggestions for.
     * @return A list of String suggestions for the given {@code commandArgument}.
     */
    protected abstract List<String> provideSuggestions(
            final Model model, final ArgumentList arguments, final CommandArgument commandArgument);
}
