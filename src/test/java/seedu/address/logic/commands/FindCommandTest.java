package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_BODIES_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_NO_FLAG;
import static seedu.address.commons.core.Messages.MESSAGE_WORKERS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalBodies.ALICE;
import static seedu.address.testutil.TypicalBodies.BOB;
import static seedu.address.testutil.TypicalWorkers.BENSON;
import static seedu.address.testutil.TypicalWorkers.CHARLIE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entity.body.BodyNameContainsKeywordsPredicate;
import seedu.address.model.entity.worker.WorkerNameContainsKeywordsPredicate;

// @@author dalisc

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {

    private static final String BODY_FLAG = "b";

    private static final String WORKER_FLAG = "w";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        List<String> firstList = Collections.singletonList("first");
        List<String> secondList = Collections.singletonList("second");

        FindCommand findFirstCommand = new FindCommand(firstList, BODY_FLAG);
        FindCommand findSecondCommand = new FindCommand(secondList, BODY_FLAG);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstList, BODY_FLAG);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_noFlagGiven_invalidCommand() {
        String expectedMessage = MESSAGE_NO_FLAG;
        CommandResult expectedResult = new CommandResult(expectedMessage);
        List<String> predicateList = prepareList(" ");
        BodyNameContainsKeywordsPredicate predicate = new BodyNameContainsKeywordsPredicate(predicateList);
        FindCommand command = new FindCommand(predicateList, "");
        expectedModel.updateFilteredBodyList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_zeroKeywords_noBodyFound() {
        String expectedMessage = String.format(MESSAGE_BODIES_LISTED_OVERVIEW, 0);
        CommandResult expectedResult = new CommandResult(expectedMessage);
        List<String> predicateList = prepareList(" ");
        BodyNameContainsKeywordsPredicate predicate = new BodyNameContainsKeywordsPredicate(predicateList);
        FindCommand command = new FindCommand(predicateList, BODY_FLAG);
        expectedModel.updateFilteredBodyList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredBodyList());
    }

    @Test
    public void execute_zeroKeywords_noWorkerFound() {
        String expectedMessage = String.format(MESSAGE_WORKERS_LISTED_OVERVIEW, 0);
        CommandResult expectedResult = new CommandResult(expectedMessage);
        List<String> predicateList = prepareList(" ");
        WorkerNameContainsKeywordsPredicate predicate = new WorkerNameContainsKeywordsPredicate(predicateList);
        FindCommand command = new FindCommand(predicateList, WORKER_FLAG);
        expectedModel.updateFilteredWorkerList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredWorkerList());
    }

    @Test
    public void execute_multipleKeywords_multipleBodiesFound() {
        String expectedMessage = String.format(MESSAGE_BODIES_LISTED_OVERVIEW, 2);
        CommandResult expectedResult = new CommandResult(expectedMessage);
        List<String> predicateList = prepareList("Alice Bob");
        BodyNameContainsKeywordsPredicate predicate = new BodyNameContainsKeywordsPredicate(predicateList);
        FindCommand command = new FindCommand(predicateList, BODY_FLAG);
        expectedModel.updateFilteredBodyList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        System.out.println(model.getFilteredBodyList());
        assertEquals(Arrays.asList(ALICE, BOB), model.getFilteredBodyList());
    }

    @Test
    public void execute_multipleKeywords_multipleWorkersFound() {
        String expectedMessage = String.format(MESSAGE_WORKERS_LISTED_OVERVIEW, 2);
        CommandResult expectedResult = new CommandResult(expectedMessage);
        List<String> predicateList = prepareList("Benson Charlie");
        WorkerNameContainsKeywordsPredicate predicate = new WorkerNameContainsKeywordsPredicate(predicateList);
        FindCommand command = new FindCommand(predicateList, WORKER_FLAG);
        expectedModel.updateFilteredWorkerList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        assertEquals(Arrays.asList(BENSON, CHARLIE), model.getFilteredWorkerList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private List<String> prepareList(String userInput) {
        return Arrays.asList(userInput.split("\\s+"));
    }
}
