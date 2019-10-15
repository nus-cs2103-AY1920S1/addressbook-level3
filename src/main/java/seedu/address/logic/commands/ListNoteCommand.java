package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;

import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.note.Note;

/**
 * Lists all persons in the address book to the user.
 */
public class ListNoteCommand extends Command {

    public static final String COMMAND_WORD = "list_notes";

    public static final String MESSAGE_SUCCESS = "Listed all notes";
    /**
     * Formats string for output
     * @param inputList list of notes
     * @return String formatted note display
     */
    private String formatOutputListString(List<Note> inputList) {
        int size = inputList.size();
        if (size == 0) {
            return "No notes to display!";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= size; i++) {
            Note note = inputList.get(i - 1);
            sb.append(i)
                    .append(". ")
                    .append(note.getTitle())
                    .append(" - ")
                    .append(note.getContent());
            if (i != size) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
        List<Note> lastShownList = model.getFilteredNoteList();
        String outputString = formatOutputListString(lastShownList);
        return new CommandResult(outputString);
    }
}
