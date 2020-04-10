package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMING;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPerformance.getTypicalPerformance;
import static seedu.address.testutil.TypicalPersons.getTypicalAthletick;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TrainingManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.history.HistoryManager;
import seedu.address.model.performance.Event;
import seedu.address.model.performance.Record;
import seedu.address.model.performance.Timing;
import seedu.address.model.person.Person;

public class PerformanceCommandTest {

    private static final AthletickDate VALID_ATHLETICK_DATE_ONE =
            new AthletickDate(25, 12, 2019, 2, "December");
    private static final AthletickDate VALID_ATHLETICK_DATE_TWO =
            new AthletickDate(26, 12, 2019, 2, "December");
    private static final Timing VALID_ATHLETICK_TIMING = new Timing(VALID_TIMING);
    private static final Record VALID_RECORD = new Record(VALID_ATHLETICK_DATE_ONE, VALID_ATHLETICK_TIMING);
    private static final String INVALID_EVENT_NAME = "hurdles";

    private Model model = new ModelManager(getTypicalAthletick(), getTypicalPerformance(),
            new TrainingManager(), new UserPrefs(), new HistoryManager());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PerformanceCommand performanceCommand =
                new PerformanceCommand(INDEX_FIRST_PERSON, VALID_EVENT, VALID_ATHLETICK_DATE_TWO,
                        VALID_ATHLETICK_TIMING);

        String expectedMessage = String.format(PerformanceCommand.MESSAGE_SUCCESS,
                person.getName().fullName, VALID_EVENT, VALID_ATHLETICK_DATE_TWO, VALID_ATHLETICK_TIMING);
        ModelManager expectedModel = new ModelManager(model.getAthletick(), model.getPerformance(),
                model.getTrainingManager(), new UserPrefs(), model.getHistory());


        expectedModel.addRecord(VALID_EVENT, person, VALID_RECORD);

        assertCommandSuccess(performanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PerformanceCommand performanceCommand =
                new PerformanceCommand(outOfBoundIndex, VALID_EVENT, VALID_ATHLETICK_DATE_ONE, VALID_ATHLETICK_TIMING);
        assertCommandFailure(performanceCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAthletick().getPersonList().size());

        PerformanceCommand performanceCommand =
                new PerformanceCommand(outOfBoundIndex, VALID_EVENT, VALID_ATHLETICK_DATE_ONE, VALID_ATHLETICK_TIMING);

        assertCommandFailure(performanceCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidEvent_throwsCommandException() {
        // event does not exist
        PerformanceCommand performanceCommand = new PerformanceCommand(
                INDEX_FIRST_PERSON, INVALID_EVENT_NAME, VALID_ATHLETICK_DATE_ONE, VALID_ATHLETICK_TIMING);
        assertCommandFailure(performanceCommand, model, String.format(Event.MESSAGE_NO_SUCH_EVENT, INVALID_EVENT_NAME));
    }

    @Test
    public void execute_duplicateRecord_throwsCommandException() {
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // adding the record first
        Record record = new Record(VALID_ATHLETICK_DATE_ONE, new Timing(VALID_TIMING));
        model.getEvent(VALID_EVENT).addRecord(person, record);

        PerformanceCommand performanceCommand = new PerformanceCommand(
                INDEX_FIRST_PERSON, VALID_EVENT, VALID_ATHLETICK_DATE_ONE, VALID_ATHLETICK_TIMING);
        assertCommandFailure(performanceCommand, model,
                String.format(Event.MESSAGE_RECORD_EXISTS, person.getName().fullName,
                        VALID_ATHLETICK_DATE_ONE, VALID_EVENT));
    }
}
