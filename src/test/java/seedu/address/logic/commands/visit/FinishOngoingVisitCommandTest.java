package seedu.address.logic.commands.visit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code CancelOngoingVisitCommand}. There is some overlap with BeginVisitCommandTest, so some of the tests are not here.
 */
public class FinishOngoingVisitCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_valid_success() {
        Model expectedModel = new ModelManager(model.getStagedAddressBook(), new UserPrefs());
        Model initialModel = new ModelManager(model.getStagedAddressBook(), new UserPrefs());

        BeginVisitCommand beginCommand = new BeginVisitCommand(INDEX_FIRST_PERSON);
        FinishOngoingVisitCommand finishCommand = new FinishOngoingVisitCommand();
        assertDoesNotThrow(() -> beginCommand.execute(model));
        assertDoesNotThrow(() -> finishCommand.execute(model));
        assertEquals(model.getOngoingVisit(), Optional.empty());
        //Verify that the visit has been stored
        assertNotEquals(initialModel.getStagedPersonList().get(0), model.getStagedPersonList().get(0));
    }
}
