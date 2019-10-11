package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.commands.CommandTestUtil.INVALID_TUTORIAL_INDEX_DESC_1;
import static seedu.tarence.logic.commands.CommandTestUtil.INVALID_TUTORIAL_INDEX_DESC_2;
import static seedu.tarence.logic.commands.CommandTestUtil.INVALID_TUTORIAL_INDEX_DESC_3;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_FIRST_IN_LIST;

import org.junit.jupiter.api.Test;

import seedu.tarence.logic.commands.ListCommand;
import seedu.tarence.model.student.StudentsInTutorialPredicate;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();
    @Test
    public void parse_emptyArg_throwsParseException() {
        ListCommand expectedListCommand =
                new ListCommand(true);
        assertParseSuccess(parser, "     ", expectedListCommand);
    }

    @Test
    public void parse_validArgs_returnsListCommand() {
        // no leading and trailing whitespaces
        ListCommand expectedListCommand =
                new ListCommand(new StudentsInTutorialPredicate(INDEX_FIRST_IN_LIST));
        assertParseSuccess(parser, "1", expectedListCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 1 \n \t", expectedListCommand);
    }

    @Test
    public void parse_invalidArgs_returnsListCommand() {
        String invalidIndexMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE);
        // Invalid index - non-integer
        assertParseFailure(parser, INVALID_TUTORIAL_INDEX_DESC_1,
                invalidIndexMessage);

        // Invalid index - character
        assertParseFailure(parser, INVALID_TUTORIAL_INDEX_DESC_2,
                invalidIndexMessage);

        // Invalid index - Index <= 0
        assertParseFailure(parser, INVALID_TUTORIAL_INDEX_DESC_3,
                invalidIndexMessage);
    }
}
