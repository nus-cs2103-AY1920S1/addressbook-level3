package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.TIMING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PerformanceCommand;
import seedu.address.model.date.AthletickDate;

public class PerformanceCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, PerformanceCommand.MESSAGE_USAGE);

    private PerformanceCommandParser parser = new PerformanceCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, EVENT_DESC + DATE_DESC + TIMING_DESC, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // missing event prefix
        assertParseFailure(parser, VALID_INDEX + VALID_EVENT + DATE_DESC + TIMING_DESC, MESSAGE_INVALID_FORMAT);

        // missing date prefix
        assertParseFailure(parser, VALID_INDEX + EVENT_DESC + VALID_DATE + TIMING_DESC, MESSAGE_INVALID_FORMAT);

        // missing timing prefix
        assertParseFailure(parser, VALID_INDEX + EVENT_DESC + DATE_DESC + VALID_TIMING, MESSAGE_INVALID_FORMAT);

        // all prefixes missing
        assertParseFailure(parser, VALID_INDEX + VALID_EVENT + VALID_DATE + VALID_TIMING, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date
        assertParseFailure(parser,
            VALID_INDEX + EVENT_DESC + INVALID_DATE_DESC + TIMING_DESC, String.format(AthletickDate.ERROR_MESSAGE,
                        AthletickDate.DATE_FORMAT_TYPE_ONE) + "\n" + AthletickDate.MONTH_CONSTRAINTS
                        + "\n" + AthletickDate.YEAR_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
            PREAMBLE_NON_EMPTY + VALID_INDEX + VALID_EVENT + DATE_DESC + TIMING_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + EVENT_DESC + DATE_DESC + TIMING_DESC, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + EVENT_DESC + DATE_DESC + TIMING_DESC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 u/ string", MESSAGE_INVALID_FORMAT);
    }
}
