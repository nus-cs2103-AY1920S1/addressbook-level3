package seedu.weme.logic.commands.memecommand;

import static java.util.Objects.requireNonNull;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;

/**
 * Clears all the memes in Weme.
 */
public class MemeClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Memes have been cleared!";
    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD + ": delete all memes in Weme.";
    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION;
    public static final String MESSAGE_NON_EMPTY_STAGING_AREA = "Clear the staging area first before "
            + "executing this command.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.isStagingAreaEmpty()) {
            throw new CommandException(MESSAGE_NON_EMPTY_STAGING_AREA);
        }
        model.clearMemes();
        CommandResult result = new CommandResult(MESSAGE_SUCCESS);
        model.commitWeme(result.getFeedbackToUser());
        return result;
    }
}
