package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {
    private static final int MAX_LENGTH = 10;
    private final List<String> commandHistory = new ArrayList<>();
    private int currentCommandIndex = -1;

    private void addCommand(String command) {
        if (commandHistory.size() != 0
                && commandHistory.get(commandHistory.size() - 1).equals(command)) {
            return;
        }
        if (commandHistory.size() == MAX_LENGTH) {
            commandHistory.remove(0);
            currentCommandIndex--;
        }
        commandHistory.add(command);
        currentCommandIndex++;
    }

    public void storeInvalidCommand(String command) {
        this.addCommand(command);
        currentCommandIndex = commandHistory.size() - 1;
    }

    public void storeValidCommand(String command) {
        this.addCommand(command);
        currentCommandIndex = commandHistory.size();
    }

    public String getPrev() {
        // Return null if reached the start of command history
        if (currentCommandIndex <= 0) {
            return null;
        }
        currentCommandIndex--;
        System.out.println(this.toString());
        return commandHistory.get(currentCommandIndex);
    }

    public String getNext() {
        // Return null if reached the end of command history
        if (currentCommandIndex >= commandHistory.size() - 1) {
            return null;
        }
        currentCommandIndex++;
        System.out.println(this.toString());
        return commandHistory.get(currentCommandIndex);
    }

    @Override
    public String toString() {
        StringBuilder commandHistoryToString = new StringBuilder("Command History: \n");
        commandHistory.forEach(
                (String command) -> commandHistoryToString.append(command).append("\n"));
        return commandHistoryToString.toString();
    }
}
