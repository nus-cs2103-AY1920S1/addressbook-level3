package seedu.address.person.logic.commands;

import java.io.IOException;

import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.person.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     * @throws java.io.IOException If an error occurs when writing into transaction text file.
     */
    //public abstract CommandResult execute(Model model) throws CommandException;
    public abstract CommandResult execute(Model model, seedu.address.transaction.logic.Logic transactionLogic,
                                          seedu.address.reimbursement.logic.Logic reimbursementLogic)
            throws CommandException, IOException;

}
