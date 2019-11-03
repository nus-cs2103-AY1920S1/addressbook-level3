package seedu.address.model.diary;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_1;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_1_INDEX;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_1_STRING;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_1_TO_STRING;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_2_INDEX;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_2_STRING;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_2_TO_STRING;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_3_INDEX;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_3_STRING;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_3_TO_STRING;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_4_INDEX;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_4_STRING;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_4_TO_STRING;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_5_INDEX;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_5_STRING;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_5_TO_STRING;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.diary.photo.PhotoListStub;

/**
 * Unit test of {@link DiaryEntry} using {@link PhotoListStub} class.
 */
class DiaryEntryTest {

    @Test
    void primaryConstructor_nullArguments_throwsNullPointer() {
        assertAll("Null arguments primary constructor", () ->
                assertThrows(NullPointerException.class, () ->
                        new DiaryEntry(null,
                                new PhotoListStub(),
                                "")), () ->
                assertThrows(NullPointerException.class, () ->
                        new DiaryEntry(Index.fromOneBased(10),
                                null,
                                "")), () ->
                assertThrows(NullPointerException.class, () ->
                        new DiaryEntry(Index.fromOneBased(10),
                                new PhotoListStub(),
                                null)));
    }

    @Test
    void indexConstructor_nullArguments_throwsNullPointer() {
        assertThrows(NullPointerException.class, () -> new DiaryEntry(null));
    }

    @Test
    void getDayIndex_newDiaryEntrySameIndex_success() {
        DiaryEntry diaryEntry = new DiaryEntry(TEST_ENTRY_1_INDEX, new PhotoListStub(), "");
        assertSame(diaryEntry.getDayIndex(), TEST_ENTRY_1_INDEX);
    }

    @Test
    void getDayIndex_newDiaryEntryDifferentIndexInstance_failure() {
        DiaryEntry diaryEntry = new DiaryEntry(TEST_ENTRY_1_INDEX, new PhotoListStub(), "");
        assertNotSame(diaryEntry.getDayIndex(), Index.fromOneBased(TEST_ENTRY_1_INDEX.getOneBased()));
    }

    @Test
    void getDayNumber_newDiaryEntry_success() {
        DiaryEntry diaryEntry = new DiaryEntry(TEST_ENTRY_1_INDEX, new PhotoListStub(), "");
        assertSame(diaryEntry.getDayNumber(), TEST_ENTRY_1_INDEX.getOneBased());
    }

    @Test
    void getPhotoList_newDiaryEntry_success() {
        PhotoListStub photoListStub = new PhotoListStub();

        DiaryEntry diaryEntry = new DiaryEntry(TEST_ENTRY_1_INDEX, photoListStub, "");
        assertSame(diaryEntry.getPhotoList(), photoListStub);
    }

    @Test
    void getPhotoList_newDiaryEntryDifferentPhotoListInstance_failure() {
        DiaryEntry diaryEntry = new DiaryEntry(TEST_ENTRY_1_INDEX, new PhotoListStub(), "");
        assertNotSame(diaryEntry.getPhotoList(), new PhotoListStub());
    }

    @Test
    void getDiaryText_newDiaryEntry_success() {
        DiaryEntry diaryEntry = new DiaryEntry(TEST_ENTRY_1_INDEX, new PhotoListStub(), TEST_ENTRY_1_TO_STRING);
        assertSame(diaryEntry.getDiaryText(), TEST_ENTRY_1_TO_STRING);
    }

    @Test
    void getDiaryText_newDiaryEntryDifferentStrings_failure() {
        DiaryEntry diaryEntry = new DiaryEntry(TEST_ENTRY_1_INDEX, new PhotoListStub(), "abc");
        assertNotEquals("", diaryEntry.getDiaryText());
    }

    @Test
    void toString_diaryTextBeyondDisplayLength_textTruncated() {
        DiaryEntry diaryEntry5 = new DiaryEntry(TEST_ENTRY_5_INDEX, new PhotoListStub(), TEST_ENTRY_5_STRING);
        assertEquals(TEST_ENTRY_5_TO_STRING, diaryEntry5.toString());
    }

