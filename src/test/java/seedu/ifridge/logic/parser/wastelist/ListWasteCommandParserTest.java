package seedu.ifridge.logic.parser.wastelist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.MONTH_INVALID;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.MONTH_OCT_RELAXED;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.MONTH_RELATIVE_LAST_MONTH;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.MONTH_SEP_RELAXED;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_CURRENT_MONTH;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_LAST_MONTH;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_SEPTEMBER;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.wastelist.ListWasteCommand;

public class ListWasteCommandParserTest {

    private ListWasteCommandParser parser = new ListWasteCommandParser();

    @Test
    public void parse_noPrefix_success() {
        assertParseSuccess(parser, " ", new ListWasteCommand(WASTE_MONTH_CURRENT_MONTH));
    }

    @Test
    public void parse_validDateFormats_success() {
        // Relaxed date format
        assertParseSuccess(parser, MONTH_SEP_RELAXED, new ListWasteCommand(WASTE_MONTH_SEPTEMBER));

        // Relative date format
        assertParseSuccess(parser, MONTH_RELATIVE_LAST_MONTH, new ListWasteCommand(WASTE_MONTH_LAST_MONTH));
    }

    @Test
    public void parse_multiplePrefixesTakesLastPrefix() {

        // Multiple valid months -> parse the last argument
        assertParseSuccess(parser, MONTH_OCT_RELAXED + MONTH_SEP_RELAXED,
                new ListWasteCommand(WASTE_MONTH_SEPTEMBER));

        // Last argument valid -> success
        assertParseSuccess(parser, MONTH_INVALID + MONTH_SEP_RELAXED,
                new ListWasteCommand(WASTE_MONTH_SEPTEMBER));

        // Last argument invalid -> failure
        assertParseFailure(parser, MONTH_SEP_RELAXED + MONTH_INVALID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListWasteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDateFormat() {
        assertParseFailure(parser, MONTH_INVALID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListWasteCommand.MESSAGE_USAGE));
    }

}
