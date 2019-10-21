package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;
import static thrift.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import thrift.logic.parser.HelpCommandParser;
import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.testutil.TypicalIndexes;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(HelpCommandParser.EMPTY_STRING), model, expectedCommandResult,
                expectedModel);
    }

    @Test
    public void execute_helpWithSpecificCommand_success() {
        CommandResult expectedCommandResult = new CommandResult(UndoCommand.MESSAGE_USAGE);
        assertCommandSuccess(new HelpCommand(UndoCommand.MESSAGE_USAGE), model, expectedCommandResult,
                expectedModel);
    }

    @Test
    public void equals() {
        HelpCommand helpCommand = new HelpCommand(AddExpenseCommand.MESSAGE_USAGE);
        HelpCommand helpCommandDuplicate = new HelpCommand(AddExpenseCommand.MESSAGE_USAGE);
        HelpCommand differentHelpCommand = new HelpCommand(AddIncomeCommand.MESSAGE_USAGE);

        // same object -> returns true
        assertTrue(helpCommand.equals(helpCommand));

        // same values for different objects -> returns true
        assertTrue(helpCommand.equals(helpCommandDuplicate));

        // different types -> returns false
        assertFalse(helpCommand.equals(new DeleteCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION)));

        // comparing with null -> returns false
        assertFalse(helpCommand.equals(null));

        // different values for different objects -> returns false
        assertFalse(helpCommand.equals(differentHelpCommand));
    }
}
