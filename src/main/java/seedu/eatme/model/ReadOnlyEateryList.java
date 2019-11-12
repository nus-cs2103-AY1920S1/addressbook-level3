package seedu.eatme.model;

import javafx.collections.ObservableList;
import seedu.eatme.model.eatery.Eatery;

/**
 * Unmodifiable view of an eatery list
 */
public interface ReadOnlyEateryList {

    /**
     * Returns an unmodifiable view of the eatery list.
     * This list will not contain any duplicate eateries.
     */
    ObservableList<Eatery> getEateryList();

    /**
     * Returns an unmodifiable view of the todos list.
     * This list will not contain any duplicate todos.
     */
    ObservableList<Eatery> getTodoList();
}
