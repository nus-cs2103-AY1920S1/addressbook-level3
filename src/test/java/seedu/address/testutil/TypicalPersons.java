package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.DukeCooks;
import seedu.address.model.diary.Diary;

/**
 * A utility class containing a list of {@code Diary} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Diary ALICE = new PersonBuilder().withName("Alice Pauline")
            .withTags("friends").build();
    public static final Diary BENSON = new PersonBuilder().withName("Benson Meier")
            .withTags("owesMoney", "friends").build();
    public static final Diary CARL = new PersonBuilder().withName("Carl Kurz")
            .build();
    public static final Diary DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withTags("friends").build();
    public static final Diary ELLE = new PersonBuilder().withName("Elle Meyer")
            .build();
    public static final Diary FIONA = new PersonBuilder().withName("Fiona Kunz")
            .build();
    public static final Diary GEORGE = new PersonBuilder().withName("George Best")
            .build();

    // Manually added
    public static final Diary HOON = new PersonBuilder().withName("Hoon Meier")
            .build();
    public static final Diary IDA = new PersonBuilder().withName("Ida Mueller")
            .build();

    // Manually added - Diary's details found in {@code CommandTestUtil}
    public static final Diary AMY = new PersonBuilder().withName(VALID_NAME_AMY).build();
    public static final Diary BOB = new PersonBuilder().withName(VALID_NAME_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code DukeCooks} with all the typical persons.
     */
    public static DukeCooks getTypicalDukeCooks() {
        DukeCooks ab = new DukeCooks();
        for (Diary diary : getTypicalPersons()) {
            ab.addDiary(diary);
        }
        return ab;
    }

    public static List<Diary> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
