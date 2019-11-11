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
public class CopyPasswordValueCommand extends CopyPasswordCommand {

    public static final String COMMAND_WORD = "password";
    public static final String COMMAND_WORD1 = "p";

    public static final String MESSAGE_SUCCESS = "Copied password to clipboard!";

    private final Index targetIndex;

    public CopyPasswordValueCommand(Index index) {
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
        ClipboardUtil.copyToClipboard(passwordToRead.getNonEncryptedPasswordValue(), null);
        return CommandResult.builder(MESSAGE_SUCCESS)
                .setObject(passwordToRead)
                .read()
                .setIndex(targetIndex)
                .build();
    }
}
