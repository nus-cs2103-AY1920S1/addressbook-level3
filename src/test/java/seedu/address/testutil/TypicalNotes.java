package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_FRAGMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CS2100;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MIDTERMS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_FRAGMENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.StudyBuddyPro;
import seedu.address.model.note.Note;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalNotes {

    public static final Note SAMPLE =
            new NoteBuilder().withTitle("Sample Title").withContent("Sample Content").withTags("SampleTag1",
                    "SampleTag2").build();
    public static final Note PIPELINE = new NoteBuilder().withTitle("Pipelining Definition.")
            .withContent("Pipelining is the process of making a single processor run multiple instructions "
                    + "simultaneously.").withTags("CS2100", "Midterms").build();
    public static final Note POTATO =
            new NoteBuilder().withTitle("Potatoes").withContent("I really like potatoes.").build();
    public static final Note FRAGMENT = new NoteBuilder().withTitle(VALID_TITLE_FRAGMENT)
            .withContent(VALID_CONTENT_FRAGMENT).withTags(VALID_TAG_CS2100, VALID_TAG_MIDTERMS).build();

    /*
    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();
    */

    private TypicalNotes() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical notes.
     */
    public static StudyBuddyPro getTypicalNoteList() {
        StudyBuddyPro ab = new StudyBuddyPro();
        for (Note note : getTypicalNotes()) {
            ab.addNote(note);
        }
        return ab;
    }

    public static List<Note> getTypicalNotes() {
        return new ArrayList<>(Arrays.asList(SAMPLE, PIPELINE, FRAGMENT));
    }
}
