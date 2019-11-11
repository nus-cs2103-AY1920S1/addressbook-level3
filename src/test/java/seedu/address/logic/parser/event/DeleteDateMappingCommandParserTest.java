package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_DATE_INVALID;
import static seedu.address.commons.core.Messages.MESSAGE_DATE_START_AFTER_END;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_STRING_1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_STRING_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_4;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseCorrectIndexFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseCorrectIndexSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseInvalidIndexFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseNegativeIndexFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseNoFieldFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseNoIndexAndFieldFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseZeroIndexFailure;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalEventDates.OCT_19_2019;
import static seedu.address.testutil.TypicalEventDates.OCT_20_2019;
import static seedu.address.testutil.TypicalEventDates.OCT_22_2019;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.DeleteDateMappingCommand;

class DeleteDateMappingCommandParserTest {
    private static final String DELETE_DATE_MAP_MUSICAL = " " + VALID_DATE_1;
    private static final String DELETE_DATE_MAP_RANGE_MUSICAL = " " + VALID_DATE_1 + VALID_DATE_3;
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteDateMappingCommand.MESSAGE_USAGE);
    private DeleteDateMappingCommandParser parser = new DeleteDateMappingCommandParser();

    @Test
    void parse_allFieldsPresent_success() {
        //only target date
        assertParseCorrectIndexSuccess(parser, DELETE_DATE_MAP_MUSICAL,
                new DeleteDateMappingCommand(INDEX_FIRST_EVENT, OCT_20_2019));

        //has date range
        assertParseCorrectIndexSuccess(parser, DELETE_DATE_MAP_RANGE_MUSICAL,
                new DeleteDateMappingCommand(INDEX_FIRST_EVENT, OCT_20_2019, OCT_22_2019));
    }


    @Test
    void parse_compulsoryFieldMissing_failure() {
        //missing index
        assertParseFailure(parser, DELETE_DATE_MAP_MUSICAL, MESSAGE_INVALID_FORMAT);

        //missing target date
        assertParseNoFieldFailure(parser, MESSAGE_INVALID_FORMAT);

        //only state ending date
        assertParseCorrectIndexFailure(parser, VALID_DATE_3, MESSAGE_INVALID_FORMAT);

    }

    @Test
    void parse_invalidArgs_throwsParseException() {
        assertParseNoIndexAndFieldFailure(parser, MESSAGE_INVALID_FORMAT);
        assertParseNegativeIndexFailure(parser, DELETE_DATE_MAP_MUSICAL, MESSAGE_INVALID_INDEX);
        assertParseZeroIndexFailure(parser, DELETE_DATE_MAP_MUSICAL, MESSAGE_INVALID_INDEX);
        assertParseInvalidIndexFailure(parser, DELETE_DATE_MAP_MUSICAL, MESSAGE_INVALID_INDEX);

        //target date is not valid
        assertParseCorrectIndexFailure(parser, PREFIX_DATE + INVALID_START_DATE_DESC,
                String.format(MESSAGE_DATE_INVALID, INVALID_DATE_STRING_1));

        //end date is not valid
        assertParseCorrectIndexFailure(parser, DELETE_DATE_MAP_MUSICAL + INVALID_END_DATE_DESC,
                String.format(MESSAGE_DATE_INVALID, INVALID_DATE_STRING_2));

        //dates and time is valid, but startDate > endDate
        assertParseCorrectIndexFailure(parser, PREFIX_DATE + VALID_DATE_1 + VALID_DATE_4,
                String.format(MESSAGE_DATE_START_AFTER_END, OCT_20_2019, OCT_19_2019));
    }

}
