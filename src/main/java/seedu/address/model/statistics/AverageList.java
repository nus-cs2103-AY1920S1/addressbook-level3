package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class represents a list of averages.
 */
public class AverageList {
    private final ObservableList<Average> internalList = FXCollections.observableArrayList();
    private final ObservableList<Average> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds an average value to the list.
     */
    public void add(Average toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }
}
