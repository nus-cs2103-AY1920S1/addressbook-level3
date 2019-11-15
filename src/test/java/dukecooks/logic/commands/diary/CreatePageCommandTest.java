package dukecooks.logic.commands.diary;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.ModelStub;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.DiaryName;
import dukecooks.model.diary.components.Page;
import dukecooks.testutil.Assert;
import dukecooks.testutil.diary.DiaryBuilder;
import dukecooks.testutil.diary.PageBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CreatePageCommandTest {

    @Test
    public void constructor_nullPage_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new CreatePageCommand(null, null));
    }

    @Test
    public void execute_pageAcceptedByModel_addSuccessful() throws Exception {
        Diary validDiary = new DiaryBuilder().build();
        Page validPage = new PageBuilder().build();
        ModelStub modelStub = new ModelStubWithDiary(validDiary);

        CommandResult commandResult = new CreatePageCommand(validPage, validDiary.getDiaryName()).execute(modelStub);
        assertEquals(String.format(CreatePageCommand.MESSAGE_SUCCESS, validPage.getTitle()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_duplicatePage_throwsCommandException() {
        Page validPage = new PageBuilder().build();
        ArrayList<Page> pageList = new ArrayList<>(Arrays.asList(validPage));
        ObservableList<Page> pages = FXCollections.observableArrayList(pageList);

        Diary validDiary = new Diary(new DiaryName("Asian Food"), pages);
        ModelStub modelStub = new ModelStubWithDiary(validDiary);

        CreatePageCommand createPageCommand = new CreatePageCommand(validPage, validDiary.getDiaryName());

        Assert.assertThrows(CommandException.class, CreatePageCommand.MESSAGE_DUPLICATE_PAGE, () -> createPageCommand
                .execute(modelStub));
    }

    @Test
    public void execute_nonExistentDiary_throwsCommandException() {
        Page validPage = new PageBuilder().build();
        ArrayList<Page> pageList = new ArrayList<>(Arrays.asList(validPage));
        ObservableList<Page> pages = FXCollections.observableArrayList(pageList);

        Diary validDiary = new Diary(new DiaryName("Asian Food"), pages);
        ModelStub modelStub = new ModelStubWithDiary(validDiary);

        CreatePageCommand createPageCommand = new CreatePageCommand(validPage, new DiaryName("Hello123"));

        Assert.assertThrows(CommandException.class, CreatePageCommand.MESSAGE_NON_EXISTENT_DIARY, () ->
                createPageCommand.execute(modelStub));
    }


    @Test
    public void equals() {
        Page alice =
                new PageBuilder().withTitle("Alice").withPageType("health")
                        .withPageDescription("A simple description!").withImage("/images/sushi.jpg").build();
        Page bob =
                new PageBuilder().withTitle("Bob").withPageType("health")
                        .withPageDescription("A simple description!").withImage("/images/sushi.jpg").build();

        Diary validDiary = new DiaryBuilder().build();

        CreatePageCommand addAliceCommand = new CreatePageCommand(alice, validDiary.getDiaryName());
        CreatePageCommand addBobCommand = new CreatePageCommand(bob, validDiary.getDiaryName());

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        CreatePageCommand addAliceCommandCopy = new CreatePageCommand(alice, validDiary.getDiaryName());
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different recipe -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
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
