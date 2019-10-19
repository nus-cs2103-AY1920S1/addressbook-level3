package seedu.address.logic.commands.findcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PHONE_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCustomers.getTypicalCustomerBook;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalPhones.IPHONEONE;
import static seedu.address.testutil.TypicalPhones.IPHONETWO;
import static seedu.address.testutil.TypicalPhones.IPHONEXR;
import static seedu.address.testutil.TypicalPhones.getTypicalPhoneBook;
import static seedu.address.testutil.TypicalSchedules.getTypicalScheduleBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.phone.Phone;
import seedu.address.model.phone.predicates.PhoneNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindPhoneCommandTest {


    private Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
            getTypicalOrderBook(), getTypicalScheduleBook(), new UserPrefs());

    private Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
            getTypicalOrderBook(), getTypicalScheduleBook(), new UserPrefs());

    @Test
    public void equals() {
        Predicate<Phone> firstPredicate =
                new PhoneNameContainsKeywordsPredicate(Collections.singletonList("first"));
        Predicate<Phone> secondPredicate =
                new PhoneNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindPhoneCommand findFirstCommand = new FindPhoneCommand(firstPredicate);
        FindPhoneCommand findSecondCommand = new FindPhoneCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindPhoneCommand findFirstCommandCopy = new FindPhoneCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different phone -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PHONE_LISTED_OVERVIEW, 0);
        PhoneNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindPhoneCommand command = new FindPhoneCommand(predicate);
        expectedModel.updateFilteredPhoneList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPhoneList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PHONE_LISTED_OVERVIEW, 3);
        PhoneNameContainsKeywordsPredicate predicate = preparePredicate("iPhone");
        FindPhoneCommand command = new FindPhoneCommand(predicate);
        expectedModel.updateFilteredPhoneList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(IPHONEONE, IPHONETWO, IPHONEXR), model.getFilteredPhoneList());
    }

    /**
     * Parses {@code userInput} into a {@code CustomerNameContainsKeywordsPredicate}.
     */
    private PhoneNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new PhoneNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
