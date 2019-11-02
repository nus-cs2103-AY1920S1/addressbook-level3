package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.KeyboardFlashCardsParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

//@@author keiteo
public class StartCommandTest {

    private KeyboardFlashCardsParser keyboardFlashCardsParser = new KeyboardFlashCardsParser();
    private Model model = new ModelManager();

    @Test
    public void execute_noFlashCards_success() {
        Command startCommand = new StartCommand(keyboardFlashCardsParser, "");
        try {
            CommandResult result = startCommand.execute(model);
            assertEquals(StartCommand.MESSAGE_NO_FLASHCARDS, result.getFeedbackToUser());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void execute_unableToFindTag_success() {
        String tagToFind = "thisTagDoesNotExist";
        Command startCommand = new StartCommand(keyboardFlashCardsParser, tagToFind);
        try {
            CommandResult result = startCommand.execute(model);
            assertEquals(StartCommand.MESSAGE_NO_FLASHCARDS, result.getFeedbackToUser());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void equals() {
        // assertTrue/False is used instead of assertEquals/NotEquals to specifically test equals()
        // but please correct me if assertEquals is actually using the class equals method

        Command noTag = new StartCommand(keyboardFlashCardsParser, "");
        Command tag = new StartCommand(keyboardFlashCardsParser, "tag");

        // same object -> returns true
        assertTrue(noTag.equals(noTag));
        assertTrue(tag.equals(tag));

        // same values -> returns true
        Command noTagCopy = new StartCommand(keyboardFlashCardsParser, "");
        Command tagCopy = new StartCommand(keyboardFlashCardsParser, "tag");
        assertTrue(noTag.equals(noTagCopy));
        assertTrue(tag.equals(tagCopy));

        // different types -> returns false
        assertFalse(noTag.equals(1));
        assertFalse(tag.equals(1));

        // null -> returns false
        assertFalse(noTag.equals(null));
        assertFalse(tag.equals(null));

        // different tags -> returns false
        Command anotherTag = new StartCommand(keyboardFlashCardsParser, "anotherTag");
        assertFalse(tag.equals(noTag));
        assertFalse(tag.equals(anotherTag));

        // different abp object
        Command diffAbp = new StartCommand(new KeyboardFlashCardsParser(), "");
        assertFalse(noTag.equals(diffAbp));
    }
}
