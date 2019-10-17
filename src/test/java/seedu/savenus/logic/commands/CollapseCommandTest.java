package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;

public class CollapseCommandTest {

    private final Model model = new ModelManager();

    @Test
    public void collapse_correctDefaultBoolean() {
        assertEquals(false, CollapseCommand.getStatus());
    }

    @Test
    public void execute_correctReturnType() {
        CollapseCommand collapseCommand = new CollapseCommand();
        assertEquals(true,
                collapseCommand.execute(model) instanceof CommandResult);
        assertEquals(true,
                collapseCommand.execute(model) instanceof CommandResult);
    }
}
