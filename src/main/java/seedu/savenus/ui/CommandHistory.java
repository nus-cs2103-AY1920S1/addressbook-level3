package seedu.savenus.ui;

import java.util.ArrayList;
import java.util.List;

/***
 * Class to store and retrieve command history.
 */
public class CommandHistory {
    private static final int MAX_LENGTH = 10;
    private final List<String> commandHistory = new ArrayList<>();
    private int currentCommandIndex = -1;

    /**
     * Add Command to history.
     * @param command Command to be added to history
     */
    private void addCommandToHistory(String command) {
        // Disregard duplicate commands
        if (commandHistory.size() != 0
                && commandHistory.get(commandHistory.size() - 1).equals(command)) {
            return;
        }
        // Shift command list to accommodate new command
        if (commandHistory.size() == MAX_LENGTH) {
            commandHistory.remove(0);
            currentCommandIndex--;
        }
        commandHistory.add(command);
        currentCommandIndex++;
    }

    /**
     * Store invalid command.
     * @param command Command to be stored
     */
    public void storeInvalidCommand(String command) {
        this.addCommandToHistory(command);
        currentCommandIndex = commandHistory.size() - 1;
    }

    /**
     * Store valid command.
     * @param command Command to be stored
     */
    public void storeValidCommand(String command) {
        this.addCommandToHistory(command);
        currentCommandIndex = commandHistory.size();
    }

    /**
     * Get previously stored command.
     */
    public String getPrev() {
        // Return null if reached the start of command history
        if (currentCommandIndex <= 0) {
            return null;
        }
        currentCommandIndex--;
        return commandHistory.get(currentCommandIndex);
    }

    /**
     * Get following stored command.
     */
    public String getNext() {
        // Return null if reached the end of command history
        if (currentCommandIndex >= commandHistory.size() - 1) {
            return null;
        }
        currentCommandIndex++;
        return commandHistory.get(currentCommandIndex);
    }

    @Override
    public String toString() {
        StringBuilder commandHistoryToString = new StringBuilder("Command History: \n");
        commandHistory.forEach((String command) -> commandHistoryToString.append(command).append("\n"));
        return commandHistoryToString.toString();
    }
}
