package seedu.address.model.util;

import seedu.address.model.note.Note;
import seedu.address.model.note.NotesRecord;
import seedu.address.model.note.ReadOnlyNotesRecord;

/**
 * Contains utility methods for populating NotesRecord with sample data.
 */
public class SampleNotesUtil {

    public static Note[] getSampleNotes() {
        return new Note[]{
                new Note("tuesday class", "give back papers"),
                new Note("thursday conference", "give presentation"),
        };
    }

    public static ReadOnlyNotesRecord getSampleNotesRecord() {
        NotesRecord sampleNr = new NotesRecord();
        for (Note sampleNote : getSampleNotes()) {
            sampleNr.addNote(sampleNote);
        }
        return sampleNr;
    }

}
