package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_BODIES_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_NO_FLAG;
import static seedu.address.commons.core.Messages.MESSAGE_WORKERS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAUSE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_JOINED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_ADMISSION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESIGNATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRIDGE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANS_FOR_DONATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELIGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalBodies.BOB;
import static seedu.address.testutil.TypicalBodies.CHARLES;
import static seedu.address.testutil.TypicalWorkers.BENSON;
import static seedu.address.testutil.TypicalWorkers.CHARLIE;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entity.body.BodyContainsAttributesKeywordsPredicate;
import seedu.address.model.entity.worker.WorkerContainsAttributesKeywordsPredicate;

// @@author dalisc
/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {

    private static final String BODY_FLAG = "b";
    private static final String WORKER_FLAG = "w";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        String bodyArgs1 = "-b /sex male /cod natural";
        ArgumentMultimap bodyMap1 = ArgumentTokenizer.tokenize(bodyArgs1, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_EMPLOYMENT_STATUS, PREFIX_FLAG,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);

        String bodyArgs2 = "-b /sex female /cod natural /nric S1234567A";
        ArgumentMultimap bodyMap2 = ArgumentTokenizer.tokenize(bodyArgs2, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_EMPLOYMENT_STATUS, PREFIX_FLAG,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);

        FilterCommand filterFirstCommand = new FilterCommand(bodyMap1, BODY_FLAG);
        FilterCommand filterSecondCommand = new FilterCommand(bodyMap2, BODY_FLAG);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(bodyMap1, BODY_FLAG);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_noFlagGiven_invalidCommand() {
        String bodyArgs1 = "-b /sex male /cod natural";
        ArgumentMultimap bodyMap1 = ArgumentTokenizer.tokenize(bodyArgs1, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_EMPLOYMENT_STATUS, PREFIX_FLAG,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);
        String expectedMessage = MESSAGE_NO_FLAG;
        CommandResult expectedResult = new CommandResult(expectedMessage);
        BodyContainsAttributesKeywordsPredicate predicate = new BodyContainsAttributesKeywordsPredicate(bodyMap1);
        FilterCommand command = new FilterCommand(bodyMap1, "");
        expectedModel.updateFilteredBodyList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_zeroKeywords_noBodyFound() {
        String bodyArgs1 = "-b /sex somethingElse";
        ArgumentMultimap bodyMap1 = ArgumentTokenizer.tokenize(bodyArgs1, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_EMPLOYMENT_STATUS, PREFIX_FLAG,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);
        String expectedMessage = String.format(MESSAGE_BODIES_LISTED_OVERVIEW, 0);
        CommandResult expectedResult = new CommandResult(expectedMessage);
        BodyContainsAttributesKeywordsPredicate predicate = new BodyContainsAttributesKeywordsPredicate(bodyMap1);
        FilterCommand command = new FilterCommand(bodyMap1, BODY_FLAG);
        expectedModel.updateFilteredBodyList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredBodyList());
    }

    @Test
    public void execute_zeroKeywords_noWorkerFound() {
        String workerArgs1 = "-w /sex somethingElse";
        ArgumentMultimap workerMap1 = ArgumentTokenizer.tokenize(workerArgs1, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_EMPLOYMENT_STATUS, PREFIX_FLAG,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);
        String expectedMessage = String.format(MESSAGE_WORKERS_LISTED_OVERVIEW, 0);
        CommandResult expectedResult = new CommandResult(expectedMessage);
        WorkerContainsAttributesKeywordsPredicate predicate = new WorkerContainsAttributesKeywordsPredicate(workerMap1);
        FilterCommand command = new FilterCommand(workerMap1, WORKER_FLAG);
        expectedModel.updateFilteredWorkerList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredWorkerList());
    }

    @Test
    public void execute_multipleKeywords_multipleBodiesFound() {
        String bodyArgs1 = "-b /sex male";
        ArgumentMultimap bodyMap1 = ArgumentTokenizer.tokenize(bodyArgs1, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_EMPLOYMENT_STATUS, PREFIX_FLAG,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);
        String expectedMessage = String.format(MESSAGE_BODIES_LISTED_OVERVIEW, 2);
        CommandResult expectedResult = new CommandResult(expectedMessage);
        BodyContainsAttributesKeywordsPredicate predicate = new BodyContainsAttributesKeywordsPredicate(bodyMap1);
        FilterCommand command = new FilterCommand(bodyMap1, BODY_FLAG);
        expectedModel.updateFilteredBodyList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        assertEquals(Arrays.asList(BOB, CHARLES), model.getFilteredBodyList());
    }

    @Test
    public void execute_multipleKeywords_multipleWorkersFound() {
        String workerArgs1 = "-w /employmentStatus transporting";
        ArgumentMultimap workerMap1 = ArgumentTokenizer.tokenize(workerArgs1, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_EMPLOYMENT_STATUS, PREFIX_FLAG,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);
        String expectedMessage = String.format(MESSAGE_WORKERS_LISTED_OVERVIEW, 2);
        CommandResult expectedResult = new CommandResult(expectedMessage);
        WorkerContainsAttributesKeywordsPredicate predicate = new WorkerContainsAttributesKeywordsPredicate(workerMap1);
        expectedModel.updateFilteredWorkerList(predicate);
        FilterCommand command = new FilterCommand(workerMap1, WORKER_FLAG);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        assertEquals(Arrays.asList(BENSON, CHARLIE), model.getFilteredWorkerList());
    }
}
