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
public class TypicalDiaries {

    public static final Diary ALICE = new DiaryBuilder().withName("Alice Pauline").build();
    public static final Diary BENSON = new DiaryBuilder().withName("Benson Meier").build();
    public static final Diary CARL = new DiaryBuilder().withName("Carl Kurz").build();
    public static final Diary DANIEL = new DiaryBuilder().withName("Daniel Meier").build();
    public static final Diary ELLE = new DiaryBuilder().withName("Elle Meyer").build();
    public static final Diary FIONA = new DiaryBuilder().withName("Fiona Kunz").build();
    public static final Diary GEORGE = new DiaryBuilder().withName("George Best").build();

    // Manually added
    public static final Diary HOON = new DiaryBuilder().withName("Hoon Meier").build();
    public static final Diary IDA = new DiaryBuilder().withName("Ida Mueller").build();

    // Manually added - Diary's details found in {@code CommandTestUtil}
    public static final Diary AMY = new DiaryBuilder().withName(VALID_NAME_AMY).build();
    public static final Diary BOB = new DiaryBuilder().withName(VALID_NAME_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalDiaries() {} // prevents instantiation

    /**
     * Returns an {@code DukeCooks} with all the typical persons.
     */
    public static DukeCooks getTypicalDukeCooks() {
        DukeCooks ab = new DukeCooks();
        for (Diary diary : getTypicalDiaries()) {
            ab.addDiary(diary);
        }
        return ab;
    }

    public static List<Diary> getTypicalDiaries() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
