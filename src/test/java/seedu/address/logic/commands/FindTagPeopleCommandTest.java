package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertListPeopleCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonPossessesTagsPredicate;
import seedu.address.model.tag.Tag;


/**
 * Contains integration tests (interaction with the Model) for {@code FindTagPeopleCommand}.
 */
public class FindTagPeopleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        List<String> firstInput = Collections.singletonList("first");
        List<String> secondInput = Collections.singletonList("second");


        FindTagPeopleCommand findFirstCommand = new FindTagPeopleCommand(firstInput);
        FindTagPeopleCommand findSecondCommand = new FindTagPeopleCommand(secondInput);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindTagPeopleCommand findFirstCommandCopy = new FindTagPeopleCommand(firstInput);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroTags_throwsCommandException() {
        FindTagPeopleCommand command = new FindTagPeopleCommand(prepareInput(" "));
        assertCommandFailure(command, model,
            String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                FindTagPeopleCommand.MESSAGE_USAGE
            )
        );
    }

    @Test
    public void execute_singleTag_singlePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        List<String> tagNames = prepareInput(" t/high blood pressure");
        FindTagPeopleCommand command = new FindTagPeopleCommand(tagNames);
        PersonPossessesTagsPredicate predicate = new PersonPossessesTagsPredicate(
                Collections.singletonList(new Tag("high blood pressure")));
        expectedModel.updateFilteredPersonList(predicate);
        assertListPeopleCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code List<String>}.
     */
    private List<String> prepareInput(String userInput) {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_TAG);
        List<String> tagNames = argMultimap.getAllValues(PREFIX_TAG);
        return tagNames;
    }
}
