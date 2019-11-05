package seedu.address.model.commanditem;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.commanditem.exceptions.DuplicateCommandException;
import seedu.address.model.help.exceptions.CommandNotFoundException;

/**
 * A list of CommandItems that enforces uniqueness between its elements and does not allow nulls.
 * A commanditem is considered unique by comparing using
 * {@code commanditem#isSameCommand(commanditem)}. As such, adding and updating of
 * commanditem uses commanditem#isSameCommand(commanditem) for equality
 * so as to ensure that the commanditem being added or updated is
 * unique in terms of identity in the UniqueCommandItemsList. However,
 * the removal of a commanditem uses commanditem#equals(Object) so
 * as to ensure that the earning with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see CommandItem #isSameCommand(commanditem)
 */
public class UniqueCommandItemsList implements Iterable<CommandItem> {

    private final ObservableList<CommandItem> internalList = FXCollections.observableArrayList();
    private final ObservableList<CommandItem> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent commanditem as the given argument.
     */
    public boolean contains(CommandItem toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCommand);
    }

    /**
     * Adds a commanditem to the list.
     * The commanditem must not already exist in the list.
     */
    public void add(CommandItem toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCommandException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the commanditem {@code target} in the list with {@code editedCommand}.
     * {@code target} must exist in the list.
     * The commanditem identity of {@code editCommand} must not be the same as another
     * existing commanditem in the list.
     */
    public void setCommand(CommandItem target, CommandItem editedCommand) {
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

    public void setCommands(UniqueCommandItemsList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code commands}.
     * {@code commands} must not contain duplicate CommandObjects.
     */
    public void setCommands(List<CommandItem> commands) {
        requireAllNonNull(commands);
        if (!commandsAreUnique(commands)) {
            throw new DuplicateCommandException();
        }

        internalList.setAll(commands);
    }

    /**
     * Removes the equivalent CommandObject from the list.
     * The commanditem must exist in the list.
     */
    public void remove(CommandItem toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CommandNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<CommandItem> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<CommandItem> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCommandItemsList // instanceof handles nulls
                && internalList.equals(((UniqueCommandItemsList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code commands} contains only unique CommandItems.
     */
    private boolean commandsAreUnique(List<CommandItem> commands) {
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
