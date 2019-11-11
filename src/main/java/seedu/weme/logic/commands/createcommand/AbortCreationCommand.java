package seedu.weme.logic.commands.createcommand;

import static java.util.Objects.requireNonNull;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.model.Model;
import seedu.weme.model.ModelContext;

/**
 * Aborts the current meme creation session.
 */
public class AbortCreationCommand extends Command {

    public static final String COMMAND_WORD = "abort";
    public static final String MESSAGE_SUCCESS = "Meme creation aborted.";
    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD
            + ": abort current meme creation session and return to templates tab.";
    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION;

    public AbortCreationCommand() {
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.abortMemeCreation();
        model.setContext(ModelContext.CONTEXT_TEMPLATES);
        CommandResult result = new CommandResult(MESSAGE_SUCCESS);
        model.commitWeme(result.getFeedbackToUser());
        return result;
    }

}
