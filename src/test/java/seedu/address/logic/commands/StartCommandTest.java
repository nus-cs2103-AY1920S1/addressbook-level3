package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

//@@author keiteo
public class StartCommandTest {

    private AddressBookParser addressBookParser = new AddressBookParser();
    private Model model = new ModelManager();

    @Test
    public void execute_noFlashCards_success() {
        Command startCommand = new StartCommand(addressBookParser);
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
        Command startCommand = new StartCommand(addressBookParser, tagToFind);
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

        Command noTag = new StartCommand(addressBookParser);
        Command tag = new StartCommand(addressBookParser, "tag");

        // same object -> returns true
        assertTrue(noTag.equals(noTag));
        assertTrue(tag.equals(tag));

        // same values -> returns true
        Command noTagCopy = new StartCommand(addressBookParser);
        Command tagCopy = new StartCommand(addressBookParser, "tag");
        assertTrue(noTag.equals(noTagCopy));
        assertTrue(tag.equals(tagCopy));

        // different types -> returns false
        assertFalse(noTag.equals(1));
        assertFalse(tag.equals(1));

        // null -> returns false
        assertFalse(noTag.equals(null));
        assertFalse(tag.equals(null));

        // different tags -> returns false
        Command anotherTag = new StartCommand(addressBookParser, "anotherTag");
        assertFalse(tag.equals(noTag));
        assertFalse(tag.equals(anotherTag));

        // different abp object
        Command diffAbp = new StartCommand(new AddressBookParser());
        assertFalse(noTag.equals(diffAbp));
    }
}
