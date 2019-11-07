package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Redos the state of the address book to a previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "A redo has been performed!";
    public static final String REDO_NOT_POSSIBLE = "Nothing to redo!";

    private static final Logger logger = LogsCenter.getLogger(RedoCommand.class);

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoAddressBook()) {
            logger.severe("Redoing even when not possible!");
            throw new CommandException(REDO_NOT_POSSIBLE);
        }

        model.redoAddressBook();
        logger.fine("Performed redo");
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
