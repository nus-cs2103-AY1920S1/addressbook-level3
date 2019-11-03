package dukecooks.model.diary;

import static dukecooks.testutil.diary.TypicalDiaries.ALL_MEAT;
import static dukecooks.testutil.diary.TypicalDiaries.BOB_DIARY;
import static dukecooks.testutil.diary.TypicalPages.PHO_PAGE;
import static dukecooks.testutil.diary.TypicalPages.SUSHI_PAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.DiaryName;
import dukecooks.model.diary.components.Page;
import dukecooks.testutil.diary.DiaryBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DiaryTest {

    @Test
    public void isSameDiary() {
        // same object -> returns true
        assertTrue(ALL_MEAT.isSameDiary(ALL_MEAT));

        // null -> returns false
        assertFalse(ALL_MEAT.isSameDiary(null));

        // different name -> returns false
        Diary editedDiary = new DiaryBuilder(ALL_MEAT).withName(CommandTestUtil.VALID_NAME_BOB).build();
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
        Diary editedAllMeat = new DiaryBuilder(ALL_MEAT).withName(CommandTestUtil.VALID_NAME_BOB).build();
        assertFalse(ALL_MEAT.equals(editedAllMeat));
    }

    @Test
    public void testDiaryHashCode() {
        DiaryName name = new DiaryName("hello");
        DiaryName otherName = new DiaryName("hello");
        Diary firstDiary = new Diary(name);
        Diary secondDiary = new Diary(otherName);
        assertEquals(firstDiary.hashCode(), secondDiary.hashCode());
    }

    @Test
    public void hasPage() {
        ArrayList<Page> pages = new ArrayList<>();
        pages.add(PHO_PAGE);
        ObservableList<Page> pageList = FXCollections.observableArrayList(pages);

        Diary diaryWithPhoPage = new Diary(ALL_MEAT.getDiaryName(), pageList);

        // Contains PHO_PAGE -> return true
        assertTrue(diaryWithPhoPage.hasPage(PHO_PAGE));

        // Contains SUSHI_PAGE -> return false
        assertFalse(diaryWithPhoPage.hasPage(SUSHI_PAGE));
    }
}
