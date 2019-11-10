package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_DATE_INVALID;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_STRING_1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAYTIME_1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAYTIME_1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseCorrectIndexFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseCorrectIndexSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseInvalidIndexFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseNegativeIndexFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseNoIndexAndFieldFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseNoIndexFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseZeroIndexFailure;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalEventDates.OCT_20_2019;
import static seedu.address.testutil.TypicalEventDates.OCT_22_2019;
import static seedu.address.testutil.TypicalEventDayTimes.TIME_0800_TO_1800;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.AssignDateCommand;
import seedu.address.model.event.EventDayTime;

class AssignDateCommandParserTest {
    private static final String MAP_MUSICAL_WITH_DATE_RANGE = " " + VALID_DATE_1 + VALID_DATE_3 + VALID_DAYTIME_1;
    private static final String MAP_MUSICAL_WITH_TARGET_DATE = " " + VALID_DATE_1 + VALID_DAYTIME_1;
    private static final String MAP_MUSICAL_WITHOUT_DATE = " " + VALID_DAYTIME_1;
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignDateCommand.MESSAGE_USAGE);
    private AssignDateCommandParser parser = new AssignDateCommandParser();

    @Test
    void parse_allFieldsPresent_success() {
        //target date stated
        assertParseCorrectIndexSuccess(parser, MAP_MUSICAL_WITH_TARGET_DATE,
                new AssignDateCommand(INDEX_FIRST_EVENT, OCT_20_2019, TIME_0800_TO_1800));

        //range stated
        assertParseCorrectIndexSuccess(parser, MAP_MUSICAL_WITH_DATE_RANGE,
                new AssignDateCommand(INDEX_FIRST_EVENT, OCT_20_2019, OCT_22_2019, TIME_0800_TO_1800));

        //date not stated
        assertParseCorrectIndexSuccess(parser, MAP_MUSICAL_WITHOUT_DATE,
                new AssignDateCommand(INDEX_FIRST_EVENT, TIME_0800_TO_1800));
    }

    @Test
    void parse_invalidArgs_throwsParseException() {
        assertParseNoIndexAndFieldFailure(parser, MESSAGE_INVALID_FORMAT);
        assertParseNegativeIndexFailure(parser, MAP_MUSICAL_WITH_TARGET_DATE, MESSAGE_INVALID_INDEX);
        assertParseZeroIndexFailure(parser, MAP_MUSICAL_WITH_TARGET_DATE, MESSAGE_INVALID_INDEX);
        assertParseInvalidIndexFailure(parser, MAP_MUSICAL_WITH_TARGET_DATE, MESSAGE_INVALID_INDEX);
        assertParseNoIndexAndFieldFailure(parser, MESSAGE_INVALID_FORMAT);
    }

    @Test
    void parse_compulsoryFieldMissing_failure() {
        //missing index
        assertParseNoIndexFailure(parser, MAP_MUSICAL_WITH_TARGET_DATE, MESSAGE_INVALID_FORMAT);

        //correct index, missing time, have target date
        assertParseCorrectIndexFailure(parser, VALID_DATE_1, MESSAGE_INVALID_FORMAT);

        //missing start date, have end date
        assertParseCorrectIndexFailure(parser, VALID_DATE_3 + VALID_DAYTIME_1, MESSAGE_INVALID_FORMAT);
    }

    @Test
    void parse_invalidValues_throwsParseException() {
        //target date is not valid
        assertParseCorrectIndexFailure(parser, PREFIX_DATE + INVALID_DATE_1 + VALID_DAYTIME_1,
                String.format(MESSAGE_DATE_INVALID, INVALID_DATE_STRING_1));

        //start and end date is not valid
        assertParseCorrectIndexFailure(parser, PREFIX_DATE + INVALID_DATE_1
                        + INVALID_END_DATE_1 + VALID_DAYTIME_1,
                String.format(MESSAGE_DATE_INVALID, INVALID_DATE_STRING_1));

        //time is not valid
        assertParseCorrectIndexFailure(parser, MAP_MUSICAL_WITHOUT_DATE + PREFIX_EVENT_TIME + INVALID_DAYTIME_1,
                EventDayTime.MESSAGE_CONSTRAINTS);
    }

}
