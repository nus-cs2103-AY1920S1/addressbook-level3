package seedu.address.itinerary.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.itinerary.logic.commands.SortCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

class SortCommandParserTest {

    private static final String TITLE_DESC_SORT = "title";
    private static final String TIME_DESC_SORT = "chronological";
    private static final String COMPLETE_DESC_SORT = "completion";
    private static final String PRIORITY_DESC_SORT = "priority";

    private SortCommandParser parser = new SortCommandParser();
    private String outcome = SortCommand.MESSAGE_SUCCESS;
    private SortCommand sortCommand = new SortCommand("priority");

    @Test
    public void parse_validArgs_returnsSortCommand() {
        assertEquals(sortCommand.MESSAGE_SUCCESS, outcome);
    }

    @Test
    public void parse_missingParts_failure() {
        // no details specified
        assertParseFailure(parser, TITLE_DESC_SORT, "Invalid command format! \n"
                + SortCommand.MESSAGE_USAGE);

        // no details specified
        assertParseFailure(parser, TIME_DESC_SORT, "Invalid command format! \n"
                + SortCommand.MESSAGE_USAGE);

        // no details specified
        assertParseFailure(parser, COMPLETE_DESC_SORT, "Invalid command format! \n"
                + SortCommand.MESSAGE_USAGE);

        // no details specified
        assertParseFailure(parser, PRIORITY_DESC_SORT, "Invalid command format! \n"
                + SortCommand.MESSAGE_USAGE);

        // no field specified
        assertParseFailure(parser, "by/", "Invalid command format! \n"
                + SortCommand.MESSAGE_USAGE);

        // no field and details specified
        assertParseFailure(parser, "", "Invalid command format! \n"
                + SortCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidCheck_condition() {
        assertThrows(ParseException.class, () -> parser.parse(SortCommand.COMMAND_WORD));

        assertThrows(ParseException.class, () -> parser.parse(SortCommand.COMMAND_WORD + " by/none"));

        assertThrows(ParseException.class, () -> parser.parse(SortCommand.COMMAND_WORD + " y/priority"));

        assertThrows(ParseException.class, () -> parser.parse(SortCommand.COMMAND_WORD + " y/"));

        assertThrows(ParseException.class, () -> parser.parse(SortCommand.COMMAND_WORD + " 1"));

        assertThrows(ParseException.class, () -> parser.parse(SortCommand.COMMAND_WORD + " by/"
                + TITLE_DESC_SORT + " by/" + PRIORITY_DESC_SORT));

        assertThrows(ParseException.class, () -> parser.parse(SortCommand.COMMAND_WORD + " by/"
                + TITLE_DESC_SORT + " by/" + COMPLETE_DESC_SORT));

        assertThrows(ParseException.class, () -> parser.parse(SortCommand.COMMAND_WORD + " by/"
                + PRIORITY_DESC_SORT + " by/" + TIME_DESC_SORT));
    }

    @Test
    public void parse_allFieldsSpecified_success() throws ParseException {
        // Single field checks
        parser.parse(" by/" + TITLE_DESC_SORT);

        parser.parse(" by/" + TIME_DESC_SORT);

        parser.parse(" by/" + COMPLETE_DESC_SORT);

        parser.parse(" by/" + PRIORITY_DESC_SORT);
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
