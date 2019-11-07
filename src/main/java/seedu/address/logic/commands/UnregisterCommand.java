package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_CANNOT_UNREGISTER_WHILE_SERVING;
import static seedu.address.commons.core.Messages.MESSAGE_CANNOT_UNREGISTER_WITH_BOOKS_ON_LOAN;
import static seedu.address.commons.core.Messages.MESSAGE_NO_SUCH_BORROWER_ID;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;

/**
 * Unregisters a borrower from the borrower record.
 */
public class UnregisterCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "unregister";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unregisters a borrower from the library "
            + "record identified by the borrower ID. \n"
            + "Parameters: BORROWER_ID (Must be a valid borrower ID) \n"
            + "Example: " + COMMAND_WORD + " id/K0001";

    public static final String MESSAGE_SUCCESS = "Borrower unregistered: %1$s";

    private final BorrowerId id;

    /**
     * Creates an UnregisterCommand to add the specified {@code Borrower}
     *
     * @param id of the {@code Borrower} to unregister.
     */
    public UnregisterCommand(BorrowerId id) {
        requireNonNull(id);
        this.id = id;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasBorrowerId(id)) {
            throw new CommandException(MESSAGE_NO_SUCH_BORROWER_ID);
        }

        if (model.isServeMode()) {
            Borrower servingBorrower = model.getServingBorrower();
            if (servingBorrower.getBorrowerId().equals(id)) {
                // Do not allow deleting current serving borrower
                throw new CommandException(
                        String.format(MESSAGE_CANNOT_UNREGISTER_WHILE_SERVING, servingBorrower.getName()));
            }
        }

        Borrower toUnregister = model.getBorrowerFromId(id);
        if (!toUnregister.getCurrentLoanList().isEmpty()) {
            throw new CommandException(
                    String.format(MESSAGE_CANNOT_UNREGISTER_WITH_BOOKS_ON_LOAN, toUnregister.getName()));
        }

        model.unregisterBorrower(toUnregister);

        undoCommand = new RegisterCommand(toUnregister);
        redoCommand = this;
        commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, toUnregister));

        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnregisterCommand // instanceof handles nulls
                && id.equals(((UnregisterCommand) other).id));
    }
}
