package organice.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static organice.logic.commands.CommandTestUtil.assertCommandSuccess;
import static organice.logic.parser.CliSyntax.PREFIX_AGE;
import static organice.logic.parser.CliSyntax.PREFIX_BLOOD_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_DOCTOR_IN_CHARGE;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN_EXPIRY_DATE;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;
import static organice.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static organice.logic.parser.CliSyntax.PREFIX_TISSUE_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;
import static organice.testutil.TypicalPersons.DONOR_ELLE;
import static organice.testutil.TypicalPersons.DONOR_FIONA;
import static organice.testutil.TypicalPersons.PATIENT_CARL;
import static organice.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import organice.logic.parser.ArgumentTokenizer;
import organice.model.Model;
import organice.model.ModelManager;
import organice.model.UserPrefs;
import organice.model.person.PersonContainsPrefixesPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PersonContainsPrefixesPredicate firstPredicate =
                new PersonContainsPrefixesPredicate(
                        ArgumentTokenizer.tokenize(FindCommand.COMMAND_WORD + " n/Alice", PREFIX_NAME));
        PersonContainsPrefixesPredicate secondPredicate =
                new PersonContainsPrefixesPredicate(
                        ArgumentTokenizer.tokenize(FindCommand.COMMAND_WORD + " n/Benson", PREFIX_NAME));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonContainsPrefixesPredicate predicate = preparePredicate(FindCommand.COMMAND_WORD + " ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PersonContainsPrefixesPredicate predicate =
                preparePredicate(FindCommand.COMMAND_WORD + " n/Kurz n/Elle n/Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(PATIENT_CARL, DONOR_ELLE, DONOR_FIONA), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private PersonContainsPrefixesPredicate preparePredicate(String userInput) {
        //TODO: Replace ArgumentTokenizer with stubs
        return new PersonContainsPrefixesPredicate(ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_NRIC,
                PREFIX_PHONE, PREFIX_TYPE, PREFIX_AGE, PREFIX_PRIORITY, PREFIX_BLOOD_TYPE, PREFIX_DOCTOR_IN_CHARGE,
                PREFIX_TISSUE_TYPE, PREFIX_ORGAN_EXPIRY_DATE, PREFIX_ORGAN));
    }
}
