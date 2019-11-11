package seedu.address.logic.commands.event;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.EventBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class ClearEventsCommandTest {

    @Test
    public void execute_emptyEventBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        assertCommandSuccess(new ClearEventsCommand(), model, ClearEventsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs());
        expectedModel.setEventBook(new EventBook());
        assertCommandSuccess(new ClearEventsCommand(), model, ClearEventsCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
