package seedu.address.logic.commands.note;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class NoteListCommand extends NoteCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + " list: List of notes";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(model.getNoteList());
    }
}
