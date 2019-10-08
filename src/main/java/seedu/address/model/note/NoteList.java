package seedu.address.model.note;

import seedu.address.commons.core.index.Index;
import seedu.address.model.question.Question;

import java.util.ArrayList;

public class NoteList {

    private ArrayList<Note> notes;

    /**
     * Creates a new NoteList object.
     */
    public NoteList() {
        notes = new ArrayList<>();
    }

    /**
     * Add a new note to the question list.
     *
     * @param note to add to the list.
     */
    public void addNote(Note note) {
        this.notes.add(note);
        // TODO: Implement check if duplicated note AND desc is entered
    }

    /**
     * Deletes the note at the specified index in the list.
     *
     * @param index of the note in the list.
     * @return note object.
     */
    public Note deleteNote(Index index) {
        return notes.remove(index.getZeroBased());
    }

    /**
     * Returns the note object.
     *
     * @param index of the note in the list.
     * @return Note object.
     */
    public Note getNote(Index index) {
        return notes.get(index.getZeroBased());
    }

    /**
     * Sets the note object at the specified index in the list.
     *
     * @param index of the question in the list.
     * @param note object.
     */
    public void setNote(Index index, Note note) {
        notes.set(index.getZeroBased(), note);
    }

    /**
     * Printing out the list of notes and how many are there.
     *
     * @return Summary of notes.
     */
    public String getNoteList() {
        String summary = "There are currently " + notes.size() + " notes saved.\n"
                + "Here is the list of notes:\n";

        for (int i = 0; i < notes.size(); i++) {
            summary += (i + 1) + ". " + "\"" + notes.get(i) + "\"";

            if ((i + 1) != notes.size()) {
                summary += "\n";
            }
        }

        return summary;
    }

}
