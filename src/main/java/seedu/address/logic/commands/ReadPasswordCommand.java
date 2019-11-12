package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.password.Password;


/**
 * Reads a password identified using it's displayed index from the password book.
 */
public class ReadPasswordCommand extends Command {
    public static final String COMMAND_WORD = "read";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : Opens and accesses the password identified by "
            + "the index number used in the display list. \n"
            + "Parameters: INDEX (must be positive integer)";

    public static final String MESSAGE_SUCCESS = "Results are shown on the right panel. "
            + "Use Copy command to copy password | username | website. \n"
            + CopyPasswordCommand.COMMAND_WORD + "/" + CopyPasswordCommand.COMMAND_WORD1
            + " [" + CopyPasswordValueCommand.COMMAND_WORD + "/" + CopyPasswordValueCommand.COMMAND_WORD1
            + "] / [" + CopyWebsiteCommand.COMMAND_WORD + "/" + CopyWebsiteCommand.COMMAND_WORD1
            + "] / [" + CopyUsernameCommand.COMMAND_WORD + "/" + CopyUsernameCommand.COMMAND_WORD1
            + "]" + " INDEX \n"
            + "Example: " + CopyPasswordCommand.COMMAND_WORD1 + " " + CopyPasswordValueCommand.COMMAND_WORD1 + " 1";

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
        Password passwordToRead = lastShownList.get(targetIndex.getZeroBased());
        passwordToRead.updateExpiry();
        return CommandResult.builder(MESSAGE_SUCCESS)
                .read()
                .setObject(passwordToRead)
                .setIndex(targetIndex)
                .build();
    }
}
