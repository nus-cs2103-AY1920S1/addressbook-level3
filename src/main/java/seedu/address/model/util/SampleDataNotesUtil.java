package seedu.address.model.util;

import static seedu.address.model.util.SampleDataUtil.getTagSet;

import seedu.address.model.ReadOnlyNoteBook;
import seedu.address.model.VersionedNoteBook;
import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteDescription;
import seedu.address.model.note.Title;


/**
 * Contains utility methods for populating {@code PasswordBook} with sample data.
 */
public class SampleDataNotesUtil {
    public static Note[] getSampleNotes() {
        return new Note[] {
            new Note(new Title("Secret Diary"), new NoteDescription("Keeps track of everyday things"),
                    getTagSet("Personal"), new Content("Content of my everyday life")),
            new Note(new Title("Office tracker"), new NoteDescription("Keeps track of my what i do in the office"),
                    getTagSet("Work"), new Content("Content of my everyday office life")),
            new Note(new Title("Project information"), new NoteDescription("Critical information for XYZ project"),
                    getTagSet("Work"), new Content("Content of XYZ Project"))
        };
    }

    public static ReadOnlyNoteBook getSampleNoteBook() {
        VersionedNoteBook sampleNb = new VersionedNoteBook();
        for (Note sampleNote : getSampleNotes()) {
            sampleNb.addNote(sampleNote);
        }
        return sampleNb;
    }
}
