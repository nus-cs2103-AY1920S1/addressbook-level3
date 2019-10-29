package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Class to keep track of all commands entered during a session when StudyBuddy application is open.
 */
public class CommandHistory {

    private static ArrayList<Command> commandHistory = new ArrayList<>();

    /**
     * Adds a command to the list commandHistory.
     * @param commandToAdd Command to be added.
     */
    public void addCommand(Command commandToAdd) {
        this.commandHistory.add(commandToAdd);
    }

    /**
     * Returns the last used command from the list commandHistory.
     * @return Optional.empty() is no last command found (size of list = 0). Otherwise returns the last used Command.
     */
    public static Optional<Command> getLastCommand() {
        int size = commandHistory.size();
        if (commandHistory.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(commandHistory.get(size - 1));
        }
    }

}
