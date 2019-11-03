package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.note.FindNoteCommand;
import seedu.address.logic.parser.note.FindNoteCommandParser;
import seedu.address.model.note.TitleContainsKeywordsPredicate;

class FindNoteCommandParserTest {

    private FindNoteCommandParser parser = new FindNoteCommandParser();

    @Test
    void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindNoteCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindNoteCommand expectedFindNoteCommand =
                new FindNoteCommand(new TitleContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindNoteCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindNoteCommand);
    }

}
