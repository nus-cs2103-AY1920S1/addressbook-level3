package seedu.address.logic.commands.note;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Note;

public class NoteDeleteCommand extends NoteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " [index]: Deletes a note\n"
            + "Note: index has to be greater than 0";

    private final Index index;

    /**
     * Creates a QuestionDeleteCommand object.
     *
     * @param index of question to delete from the list.
     */
    public NoteDeleteCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(generateSuccessMessage(model.deleteNote(index)));
    }

    /**
     * Generates a command execution success message.
     *
     * @param note that has been added.
     */
    private String generateSuccessMessage(Note note) {
        return "Deleted note: " + note;
    }
}
