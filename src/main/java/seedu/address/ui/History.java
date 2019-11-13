package seedu.address.ui;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;

/**
 * Class to support history feature for UI
 */
public class History {
    private ArrayDeque<String> pastCommands = new ArrayDeque<>();
    private ArrayDeque<String> nextCommands = new ArrayDeque<>();

    /**
     * Returns next command in
     * sequence of user entered commands.
     * @return String
     * @throws NoSuchElementException
     */
    public String getNextCommand() throws NoSuchElementException {
        String nextCommand = nextCommands.pop();
        pastCommands.push(nextCommand);
        return nextCommand;
    }

    /**
     * Returns previous command in
     * sequence of user entered commands.
     * @return String
     * @throws NoSuchElementException
     */
    public String getPastCommand() throws NoSuchElementException {
        String pastCommand = pastCommands.pop();
        nextCommands.push(pastCommand);
        return pastCommand;
    }

    /**
     * Sends command to past command
     * while maintaining order of commands.
     * @param command
     */
    public void sendToHistory(String command) {
        while (!nextCommands.isEmpty()) {
            pastCommands.push(nextCommands.pop());
        }
        pastCommands.push(command);
    }
}
