package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddVisitCommand;

public class AddVisitCommandParserTest {
    private AddVisitCommandParser parser = new AddVisitCommandParser();
    private String nonEmptyDate = "12/12/2012";
    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddVisitCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, AddVisitCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, AddVisitCommand.COMMAND_WORD + " " + nonEmptyDate, expectedMessage);
    }
}
