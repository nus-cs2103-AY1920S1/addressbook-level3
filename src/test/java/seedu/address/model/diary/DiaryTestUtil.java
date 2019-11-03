package seedu.address.model.diary;

import static seedu.address.model.diary.photo.PhotoListStub.PHOTO_LIST_STUB_STRING;

import java.util.Collection;

import javafx.collections.FXCollections;

import seedu.address.commons.core.index.Index;
import seedu.address.model.diary.photo.PhotoListStub;

/**
 * Collection of diary entry test cases using {@link PhotoListStub}s.
 * NOTE:
 * The instances used should correspond to the class being tested, and should take into account the relationships
 * between dependencies and dependents.
 * For example, DiaryEntryTest should not utilize TEST_ENTRY_1, but should construct and test the DiaryEntry from
 * TEST_ENTRY_1_INDEX instead, to avoid any modification operation of TEST_ENTRY_1 affecting the tests of
 * DiaryEntryListTest.
 * This is to avoid race conditions interfering with test results between dependencies and dependent diary classes.
 */
public interface DiaryTestUtil {
    Index TEST_ENTRY_1_INDEX = Index.fromOneBased(1);
    Index TEST_ENTRY_1_COPY_INDEX = Index.fromOneBased(TEST_ENTRY_1_INDEX.getOneBased());
    String TEST_ENTRY_1_STRING = "";
    DiaryEntry TEST_ENTRY_1 = new DiaryEntry(TEST_ENTRY_1_INDEX, new PhotoListStub(), TEST_ENTRY_1_STRING);
    DiaryEntry TEST_ENTRY_1_COPY = new DiaryEntry(TEST_ENTRY_1_COPY_INDEX, new PhotoListStub(), TEST_ENTRY_1_STRING);
    String TEST_ENTRY_1_TO_STRING = String.format(
            "Day Number: 1 Diary Text (Truncated): ...\nPhoto List:\n%1$s",
            PHOTO_LIST_STUB_STRING);

    Index TEST_ENTRY_2_INDEX = Index.fromOneBased(19);
    Index TEST_ENTRY_2_COPY_INDEX = Index.fromOneBased(TEST_ENTRY_2_INDEX.getOneBased());
    String TEST_ENTRY_2_STRING = "abc";
    DiaryEntry TEST_ENTRY_2 = new DiaryEntry(TEST_ENTRY_2_INDEX, new PhotoListStub(), TEST_ENTRY_2_STRING);
    DiaryEntry TEST_ENTRY_2_COPY = new DiaryEntry(TEST_ENTRY_2_COPY_INDEX, new PhotoListStub(), TEST_ENTRY_2_STRING);
    String TEST_ENTRY_2_TO_STRING = String.format(
            "Day Number: 19 Diary Text (Truncated): abc...\nPhoto List:\n%1$s",
            PHOTO_LIST_STUB_STRING);

    Index TEST_ENTRY_3_INDEX = Index.fromOneBased(21);
    Index TEST_ENTRY_3_COPY_INDEX = Index.fromOneBased(TEST_ENTRY_3_INDEX.getOneBased());
    String TEST_ENTRY_3_STRING = "asdf";
    DiaryEntry TEST_ENTRY_3 = new DiaryEntry(TEST_ENTRY_3_INDEX, new PhotoListStub(), TEST_ENTRY_3_STRING);
    DiaryEntry TEST_ENTRY_3_COPY = new DiaryEntry(TEST_ENTRY_3_COPY_INDEX, new PhotoListStub(), TEST_ENTRY_3_STRING);
    String TEST_ENTRY_3_TO_STRING = String.format(
            "Day Number: 21 Diary Text (Truncated): asdf...\nPhoto List:\n%1$s",
            PHOTO_LIST_STUB_STRING);

