package seedu.jarvis.model.cca.ccaprogress;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.cca.exceptions.CcaMilestoneNotFoundException;
import seedu.jarvis.model.cca.exceptions.DuplicateCcaMilestoneException;



/**
 * A list of cca progress milestones that the user can set and increment.
 *
 * Supports a minimal set of list operations.
 */
public class CcaMilestoneList implements Iterable<CcaMilestone> {

    private final ObservableList<CcaMilestone> internalList = FXCollections.observableArrayList();
    private final ObservableList<CcaMilestone> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Gets the {@code CcaMilestone} at the specified index.
     */
    public CcaMilestone getCcaMilestone(Index index) {
        requireNonNull(index);
        return internalList.get(index.getZeroBased());
    }

    /**
     * Return the number of milestones in the CcaMilestoneList as an int.
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Returns true if the list contains an equivalent milestone as the given argument.
     */
    public boolean contains(CcaMilestone toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCcaMilestone);
    }

    /**
     * Adds a CcaMilestone to the list.
     * The CcaMilestone must not already exist in the list.
     */
    public void add(CcaMilestone toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCcaMilestoneException();
        }
        internalList.add(toAdd);
    }

    /**
     * Adds {@code CcaMilestone} at a given {@code Index}.
     *
     * @param zeroBasedIndex Zero-based index to add {@code CcaMilestone} to.
     * @param toAdd {@code CcaMilestone} to be added.
     */
    public void add(int zeroBasedIndex, CcaMilestone toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCcaMilestoneException();
        }
        internalList.add(zeroBasedIndex, toAdd);
    }

    /**
     * Replaces the CcaMilestone {@code target} in the list with {@code editedCcaMilestone}.
     * {@code target} must exist in the list.
     * The CcaMilestone identity of {@code editedCcaMilestone} must not be the same as another existing CcaMilestone in
     * the list.
     */
    public void setCcaMilestone(CcaMilestone target, CcaMilestone editedCcaMilestone) {
        requireAllNonNull(target, editedCcaMilestone);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CcaMilestoneNotFoundException();
        }

        if (!target.isSameCcaMilestone(editedCcaMilestone) && contains(editedCcaMilestone)) {
            throw new DuplicateCcaMilestoneException();
        }

        internalList.set(index, editedCcaMilestone);
    }

    /**
     * Removes the equivalent CcaMilestone from the list.
     * The cca milstone must exist in the list.
     */
    public void remove(CcaMilestone toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CcaMilestoneNotFoundException();
        }
    }

    public void setMilestones(CcaMilestoneList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code milestones}.
     * {@code milestones} must not contain duplicate milestones.
     */
    public void setMilestones(List<CcaMilestone> milestones) {
        requireAllNonNull(milestones);
        if (!milestonesAreUnique(milestones)) {
            throw new DuplicateCcaMilestoneException();
        }

        internalList.setAll(milestones);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<CcaMilestone> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns true if there are no {@code Milestones} in the list.
     */
    public boolean isEmpty() {
        return internalList.size() == 0;
    }

    @Override
    public Iterator<CcaMilestone> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CcaMilestoneList // instanceof handles nulls
                && internalList.equals(((CcaMilestoneList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the milestones in the milestone list: ");
        for (CcaMilestone ccaMilestone : internalList) {
            sb.append(ccaMilestone);
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns true if {@code milestones} contains only unique milestones.
     */
    private boolean milestonesAreUnique(List<CcaMilestone> milestones) {
        for (int i = 0; i < milestones.size() - 1; i++) {
            for (int j = i + 1; j < milestones.size(); j++) {
                if (milestones.get(i).isSameCcaMilestone(milestones.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
