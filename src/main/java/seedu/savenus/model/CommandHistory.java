package seedu.savenus.model;

import java.util.ArrayList;
import java.util.List;

/***
 * Singleton Class to store and retrieve command history.
 */
public class CommandHistory {
    private static CommandHistory commandHistory;

    private static final int MAX_LENGTH = 10;
    private final List<String> commandHistoryList = new ArrayList<>();
    private int currentCommandIndex = -1;

    // Prevents instantiation
    private CommandHistory() {}

    public static CommandHistory getInstance() {
        if (commandHistory == null) {
            commandHistory = new CommandHistory();
        }

        return commandHistory;
    }

    /**
     * Add Command to history.
     * @param command Command to be added to history
     */
    private void addCommandToHistory(String command) {
        // Disregard duplicate commands
        if (commandHistoryList.size() != 0
                && commandHistoryList.get(commandHistoryList.size() - 1).equals(command)) {
            return;
        }
        // Shift command list to accommodate new command
        if (commandHistoryList.size() == MAX_LENGTH) {
            commandHistoryList.remove(0);
            currentCommandIndex--;
        }
        commandHistoryList.add(command);
        currentCommandIndex++;
    }

    /**
     * Store invalid command.
     * @param command Command to be stored
     */
    public void storeInvalidCommand(String command) {
        this.addCommandToHistory(command);
        currentCommandIndex = commandHistoryList.size() - 1;
    }

    /**
     * Used to get the index.
     * @return Command index.
     */
    public int getCurrentCommandIndex() {
        return currentCommandIndex;
    }

    /**
     * Used to get the command history.
     * @return List of command history.
     */
    public List<String> getCommandHistory() {
        return commandHistoryList;
    }

    /**
     * Store valid command.
     * @param command Command to be stored
     */
    public void storeValidCommand(String command) {
        this.addCommandToHistory(command);
        currentCommandIndex = commandHistoryList.size();
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
        return commandHistoryList.get(currentCommandIndex);
    }

    /**
     * Get following stored command.
     */
    public String getNext() {
        // Return null if reached the end of command history
        if (currentCommandIndex >= commandHistoryList.size() - 1) {
            return null;
        }
        currentCommandIndex++;
        return commandHistoryList.get(currentCommandIndex);
    }

    @Override
    public String toString() {
        StringBuilder commandHistoryListToString = new StringBuilder("Command History: \n");
        commandHistoryList.forEach((String command) -> commandHistoryListToString.append(command).append("\n"));
        return commandHistoryListToString.toString();
    }
}
