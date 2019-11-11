package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.datamanagement.FindModuleCommand;
import seedu.address.logic.parser.datamanagement.FindModuleCommandParser;
import seedu.address.testutil.ModuleBuilder;

public class FindModuleCommandParserTest {

    private FindModuleCommandParser parser = new FindModuleCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindModuleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindModuleCommand expectedFindCommand =
                new FindModuleCommand(new ModuleBuilder().build().getModuleCode().toString());
        assertParseSuccess(parser, "findmod CS1231S", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "findmod     CS1231S     ", expectedFindCommand);
    }

}
