package seedu.address.model.diary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.diary.DiaryTestUtil.ABSENT_DAY_INDEX;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_1;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_1_COPY;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_1_INDEX;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_2;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_2_INDEX;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_3;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_4;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_5;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_5_INDEX;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import javafx.collections.transformation.SortedList;

import seedu.address.testutil.Assert;

/**
 * Mix of unit and integration tests of {@link DiaryEntryList} with {@link DiaryEntry}.
 */
public class DiaryEntryListTest {

    @Test
    void constructor_collectionOfDiaryEntries_entriesAdded() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);
        DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE.forEach(diaryEntry -> {
            assertTrue(diaryEntryList.getDiaryEntry(diaryEntry.getDayIndex()).isPresent());
        });
    }

    @Test
    void constructor_duplicateDiaryEntries_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, DiaryEntryList.MESSAGE_DUPLICATE_ENTRIES, () ->
                new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_DUPLICATES));
    }

    @Test
    void getDiaryEntrySortedList_uniqueDiaryEntries_entriesSortedByDay() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE_OUT_OF_ORDER);
        SortedList<DiaryEntry> sortedEntries = diaryEntryList.getDiaryEntrySortedList();

        assertEquals(TEST_ENTRY_1, sortedEntries.get(0));
        assertEquals(TEST_ENTRY_2, sortedEntries.get(1));
        assertEquals(TEST_ENTRY_3, sortedEntries.get(2));
        assertEquals(TEST_ENTRY_4, sortedEntries.get(3));
    }

    @Test
    void getDiaryEntry_nullIndex_throwsNullPointer() {
        DiaryEntryList diaryEntryList = new DiaryEntryList();

        Assert.assertThrows(NullPointerException.class, () ->
                diaryEntryList.getDiaryEntry(null));
    }

    @Test
    void getDiaryEntry_absentIndex_emptyOptionalReturned() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);
        Optional<DiaryEntry> diaryEntry = diaryEntryList.getDiaryEntry(ABSENT_DAY_INDEX);

        assertTrue(diaryEntry.isEmpty());
    }

    @Test
    void getDiaryEntry_presentIndex_optionalWithEqualDiaryEntryReturned() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);
        Optional<DiaryEntry> diaryEntry = diaryEntryList.getDiaryEntry(TEST_ENTRY_2_INDEX);

        assertTrue(diaryEntry.isPresent());
        assertEquals(TEST_ENTRY_2, diaryEntry.get());
    }

    @Test
    void getFirstDiaryEntry_emptyDiaryEntryList_returnsEmptyOptional() {
        DiaryEntryList diaryEntryList = new DiaryEntryList();

        assertTrue(diaryEntryList.getFirstDiaryEntry().isEmpty());
    }

    @Test
    void getFirstDiaryEntry_outOfOrderDiaryEntryList_returnsOptionalWithSmallestDay() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE_OUT_OF_ORDER);
        Optional<DiaryEntry> diaryEntry = diaryEntryList.getFirstDiaryEntry();

        assertTrue(diaryEntry.isPresent());
        assertEquals(TEST_ENTRY_1, diaryEntry.get());
    }

    @Test
    void addDiaryEntry_diaryEntriesWithClashingDayIndex_throwsIllegalArgumentException() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);
        Assert.assertThrows(IllegalArgumentException.class, DiaryEntryList.MESSAGE_DUPLICATE_ENTRIES, () ->
                diaryEntryList.addDiaryEntry(TEST_ENTRY_1_COPY));
    }

    @Test
    void addDiaryEntry_nonClashingDiaryEntry_entryAdded() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);
        diaryEntryList.addDiaryEntry(TEST_ENTRY_5);
        Optional<DiaryEntry> diaryEntry = diaryEntryList.getDiaryEntry(TEST_ENTRY_5_INDEX);

        assertTrue(diaryEntry.isPresent());
        assertEquals(TEST_ENTRY_5, diaryEntry.get());
    }

    @Test
    void setDiaryEntry_targetNotInList_throwsIllegalArgumentException() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);
        Assert.assertThrows(IllegalArgumentException.class, DiaryEntryList.MESSAGE_DAY_MISSING, () ->
                diaryEntryList.setDiaryEntry(TEST_ENTRY_5, TEST_ENTRY_1_COPY));
    }

    @Test
    void setDiaryEntry_targetInList_targetRemovedReplacementAdded() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);

        assertTrue(diaryEntryList.getDiaryEntry(TEST_ENTRY_1_INDEX).isPresent());

        diaryEntryList.setDiaryEntry(TEST_ENTRY_1, TEST_ENTRY_5);
        Optional<DiaryEntry> diaryEntry1 = diaryEntryList.getDiaryEntry(TEST_ENTRY_1_INDEX);
        Optional<DiaryEntry> diaryEntry5 = diaryEntryList.getDiaryEntry(TEST_ENTRY_5_INDEX);

        assertTrue(diaryEntry1.isEmpty());
        assertTrue(diaryEntry5.isPresent());
        assertEquals(TEST_ENTRY_5, diaryEntry5.get());
    }

    @Test
    void toString_uniqueDiaryEntries_equals() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);

        assertEquals(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE_TO_STRING, diaryEntryList.toString());
    }

    @Test
    void equals_sameInstance_returnsTrue() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);
        assertEquals(diaryEntryList, diaryEntryList);
    }

    @Test
    void equals_notInstanceOfDiaryEntryList_returnsFalse() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);
        assertNotEquals(diaryEntryList, new Object());
    }

    @Test
    void equals_sameDiaryEntries_returnsTrue() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);
        DiaryEntryList diaryEntryList2 = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);

        assertEquals(diaryEntryList, diaryEntryList2);
    }
}
