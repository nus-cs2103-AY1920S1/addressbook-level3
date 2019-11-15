package seedu.address.itinerary.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_TITLE;

import org.junit.jupiter.api.Test;

import seedu.address.itinerary.logic.commands.EditCommand;
import seedu.address.itinerary.model.event.Date;
import seedu.address.itinerary.model.event.Description;
import seedu.address.itinerary.model.event.Location;
import seedu.address.itinerary.model.event.Time;
import seedu.address.itinerary.model.event.Title;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

class EditCommandParserTest {

    private static final Title VALID_TITLE_EVENT = new Title("Awesome Title");
    private static final Date VALID_DATE_EVENT = new Date("28102019");
    private static final Location VALID_LOCATION_EVENT = new Location("Singapore");
    private static final Description VALID_DESCRIPTION_EVENT = new Description("My awesome description");
    private static final Time VALID_TIME_EVENT = new Time("2000");

    private static final String TITLE_DESC_EVENT = " " + PREFIX_TITLE
            + VALID_TITLE_EVENT.toString();
    private static final String DATE_DESC_EVENT = " " + PREFIX_DATE
            + VALID_DATE_EVENT.getOriginalDate();
    private static final String TIME_DESC_EVENT = " " + PREFIX_TIME
            + VALID_TIME_EVENT.getOriginalTime();
    private static final String LOCATION_DESC_EVENT = " " + PREFIX_LOCATION
            + VALID_LOCATION_EVENT.toString();
    private static final String DESCRIPTION_DESC_EVENT = " " + PREFIX_DESCRIPTION
            + VALID_DESCRIPTION_EVENT.toString();

    private static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE
            + "0000000000000000000000000000000000000000"
            + "0000000000000000000000000000000"; // Title should not be more than 70 characters
    private static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "12-09-1997"; // Date not formatted correctly
    private static final String INVALID_TIME_DESC = " " + PREFIX_TIME + "1.00 p.m."; // Time not formatted correctly
    private static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION
            + "000000000000000000000"; // Location should not be more than 20 characters
    private static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION
            + "000000000000000000000000000000000000000000000000000000000";
    // Description should not be more than 50 characters

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, TITLE_DESC_EVENT, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TITLE_DESC_EVENT, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TITLE_DESC_EVENT, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 prefix/ string", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble before proper preamble
        assertParseFailure(parser, "1 some random string title/Valid Title", MESSAGE_INVALID_FORMAT);
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
        // Short circuits at the first fail of the prefix and return that field usage
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC + INVALID_DATE_DESC + TIME_DESC_EVENT
                        + LOCATION_DESC_EVENT, Title.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "1" + INVALID_TITLE_DESC + DATE_DESC_EVENT + INVALID_TIME_DESC
                + DESCRIPTION_DESC_EVENT, Title.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "1" + TITLE_DESC_EVENT + DATE_DESC_EVENT + INVALID_TIME_DESC
                + INVALID_DESCRIPTION_DESC, Time.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "1" + TITLE_DESC_EVENT + DATE_DESC_EVENT + INVALID_TIME_DESC
                + INVALID_LOCATION_DESC + INVALID_DESCRIPTION_DESC, Time.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "1" + TITLE_DESC_EVENT + INVALID_DATE_DESC + INVALID_TIME_DESC
                + INVALID_LOCATION_DESC + INVALID_DESCRIPTION_DESC, Date.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "1" + TITLE_DESC_EVENT + DATE_DESC_EVENT + TIME_DESC_EVENT
                + INVALID_LOCATION_DESC + INVALID_DESCRIPTION_DESC, Location.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() throws ParseException {
        // Single field checks
        parser.parse("1" + TITLE_DESC_EVENT);

        parser.parse("1" + DATE_DESC_EVENT);

        parser.parse("1" + TIME_DESC_EVENT);

        parser.parse("1" + LOCATION_DESC_EVENT);

        parser.parse("1" + DESCRIPTION_DESC_EVENT);

        // Multiple field check
        parser.parse("1" + TITLE_DESC_EVENT + DATE_DESC_EVENT);

        parser.parse("1" + LOCATION_DESC_EVENT + DESCRIPTION_DESC_EVENT);

        parser.parse("1" + TIME_DESC_EVENT + DESCRIPTION_DESC_EVENT);

        parser.parse("1" + TITLE_DESC_EVENT + TIME_DESC_EVENT + DESCRIPTION_DESC_EVENT);

        parser.parse("1" + DATE_DESC_EVENT + TIME_DESC_EVENT + LOCATION_DESC_EVENT);

        parser.parse("1" + TITLE_DESC_EVENT + DATE_DESC_EVENT + TIME_DESC_EVENT + LOCATION_DESC_EVENT
                + DESCRIPTION_DESC_EVENT);

        // Mixing prefix in different order and different index
        parser.parse("1" + DESCRIPTION_DESC_EVENT + TITLE_DESC_EVENT);

        parser.parse("2" + LOCATION_DESC_EVENT + TIME_DESC_EVENT + DATE_DESC_EVENT);

        parser.parse("3" + TIME_DESC_EVENT + TITLE_DESC_EVENT);

        parser.parse("4" + TIME_DESC_EVENT + DATE_DESC_EVENT + TITLE_DESC_EVENT);

        parser.parse("5" + DATE_DESC_EVENT + TITLE_DESC_EVENT + DESCRIPTION_DESC_EVENT);

        parser.parse("6" + DESCRIPTION_DESC_EVENT + LOCATION_DESC_EVENT + DATE_DESC_EVENT + TITLE_DESC_EVENT
                + TIME_DESC_EVENT);
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
