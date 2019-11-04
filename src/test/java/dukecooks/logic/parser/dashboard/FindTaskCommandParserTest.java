package dukecooks.logic.parser.dashboard;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.dashboard.FindTaskCommand;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.dashboard.components.DashboardNameContainsKeywordsPredicate;

public class FindTaskCommandParserTest {

    private FindTaskCommandParser parser = new FindTaskCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                FindTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindTaskCommand expectedFindTaskCommand =
                new FindTaskCommand(new DashboardNameContainsKeywordsPredicate(Arrays.asList("Fish", "Burger")));
        CommandParserTestUtil.assertParseSuccess(parser, "Fish Burger", expectedFindTaskCommand);

        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, " \n Fish \n \t Burger  \t", expectedFindTaskCommand);
    }

}
