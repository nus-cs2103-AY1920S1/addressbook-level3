package organice.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
 * Contains integration tests (interaction with the Model) for {@code ExactFindCommand}.
 */
public class ExactFindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PersonContainsPrefixesPredicate firstPredicate =
                new PersonContainsPrefixesPredicate(
                        ArgumentTokenizer.tokenize(ExactFindCommand.COMMAND_WORD + " n/Alice", PREFIX_NAME));
        PersonContainsPrefixesPredicate secondPredicate =
                new PersonContainsPrefixesPredicate(
                        ArgumentTokenizer.tokenize(ExactFindCommand.COMMAND_WORD + " n/Benson", PREFIX_NAME));
        ExactFindCommand findFirstCommand = new ExactFindCommand(firstPredicate);
        ExactFindCommand findSecondCommand = new ExactFindCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        ExactFindCommand findFirstCommandCopy = new ExactFindCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different person -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonContainsPrefixesPredicate predicate = preparePredicate(ExactFindCommand.COMMAND_WORD + " ");
        ExactFindCommand command = new ExactFindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PersonContainsPrefixesPredicate predicate =
                preparePredicate(ExactFindCommand.COMMAND_WORD + " n/Kurz n/Elle n/Kunz");
        ExactFindCommand command = new ExactFindCommand(predicate);
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
