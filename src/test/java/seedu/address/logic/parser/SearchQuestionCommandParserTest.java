//@@author shutingy-reused

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SearchQuestionCommand;
import seedu.address.model.flashcard.QuestionContainsAnyKeywordsPredicate;

public class SearchQuestionCommandParserTest {

    private FindQuestionCommandParser parser = new FindQuestionCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        SearchQuestionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindQuestionCommand() {
        // no leading and trailing whitespaces
        SearchQuestionCommand expectedSearchQuestionCommand =
                new SearchQuestionCommand(new QuestionContainsAnyKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedSearchQuestionCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedSearchQuestionCommand);
    }

}
