package seedu.flashcard.model.flashcard;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of flashcards that enforces uniqueness between its elements and does not allow nulls.
 * A flashcard is considered unique by comparing using {@code Flashcard#isSameFlashcard(Flashcard)}. As such, adding and updating of
 * flashcards uses Flashcard#isSameFlashcard(Flashcard) for equality so as to ensure that the flashcard being added or updated is
 * unique in terms of identity in the UniqueFlashcardList. However, the removal of a flashcard uses Flashcard#equals(Object) so
 * as to ensure that the flashcard with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Flashcard#isSameFlashcard(Flashcard)
 */
public class UniqueFlashcardList {

    private final ObservableList<Flashcard> internalList = FXCollections.observableArrayList();
    private final ObservableList<Flashcard> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent flashcard as the given argument
     */
    public boolean contains(Flashcard toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameFlashcard);
    }
}
