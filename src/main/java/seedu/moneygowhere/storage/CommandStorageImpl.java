package seedu.moneygowhere.storage;

import java.util.ArrayList;

/**
 * A storage responsible for storing user input commands.
 */
public class CommandStorageImpl implements CommandStorage {
    private ArrayList<String> commands;
    private int currIndex = 0;

    public CommandStorageImpl() {
        commands = new ArrayList<String>();
    }

    /**
     * Adds a command to the commandStorage.
     * @param command the command to the added.
     */
    public void addCommand(String command) {
        commands.add(command);
        currIndex = commands.size();
    }

    /**
     * Retrieves the previous instruction stored with respect to the current index.
     * if current index is the first element in storage, returns first command.
     * @return the previous command with respect to the current index.
     */
    public String getPrevCommand() {
        if (--currIndex < 0) {
            currIndex = -1;
            return "";
        } else {
            return commands.get(currIndex);
        }
    }

    /**
     * Retrieves the next instruction stored with respect to the current index.
     * if the current index is the last element in storage, returns last command.
     * @return the next command with respect tot eh current index.
     */
    public String getNextCommand() {
        if (++currIndex > commands.size() - 1) {
            currIndex = commands.size();
            return "";
        } else {
            return commands.get(currIndex);
        }
    }
}
