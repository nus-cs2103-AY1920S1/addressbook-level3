package seedu.address.itinerary.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_TITLE;

import org.junit.jupiter.api.Test;
import seedu.address.itinerary.logic.commands.AddEventCommand;
import seedu.address.itinerary.model.event.Date;
import seedu.address.itinerary.model.event.Description;
import seedu.address.itinerary.model.event.Event;
import seedu.address.itinerary.model.event.Location;
import seedu.address.itinerary.model.event.Tag;
import seedu.address.itinerary.model.event.Time;
import seedu.address.itinerary.model.event.Title;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

class AddEventCommandParserTest {
    AddEventCommandParser parser = new AddEventCommandParser();
    private static final Title VALID_TITLE_EVENT  = new Title("Awesome Title");
    private static final Date VALID_DATE_EVENT = new Date("28102019");
    private static final Location VALID_LOCATION_EVENT = new Location("Singapore");
    private static final Description VALID_DESCRIPTION_EVENT = new Description("My awesome description");
    private static final Time VALID_TIME_EVENT = new Time("2000");
    private static final Tag VALID_TAG_EVENT = new Tag("Priority: High");

    private static final Title VALID_TITLE_EVENT2 = new Title("Another Cool Title");
    private static final Date VALID_DATE_EVENT2 = new Date("13071997");
    private static final Location VALID_LOCATION_EVENT2 = new Location("USA");
    private static final Description VALID_DESCRIPTION_EVENT2 = new Description("My cool description");
    private static final Time VALID_TIME_EVENT2 = new Time("0000");
    private static final Tag VALID_TAG_EVENT2 = new Tag("Priority: Medium");

    public static final String TITLE_DESC_EVENT = " " + PREFIX_TITLE + VALID_TITLE_EVENT;
    public static final String TITLE_DESC_EVENT2 = " " + PREFIX_TITLE + VALID_TITLE_EVENT2;
    public static final String DATE_DESC_EVENT = " " + PREFIX_DATE + VALID_DATE_EVENT;
    public static final String DATE_DESC_EVENT2 = " " + PREFIX_DATE + VALID_DATE_EVENT2;
    public static final String TIME_DESC_EVENT = " " + PREFIX_TIME + VALID_TIME_EVENT;
    public static final String TIME_DESC_EVENT2 = " " + PREFIX_TIME + VALID_TIME_EVENT2;
    public static final String LOCATION_DESC_EVENT = " " + PREFIX_LOCATION + VALID_LOCATION_EVENT;
    public static final String LOCATION_DESC_EVENT2 = " " + PREFIX_LOCATION + VALID_LOCATION_EVENT2;
    public static final String TAG_DESC_EVENT = " " + PREFIX_TAG + VALID_TAG_EVENT;
    public static final String TAG_DESC_EVENT2 = " " + PREFIX_TAG + VALID_TAG_EVENT2;
    public static final String DESCRIPTION_DESC_EVENT = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_EVENT;
    public static final String DESCRIPTION_DESC_EVENT2 = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_EVENT2;

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

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    private Event eventTest = new Event(VALID_TITLE_EVENT, VALID_DATE_EVENT, VALID_LOCATION_EVENT
            , VALID_DESCRIPTION_EVENT, VALID_TIME_EVENT, VALID_TAG_EVENT);
    private Event eventTest2 = new Event(VALID_TITLE_EVENT2, VALID_DATE_EVENT2, VALID_LOCATION_EVENT2
            , VALID_DESCRIPTION_EVENT2, VALID_TIME_EVENT2, VALID_TAG_EVENT2);

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        //missing title prefix
        assertParseFailure(parser, VALID_TITLE_EVENT + DATE_DESC_EVENT + TIME_DESC_EVENT + LOCATION_DESC_EVENT
                        + DESCRIPTION_DESC_EVENT,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, TITLE_DESC_EVENT + VALID_DATE_EVENT + TIME_DESC_EVENT + LOCATION_DESC_EVENT
                        + DESCRIPTION_DESC_EVENT,
                expectedMessage);

        // missing time prefix
        assertParseFailure(parser, TITLE_DESC_EVENT + DATE_DESC_EVENT + VALID_TIME_EVENT + LOCATION_DESC_EVENT
                        + DESCRIPTION_DESC_EVENT,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_EVENT + VALID_DATE_EVENT.toString()
                        + VALID_TIME_EVENT.toString() + VALID_LOCATION_EVENT.toString()
                        + VALID_DESCRIPTION_EVENT.toString(),
                expectedMessage);
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseSuccess(Parser<? extends Command> parser, String userInput,
                                          Command expectedCommand) {
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