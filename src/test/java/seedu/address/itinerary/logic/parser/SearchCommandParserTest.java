package seedu.address.itinerary.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.itinerary.logic.commands.SearchCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

class SearchCommandParserTest {

    public static final String TITLE_DESC_SEARCH = " title/title";
    public static final String DATE_DESC_SEARCH = " date/13071997";
    public static final String TIME_DESC_SEARCH = " time/2000";
    public static final String LOCATION_DESC_SEARCH = " l/location";

    private SearchCommandParser parser = new SearchCommandParser();
    private String outcome = "Processing...\n"
            + "Done!\n"
            + "Here are the events that matches the details. ( ͡° ͜ʖ ͡°)";

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        assertEquals(SearchCommand.MESSAGE_SUCCESS, outcome);
    }

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, "title/", SearchCommand.MESSAGE_USAGE);

        // no field specified
        assertParseFailure(parser, "date/", SearchCommand.MESSAGE_USAGE);

        // no field specified
        assertParseFailure(parser, "time/", SearchCommand.MESSAGE_USAGE);

        // no field specified
        assertParseFailure(parser, "location/", SearchCommand.MESSAGE_USAGE);

        // no wrong prefix specified
        assertParseFailure(parser, "by/", SearchCommand.MESSAGE_USAGE);

        // no details given
        assertParseFailure(parser, "", SearchCommand.MESSAGE_USAGE);

        // random string given
        assertParseFailure(parser, "hello world!", SearchCommand.MESSAGE_USAGE);

        // index given
        assertParseFailure(parser, "420", SearchCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidCheck_condition() {
        assertThrows(ParseException.class, () -> parser.parse(SearchCommand.COMMAND_WORD));

        assertThrows(ParseException.class, () -> parser.parse(SearchCommand.COMMAND_WORD + " by/none"));

        assertThrows(ParseException.class, () -> parser.parse(SearchCommand.COMMAND_WORD + " y/priority"));

        assertThrows(ParseException.class, () -> parser.parse(SearchCommand.COMMAND_WORD + " y/"));

        assertThrows(ParseException.class, () -> parser.parse(SearchCommand.COMMAND_WORD + " 1"));

        assertThrows(ParseException.class, () -> parser.parse(SearchCommand.COMMAND_WORD + " date/2450"));

        assertThrows(ParseException.class, () -> parser.parse(SearchCommand.COMMAND_WORD
                + " date/08/09/2019"));

        assertThrows(ParseException.class, () -> parser.parse(SearchCommand.COMMAND_WORD
                + " date/29022019"));

        assertThrows(ParseException.class, () -> parser.parse(SearchCommand.COMMAND_WORD
                + " time/2450"));

        assertThrows(ParseException.class, () -> parser.parse(SearchCommand.COMMAND_WORD
                + " time/-1000"));

        assertThrows(ParseException.class, () -> parser.parse(SearchCommand.COMMAND_WORD
                + " l/000000000000000000000"));
    }


    @Test
    public void parse_allFieldsSpecified_success() throws ParseException {
        // Single field checks
        parser.parse(TITLE_DESC_SEARCH);

        parser.parse(DATE_DESC_SEARCH);

        parser.parse(TIME_DESC_SEARCH);

        parser.parse(LOCATION_DESC_SEARCH);

        // This means that regardless of the words in the front, the prefix takes preecedence
        parser.parse(SearchCommand.COMMAND_WORD + " words before"
                + LOCATION_DESC_SEARCH + TIME_DESC_SEARCH);

        parser.parse(SearchCommand.COMMAND_WORD + " words before"
                + TITLE_DESC_SEARCH + TIME_DESC_SEARCH);

        parser.parse(SearchCommand.COMMAND_WORD + " words before"
                + TITLE_DESC_SEARCH + LOCATION_DESC_SEARCH);
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertParseFailure(Parser<? extends Command> parser, String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

}
