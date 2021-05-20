package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Views.FIRST_VIEW;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.GotoCommand;

class GotoCommandParserTest {

    private GotoCommandParser parser = new GotoCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format("Invalid command format! \n"
                        + "goto: Goes to the view identified in the parameter typed by user\n"
                        + "Parameters: ' contacts', ' claims', ' incomes'\n"
                        + "Example: goto contacts",
                GotoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format("Invalid command format! \n"
                        + "goto: Goes to the view identified in the parameter typed by user\n"
                        + "Parameters: ' contacts', ' claims', ' incomes'\n"
                        + "Example: goto contacts",
                GotoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsGotoCommand() {
        // no leading and trailing whitespaces
        GotoCommand expectedGotoCommand =
                new GotoCommand(FIRST_VIEW);
        assertParseSuccess(parser, "contacts", expectedGotoCommand);
    }

}
