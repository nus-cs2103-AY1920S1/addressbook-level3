package seedu.address.model.competition;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.competition.exceptions.CompetitionNotFoundException;
import seedu.address.model.competition.exceptions.DuplicateCompetitionException;

/**
 * A list of competitions that enforces uniqueness between its elements and does not allow nulls.
 * A competition is considered unique by comparing using {@code Competition#equals(Competition)}.
 *
 * Supports a minimal set of list operations.
 *
 * @see Competition#equals(Object)
 */
public class UniqueCompetitionList implements Iterable<Competition> {
    private final ObservableList<Competition> internalList = FXCollections.observableArrayList();
    private final ObservableList<Competition> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent competition as the given argument.
     */
    public boolean contains(Competition toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a competition to the list.
     * The competition must not already exist in the list.
     */
    public void add(Competition toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCompetitionException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the competition {@code target} in the list with {@code editedCompetition}.
     * {@code target} must exist in the list.
     * The competition identity of {@code editedCompetition} must not be the
     * same as another existing competition in the list.
     */
    public void setCompetition(Competition target, Competition editedCompetition) {
        requireAllNonNull(target, editedCompetition);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CompetitionNotFoundException();
        }

        if (!target.equals(editedCompetition) && contains(editedCompetition)) {
            throw new DuplicateCompetitionException();
        }

        internalList.set(index, editedCompetition);
    }

    /**
     * Removes the equivalent competition from the list.
     * The competition must exist in the list.
     */
    public void remove(Competition toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CompetitionNotFoundException();
        }
    }

    public void setCompetitions(seedu.address.model.competition.UniqueCompetitionList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code competitions}.
     * {@code competitions} must not contain duplicate competitions.
     */
    public void setCompetitions(List<Competition> competitions) {
        requireAllNonNull(competitions);
        if (!competitionsAreUnique(competitions)) {
            throw new DuplicateCompetitionException();
        }

        internalList.setAll(competitions);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Competition> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Competition> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.competition.UniqueCompetitionList // instanceof handles nulls
                && internalList.equals(((seedu.address.model.competition.UniqueCompetitionList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code competitions} contains only unique competitions.
     */
    private boolean competitionsAreUnique(List<Competition> competitions) {
        for (int i = 0; i < competitions.size() - 1; i++) {
            for (int j = i + 1; j < competitions.size(); j++) {
                if (competitions.get(i).equals(competitions.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
