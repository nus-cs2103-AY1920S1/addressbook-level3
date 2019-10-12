package seedu.exercise.model;

import javafx.collections.ObservableList;
import seedu.exercise.model.regime.Regime;

/**
 * Unmodifiable view of an exercise book
 */
public interface ReadOnlyRegimeBook {

    /**
     * Returns an unmodifiable view of the regimes list.
     * This list will not contain any duplicate regimes.
     */
    ObservableList<Regime> getRegimeList();

}
