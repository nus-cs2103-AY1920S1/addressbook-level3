package dukecooks.logic.commands.health;

import static dukecooks.logic.commands.CommandTestUtil.VALID_TYPE_CALORIES;
import static dukecooks.logic.commands.CommandTestUtil.assertCommandSuccess;
import static dukecooks.testutil.health.TypicalRecords.GLUCOSE_FIRST;
import static dukecooks.testutil.health.TypicalRecords.getTypicalHealthRecords;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.Type;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListHealthCommand.
 */
public class ListHealthByTypeCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalHealthRecords(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalHealthRecords(), new UserPrefs());
    }

    @Test
    public void equals() {
        Predicate<Record> firstPredicate = x -> x.getType().equals(Type.valueOf(CommandTestUtil.VALID_TYPE_GLUCOSE));
        Predicate<Record> secondPredicate = x -> x.getType().equals(Type.valueOf(CommandTestUtil.VALID_TYPE_CALORIES));

        ListHealthByTypeCommand findFirstCommand = new ListHealthByTypeCommand(firstPredicate);
        ListHealthByTypeCommand findSecondCommand = new ListHealthByTypeCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        ListHealthByTypeCommand findFirstCommandCopy = new ListHealthByTypeCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicate
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void execute_zeroRecords_noRecordsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_RECORDS_LISTED_OVERVIEW, 0);
        Predicate<Record> predicate = x -> x.getType().equals("hello");
        ListHealthByTypeCommand command = new ListHealthByTypeCommand(predicate);
        expectedModel.updateFilteredRecordList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecordList());
    }

    @Test
    public void execute_multipleRecords_multipleRecordsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_RECORDS_LISTED_OVERVIEW, 1);
        Predicate<Record> predicate = x -> x.getType().equals(Type.valueOf(CommandTestUtil.VALID_TYPE_GLUCOSE));
        ListHealthByTypeCommand command = new ListHealthByTypeCommand(predicate);
        expectedModel.updateFilteredRecordList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GLUCOSE_FIRST), model.getFilteredRecordList());
    }

    @Test
    public void execute_recordListIsFiltered_showsEverything() {
        assertCommandSuccess(new ListHealthCommand(), model, ListHealthCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_getCurrentTypeView() {
        ListHealthByTypeCommand.setCurrentTypeView(Type.valueOf(VALID_TYPE_CALORIES));
        assertEquals(Type.valueOf(VALID_TYPE_CALORIES), ListHealthByTypeCommand.getCurrentTypeView());
    }
}
