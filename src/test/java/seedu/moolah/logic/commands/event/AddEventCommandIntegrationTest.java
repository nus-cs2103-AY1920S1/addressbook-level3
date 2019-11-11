package seedu.moolah.logic.commands.event;

import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.modelhistory.ModelHistory;

/**
 * Contains integration tests (interaction with the Model) for {@code AddExpenseCommand}.
 */
public class AddEventCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
    }

    // No addCommand should not be the same as another addCommand, odds are unlikely because of use of UUID

    @Test
    public void run_duplicateEvent_throwsCommandException() {
        Event eventInList = model.getMooLah().getEventList().get(0);
        assertCommandFailure(new AddEventCommand(eventInList), model, AddEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

}
