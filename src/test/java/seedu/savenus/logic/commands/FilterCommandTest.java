package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.UserPrefs;

public class FilterCommandTest {
    private List<String> fields;
    private FilterCommand command;
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void set_up() {
        fields = new ArrayList<String>();
        model = new ModelManager(getTypicalMenu(), new UserPrefs());
        expectedModel = new ModelManager(model.getMenu(), new UserPrefs());
    }

    @Test
    public void execute_recommendCommand() {
        assertCommandSuccess(new FilterCommand(fields), model,
            FilterCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void getFields_test() {
        command = new FilterCommand(fields);
        assertEquals(command.getFields(), fields);
        assertEquals(command.getFields(), new ArrayList<String>());
    }

    @Test
    public void equals_test() {
        command = new FilterCommand(fields);
        assertEquals(command, command);
        assertEquals(command, new FilterCommand(fields));
        assertNotEquals(command, null);
    }

}
