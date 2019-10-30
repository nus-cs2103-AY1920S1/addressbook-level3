package seedu.address.itinerary.parser;

import org.junit.jupiter.api.Test;
import seedu.address.itinerary.commands.EditCommand;
import seedu.address.itinerary.model.event.*;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.itinerary.parser.CliSyntax.*;
import static seedu.address.itinerary.parser.CliSyntax.PREFIX_DESCRIPTION;

class EditCommandParserTest {
    private static final Title VALID_TITLE_EVENT  = new Title("Awesome Title");
    private static final Date VALID_DATE_EVENT = new Date("28102019");
    private static final Location VALID_LOCATION_EVENT = new Location("Singapore");
    private static final Description VALID_DESCRIPTION_EVENT = new Description("My awesome description");
    private static final Time VALID_TIME_EVENT = new Time("2000");
    private static final Tag VALID_TAG_EVENT = new Tag("Priority: High");

    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE
            + "0000000000000000000000000000000000000000"
            + "0000000000000000000000000000000"; // Title should not be more than 70 characters
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "12-09-1997"; // Date not formatted correctly
    public static final String INVALID_TIME_DESC = " " + PREFIX_TIME + "1.00 p.m."; // Time not formatted correctly
    public static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION
            + "000000000000000000000"; // Location should not be more than 20 characters
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG
            + "000000000000000000000"; // Tag should not be more than 20 characters
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION
            + "000000000000000000000000000000000000000000000000000000000";
    // Description should not be more than 50 characters

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TITLE_EVENT.toString(), MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_TITLE_EVENT, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_TITLE_EVENT, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC, Title.MESSAGE_CONSTRAINTS); // invalid title
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_TIME_DESC, Time.MESSAGE_CONSTRAINTS); // invalid time
        assertParseFailure(parser, "1" + INVALID_LOCATION_DESC, Location.MESSAGE_CONSTRAINTS);
        // invalid description
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured due to short circuit
        // of the parse check of the EditEventCommandParser
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC + INVALID_DATE_DESC + VALID_TIME_EVENT
                        + VALID_LOCATION_EVENT, Title.MESSAGE_CONSTRAINTS);
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseSuccess(Parser<? extends Command> parser, String userInput, Command expectedCommand) {
        try {
            Command command = parser.parse(userInput);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
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
