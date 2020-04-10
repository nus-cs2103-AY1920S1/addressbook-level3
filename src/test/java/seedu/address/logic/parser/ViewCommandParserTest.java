package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.feature.Feature;

public class ViewCommandParserTest {

    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "      ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs1_throwsParseException() {
        assertParseFailure(parser, "1", Feature.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidArgs2_throwsParseException() {
        assertParseFailure(parser, "string", Feature.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validArgs_returnsViewCommand() throws ParseException {
        assertParseSuccess(parser, "calendar", new ViewCommand(new Feature("calendar")));
    }
}
