package seedu.address.model.diary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.diary.DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE;
import static seedu.address.model.diary.DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE_TO_STRING;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_1;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_2;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_2_INDEX;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_3;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_3_INDEX;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_5;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_5_INDEX;

import java.util.Optional;

import org.junit.jupiter.api.Test;

/**
 * Integration test of {@link Diary} with {@link DiaryEntryList}.
 * Wrapper methods around {@link DiaryEntryList} are tested only with positive test cases as
 * negative test cases are already covered in {@link DiaryEntryListTest}.
 */
class DiaryTest {
    @Test
    void getDiaryEntryList_diaryEntryListProvided_diaryEntryListSet() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(TEST_DIARY_ENTRIES_UNIQUE);
        Diary diary = new Diary(diaryEntryList);
        assertEquals(diaryEntryList, diary.getDiaryEntryList());
    }

    @Test
    void getDiaryEntry_existingEntryWithIndex_entryReturned() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);
        Diary diary = new Diary(diaryEntryList);
        Optional<DiaryEntry> diaryEntry = diary.getDiaryEntry(TEST_ENTRY_3_INDEX);

        assertTrue(diaryEntry.isPresent());
        assertEquals(TEST_ENTRY_3, diaryEntry.get());
    }

    @Test
    void getFirstDiaryEntry_outOfOrderDiaryEntryList_returnsOptionalWithSmallestDay() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE_OUT_OF_ORDER);
        Diary diary = new Diary(diaryEntryList);
        Optional<DiaryEntry> diaryEntry = diary.getFirstDiaryEntry();

        assertTrue(diaryEntry.isPresent());
        assertEquals(TEST_ENTRY_1, diaryEntry.get());
    }

    @Test
    void addDiaryEntry_nonClashingDiaryEntry_entryAdded() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);
        Diary diary = new Diary(diaryEntryList);
        diary.addDiaryEntry(TEST_ENTRY_5);
        Optional<DiaryEntry> diaryEntry = diary.getDiaryEntry(TEST_ENTRY_5_INDEX);

        assertTrue(diaryEntry.isPresent());
        assertEquals(TEST_ENTRY_5, diaryEntry.get());
    }

    @Test
    void setDiaryEntry_targetInList_targetRemovedReplacementAdded() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);
        Diary diary = new Diary(diaryEntryList);

        assertTrue(diary.getDiaryEntry(TEST_ENTRY_2_INDEX).isPresent());

        diary.setDiaryEntry(TEST_ENTRY_2, TEST_ENTRY_5);
        Optional<DiaryEntry> diaryEntry1 = diary.getDiaryEntry(TEST_ENTRY_2_INDEX);
        Optional<DiaryEntry> diaryEntry5 = diary.getDiaryEntry(TEST_ENTRY_5_INDEX);

        assertTrue(diaryEntry1.isEmpty());
        assertTrue(diaryEntry5.isPresent());
        assertEquals(TEST_ENTRY_5, diaryEntry5.get());
    }

    @Test
    void toString_uniqueDiaryEntryList_equals() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);
        Diary diary = new Diary(diaryEntryList);

        assertEquals("Diary Entry List: " + TEST_DIARY_ENTRIES_UNIQUE_TO_STRING,
                diary.toString());
    }

    @Test
    void equals_sameInstance_returnsTrue() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);
        Diary diary = new Diary(diaryEntryList);
        assertEquals(diary, diary);
    }

    @Test
    void equals_notInstanceOfDiary_returnsFalse() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);
        Diary diary = new Diary(diaryEntryList);
        assertNotEquals(diary, new Object());
    }

    @Test
    void equals_sameDiaryEntryLists_returnsTrue() {
        DiaryEntryList diaryEntryList = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);
        Diary diary = new Diary(diaryEntryList);
        DiaryEntryList diaryEntryList2 = new DiaryEntryList(DiaryTestUtil.TEST_DIARY_ENTRIES_UNIQUE);
        Diary diary2 = new Diary(diaryEntryList);

        assertEquals(diary, diary2);
    }
}
