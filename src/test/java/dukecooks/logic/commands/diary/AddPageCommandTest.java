package dukecooks.logic.commands.diary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.Page;
import dukecooks.testutil.Assert;
import dukecooks.testutil.diary.DiaryBuilder;
import dukecooks.testutil.diary.PageBuilder;

public class AddPageCommandTest {

    @Test
    public void constructor_nullPage_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddPageCommand(null, null));
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

        AddPageCommand addAliceCommand = new AddPageCommand(alice, validDiary.getDiaryName());
        AddPageCommand addBobCommand = new AddPageCommand(bob, validDiary.getDiaryName());

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddPageCommand addAliceCommandCopy = new AddPageCommand(alice, validDiary.getDiaryName());
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different recipe -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

}
