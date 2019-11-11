package seedu.address.financialtracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.financialtracker.model.Model;
import seedu.address.logic.commands.exceptions.CommandException;

class HelpCommandTest {

    private Model model = new Model();

    @Test
    void execute() throws CommandException {
        // regardless of model exists
        assertTrue(new HelpCommand().execute(null).equals(new HelpCommand().execute(model)));
    }
}
