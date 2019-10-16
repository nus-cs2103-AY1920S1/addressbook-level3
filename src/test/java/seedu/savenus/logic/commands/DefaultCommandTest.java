package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;

public class DefaultCommandTest {

    private Model model = new ModelManager();

    @Test
    public void equals() {
        DefaultCommand test = new DefaultCommand();
        assertEquals(test, test);
        assertEquals(test, new DefaultCommand());
        assertEquals(new DefaultCommand(), new DefaultCommand());
    }

    @Test
    public void execute_correctReturnType() throws CommandException {
        DefaultCommand test = new DefaultCommand();
        assertTrue(test.execute(model) instanceof CommandResult);
    }
}
