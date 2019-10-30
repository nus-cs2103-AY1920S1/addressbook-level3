package seedu.address.model.util;

import static seedu.address.model.util.SampleDataUtil.getTagSet;

import seedu.address.model.NoteBook;
import seedu.address.model.ReadOnlyNoteBook;
import seedu.address.model.note.Content;
import seedu.address.model.note.Description;
import seedu.address.model.note.Note;
import seedu.address.model.note.Title;


/**
 * Contains utility methods for populating {@code PasswordBook} with sample data.
 */
public class SampleDataNotesUtil {
    public static Note[] getSampleNotes() {
        return new Note[] {
            new Note(new Title("Secret Diary"), new Description("Keeps track of everyday things"),
                    getTagSet("Personal"), new Content("Content of my everyday life")),
            new Note(new Title("Office tracker"), new Description("Keeps track of my what i do in the office"),
                    getTagSet("Work"), new Content("Content of my everyday office life")),
            new Note(new Title("Project information"), new Description("Critical information for XYZ project"),
                    getTagSet("Work"), new Content("Content of XYZ Project"))
        };
    }

    public static ReadOnlyNoteBook getSampleNoteBook() {
        NoteBook sampleNb = new NoteBook();
        for (Note sampleNote : getSampleNotes()) {
            sampleNb.addNote(sampleNote);
        }
        return sampleNb;
    }
}
