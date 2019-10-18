package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;

public class ExpandCommandTest {

    private Model model = new ModelManager();

    @Test
    public void expand_defaultBooleanValue() {
        assertEquals(true, ExpandCommand.getStatus());
    }

    @Test
    public void expand_correctReturnType() {
        ExpandCommand expandCommand = new ExpandCommand();
        assertEquals(true,
                expandCommand.execute(model) instanceof CommandResult);
    }
}
