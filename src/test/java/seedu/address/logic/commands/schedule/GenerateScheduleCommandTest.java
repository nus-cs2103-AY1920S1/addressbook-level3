/*
@@author shihaoyap
 */

package seedu.address.logic.commands.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDistinctDates.DATE_01_01_2020;
import static seedu.address.testutil.TypicalDistinctDates.DATE_05_12_2019;
import static seedu.address.testutil.TypicalDistinctDates.DATE_07_12_2019;
import static seedu.address.testutil.TypicalDistinctDates.DATE_10_10_2019;
import static seedu.address.testutil.TypicalDistinctDates.DATE_12_10_2019;
import static seedu.address.testutil.TypicalDistinctDates.DATE_13_10_2019;
import static seedu.address.testutil.TypicalDistinctDates.DATE_15_10_2019;
import static seedu.address.testutil.TypicalDistinctDates.DATE_31_12_2019;
import static seedu.address.testutil.TypicalEvents.getEmptyEventBook;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.processor.DistinctDatesProcessor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.distinctdate.DistinctDate;

/**
 * Contains integration tests (interaction with the Model) for {@code DisplayScheduleForYearMonthCommand}.
 */
public class GenerateScheduleCommandTest {

    private Model emptyModel = new ModelManager(getEmptyEventBook(), new UserPrefs());
    private Model emptyExpectedModel = new ModelManager(getEmptyEventBook(), new UserPrefs());

    private Model model = new ModelManager(getTypicalEventBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalEventBook(), new UserPrefs());

    @Test
    public void equals() {

        GenerateScheduleCommand generateFirstCommand = new GenerateScheduleCommand();

        // same object -> returns true
        assertTrue(generateFirstCommand.equals(generateFirstCommand));

        // different types -> returns false
        assertFalse(generateFirstCommand.equals(1));

        // null -> returns false
        assertFalse(generateFirstCommand.equals(null));
    }

    @Test
    public void execute_generateSchedule_noEventFound() {
        String expectedMessage = String.format(Messages.MESSAGE_GENERATE_FAILURE);
        GenerateScheduleCommand command = new GenerateScheduleCommand();
        List<DistinctDate> distinctDates = DistinctDatesProcessor.generateAllDistinctDateList(emptyExpectedModel);
        emptyExpectedModel.updateEventDistinctDatesList(distinctDates);
        assertCommandSuccess(command, emptyModel, expectedMessage, emptyExpectedModel);
        assertEquals(Collections.emptyList(), emptyModel.getEventDistinctDatesList());
    }

    @Test
    public void execute_generateSchedule_multipleEventFound() {
        String expectedMessage = String.format(Messages.MESSAGE_GENERATE_SUCCESS);
        GenerateScheduleCommand command = new GenerateScheduleCommand();
        List<DistinctDate> distinctDates = DistinctDatesProcessor.generateAllDistinctDateList(expectedModel);
        expectedModel.updateEventDistinctDatesList(distinctDates);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        System.out.println(model.getEventDistinctDatesList());
        System.out.println(Arrays.asList(DATE_10_10_2019, DATE_12_10_2019,
                DATE_13_10_2019, DATE_15_10_2019, DATE_05_12_2019, DATE_07_12_2019,
                DATE_31_12_2019, DATE_01_01_2020));
        assertEquals(Arrays.asList(DATE_10_10_2019, DATE_12_10_2019,
                DATE_13_10_2019, DATE_15_10_2019, DATE_05_12_2019, DATE_07_12_2019,
                DATE_31_12_2019, DATE_01_01_2020), model.getEventDistinctDatesList());
    }

}
