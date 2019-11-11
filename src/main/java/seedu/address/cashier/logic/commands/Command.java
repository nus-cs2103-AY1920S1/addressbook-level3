package seedu.address.cashier.logic.commands;

import seedu.address.util.CommandResult;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on
     * @param personModel which the command uses to add the cashier-in-charge
     * @return feedback message of the operation result for display
     * @throws Exception If an error occurs during command execution.
     */
    public abstract CommandResult execute(seedu.address.cashier.model.Model model,
                                          seedu.address.person.model.CheckAndGetPersonByNameModel personModel)
            throws Exception;
}

