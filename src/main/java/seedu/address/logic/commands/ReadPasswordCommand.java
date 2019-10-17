package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.password.Password;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Reads a password identified using it's displayed index from the password book.
 */
public class ReadPasswordCommand extends Command {
    public static final String COMMAND_WORD = "read";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : Opens and accesses the password identified by "
            + "the index number used in the display list. \n"
            + "Parameters: INDEX (must be positive integer)"
            + "Some example ...";

    public static final String MESSAGE_SUCCESS = "%1$s";

    private final Index targetIndex;

    public ReadPasswordCommand(Index index) {
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Password> lastShownList = model.getFilteredPasswordList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PASSWORD_DISPLAYED_INDEX);
        }

        Password passwordToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePassword(passwordToDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, passwordToDelete.toNonAsterixString()));

    }
}
