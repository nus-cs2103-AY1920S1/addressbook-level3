package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDiaries.ALL_MEAT;
import static seedu.address.testutil.TypicalDiaries.BOB_DIARY;

import org.junit.jupiter.api.Test;

import seedu.address.model.diary.Diary;
import seedu.address.testutil.DiaryBuilder;

public class DiaryTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Diary diary = new DiaryBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> diary.getPages().remove(0));
    }

    @Test
    public void isSameDiary() {
        // same object -> returns true
        assertTrue(ALL_MEAT.isSameDiary(ALL_MEAT));

        // null -> returns false
        assertFalse(ALL_MEAT.isSameDiary(null));

        // different name -> returns false
        Diary editedDiary = new DiaryBuilder(ALL_MEAT).withName(VALID_NAME_BOB).build();
        assertFalse(ALL_MEAT.isSameDiary(editedDiary));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Diary allMeatCopy = new DiaryBuilder(ALL_MEAT).build();
        assertTrue(ALL_MEAT.equals(allMeatCopy));

        // same object -> returns true
        assertTrue(ALL_MEAT.equals(ALL_MEAT));

        // null -> returns false
        assertFalse(ALL_MEAT.equals(null));

        // different type -> returns false
        assertFalse(ALL_MEAT.equals(5));

        // different diary -> returns false
        assertFalse(ALL_MEAT.equals(BOB_DIARY));

        // different name -> returns false
        Diary editedAllMeat = new DiaryBuilder(ALL_MEAT).withName(VALID_NAME_BOB).build();
        assertFalse(ALL_MEAT.equals(editedAllMeat));
    }
}
