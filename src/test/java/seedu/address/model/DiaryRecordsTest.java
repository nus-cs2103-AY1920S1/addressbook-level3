package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDiaries.ALL_MEAT;
import static seedu.address.testutil.TypicalDiaries.getTypicalDiaryRecords;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.exceptions.DuplicateDiaryException;
import seedu.address.testutil.DiaryBuilder;

public class DiaryRecordsTest {

    private final DiaryRecords diaryRecords = new DiaryRecords();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), diaryRecords.getDiaryList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> diaryRecords.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyDukeCooks_replacesData() {
        DiaryRecords newData = getTypicalDiaryRecords();
        diaryRecords.resetData(newData);
        assertEquals(newData, diaryRecords);
    }

    @Test
    public void resetData_withDuplicateDiaries_throwsDuplicateDiaryException() {
        // Two diaries with the same identity fields
        Diary editedAllMeat = new DiaryBuilder(ALL_MEAT).build();
        List<Diary> newDiary = Arrays.asList(ALL_MEAT, editedAllMeat);
        DiaryStub newData = new DiaryStub(newDiary);

        assertThrows(DuplicateDiaryException.class, () -> diaryRecords.resetData(newData));
    }

    @Test
    public void hasDiary_nullDiary_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> diaryRecords.hasDiary(null));
    }

    @Test
    public void hasDiary_diaryNotInDukeCooks_returnsFalse() {
        assertFalse(diaryRecords.hasDiary(ALL_MEAT));
    }

    @Test
    public void hasDiary_diaryInDukeCooks_returnsTrue() {
        diaryRecords.addDiary(ALL_MEAT);
        assertTrue(diaryRecords.hasDiary(ALL_MEAT));
    }

    @Test
    public void hasDiary_diaryWithSameIdentityFieldsInDukeCooks_returnsTrue() {
        diaryRecords.addDiary(ALL_MEAT);
        Diary editedDiary = new DiaryBuilder(ALL_MEAT).build();
        assertTrue(diaryRecords.hasDiary(editedDiary));
    }

    @Test
    public void getDiaryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> diaryRecords.getDiaryList().remove(0));
    }

    /**
     * A stub ReadOnlyDiary whose diaries list can violate interface constraints.
     */
    private static class DiaryStub implements ReadOnlyDiary {
        private final ObservableList<Diary> diaries = FXCollections.observableArrayList();

        DiaryStub(Collection<Diary> diaries) {
            this.diaries.setAll(diaries);
        }

        @Override
        public ObservableList<Diary> getDiaryList() {
            return diaries;
        }
    }

}
