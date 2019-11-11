package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ClipboardUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.password.Password;

/**
 * Copies a password value from the password identified using it's displayed index
 * from the password book.
 */
public class CopyWebsiteCommand extends CopyPasswordCommand {

    public static final String COMMAND_WORD = "website";
    public static final String COMMAND_WORD1 = "w";

    public static final String MESSAGE_SUCCESS = "Copied website to clipboard!";
    public static final String MESSAGE_FAILURE = "There is no website to be copied.";

    private final Index targetIndex;

    public CopyWebsiteCommand(Index index) {
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
        if (passwordToRead.getWebsite().isNilWebsite()) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        ClipboardUtil.copyToClipboard(passwordToRead.getWebsite().value, null);
        return CommandResult.builder(MESSAGE_SUCCESS)
                .setObject(passwordToRead)
                .setIndex(targetIndex)
                .read()
                .build();
    }
}
