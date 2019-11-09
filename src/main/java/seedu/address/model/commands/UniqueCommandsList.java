package seedu.address.model.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.commands.commandsexceptions.CommandNotFoundException;
import seedu.address.model.commands.commandsexceptions.DuplicateCommandException;

/**
 * A list of CommandObjects that enforces uniqueness between its elements and does not allow nulls.
 * An CommandObject is considered unique by comparing using
 * {@code CommandObject#isSameCommand(CommandObject)}. As such, adding and updating of
 * CommandObject uses CommandObject#isSameCommand(CommandObject) for equality
 * so as to ensure that the CommandObject being added or updated is
 * unique in terms of identity in the UniqueCommandsList. However,
 * the removal of a CommandObject uses CommandObjects#equals(Object) so
 * as to ensure that the earning with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see CommandObject#isSameCommand(CommandObject)
 */
public class UniqueCommandsList implements Iterable<CommandObject> {

    private final ObservableList<CommandObject> internalList = FXCollections.observableArrayList();
    private final ObservableList<CommandObject> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent CommandObject as the given argument.
     */
    public boolean contains(CommandObject toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCommand);
    }

    /**
     * Adds a CommandObject to the list.
     * The CommandObject must not already exist in the list.
     */
    public void add(CommandObject toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCommandException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the CommandObject {@code target} in the list with {@code editedCommand}.
     * {@code target} must exist in the list.
     * The CommandObject identity of {@code editCommand} must not be the same as another
     * existing CommandObject in the list.
     */
    public void setCommands(CommandObject target, CommandObject editedCommand) {
        requireAllNonNull(target, editedCommand);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CommandNotFoundException();
        }

        if (!target.isSameCommand(editedCommand) && contains(editedCommand)) {
            throw new DuplicateCommandException();
        }

        internalList.set(index, editedCommand);
    }

    public void setCommands(UniqueCommandsList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code commands}.
     * {@code commands} must not contain duplicate CommandObjects.
     */
    public void setCommands(List<CommandObject> commands) {
        requireAllNonNull(commands);
        if (!commandsAreUnique(commands)) {
            throw new DuplicateCommandException();
        }

        internalList.setAll(commands);
    }

    /**
     * Removes the equivalent CommandObject from the list.
     * The CommandObject must exist in the list.
     */
    public void remove(CommandObject toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CommandNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<CommandObject> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<CommandObject> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCommandsList // instanceof handles nulls
                && internalList.equals(((UniqueCommandsList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code commands} contains only unique CommandObjects.
     */
    private boolean commandsAreUnique(List<CommandObject> commands) {
        for (int i = 0; i < commands.size() - 1; i++) {
            for (int j = i + 1; j < commands.size(); j++) {
                if (commands.get(i).isSameCommand(commands.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
