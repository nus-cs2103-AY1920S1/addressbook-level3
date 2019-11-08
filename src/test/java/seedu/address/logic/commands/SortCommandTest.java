package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPerformance.getTypicalPerformance;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAthletick;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TrainingManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.history.HistoryManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAthletick(), getTypicalPerformance(), new TrainingManager(), new UserPrefs(),
            new HistoryManager());
        expectedModel = new ModelManager(model.getAthletick(), model.getPerformance(),
                new TrainingManager(), new UserPrefs(), new HistoryManager());
    }

    @Test
    public void execute_listIsAlreadySorted_showsSameList() {
        assertCommandSuccess(new SortCommand(), model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsUnsorted_showsSortedList() {
        model.deletePerson(ALICE);
        model.addPerson(ALICE);
        model.sortAthletickByName();
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
