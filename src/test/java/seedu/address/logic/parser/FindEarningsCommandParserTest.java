package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindEarningsCommand;
import seedu.address.model.earnings.ClassIdContainKeywordPredicate;

public class FindEarningsCommandParserTest {

    private FindEarningsCommandParser parser = new FindEarningsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEarningsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindEarningsCommand expectedFindCommand =
                new FindEarningsCommand(new ClassIdContainKeywordPredicate(Arrays.asList("CS", "MA")));
        assertParseSuccess(parser, "CS MA" , expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n CS \n \t MA  \t", expectedFindCommand);
    }

}
