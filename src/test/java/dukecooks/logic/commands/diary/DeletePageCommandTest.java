package dukecooks.logic.commands.diary;

import static dukecooks.testutil.diary.TypicalDiaries.ALL_MEAT;
import static dukecooks.testutil.diary.TypicalDiaries.getTypicalDiaryRecords;
import static dukecooks.testutil.diary.TypicalPages.PHO_PAGE;
import static dukecooks.testutil.diary.TypicalPages.SUSHI_PAGE;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.ModelStub;
import dukecooks.model.UserPrefs;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.DiaryName;
import dukecooks.model.diary.components.Page;
import dukecooks.testutil.Assert;
import dukecooks.testutil.TypicalIndexes;
import dukecooks.testutil.diary.PageBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeletePageCommandTest {

    private Model model = new ModelManager(getTypicalDiaryRecords(), new UserPrefs());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        int size = model.getFilteredDiaryList().size();
        Diary targetDiary = model.getFilteredDiaryList().get(size - 1);
        int sizeOfPages = targetDiary.getPages().size();
        Index outOfBoundIndex = Index.fromOneBased(sizeOfPages + 1);
        DeletePageCommand deleteCommand = new DeletePageCommand(outOfBoundIndex, targetDiary.getDiaryName());

        assertFalse(targetDiary.getPages().size() < sizeOfPages, Messages.MESSAGE_INVALID_PAGE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws CommandException {
        Page validPage = new PageBuilder().build();
        ArrayList<Page> pageList = new ArrayList<>(Arrays.asList(validPage));
        ObservableList<Page> pages = FXCollections.observableArrayList(pageList);

        Diary validDiary = new Diary(new DiaryName("Asian Food"), pages);
        ModelStub modelStub = new ModelStubWithDiary(validDiary);

        CommandResult commandResult =
                new DeletePageCommand(Index.fromOneBased(1), validDiary.getDiaryName()).execute(modelStub);
        assertEquals(String.format(DeletePageCommand.MESSAGE_DELETE_PAGE_SUCCESS, validPage.getTitle()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_nonExistentDiary_throwsCommandException() {
        Page validPage = new PageBuilder().build();
        ArrayList<Page> pageList = new ArrayList<>(Arrays.asList(validPage));
        ObservableList<Page> pages = FXCollections.observableArrayList(pageList);

        Diary validDiary = new Diary(new DiaryName("Asian Food"), pages);
        ModelStub modelStub = new ModelStubWithDiary(validDiary);

        DeletePageCommand deletePageCommand = new DeletePageCommand(Index.fromOneBased(1), new DiaryName("Hello123"));
        Assert.assertThrows(CommandException.class,
                DeletePageCommand.MESSAGE_NON_EXISTENT_DIARY, () -> deletePageCommand.execute(modelStub));
    }

    @Test
    public void execute_indexOutOfBounds_throwsCommandException() {
        Page validPage = new PageBuilder().build();
        ArrayList<Page> pageList = new ArrayList<>(Arrays.asList(validPage));
        ObservableList<Page> pages = FXCollections.observableArrayList(pageList);

        Diary validDiary = new Diary(new DiaryName("Asian Food"), pages);
        ModelStub modelStub = new ModelStubWithDiary(validDiary);

        DeletePageCommand deletePageCommand = new DeletePageCommand(Index.fromOneBased(2), validDiary.getDiaryName());
        Assert.assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_PAGE_DISPLAYED_INDEX, () -> deletePageCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Diary diary = ALL_MEAT;
        diary.getPages().add(PHO_PAGE);
        diary.getPages().add(SUSHI_PAGE);
        DeletePageCommand deleteFirstCommand =
                new DeletePageCommand(TypicalIndexes.INDEX_FIRST_PAGE, diary.getDiaryName());
        DeletePageCommand deleteSecondCommand =
                new DeletePageCommand(TypicalIndexes.INDEX_SECOND_PAGE, diary.getDiaryName());

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePageCommand deleteFirstCommandCopy =
                new DeletePageCommand(TypicalIndexes.INDEX_FIRST_DIARY, diary.getDiaryName());
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different delete page commands -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * A Model stub that contains a single diary.
     */
    class ModelStubWithDiary extends ModelStub {
        private Diary diary;

        ModelStubWithDiary(Diary diary) {
            requireNonNull(diary);
            this.diary = diary;
        }

        @Override
        public boolean hasDiary(Diary diary) {
            requireNonNull(diary);
            return this.diary.isSameDiary(diary);
        }

        @Override
        public ObservableList<Diary> getFilteredDiaryList() {
            ArrayList<Diary> diaryList = new ArrayList<>(Arrays.asList(diary));
            return FXCollections.observableList(diaryList);
        }

        @Override
        public void setDiary(Diary target, Diary editedDiary) {
            requireNonNull(target);
            requireNonNull(editedDiary);
            this.diary = editedDiary;
        }
    }
}
