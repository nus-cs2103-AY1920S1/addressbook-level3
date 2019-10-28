package seedu.algobase.model.commandhistory;

/**
 * Represents a command history in AlgoBase.
 */
public class CommandHistory {
    private final String commandText;

    public CommandHistory(String commandText) {
        this.commandText = commandText;
    }

    public String getCommandText() {
        return commandText;
    }
}
