package seedu.jarvis.logic.parser.cca;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.cca.FindCcaCommand;
import seedu.jarvis.model.cca.CcaNameContainsKeywordsPredicate;

public class FindCcaCommandParserTest {

    private FindCcaCommandParser parser = new FindCcaCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCcaCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCcaCommand expectedFindCcaCommand =
                new FindCcaCommand(new CcaNameContainsKeywordsPredicate(Arrays.asList("Canoeing", "Guitar")));
        assertParseSuccess(parser, "Canoeing Guitar", expectedFindCcaCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Canoeing \n \t Guitar  \t", expectedFindCcaCommand);
    }

}
