package seedu.address.model.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.commands.commandsexceptions.CommandNotFoundException;
import seedu.address.model.earnings.earningsexception.DuplicateEarningsException;
import seedu.address.model.earnings.earningsexception.EarningsNotFoundException;

/**
 * A list of earnings that enforces uniqueness between its elements and does not allow nulls.
 * An earnings is considered unique by comparing using
 * {@code Earnings#isSameEarnings(Earnings)}. As such, adding and updating of
 * earnings uses Earnings#isSameEarnings(Earnings) for equality
 * so as to ensure that the earnings being added or updated is
 * unique in terms of identity in the UniqueEarningsList. However,
 * the removal of a person uses Earnings#equals(Object) so
 * as to ensure that the earning with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Earnings#isSameEarnings(CommandObject)
 */
public class UniqueCommandsList implements Iterable<CommandObject> {

    private final ObservableList<CommandObject> internalList = FXCollections.observableArrayList();
    private final ObservableList<CommandObject> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(CommandObject toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCommand);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(CommandObject toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEarningsException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setCommands(CommandObject target, CommandObject editedCommand) {
        requireAllNonNull(target, editedCommand);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EarningsNotFoundException();
        }

        if (!target.isSameCommand(editedCommand) && contains(editedCommand)) {
            throw new DuplicateEarningsException();
        }

        internalList.set(index, editedCommand);
    }

    public void setCommands(UniqueCommandsList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setCommands(List<CommandObject> commands) {
        requireAllNonNull(commands);
        if (!commandsAreUnique(commands)) {
            throw new DuplicateEarningsException();
        }

        internalList.setAll(commands);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
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
     * Returns true if {@code persons} contains only unique persons.
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
