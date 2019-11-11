package organice.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import organice.logic.parser.ArgumentMultimap;
import organice.logic.parser.ArgumentTokenizer;
import organice.model.Model;
import organice.model.ModelManager;
import organice.model.UserPrefs;

public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void equals() {
        ArgumentMultimap firstArgMultimap = ArgumentTokenizer
                .tokenize(FindCommand.COMMAND_WORD + " n/Alice", PREFIX_NAME);
        ArgumentMultimap secondArgMultimap =
                ArgumentTokenizer.tokenize(FindCommand.COMMAND_WORD + " n/Benson", PREFIX_NAME);
        FindCommand findFirstCommand = new FindCommand(firstArgMultimap);
        FindCommand findSecondCommand = new FindCommand(secondArgMultimap);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstArgMultimap);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different person -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_nonMatchingKeywords_noPersonFound() {
        ArgumentMultimap argMutlimap = ArgumentTokenizer.tokenize(" n/Talesin Jaffe", PREFIX_NAME);
        FindCommand command = new FindCommand(argMutlimap);
        CommandResult findResult = command.execute(model);
        assertEquals(findResult.getFeedbackToUser(), "Found 0 exact matches and 0 possible matches!");
        assertEquals(Collections.emptyList(), model.getDisplayedPersonList());
    }

    @Test
    public void execute_oneMatchingKeyword_oneExactEightPossibleMatches() {
        ArgumentMultimap argMutlimap = ArgumentTokenizer.tokenize(" n/Alice", PREFIX_NAME);
        FindCommand command = new FindCommand(argMutlimap);
        CommandResult findResult = command.execute(model);
        assertEquals(findResult.getFeedbackToUser(), "Found 1 exact matches and 8 possible matches!");
        assertEquals(model.getDisplayedPersonList().size(), 9);
    }

}
