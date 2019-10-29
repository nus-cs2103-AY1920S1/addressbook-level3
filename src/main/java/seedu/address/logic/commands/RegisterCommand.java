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
public class RegisterCommand extends Command implements ReversibleCommand {

    public static final String COMMAND_WORD = "register";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Register a new borrower to the library \n"
            + "records. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE NUMBER \n"
            + PREFIX_EMAIL + "EMAIL\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "matt "
            + PREFIX_PHONE + "83938249 \n"
            + PREFIX_EMAIL + "matt@damon.com ";

    public static final String MESSAGE_SUCCESS = "New borrower added: %1$s";

    private final Borrower toAdd;
    private final boolean isUndoRedo;
    private Command undoCommand;
    private Command redoCommand;

    /**
     * Creates a RegisterCommand to add the specified {@code Borrower}
     *
     * @param borrower to be registered.
     */
    public RegisterCommand(Borrower borrower) {
        requireNonNull(borrower);
        toAdd = borrower;
        this.isUndoRedo = false;
    }

    /**
     * Creates a RegisterCommand to add the specified {@code Borrower}
     *
     * @param borrower to be registered.
     * @param isUndoRedo used to check whether the RegisterCommand is an undo/redo command.
     */
    public RegisterCommand(Borrower borrower, boolean isUndoRedo) {
        requireNonNull(borrower);
        toAdd = borrower;
        this.isUndoRedo = isUndoRedo;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasBorrower(toAdd)) {
            // borrowers with same name and (phone number or email)
            model.resetGenerator();
            throw new CommandException(MESSAGE_DUPLICATE_BORROWER);
        }

        undoCommand = new UnregisterCommand(toAdd.getBorrowerId());
        redoCommand = new RegisterCommand(toAdd, true);

        model.registerBorrower(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.toFullString()));
    }

    @Override
    public Command getUndoCommand() {
        return undoCommand;
    }

    @Override
    public Command getRedoCommand() {
        return redoCommand;
    }

    @Override
    public boolean isUndoRedoCommand() {
        return isUndoRedo;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RegisterCommand // instanceof handles nulls
                && toAdd.equals(((RegisterCommand) other).toAdd))
                && isUndoRedo == ((RegisterCommand) other).isUndoRedo;
    }

}
