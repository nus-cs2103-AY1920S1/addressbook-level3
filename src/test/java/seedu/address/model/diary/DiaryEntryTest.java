package seedu.address.model.diary;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;

class DiaryEntryTest {

    private static final DiaryEntry testEntry1 = new DiaryEntry(Index.fromOneBased(10));
    private static final DiaryEntry testEntry1Copy = new DiaryEntry(Index.fromOneBased(10));
    private static final String testEntry1String =
            String.format("Day Number: 10 Diary Text (Truncated): ...\nPhoto List:\n");

    private static final DiaryEntry testEntry2 = new DiaryEntry(Index.fromZeroBased(19));
    private static final DiaryEntry testEntry2Copy = new DiaryEntry(Index.fromOneBased(20));
    private static final String testEntry2String =
            String.format("Day Number: 20 Diary Text (Truncated): ...\nPhoto List:\n");

    @Test
    void getDayIndex_newDiaryEntry_success() {
        Index index = Index.fromOneBased(1);
        DiaryEntry diaryEntry = new DiaryEntry(index);

        assertTrue(diaryEntry.getDayIndex() == index);
    }

    @Test
    void testToString_validStringCombinations_success() {
        assertAll("Empty photolist and text cases:", () ->
                assertEquals(testEntry1.toString(), testEntry1String), () ->
                assertEquals(testEntry2.toString(), testEntry2String));
    }

    @Test
    void testToString_invalidStringCombinations_failure() {
        assertAll("Empty photolist and text cases:", () ->
                assertNotEquals(testEntry1.toString(), testEntry2String), () ->
                assertNotEquals(testEntry2.toString(), testEntry1String));
    }

    @Test
    void testEquals_equalDiaryEntries_success() {
        assertAll("Empty photolist and text cases", () ->
                assertEquals(testEntry1, testEntry1), () ->
                assertEquals(testEntry2, testEntry2), () ->
                assertEquals(testEntry1, testEntry1Copy), () ->
                assertEquals(testEntry2, testEntry2Copy));
    }

    @Test
    void testEquals_differentDiaryEntries_success() {
        assertAll("Empty photolist and text cases", () ->
                assertNotEquals(testEntry1, testEntry2Copy), () ->
                assertNotEquals(testEntry2, testEntry1Copy));
    }
}
