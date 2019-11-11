package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_BORROWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.borrower.Borrower;

/**
 * Registers a borrower to the library records.
 */
public class RegisterCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "register";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Register a new borrower to the library records.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE NUMBER "
            + PREFIX_EMAIL + "EMAIL\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "matt "
            + PREFIX_PHONE + "83938249 "
            + PREFIX_EMAIL + "matt@damon.com ";

    public static final String MESSAGE_SUCCESS = "New borrower added: %1$s";

    private final Borrower toAdd;

    /**
     * Creates a RegisterCommand to add the specified {@code Borrower}
     *
     * @param borrower to be registered.
     */
    public RegisterCommand(Borrower borrower) {
        requireNonNull(borrower);
        toAdd = borrower;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasBorrower(toAdd)) {
            // borrowers with same name and (phone number or email)
            model.resetGenerator();
            throw new CommandException(MESSAGE_DUPLICATE_BORROWER);
        }

        model.registerBorrower(toAdd);

        undoCommand = new UnregisterCommand(toAdd.getBorrowerId());
        redoCommand = this;
        commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RegisterCommand // instanceof handles nulls
                && toAdd.equals(((RegisterCommand) other).toAdd));
    }

}
