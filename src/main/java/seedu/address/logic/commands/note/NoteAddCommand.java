package seedu.address.logic.commands.note;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.question.QuestionAddCommand;
import seedu.address.model.Model;
import seedu.address.model.note.Note;
import seedu.address.model.question.McqQuestion;
import seedu.address.model.question.OpenEndedQuestion;
import seedu.address.model.question.Question;


import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class NoteAddCommand extends NoteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new note\n"
            + "Parameters:\n"
            + "desc/ [Description]\n"
            + "Example: desc/ Give class 10 minutes extra for recess today\n\n";

    private final String note;
    private final String description;

    /**
     * Creates a NoteAddCommand object.
     *
     * @param note to set.
     * @param details   of the note.
     */
    public NoteAddCommand(String note, String details) {
        requireAllNonNull(note);
        this.note = note;
        this.description = details;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Note note;

        note = new Note(this.note, this.description);
        model.addNote(note);
        return new CommandResult(generateSuccessMessage(note));
    }

    /**
     * Generates a command execution success message.
     *
     * @param note that has been added.
     */
    private String generateSuccessMessage(Note note) {
        return "Added note: " + note;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteAddCommand)) {
            return false;
        }

        // state check
        NoteAddCommand e = (NoteAddCommand) other;
        return note.equals(e.note);
    }
}
