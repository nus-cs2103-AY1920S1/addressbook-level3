package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import seedu.address.model.history.HistoryManager;
import seedu.address.model.person.Person;

class SelectCommandTest {

    private Model model = new ModelManager(getTypicalAthletick(), getTypicalPerformance(), new TrainingManager(),
            new UserPrefs(), new HistoryManager());

    @Test
    public void execute_validIndexUnfliteredList_success() {
        Person personToSelect = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        SelectCommand selectCommand = new SelectCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, personToSelect);

        ModelManager expectedModel = new ModelManager(model.getAthletick(), model.getPerformance(),
                model.getTrainingManager(), new UserPrefs(), model.getHistory());

        expectedModel.selectPerson();

        assertCommandSuccess(selectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        SelectCommand selectCommand = new SelectCommand(outOfBoundIndex);

        assertCommandFailure(selectCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandExpection() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        assertTrue(outOfBoundIndex.getZeroBased() < model.getAthletick().getPersonList().size());

        SelectCommand selectCommand = new SelectCommand(outOfBoundIndex);

        assertCommandFailure(selectCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_PERSON);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_PERSON);

        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_PERSON);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        assertFalse(selectFirstCommand.equals(1));

        assertFalse(selectFirstCommand.equals(null));

        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showSelectPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
