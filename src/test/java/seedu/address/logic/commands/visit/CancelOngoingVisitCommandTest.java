package seedu.address.logic.commands.visit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, Begin, Cancel, Finish command, Parser) and unit tests for
 * {@code CancelOngoingVisitCommand}. There is some overlap with BeginVisitCommandTest, so some of the tests are
 * not here.
 */
public class CancelOngoingVisitCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_valid_success() {
        Model expectedModel = new ModelManager(model.getStagedAddressBook(), new UserPrefs());

        BeginVisitCommand beginCommand = new BeginVisitCommand(INDEX_FIRST_PERSON);
        CancelOngoingVisitCommand cancelCommand = new CancelOngoingVisitCommand();
        assertDoesNotThrow(() -> beginCommand.execute(model));

        assertCommandSuccess(cancelCommand, model, CancelOngoingVisitCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(model.getOngoingVisit(), Optional.empty());
    }
}
