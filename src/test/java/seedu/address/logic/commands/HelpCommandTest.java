
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpCommand.getHelpMessage;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.help.SecondaryCommand;
import seedu.address.model.help.Type;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(getHelpMessage(), true, false, false, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpTypeBrief_success() {
        String expectedMessage = "Opened help window for the add_claim command. Type of help requested: brief";
        SecondaryCommand addClaimCommand = new SecondaryCommand("add_claim");
        Type briefType = new Type("brief");
        new HelpCommand(addClaimCommand, briefType);
        assertEquals(expectedMessage, getHelpMessage());
    }

    @Test
    public void execute_helpTypeApi_success() {
        String expectedMessage = "Opened help window for the sort command. Type of help requested: api";
        SecondaryCommand sortCommand = new SecondaryCommand("sort");
        Type apiType = new Type("api");
        new HelpCommand(sortCommand, apiType);
        assertEquals(expectedMessage, getHelpMessage());
    }

    @Test
    public void execute_helpTypeGuide_success() {
        String expectedMessage = "Opened help window for the help command. Type of help requested: guide";
        SecondaryCommand helpCommand = new SecondaryCommand("help");
        Type guideType = new Type("guide");
        new HelpCommand(helpCommand, guideType);
        assertEquals(expectedMessage, getHelpMessage());
    }
}
