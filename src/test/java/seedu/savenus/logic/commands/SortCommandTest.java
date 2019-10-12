package seedu.savenus.logic.commands;

//import static seedu.savenus.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.TypicalFood.getTypicalMenu;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {
    private Model model = new ModelManager(getTypicalMenu(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMenu(), new UserPrefs());

    @Test
    public void equals() {
        List<String> myFields = new ArrayList<>();
        myFields.add("PRICE");
        SortCommand command = new SortCommand(myFields);
        assertTrue(command.equals(command));

        assertFalse(command.equals(new SortCommand(null)));
    }

}
