package seedu.address.model;

/**
 * The API of the CommandHistory component.
 */
public interface CommandHistory {
    void saveCommandExecutionString(String commandInputString);

    String getPrevCommandString();

    String getNextCommandString();
}
