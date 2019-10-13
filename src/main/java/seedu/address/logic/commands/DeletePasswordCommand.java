package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.password.Password;

/**
 * Deletes a password identified using it's displayed index from the password book.
 */
public class DeletePasswordCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "Deletes a password identified by the index number from the existing password book"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Some example ...";
    public static final String MESSAGE_SUCCESS = "Deleted Password: %1$s";

    private final Index targetIndex;

    public DeletePasswordCommand(Index index) {
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
        return new CommandResult(String.format(MESSAGE_SUCCESS, passwordToDelete));
    }
}
