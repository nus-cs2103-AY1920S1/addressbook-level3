package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void execute_unableToFindTagEmptyTest_success() {
        String tagToFind = "thisTagDoesNotExist";
        Command startCommand = new StartCommand(addressBookParser, tagToFind);
        try {
            CommandResult result = startCommand.execute(model);
            assertEquals(StartCommand.MESSAGE_NO_FLASHCARDS, result.getFeedbackToUser());
        } catch (Exception e) {
            fail();
        }
    }
}
