package seedu.address.logic.commands;

import javafx.collections.ObservableList;

/**
 * Represents the result of a command execution.
 */
public class CommandSuggestions {

    private final ObservableList<String> suggestions;

    public CommandSuggestions() {
        this.suggestions = javafx.collections.FXCollections.observableArrayList();
    }
}
