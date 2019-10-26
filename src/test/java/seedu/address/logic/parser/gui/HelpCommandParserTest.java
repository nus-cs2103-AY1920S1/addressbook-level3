package seedu.address.logic.parser.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.cli.AddModuleCommand;
import seedu.address.logic.commands.cli.UndoCommand;
import seedu.address.logic.commands.datamanagement.ViewTaggedCommand;
import seedu.address.logic.commands.gui.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class HelpCommandParserTest {
    private static final String NON_EXISTENT_COMMAND_NAME = "notexistingcommandname";

    @Test
    public void parse_helpNoCommand_success() throws ParseException {
        HelpCommand expectedHelpCommand = new HelpCommand();
        assertEquals(new HelpCommandParser().parse(""), expectedHelpCommand);
    }

    @Test
    public void parse_helpWithCommand_success() throws ParseException {
        HelpCommand expectedHelpCommand = new HelpCommand(AddModuleCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(AddModuleCommand.COMMAND_WORD), expectedHelpCommand);
        expectedHelpCommand = new HelpCommand(UndoCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(UndoCommand.COMMAND_WORD), expectedHelpCommand);
        expectedHelpCommand = new HelpCommand(ViewTaggedCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(ViewTaggedCommand.COMMAND_WORD), expectedHelpCommand);
    }

    @Test
    public void parse_helpWithCommand_failure() {
        assertThrows(ParseException.class, () -> new HelpCommandParser().parse(NON_EXISTENT_COMMAND_NAME));
    }
}
