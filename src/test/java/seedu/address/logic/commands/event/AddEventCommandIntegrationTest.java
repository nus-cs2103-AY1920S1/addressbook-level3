package seedu.address.logic.commands.event;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelHistory;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.expense.Event;

/**
 * Contains integration tests (interaction with the Model) for {@code AddExpenseCommand}.
 */
public class AddEventCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
    }

    // No addCommand should not be the same as another addCommand, odds are unlikely because of use of UUID

    @Test
    public void run_duplicateEvent_throwsCommandException() {
        Event eventInList = model.getMooLah().getEventList().get(0);
        assertCommandFailure(new AddEventCommand(eventInList), model, AddEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

}
