package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.note.Note;

/**
 * A utility class containing a list of {@code Note} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Note ALICE = new PersonBuilder().withTitle("Alice Pauline")
            .withContent("123, Jurong West Ave 6, #08-111").build();
    public static final Note BENSON = new PersonBuilder().withTitle("Benson Meier")
            .withContent("311, Clementi Ave 2, #02-25").build();
    public static final Note CARL = new PersonBuilder().withTitle("Carl Kurz")
            .withContent("wall street").build();
    public static final Note DANIEL = new PersonBuilder().withTitle("Daniel Meier")
            .withContent("10th street").build();
    public static final Note ELLE = new PersonBuilder().withTitle("Elle Meyer")
            .withContent("michegan ave").build();
    public static final Note FIONA = new PersonBuilder().withTitle("Fiona Kunz")
            .withContent("little tokyo").build();
    public static final Note GEORGE = new PersonBuilder().withTitle("George Best")
            .withContent("4th street").build();

    // Manually added
    public static final Note HOON = new PersonBuilder().withTitle("Hoon Meier")
            .withContent("little india").build();
    public static final Note IDA = new PersonBuilder().withTitle("Ida Mueller")
            .withContent("chicago ave").build();

    // Manually added - Note's details found in {@code CommandTestUtil}
    public static final Note AMY = new PersonBuilder().withTitle(VALID_TITLE_AMY)
            .withContent(VALID_CONTENT_AMY).build();
    public static final Note BOB = new PersonBuilder().withTitle(VALID_TITLE_BOB)
            .withContent(VALID_CONTENT_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Note note : getTypicalPersons()) {
            ab.addNote(note);
        }
        return ab;
    }

    public static List<Note> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
