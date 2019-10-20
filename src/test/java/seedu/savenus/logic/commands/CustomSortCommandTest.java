package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;

public class CustomSortCommandTest {
    private Model model = new ModelManager();

    @Test
    public void execute_correctReturnType() throws CommandException {
        CustomSortCommand test = new CustomSortCommand();
        assertTrue(test.execute(model) instanceof CommandResult);
    }
}
