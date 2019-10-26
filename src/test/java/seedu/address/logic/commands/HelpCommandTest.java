package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.gui.HelpCommand.SHOWING_HELP_MESSAGE;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.cli.AddModuleCommand;
import seedu.address.logic.commands.cli.UndoCommand;
import seedu.address.logic.commands.datamanagement.RenameTagCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.gui.HelpCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalModulesInfo;
import seedu.address.testutil.TypicalStudyPlans;

public class HelpCommandTest {
    private static final String NON_EXISTENT_COMMAND_NAME = "notexistingcommandname";

    private Model model = new ModelManager(TypicalStudyPlans.getTypicalModulePlanner(), new UserPrefs(),
            TypicalModulesInfo.getTypicalModulesInfo());

    @Test
    public void execute_helpNoCommand_success() throws CommandException {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, false, false);
        assertEquals(new HelpCommand().execute(model), expectedCommandResult);
    }

    @Test
    public void execute_helpWithCommand_success() throws CommandException {
        CommandResult expectedCommandResult = new CommandResult(AddModuleCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(AddModuleCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(UndoCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(UndoCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(RenameTagCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(RenameTagCommand.COMMAND_WORD).execute(model), expectedCommandResult);
    }

    @Test
    public void execute_helpWithCommand_failure() {
        assertThrows(CommandException.class, () -> new HelpCommand(NON_EXISTENT_COMMAND_NAME).execute(model));
    }
}
