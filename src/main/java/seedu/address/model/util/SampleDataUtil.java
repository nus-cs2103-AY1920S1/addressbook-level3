package seedu.address.model.util;

import seedu.address.model.AppData;
import seedu.address.model.ReadOnlyAppData;
import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.note.Title;

/**
 * Contains utility methods for populating {@code AppData} with sample data.
 */
public class SampleDataUtil {
    public static Note[] getSampleNotes() {
        return new Note[] {
            new Note(new Title("Magnus Carlsen"), new Content("World Chess Champion as of 2012")),
            new Note(new Title("Russell's paradox"), new Content("arises in naive set theory")),
            new Note(new Title("An AVL tree"), new Content("Self-balancing binary search tree. Needs rotations "
                    + "when adding or deleting. Suitable for the lazy variant of Dijkstra's algorithm"))
        };
    }

    public static ReadOnlyAppData getSampleAppData() {
        AppData sampleAb = new AppData();
        for (Note sampleNote: getSampleNotes()) {
            sampleAb.addNote(sampleNote);
        }
        return sampleAb;
    }
}