    @Test
    void testToString_validStringCombinations_success() {
        DiaryEntry diaryEntry1 = new DiaryEntry(TEST_ENTRY_1_INDEX, new PhotoListStub(), TEST_ENTRY_1_STRING);
        DiaryEntry diaryEntry2 = new DiaryEntry(TEST_ENTRY_2_INDEX, new PhotoListStub(), TEST_ENTRY_2_STRING);
        DiaryEntry diaryEntry3 = new DiaryEntry(TEST_ENTRY_3_INDEX, new PhotoListStub(), TEST_ENTRY_3_STRING);
        DiaryEntry diaryEntry4 = new DiaryEntry(TEST_ENTRY_4_INDEX, new PhotoListStub(), TEST_ENTRY_4_STRING);

        assertAll("Diary text below display length", () ->
                assertEquals(TEST_ENTRY_1_TO_STRING, diaryEntry1.toString()), () ->
                assertEquals(TEST_ENTRY_2_TO_STRING, diaryEntry2.toString()), () ->
                assertEquals(TEST_ENTRY_3_TO_STRING, diaryEntry3.toString()), () ->
                assertEquals(TEST_ENTRY_4_TO_STRING, diaryEntry4.toString()));
    }

    @Test
    void testToString_diaryTextsNotEqual_failure() {
        DiaryEntry diaryEntry1 = new DiaryEntry(TEST_ENTRY_1_INDEX, new PhotoListStub(), TEST_ENTRY_2_STRING);
        DiaryEntry diaryEntry2 = new DiaryEntry(TEST_ENTRY_2_INDEX, new PhotoListStub(), TEST_ENTRY_1_STRING);

        assertAll("Different strings DiaryEntry toString equality", () ->
                assertNotEquals(TEST_ENTRY_1_TO_STRING, diaryEntry1.toString()), () ->
                assertNotEquals(TEST_ENTRY_2_TO_STRING, diaryEntry2.toString()));
    }

    @Test
    void testEquals_sameInstanceOrEqualFields_equals() {
        DiaryEntry diaryEntry1 = new DiaryEntry(TEST_ENTRY_1_INDEX, new PhotoListStub(), TEST_ENTRY_1_STRING);
        DiaryEntry diaryEntry2 = new DiaryEntry(TEST_ENTRY_2_INDEX, new PhotoListStub(), TEST_ENTRY_2_STRING);
        DiaryEntry diaryEntry1copy = new DiaryEntry(TEST_ENTRY_1_INDEX, new PhotoListStub(), TEST_ENTRY_1_STRING);
        DiaryEntry diaryEntry2copy = new DiaryEntry(TEST_ENTRY_2_INDEX, new PhotoListStub(), TEST_ENTRY_2_STRING);

        assertAll("Equal diary entries success", () ->
                assertEquals(diaryEntry1, diaryEntry1), () ->
                assertEquals(diaryEntry2, diaryEntry2), () ->
                assertEquals(diaryEntry1, diaryEntry1copy), () ->
                assertEquals(diaryEntry2, diaryEntry2copy));
    }

    @Test
    void testEquals_differentDiaryEntries_notEquals() {
        DiaryEntry diaryEntry1 = new DiaryEntry(TEST_ENTRY_1_INDEX, new PhotoListStub(), TEST_ENTRY_1_STRING);
        DiaryEntry diaryEntry2 = new DiaryEntry(TEST_ENTRY_2_INDEX, new PhotoListStub(), TEST_ENTRY_2_STRING);
        DiaryEntry diaryEntry1copy = new DiaryEntry(TEST_ENTRY_1_INDEX, new PhotoListStub(), TEST_ENTRY_1_STRING);
        DiaryEntry diaryEntry2copy = new DiaryEntry(TEST_ENTRY_2_INDEX, new PhotoListStub(), TEST_ENTRY_2_STRING);

        assertAll("Different diary entries not equals", () ->
                assertNotEquals(diaryEntry1, diaryEntry2), () ->
                assertNotEquals(diaryEntry1copy, diaryEntry2copy));
    }

    @Test
    void testEquals_notInstanceOfDiaryEntry_notEquals() {
        assertNotEquals(TEST_ENTRY_1, new Object());
    }
}
