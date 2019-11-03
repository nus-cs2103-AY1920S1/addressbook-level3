package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSec;

import org.junit.jupiter.api.Test;

import seedu.address.model.FinSec;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertEquals(model, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(new FinSec(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalFinSec(), new UserPrefs());
        expectedModel.setFinSec(new FinSec());

        assertEquals(model, expectedModel);
    }

}
