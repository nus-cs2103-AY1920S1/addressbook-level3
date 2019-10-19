package seedu.address.model;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

/**
 * Used to store a list of previously entered commands.
 * Used in the command history listing, and also used to determine which stateful list to undo/redo
 * in the address book with state list.
 */
public class CommandHistory {

    private final ObservableList<Pair<String, String>> userInputHistory = FXCollections.observableArrayList();

    public CommandHistory() {}

    /**
     * Adds a triplet of {@code objectType, @code CommandWord, @code userInput}
     * in the command history.
     */
    public void addCommand(String commandWord, String userInput) {
        requireNonNull(commandWord);
        requireNonNull(userInput);
        userInputHistory.add(new Pair<>(commandWord, userInput));
    }

    /**
     * Gets the history of previously entered commands.
     */
    public ObservableList<Pair<String, String>> getHistory() {
        ObservableList<Pair<String, String>> reversedList = FXCollections.observableList(userInputHistory);
        FXCollections.reverse(reversedList);
        return FXCollections.unmodifiableObservableList(reversedList);
    }

    @Override
    public boolean equals(Object obj) {
        // if same object
        if (obj == this) {
            return true;
        }

        // if different kind of objects
        if (!(obj instanceof CommandHistory)) {
            return false;
        }

        CommandHistory other = (CommandHistory) obj;
        return userInputHistory.equals(other.userInputHistory);
    }
}
