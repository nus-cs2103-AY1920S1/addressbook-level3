package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.parser.CliSyntax.ASCENDING_DIRECTION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_NAME;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {
    private Model model = new ModelManager();
    private List<String> fields = new ArrayList<String>();

    @Test
    public void return_correctFields() {
        fields.add(FIELD_NAME_NAME);
        fields.add(ASCENDING_DIRECTION);
        SortCommand test = new SortCommand(fields);
        assertEquals(fields, test.getFields());
    }

    @Test
    public void equals() {
        List<String> myFields = new ArrayList<>();
        myFields.add(FIELD_NAME_NAME);
        SortCommand command = new SortCommand(myFields);
        assertTrue(command.equals(command));

        assertFalse(command.equals(new SortCommand(null)));
    }

    @Test
    public void execute_correctReturnType() throws CommandException {
        SortCommand test = new SortCommand(fields);
        assertTrue(test.execute(model) instanceof CommandResult);
    }
}
