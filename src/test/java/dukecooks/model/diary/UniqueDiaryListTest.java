package dukecooks.model.diary;

import static dukecooks.testutil.diary.TypicalDiaries.ALL_MEAT;
import static dukecooks.testutil.diary.TypicalDiaries.BOB_DIARY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.UniqueDiaryList;
import dukecooks.model.diary.exceptions.DiaryNotFoundException;
import dukecooks.model.diary.exceptions.DuplicateDiaryException;
import dukecooks.testutil.Assert;
import dukecooks.testutil.diary.DiaryBuilder;

public class UniqueDiaryListTest {

    private final UniqueDiaryList uniqueDiaryList = new UniqueDiaryList();

    @Test
    public void contains_nullDiary_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDiaryList.contains(null));
    }

    @Test
    public void contains_diaryNotInList_returnsFalse() {
        assertFalse(uniqueDiaryList.contains(ALL_MEAT));
    }

    @Test
    public void contains_diaryInList_returnsTrue() {
        uniqueDiaryList.add(ALL_MEAT);
        assertTrue(uniqueDiaryList.contains(ALL_MEAT));
    }

    @Test
    public void contains_diaryWithSameIdentityFieldsInList_returnsTrue() {
        uniqueDiaryList.add(ALL_MEAT);
        Diary editedDiary = new DiaryBuilder(ALL_MEAT).build();
        assertTrue(uniqueDiaryList.contains(editedDiary));
    }

    @Test
    public void add_nullDiary_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDiaryList.add(null));
    }

    @Test
    public void add_duplicateDiary_throwsDuplicateDiaryException() {
        uniqueDiaryList.add(ALL_MEAT);
        Assert.assertThrows(DuplicateDiaryException.class, () -> uniqueDiaryList.add(ALL_MEAT));
    }

    @Test
    public void setDiary_nullTargetDiary_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDiaryList.setDiary(null, ALL_MEAT));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDiaryList.setDiary(ALL_MEAT, null));
    }

    @Test
    public void setDiary_targetDiaryNotInList_throwsDiaryNotFoundException() {
        Assert.assertThrows(DiaryNotFoundException.class, () -> uniqueDiaryList.setDiary(ALL_MEAT, ALL_MEAT));
    }

    @Test
    public void setDiary_editedDiaryIsSameDiary_success() {
        uniqueDiaryList.add(ALL_MEAT);
        uniqueDiaryList.setDiary(ALL_MEAT, ALL_MEAT);
        UniqueDiaryList expectedUniqueDiaryList = new UniqueDiaryList();
        expectedUniqueDiaryList.add(ALL_MEAT);
        assertEquals(expectedUniqueDiaryList, uniqueDiaryList);
    }

    @Test
    public void setDiary_editedDiaryHasSameIdentity_success() {
        uniqueDiaryList.add(ALL_MEAT);
        Diary editedDiary = new DiaryBuilder(ALL_MEAT).build();
        uniqueDiaryList.setDiary(ALL_MEAT, editedDiary);
        UniqueDiaryList expectedUniqueDiaryList = new UniqueDiaryList();
        expectedUniqueDiaryList.add(editedDiary);
        assertEquals(expectedUniqueDiaryList, uniqueDiaryList);
    }

    @Test
    public void setDiary_editedDiaryHasDifferentIdentity_success() {
        uniqueDiaryList.add(ALL_MEAT);
        uniqueDiaryList.setDiary(ALL_MEAT, BOB_DIARY);
        UniqueDiaryList expectedUniqueDiaryList = new UniqueDiaryList();
        expectedUniqueDiaryList.add(BOB_DIARY);
        assertEquals(expectedUniqueDiaryList, uniqueDiaryList);
    }

    @Test
    public void setDiary_editedDiaryHasNonUniqueIdentity_throwsDuplicateDiaryException() {
        uniqueDiaryList.add(ALL_MEAT);
        uniqueDiaryList.add(BOB_DIARY);
        Assert.assertThrows(DuplicateDiaryException.class, () -> uniqueDiaryList.setDiary(ALL_MEAT, BOB_DIARY));
    }

    @Test
    public void remove_nullDiary_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDiaryList.remove(null));
    }

    @Test
    public void remove_diaryDoesNotExist_throwsDiaryNotFoundException() {
        Assert.assertThrows(DiaryNotFoundException.class, () -> uniqueDiaryList.remove(ALL_MEAT));
    }

    @Test
    public void remove_existingDiary_removesDiary() {
        uniqueDiaryList.add(ALL_MEAT);
        uniqueDiaryList.remove(ALL_MEAT);
        UniqueDiaryList expectedUniqueDiaryList = new UniqueDiaryList();
        assertEquals(expectedUniqueDiaryList, uniqueDiaryList);
    }

    @Test
    public void setDiary_nullUniqueDiaryList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDiaryList.setDiaries((UniqueDiaryList) null));
    }

    @Test
    public void setDiary_uniqueDiaryList_replacesOwnListWithProvidedUniqueDiaryList() {
        uniqueDiaryList.add(ALL_MEAT);
        UniqueDiaryList expectedUniqueDiaryList = new UniqueDiaryList();
        expectedUniqueDiaryList.add(BOB_DIARY);
        uniqueDiaryList.setDiaries(expectedUniqueDiaryList);
        assertEquals(expectedUniqueDiaryList, uniqueDiaryList);
    }

    @Test
    public void setDiaries_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDiaryList.setDiaries((List<Diary>) null));
    }

    @Test
    public void setDiaries_list_replacesOwnListWithProvidedList() {
        uniqueDiaryList.add(ALL_MEAT);
        List<Diary> diaryList = Collections.singletonList(BOB_DIARY);
        uniqueDiaryList.setDiaries(diaryList);
        UniqueDiaryList expectedUniqueDiaryList = new UniqueDiaryList();
        expectedUniqueDiaryList.add(BOB_DIARY);
        assertEquals(expectedUniqueDiaryList, uniqueDiaryList);
    }

    @Test
    public void setDiaries_listWithDuplicateDiaries_throwsDuplicateDiaryException() {
        List<Diary> listWithDuplicatePersons = Arrays.asList(ALL_MEAT, ALL_MEAT);
        Assert.assertThrows(DuplicateDiaryException.class, () -> uniqueDiaryList.setDiaries(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniqueDiaryList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void testUniqueDiaryListIterator() {
        UniqueDiaryList list = new UniqueDiaryList();

        assertTrue(list.iterator() instanceof Iterator);
    }
}
