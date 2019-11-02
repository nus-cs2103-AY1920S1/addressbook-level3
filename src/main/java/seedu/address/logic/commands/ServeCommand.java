package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NO_SUCH_BORROWER_ID;
import static seedu.address.commons.core.Messages.MESSAGE_STILL_IN_SERVE_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BORROWER_ID;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;

/**
 * Opens a serving session for a borrower and allows the borrower to start borrower book
 */
public class ServeCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "serve";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the Serve Mode. "
            + "Parameters: "
            + PREFIX_BORROWER_ID + "ID\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_BORROWER_ID + "K0001 ";

    public static final String MESSAGE_SUCCESS = "Currently serving Borrower: %1$s\n";

    private final BorrowerId borrowerId;

    /**
     * Creates a ServeCommand to serve a {@code Borrower}.
     *
     * @param borrowerId id of {@code Borrower} we are serving.
     */
    public ServeCommand (BorrowerId borrowerId) {
        requireNonNull(borrowerId);
        this.borrowerId = borrowerId;
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

        if (model.isServeMode()) {
            throw new CommandException(String.format(MESSAGE_STILL_IN_SERVE_MODE,
                    model.getServingBorrower().getName().toString()));
        }

        if (!model.hasBorrowerId(borrowerId)) {
            throw new CommandException(MESSAGE_NO_SUCH_BORROWER_ID);
        }

        model.setServingBorrower(borrowerId);
        Borrower borrower = model.getServingBorrower();

        undoCommand = new DoneCommand();
        redoCommand = this;
        commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, borrower), false,
                false, true, false);

        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ServeCommand // instanceof handles nulls
                && borrowerId.equals(((ServeCommand) other).borrowerId));
    }
}
