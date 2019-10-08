/*
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddFlashcardCommand;

/*
public class AddFlashcardCommandParserTest {
    private AddFlashcardCommandParser parser = new AddFlashcardCommandParser();
    private final String nonEmptyQuestion = "What is 1 + 1?";
    private final String nonEmptyAnswer = "2";

    @Test
    public void parse_indexSpecified_success() {
        // have remark
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK + nonEmptyRemark;
        AddFlashcardCommand expectedCommand = new AddFlashcardCommand(INDEX_FIRST_PERSON, nonEmptyRemark);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no remark
        userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK;
        expectedCommand = new AddFlashcardCommand(INDEX_FIRST_PERSON, "");
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFlashcardCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, AddFlashcardCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, AddFlashcardCommand.COMMAND_WORD + " " + nonEmptyRemark, expectedMessage);
    }
}
*/
