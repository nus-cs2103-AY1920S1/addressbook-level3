package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;

public class MakeSortCommandTest {
    private Model model = new ModelManager();
    private List<String> fields = new ArrayList<String>();

    @Test
    public void execute_correctReturnType() throws CommandException {
        MakeSortCommand test = new MakeSortCommand(fields);
        assertTrue(test.execute(model) instanceof CommandResult);
    }
}
