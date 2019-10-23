package seedu.travezy.address.logic.commands;

import seedu.travezy.logic.commands.CommandResult;
import seedu.travezy.logic.commands.exceptions.CommandException;
import seedu.travezy.address.model.AddressBookModel;

/**
 * Represents a command with hidden internal addressBookLogic and the ability to be executed.
 */
public abstract class Command extends seedu.travezy.logic.commands.Command<AddressBookModel> {

    /**
     * Executes the command and returns the result message.
     *
     * @param addressBookModel {@code AddressBookModel} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(AddressBookModel addressBookModel) throws CommandException;

}
