package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;

public class AutoSortCommandTest {
    private AutoSortCommand offCommand;
    private AutoSortCommand onCommand;
    private Model model;

    @BeforeEach
    public void setUp() {
        offCommand = new AutoSortCommand(false);
        onCommand = new AutoSortCommand(true);
        model = new ModelManager();
    }

    @Test
    public void execute_command_success() {
        assertTrue(offCommand.execute(model) instanceof CommandResult);
        assertTrue(onCommand.execute(model) instanceof CommandResult);
    }

    @Test
    public void test_values() {
        assertEquals(offCommand.getAutoSortValue(), false);
        assertEquals(onCommand.getAutoSortValue(), true);
    }

    @Test
    public void equals() {
        assertNotEquals(offCommand, onCommand);
        assertNotEquals(offCommand, new Object());
        assertEquals(offCommand, offCommand);
        assertEquals(offCommand, new AutoSortCommand(false));
    }
}
