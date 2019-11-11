package seedu.address.logic.commands.suggestions;

import static seedu.address.commons.util.CollectionUtil.createUnmodifiableObservableList;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.logic.commands.AddNusModCommand;
import seedu.address.logic.commands.AddNusModsCommand;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.AddToGroupCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeleteFromGroupCommand;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.EditGroupCommand;
import seedu.address.logic.commands.EditPersonCommand;
import seedu.address.logic.commands.EditUserCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.LookAtGroupMemberCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.SelectFreeTimeCommand;
import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.commands.ShowNusModCommand;
import seedu.address.logic.commands.suggestions.stateless.GroupNameSuggester;
import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents a source of suggestions with hidden internal logic.
 */
public abstract class Suggester {
    protected static final ObservableList<String> NO_SUGGESTIONS = FXCollections.emptyObservableList();
    static final Map<String, Class<? extends Suggester>> SUGGESTER_MAP = Map.ofEntries(
            Map.entry(AddEventCommand.COMMAND_WORD, AddEventCommandSuggester.class),
            Map.entry(AddGroupCommand.COMMAND_WORD, AddGroupCommandSuggester.class),
            Map.entry(AddNusModCommand.COMMAND_WORD, AddNusModCommandSuggester.class),
            Map.entry(AddNusModsCommand.COMMAND_WORD, AddNusModsCommandSuggester.class),
            Map.entry(AddPersonCommand.COMMAND_WORD, AddPersonCommandSuggester.class),
            Map.entry(AddToGroupCommand.COMMAND_WORD, AddToGroupCommandSuggester.class),
            Map.entry(DeleteEventCommand.COMMAND_WORD, DeleteEventCommandSuggester.class),
            Map.entry(DeleteFromGroupCommand.COMMAND_WORD, DeleteFromGroupCommandSuggester.class),
            Map.entry(DeleteGroupCommand.COMMAND_WORD, DeleteGroupCommandSuggester.class),
            Map.entry(DeletePersonCommand.COMMAND_WORD, DeletePersonCommandSuggester.class),
            Map.entry(EditGroupCommand.COMMAND_WORD, EditGroupCommandSuggester.class),
            Map.entry(EditPersonCommand.COMMAND_WORD, EditPersonCommandSuggester.class),
            Map.entry(EditUserCommand.COMMAND_WORD, EditUserCommandSuggester.class),
            Map.entry(ExportCommand.COMMAND_WORD, ExportCommandSuggester.class),
            Map.entry(SelectFreeTimeCommand.COMMAND_WORD, GroupNameSuggester.class),
            Map.entry(LookAtGroupMemberCommand.COMMAND_WORD, LookAtGroupMemberCommandSuggester.class),
            Map.entry(ScheduleCommand.COMMAND_WORD, GroupNameSuggester.class),
            Map.entry(ShowCommand.COMMAND_WORD, ShowCommandSuggester.class),
            Map.entry(ShowNusModCommand.COMMAND_WORD, ShowNusModCommandSuggester.class)
    );

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
     * Requires the {@code commandArgument} to be of type {@link CliSyntax#PREFIX_NAME} or
     * {@link CliSyntax#PREFIX_EDIT}.
     *
     * @param model           The {@link Model} containing the {@link seedu.address.model.person.Person}s to look
     *                        through.
     * @param commandArgument The {@link CommandArgument} of type {@link CliSyntax#PREFIX_NAME} or
     *                        {@link CliSyntax#PREFIX_EDIT} containing the name to search for.
     * @return A list of person names that match the search string (from {@link CommandArgument#getValue()}).
     */
    static List<String> getPersonNameSuggestions(final Model model, final CommandArgument commandArgument) {
        CollectionUtil.requireAllNonNull(model, commandArgument);

        final Prefix prefix = commandArgument.getPrefix();
        assert prefix.equals(CliSyntax.PREFIX_NAME) || prefix.equals(CliSyntax.PREFIX_EDIT);

        final String personNameInput = commandArgument.getValue();
        return model.personSuggester(personNameInput);
    }

    /**
     * Gets a list of group names contained within the {@link Model} that match the {@link CommandArgument#getValue()}.
     * Requires the {@code commandArgument} to be either of type {@link CliSyntax#PREFIX_GROUPNAME}
     * or {@link CliSyntax#PREFIX_EDIT}.
     *
     * @param model           The {@link Model} containing the {@link seedu.address.model.group.Group}s to look through.
     * @param commandArgument The {@link CommandArgument} of type {@link CliSyntax#PREFIX_GROUPNAME} or
     *                        {@link CliSyntax#PREFIX_EDIT} containing the name to search for.
     * @return A list of group names that match the search string (from {@link CommandArgument#getValue()}).
     */
    static List<String> getGroupNameSuggestions(final Model model, final CommandArgument commandArgument) {
        CollectionUtil.requireAllNonNull(model, commandArgument);

        final Prefix prefix = commandArgument.getPrefix();
        assert prefix.equals(CliSyntax.PREFIX_GROUPNAME) || prefix.equals(CliSyntax.PREFIX_EDIT);

        final String groupNameInput = commandArgument.getValue();
        return model.groupSuggester(groupNameInput);
    }
    /**
     * Gets a list of valid locations contained within the {@link Model} that match {@link CommandArgument#getValue()}.
     * Requires the {@code commandArgument} to be of type {@link CliSyntax#PREFIX_GROUPNAME}.
     *
     * @param model           The {@link Model} containing the {@link seedu.address.model.group.Group}s to look through.
     * @param commandArgument The {@link CommandArgument} of type {@link CliSyntax#PREFIX_GROUPNAME} containing the name
     *                        to search for.
     * @return A list of group names that match the search string (from {@link CommandArgument#getValue()}).
     */
    static List<String> getValidLocationSuggestions(final Model model, final CommandArgument commandArgument) {
        CollectionUtil.requireAllNonNull(model, commandArgument);

        final Prefix prefix = commandArgument.getPrefix();
        assert prefix.equals(CliSyntax.PREFIX_LOCATIONS) || prefix.equals((CliSyntax.PREFIX_TIMING));

        final String locationInput = commandArgument.getValue();
        return model.validLocationSuggester(locationInput);
    }

    /**
     * Gets the {@link Person} instance that was selected by the user based on the name they keyed in.
     *
     * @param model           The {@link Model} containing the {@link Person}s to look through.
     * @param commandArgument The {@link CommandArgument} of type {@link CliSyntax#PREFIX_NAME} or
     *                        {@link CliSyntax#PREFIX_EDIT} containing the name to search for.
     * @return The {@link Person} instance that was selected by the user.
     */
    static Optional<Person> getSelectedPerson(final Model model, final CommandArgument commandArgument) {
        CollectionUtil.requireAllNonNull(model, commandArgument);

        final Prefix prefix = commandArgument.getPrefix();
        assert prefix.equals(CliSyntax.PREFIX_NAME) || prefix.equals(CliSyntax.PREFIX_EDIT);

        final String personNameInput = commandArgument.getValue();
        final Name personName = new Name(personNameInput);
        Person person;
        try {
            person = model.findPerson(personName);
        } catch (PersonNotFoundException e) {
            person = null;
        }

        return Optional.ofNullable(person);
    }

    /**
     * Gets the {@link Person} instance that was selected by the user based on the value in the first
     * {@code prefixType}.
     *
     * @param model      The {@link Model} containing the {@link Person}s to look through.
     * @param arguments  The {@link ArgumentList} representing all the arguments/{@link Prefix}es and their values.
     * @param prefixType Either {@link CliSyntax#PREFIX_NAME} or {@link CliSyntax#PREFIX_EDIT}.
     * @return The {@link Person} instance that was selected by the user.
     */
    static Optional<Person> getSelectedPerson(final Model model, final ArgumentList arguments,
                                              final Prefix prefixType) {
        CollectionUtil.requireAllNonNull(model, arguments, prefixType);
        assert prefixType.equals(CliSyntax.PREFIX_NAME) || prefixType.equals(CliSyntax.PREFIX_EDIT);

        return arguments.getFirstOfPrefix(prefixType).flatMap(commandArgument -> {
            return getSelectedPerson(model, commandArgument);
        });
    }

    /**
     * Gets the {@link Person} instance that was selected by the user based on the value in the first
     * {@link CliSyntax#PREFIX_NAME}.
     *
     * @param model     The {@link Model} containing the {@link Person}s to look through.
     * @param arguments The {@link ArgumentList} representing all the arguments/{@link Prefix}es and their values.
     * @return The {@link Person} instance that was selected by the user.
     */
    static Optional<Person> getSelectedPerson(final Model model, final ArgumentList arguments) {
        CollectionUtil.requireAllNonNull(model, arguments);

        return getSelectedPerson(model, arguments, CliSyntax.PREFIX_NAME);
    }

    /**
     * Gets the {@link Group} instance that matches the given {@code groupName}.
     *
     * @param model     The {@link Model} containing the {@link Person}s to look through.
     * @param groupName The exact name of the group to find.
     * @return The {@link Group} instance that matches the given {@code groupName}.
     */
    static Optional<Group> getGroupByName(final Model model, final String groupName) {
        CollectionUtil.requireAllNonNull(model, groupName);
        Group group;
        final GroupName groupNameObj = new GroupName(groupName);

        try {
            group = model.findGroup(groupNameObj);
        } catch (GroupNotFoundException e) {
            group = null;
        }

        return Optional.ofNullable(group);
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
