package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SearchAnswerCommand;
import seedu.address.model.flashcard.AnswerContainsAnyKeywordsPredicate;

public class SearchAnswerCommandParserTest {

    private FindAnswerCommandParser parser = new FindAnswerCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        SearchAnswerCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindAnswerCommand() {
        // no leading and trailing whitespaces
        SearchAnswerCommand expectedSearchAnswerCommand =
                new SearchAnswerCommand(new AnswerContainsAnyKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedSearchAnswerCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedSearchAnswerCommand);
    }

}
