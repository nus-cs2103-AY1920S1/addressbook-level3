package seedu.tarence.model.tutorial;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tarence.model.tutorial.exeptions.DuplicateTutorialException;
import seedu.tarence.model.tutorial.exeptions.TutorialNotFoundException;

/**
 * Represents a list of Tutorials.
 */
public class UniqueTutorialList implements Iterable<Tutorial> {

    private final ObservableList<Tutorial> internalList = FXCollections.observableArrayList();


    /**
     * Returns true if the list contains an equivalent tutorial with the given argument.
     */
    public boolean contains(Tutorial toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTutorial);
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


}