    Index TEST_ENTRY_4_INDEX = Index.fromOneBased(25);
    Index TEST_ENTRY_4_COPY_INDEX = Index.fromOneBased(TEST_ENTRY_4_INDEX.getOneBased());
    String TEST_ENTRY_4_STRING = "aaa";
    DiaryEntry TEST_ENTRY_4 = new DiaryEntry(TEST_ENTRY_4_INDEX, new PhotoListStub(), TEST_ENTRY_4_STRING);
    DiaryEntry TEST_ENTRY_4_COPY = new DiaryEntry(TEST_ENTRY_4_COPY_INDEX, new PhotoListStub(), TEST_ENTRY_4_STRING);
    String TEST_ENTRY_4_TO_STRING = String.format(
            "Day Number: 25 Diary Text (Truncated): aaa...\nPhoto List:\n%1$s",
            PHOTO_LIST_STUB_STRING);

    Index TEST_ENTRY_5_INDEX = Index.fromOneBased(27);
    Index TEST_ENTRY_5_COPY_INDEX = Index.fromOneBased(TEST_ENTRY_5_INDEX.getOneBased());
    String TEST_ENTRY_5_STRING = "abc".repeat(DiaryEntry.MAX_DIARY_TEXT_DISPLAY_LENGTH * 10);
    DiaryEntry TEST_ENTRY_5 = new DiaryEntry(TEST_ENTRY_5_INDEX, new PhotoListStub(), TEST_ENTRY_5_STRING);
    DiaryEntry TEST_ENTRY_5_COPY = new DiaryEntry(TEST_ENTRY_5_COPY_INDEX, new PhotoListStub(), TEST_ENTRY_5_STRING);
    String TEST_ENTRY_5_TO_STRING = String.format(
            "Day Number: 27 Diary Text (Truncated): %2$s...\nPhoto List:\n%1$s",
            PHOTO_LIST_STUB_STRING,
            TEST_ENTRY_5_STRING.substring(0, DiaryEntry.MAX_DIARY_TEXT_DISPLAY_LENGTH));

    Collection<DiaryEntry> TEST_DIARY_ENTRIES_UNIQUE_OUT_OF_ORDER =
            FXCollections.observableArrayList(TEST_ENTRY_4, TEST_ENTRY_1, TEST_ENTRY_3, TEST_ENTRY_2);

    Collection<DiaryEntry> TEST_DIARY_ENTRIES_UNIQUE =
            FXCollections.observableArrayList(TEST_ENTRY_1, TEST_ENTRY_2, TEST_ENTRY_3, TEST_ENTRY_4);

    Collection<DiaryEntry> TEST_DIARY_ENTRIES_DUPLICATES = FXCollections.observableArrayList(
            TEST_ENTRY_1, TEST_ENTRY_2_COPY,
            TEST_ENTRY_2, TEST_ENTRY_2_COPY,
            TEST_ENTRY_3, TEST_ENTRY_3_COPY,
            TEST_ENTRY_4, TEST_ENTRY_4_COPY);

    String TEST_DIARY_ENTRIES_UNIQUE_TO_STRING = "Diary Entries: "
            + TEST_ENTRY_1_TO_STRING + "\n"
            + TEST_ENTRY_2_TO_STRING + "\n"
            + TEST_ENTRY_3_TO_STRING + "\n"
            + TEST_ENTRY_4_TO_STRING + "\n";

    Index ABSENT_DAY_INDEX = Index.fromOneBased(2);

    /**
     * Generates a string of random characters, affected by the constants {@code MIN_RANDOM_ASCII_CHAR},
     * {@code MAX_RANDOM_ASCII_CHAR} and {@code GENERATE_TEXT_BREAK_CHANCE}.
     *
     * @return The randomly generated string.
     */
    static String generateRandomText() {
        int minRandomAsciiChar = 32;
        int maxRandomAsciiChar = 126;
        double generateTextBreakChance = 0.05;

        StringBuilder diaryLineText = new StringBuilder();
        double rand;

        for (int i = 0; i < 100; i++) {
            rand = Math.random();
            if (rand <= generateTextBreakChance) {
                break;
            }
            rand -= generateTextBreakChance;
            rand /= (1.0 - generateTextBreakChance);

            diaryLineText.append((char) (((int) (rand * (maxRandomAsciiChar - minRandomAsciiChar)))
                    + minRandomAsciiChar));
        }

        return diaryLineText.toString();
    }
}
