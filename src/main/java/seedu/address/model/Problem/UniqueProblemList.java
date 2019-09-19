package seedu.address.model.Problem;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.Problem.exceptions.DuplicateProblemException;
import seedu.address.model.Problem.exceptions.ProblemNotFoundException;

/**
 * A list of problems that enforces uniqueness between its elements and does not allow nulls.
 * A Problem is considered unique by comparing using {@code Problem#isSameProblem(Problem)}. As such, adding and updating of
 * problems uses Problem#isSameProblem(Problem) for equality so as to ensure that the Problem being added or updated is
 * unique in terms of identity in the UniqueProblemList. However, the removal of a Problem uses Problem#equals(Object) so
 * as to ensure that the Problem with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Problem#isSameProblem(Problem)
 */
public class UniqueProblemList implements Iterable<Problem> {

    private final ObservableList<Problem> internalList = FXCollections.observableArrayList();
    private final ObservableList<Problem> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Problem as the given argument.
     */
    public boolean contains(Problem toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameProblem);
    }

    /**
     * Adds a Problem to the list.
     * The Problem must not already exist in the list.
     */
    public void add(Problem toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateProblemException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Problem {@code target} in the list with {@code editedProblem}.
     * {@code target} must exist in the list.
     * The Problem identity of {@code editedProblem} must not be the same as another existing Problem in the list.
     */
    public void setProblem(Problem target, Problem editedProblem) {
        requireAllNonNull(target, editedProblem);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ProblemNotFoundException();
        }

        if (!target.isSameProblem(editedProblem) && contains(editedProblem)) {
            throw new DuplicateProblemException();
        }

        internalList.set(index, editedProblem);
    }

    /**
     * Removes the equivalent Problem from the list.
     * The Problem must exist in the list.
     */
    public void remove(Problem toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ProblemNotFoundException();
        }
    }

    public void setProblems(UniqueProblemList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code problems}.
     * {@code problems} must not contain duplicate problems.
     */
    public void setProblems(List<Problem> problems) {
        requireAllNonNull(problems);
        if (!problemsAreUnique(problems)) {
            throw new DuplicateProblemException();
        }

        internalList.setAll(problems);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Problem> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Problem> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueProblemList // instanceof handles nulls
                        && internalList.equals(((UniqueProblemList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code problems} contains only unique problems.
     */
    private boolean problemsAreUnique(List<Problem> problems) {
        for (int i = 0; i < problems.size() - 1; i++) {
            for (int j = i + 1; j < problems.size(); j++) {
                if (problems.get(i).isSameProblem(problems.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
