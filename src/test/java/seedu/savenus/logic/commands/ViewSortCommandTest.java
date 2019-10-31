package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewSortCommand}.
 */
public class ViewSortCommandTest {
    private Model model = new ModelManager();

    @Test
    public void execute_correctReturnType() {
        ViewSortCommand test = new ViewSortCommand();
        assertTrue(test.execute(model) instanceof CommandResult);
    }
}
