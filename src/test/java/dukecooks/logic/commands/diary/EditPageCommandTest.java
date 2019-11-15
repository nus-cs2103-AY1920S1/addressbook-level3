package dukecooks.logic.commands.diary;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.diary.EditPageCommand.EditPageDescriptor;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.ModelStub;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.DiaryName;
import dukecooks.model.diary.components.Page;
import dukecooks.testutil.Assert;
import dukecooks.testutil.diary.DiaryBuilder;
import dukecooks.testutil.diary.EditPageDescriptorBuilder;
import dukecooks.testutil.diary.PageBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditPageCommand.
 */
public class EditPageCommandTest {

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws CommandException {
        Page editedPage = new PageBuilder().build();
        ArrayList<Page> pageList = new ArrayList<>(Arrays.asList(editedPage));
        ObservableList<Page> pages = FXCollections.observableArrayList(pageList);

        Diary validDiary = new Diary(new DiaryName("Asian Food"), pages);
        ModelStub modelStub = new ModelStubWithDiary(validDiary);


        EditPageDescriptor descriptor = new EditPageDescriptorBuilder(editedPage).build();
        EditPageCommand editPageCommand = new EditPageCommand(Index.fromOneBased(1), validDiary.getDiaryName(),
                descriptor);

        CommandResult commandResult = editPageCommand.execute(modelStub);
        assertEquals(String.format(EditPageCommand.MESSAGE_EDIT_PAGE_SUCCESS, editedPage.getTitle()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws CommandException {
        Page editedPage = new PageBuilder().build();
        ArrayList<Page> pageList = new ArrayList<>(Arrays.asList(editedPage));
        ObservableList<Page> pages = FXCollections.observableArrayList(pageList);

        Diary validDiary = new Diary(new DiaryName("Asian Food"), pages);
        ModelStub modelStub = new ModelStubWithDiary(validDiary);


        EditPageDescriptor descriptor = new EditPageDescriptorBuilder().withTitle(CommandTestUtil.VALID_SUSHI_TITLE)
                .withImage(CommandTestUtil.VALID_SUSHI_IMAGE).build();
        EditPageCommand editPageCommand = new EditPageCommand(Index.fromOneBased(1), validDiary.getDiaryName(),
                descriptor);

        CommandResult commandResult = editPageCommand.execute(modelStub);
        assertEquals(String.format(EditPageCommand.MESSAGE_EDIT_PAGE_SUCCESS, editedPage.getTitle()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_duplicatePageUnfilteredList_failure() {
        Page editedPage = new PageBuilder().build();
        Page nextPage = new PageBuilder().withTitle("Pho").withPageType("food").withPageDescription("Test description")
                .withImage("/images/pho.jpg").build();
        ArrayList<Page> pageList = new ArrayList<>(Arrays.asList(editedPage, nextPage));
        ObservableList<Page> pages = FXCollections.observableArrayList(pageList);

        Diary validDiary = new Diary(new DiaryName("Asian Food"), pages);
        ModelStub modelStub = new ModelStubWithDiary(validDiary);


        EditPageDescriptor descriptor = new EditPageDescriptorBuilder().withTitle(CommandTestUtil.VALID_SUSHI_TITLE)
                .withImage(CommandTestUtil.VALID_SUSHI_IMAGE).build();
        EditPageCommand editPageCommand = new EditPageCommand(Index.fromOneBased(2), validDiary.getDiaryName(),
                descriptor);

        Assert.assertThrows(CommandException.class, EditPageCommand.MESSAGE_DUPLICATE_PAGE, () -> editPageCommand
                .execute(modelStub));
    }

    @Test
    public void execute_diaryNotFound_failure() {
        Page editedPage = new PageBuilder().build();
        ArrayList<Page> pageList = new ArrayList<>(Arrays.asList(editedPage));
        ObservableList<Page> pages = FXCollections.observableArrayList(pageList);

        Diary validDiary = new Diary(new DiaryName("Asian Food"), pages);
        ModelStub modelStub = new ModelStubWithDiary(validDiary);


        EditPageDescriptor descriptor = new EditPageDescriptorBuilder().withTitle(CommandTestUtil.VALID_SUSHI_TITLE)
                .withImage(CommandTestUtil.VALID_SUSHI_IMAGE).build();
        EditPageCommand editPageCommand = new EditPageCommand(Index.fromOneBased(1), new DiaryName("Hello123"),
                descriptor);

        Assert.assertThrows(CommandException.class, EditPageCommand.MESSAGE_DIARY_NOT_FOUND, () -> editPageCommand
                .execute(modelStub));
    }

    @Test
    public void execute_invalidPageIndex_failure() {
        Page editedPage = new PageBuilder().build();
        ArrayList<Page> pageList = new ArrayList<>(Arrays.asList(editedPage));
        ObservableList<Page> pages = FXCollections.observableArrayList(pageList);

        Diary validDiary = new Diary(new DiaryName("Asian Food"), pages);
        ModelStub modelStub = new ModelStubWithDiary(validDiary);


        EditPageDescriptor descriptor = new EditPageDescriptorBuilder().withTitle(CommandTestUtil.VALID_SUSHI_TITLE)
                .withImage(CommandTestUtil.VALID_SUSHI_IMAGE).build();
        EditPageCommand editPageCommand = new EditPageCommand(Index.fromOneBased(2), validDiary.getDiaryName(),
                descriptor);

        Assert.assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_PAGE_DISPLAYED_INDEX, () -> editPageCommand.execute(modelStub));
    }

    @Test
    public void equals() {

        Diary validDiary = new DiaryBuilder().build();

        final EditPageCommand standardCommand = new EditPageCommand(Index.fromOneBased(1),
                validDiary.getDiaryName(), CommandTestUtil.DESC_PHO_PAGE);

        // same values -> returns true
        EditPageDescriptor copyDescriptor = new EditPageDescriptor(CommandTestUtil.DESC_PHO_PAGE);
        EditPageCommand commandWithSameValues = new EditPageCommand(Index.fromOneBased(1),
                validDiary.getDiaryName(), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditPageCommand(Index.fromOneBased(2), validDiary.getDiaryName(),
                CommandTestUtil.DESC_PHO_PAGE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditPageCommand(Index.fromOneBased(1), validDiary.getDiaryName(),
                CommandTestUtil.DESC_SUSHI_PAGE)));
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

        @Override
        public void updateFilteredDiaryList(Predicate<Diary> predicate) {
            requireNonNull(predicate);
            FilteredList<Diary> filteredDiary = new FilteredList<>(getFilteredDiaryList());
            filteredDiary.setPredicate(predicate);
        }
    }

}
