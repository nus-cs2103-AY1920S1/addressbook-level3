package seedu.address.logic.commands.note;

/**
 * Lists the summary of notes in note list.
 */
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists the summary of notes in note list.
 */
public class NoteListCommand extends NoteCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + " list: List of notes";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(model.getNoteSummary());
    }
}
