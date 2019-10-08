package seedu.address.logic.commands.note;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.question.QuestionEditCommand;
import seedu.address.model.Model;
import seedu.address.model.note.Note;
import seedu.address.model.question.McqQuestion;
import seedu.address.model.question.OpenEndedQuestion;
import seedu.address.model.question.Question;

import java.util.HashMap;

public class NoteEditCommand extends NoteCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an existing note\n"
            + "Parameters:\n"
            + "desc/ [Description]\n"
            + "Example: desc/ Give class 10 minutes extra for recess today\n\n";

    private final Index index;
    private final String note;
    private final String description;

    /**
     * Creates a QuestionEditCommand object.
     *
     * @param fields to edit.
     */
    public NoteEditCommand(Index index, HashMap<String, String> fields) {
        this.index = index;
        this.note = fields.get("note");
        this.description = fields.get("description");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Note noteObj = model.getNote(index);

        String note = (!this.note.isBlank()) ? this.note : noteObj.getNote();
        String details = (!this.description.isBlank()) ? this.description : noteObj.getDescription();

        noteObj.setNote(note);
        noteObj.setDescription(details);

        model.setNote(index, noteObj);
        return new CommandResult(generateSuccessMessage(noteObj));
    }

    /**
     * Generates a command execution success message.
     *
     * @param note that has been added.
     */
    private String generateSuccessMessage(Note note) {
        return "Edited note: " + note;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteEditCommand)) {
            return false;
        }

        // state check
        NoteEditCommand e = (NoteEditCommand) other;
        return note.equals(e.note);
    }
}
