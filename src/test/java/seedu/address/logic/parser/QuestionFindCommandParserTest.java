package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_MISSING_TEXT_SEARCH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.question.QuestionFindCommand;
import seedu.address.logic.parser.question.QuestionCommandParser;

public class QuestionFindCommandParserTest {

    private QuestionCommandParser parser = new QuestionCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " find/", MESSAGE_MISSING_TEXT_SEARCH);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        QuestionFindCommand expectedFindCommand =
                new QuestionFindCommand("What is 1+1?");
        assertParseSuccess(parser, " find/What is 1+1?", expectedFindCommand);
    }

}
