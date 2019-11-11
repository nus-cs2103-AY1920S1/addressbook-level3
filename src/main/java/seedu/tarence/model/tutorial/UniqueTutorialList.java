package seedu.tarence.model.tutorial;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tarence.model.tutorial.exceptions.DuplicateTutorialException;
import seedu.tarence.model.tutorial.exceptions.TutorialNotFoundException;

/**
 * Represents a list of Tutorials.
 */
public class UniqueTutorialList implements Iterable<Tutorial> {

    private final ObservableList<Tutorial> internalList = FXCollections.observableArrayList();
    private final ObservableList<Tutorial> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent tutorial with the given argument.
     */
    public boolean contains(Tutorial toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTutorial);
    }

    /**
     * Returns true if the list contains an equivalent tutorial with the given argument.
     */
    public boolean containsTutName(TutName tutName) {
        requireNonNull(tutName);
        return internalList.stream().anyMatch(tutorial -> tutorial.getTutName().equals(tutName));
    }

    /**
     * Adds a Tutorial to the list.
     * The Tutorial must not already exist in the list.
     */
    public void add(Tutorial newTutorial) {
        requireNonNull(newTutorial);
        if (contains(newTutorial)) {
            throw new DuplicateTutorialException();
        }
        internalList.add(newTutorial);
    }

    /**
     * Replaces the tutorial {@code target} in the list with {@code editedTutorial}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedTutorial} must not be the same as another existing tutorial in the list.
     */
    public void setTutorial(Tutorial target, Tutorial editedTutorial) {
        requireAllNonNull(target, editedTutorial);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TutorialNotFoundException();
        }

        if (!target.isSameTutorial(editedTutorial) && contains(editedTutorial)) {
            throw new DuplicateTutorialException();
        }

        internalList.set(index, editedTutorial);
    }

    public void setTutorials(UniqueTutorialList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code tutorials}.
     * {@code tutorials} must not contain duplicate tutorials.
     */
    public void setTutorials(List<Tutorial> tutorials) {
        requireAllNonNull(tutorials);
        if (!tutorialsAreUnique(tutorials)) {
            throw new DuplicateTutorialException();
        }

        internalList.setAll(tutorials);
    }

    /**
     * Returns the {@code Tutorial} of the given {@code TutName}, or {@code Optional<Empty>} if it does not exist.
     */
    public Optional<Tutorial> getTutorialByName(TutName tutName) {
        requireNonNull(tutName);
        for (Tutorial tutorial : internalList) {
            if (tutorial.getTutName().equals(tutName)) {
                return Optional.of(tutorial);
            }
        }
        return Optional.empty();
    }

    /**
     * Removes the equivalent Tutorial from the list.
     * The Tutorial must exist in the list.
     */
    public void remove(Tutorial toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TutorialNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Tutorial> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Tutorial> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTutorialList // instanceof handles nulls
                && internalList.equals(((UniqueTutorialList) other).internalList));
    }

    /**
     * Returns true if {@code tutorials} contains only unique tutorials.
     */
    private boolean tutorialsAreUnique(List<Tutorial> tutorials) {
        for (int i = 0; i < tutorials.size() - 1; i++) {
            for (int j = i + 1; j < tutorials.size(); j++) {
                if (tutorials.get(i).isSameTutorial(tutorials.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
