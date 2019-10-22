package mams.logic;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Class that stores the history of all user inputs into MAMS */
public class CommandHistory {
    private final ObservableList<String> inputHistory = FXCollections.observableArrayList();
    private final ObservableList<String> unmodifiableInputHistory =
            FXCollections.unmodifiableObservableList(inputHistory);


    /**
     * Adds the entered input text from the user into a list.
     */
    public void add(String input) {
        requireNonNull(input);
        inputHistory.add(input);
    }

    /**
     * Returns an unmodifiable view of {@code inputHistory}
     */
    public ObservableList<String> getInputHistory() {
        return unmodifiableInputHistory;
    }

    @Override
    public int hashCode() {
        return inputHistory.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof CommandHistory)) {
            return false;
        }

        // state check
        CommandHistory other = (CommandHistory) obj;
        return inputHistory.equals(other.inputHistory);
    }
}
