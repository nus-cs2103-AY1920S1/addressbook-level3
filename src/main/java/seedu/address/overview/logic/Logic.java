package seedu.address.overview.logic;

import seedu.address.overview.commands.CommandResult;
import seedu.address.overview.model.Transaction;
import seedu.address.overview.util.TransactionList;

import java.io.IOException;

/**
 * API of the Logic component
 */
public interface Logic {

    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws Exception If an error occurs during command execution.
     */
    CommandResult execute(String commandText) throws Exception;

    /**
     * Writes the overview information to file.
     * @throws Exception If an error occurs when writing into the text file.
     */
    void writeToOverviewFile() throws IOException;

}
