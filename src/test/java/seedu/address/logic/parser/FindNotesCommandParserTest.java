package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.note.FindNotesCommand;
import seedu.address.model.note.NotesContainsKeywordsPredicate;

public class FindNotesCommandParserTest {
    private FindNotesCommandParser parser = new FindNotesCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindNotesCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindNotesCommand expectedFindNotesCommand =
                new FindNotesCommand(new NotesContainsKeywordsPredicate(Arrays.asList("CS", "MA")));
        assertParseSuccess(parser, "CS MA" , expectedFindNotesCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n CS \n \t MA  \t", expectedFindNotesCommand);
    }
}
