package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_POLICIES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertListPolicyCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPolicy.LIFE_INSURANCE;

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
import seedu.address.model.policy.PolicyPossessesTagsPredicate;
import seedu.address.model.tag.Tag;


/**
 * Contains integration tests (interaction with the Model) for {@code FindTagPolicyCommand}.
 */
public class FindTagPolicyCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        List<String> firstInput = Collections.singletonList("first");
        List<String> secondInput = Collections.singletonList("second");


        FindTagPolicyCommand findFirstCommand = new FindTagPolicyCommand(firstInput);
        FindTagPolicyCommand findSecondCommand = new FindTagPolicyCommand(secondInput);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindTagPolicyCommand findFirstCommandCopy = new FindTagPolicyCommand(firstInput);
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
        FindTagPolicyCommand command = new FindTagPolicyCommand(prepareInput(" "));
        assertCommandFailure(command, model,
                String.format(
                        Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        FindTagPolicyCommand.MESSAGE_USAGE
                )
        );
    }

    @Test
    public void execute_singleTag_singlePolicyFound() {
        String expectedMessage = String.format(MESSAGE_POLICIES_LISTED_OVERVIEW, 1);
        List<String> tagNames = prepareInput(" t/term insurance");
        FindTagPolicyCommand command = new FindTagPolicyCommand(tagNames);
        PolicyPossessesTagsPredicate predicate = new PolicyPossessesTagsPredicate(
                Collections.singletonList(new Tag("term insurance")));
        expectedModel.updateFilteredPolicyList(predicate);
        assertListPolicyCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(LIFE_INSURANCE), model.getFilteredPolicyList());
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
