package seedu.moneygowhere.storage;

/**
 * Represents a storage for user inputted commands.
 */
public interface CommandStorage {

    /**
     * Adds a particular command to storage.
     * @param command The command to be added.
     */
    void addCommand(String command);

    /**
     * Retrieves the previous user inputted command that is stored in storage.
     * @return The previous user input command.
     */
    String getPrevCommand();

    /**
     * Retrieves the next user inputted command that is stored in storage.
     * @return The next user input command.
     */
    String getNextCommand();
}
