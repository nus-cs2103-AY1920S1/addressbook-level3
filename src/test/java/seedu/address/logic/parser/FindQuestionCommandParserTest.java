//@@author shutingy-reused

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindQuestionCommand;
import seedu.address.model.flashcard.QuestionContainsAnyKeywordsPredicate;

public class FindQuestionCommandParserTest {

    private FindQuestionCommandParser parser = new FindQuestionCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        FindQuestionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindQuestionCommand() {
        // no leading and trailing whitespaces
        FindQuestionCommand expectedFindQuestionCommand =
                new FindQuestionCommand(new QuestionContainsAnyKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindQuestionCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindQuestionCommand);
    }

}
