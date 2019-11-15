package seedu.address.itinerary.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_TITLE;

import org.junit.jupiter.api.Test;

import seedu.address.itinerary.logic.commands.AddEventCommand;
import seedu.address.itinerary.model.event.Date;
import seedu.address.itinerary.model.event.Description;
import seedu.address.itinerary.model.event.Location;
import seedu.address.itinerary.model.event.Time;
import seedu.address.itinerary.model.event.Title;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

class AddEventCommandParserTest {

    private static final Title VALID_TITLE_EVENT = new Title("Awesome Title");
    private static final Date VALID_DATE_EVENT = new Date("28102019");
    private static final Location VALID_LOCATION_EVENT = new Location("Singapore");
    private static final Description VALID_DESCRIPTION_EVENT = new Description("My awesome description");
    private static final Time VALID_TIME_EVENT = new Time("2000");

    private static final Title VALID_TITLE_EVENT2 = new Title("Another Cool Title");
    private static final Date VALID_DATE_EVENT2 = new Date("13071997");
    private static final Location VALID_LOCATION_EVENT2 = new Location("USA");
    private static final Description VALID_DESCRIPTION_EVENT2 = new Description("My cool description");
    private static final Time VALID_TIME_EVENT2 = new Time("0000");

    private static final String TITLE_DESC_EVENT = " " + PREFIX_TITLE
            + VALID_TITLE_EVENT.toString();
    private static final String TITLE_DESC_EVENT2 = " " + PREFIX_TITLE
            + VALID_TITLE_EVENT2.toString();
    private static final String DATE_DESC_EVENT = " " + PREFIX_DATE
            + VALID_DATE_EVENT.getOriginalDate();
    private static final String DATE_DESC_EVENT2 = " " + PREFIX_DATE
            + VALID_DATE_EVENT2.getOriginalDate();
    private static final String TIME_DESC_EVENT = " " + PREFIX_TIME
            + VALID_TIME_EVENT.getOriginalTime();
    private static final String TIME_DESC_EVENT2 = " " + PREFIX_TIME
            + VALID_TIME_EVENT2.getOriginalTime();
    private static final String LOCATION_DESC_EVENT = " " + PREFIX_LOCATION
            + VALID_LOCATION_EVENT.toString();
    private static final String LOCATION_DESC_EVENT2 = " " + PREFIX_LOCATION
            + VALID_LOCATION_EVENT2.toString();
    private static final String DESCRIPTION_DESC_EVENT = " " + PREFIX_DESCRIPTION
            + VALID_DESCRIPTION_EVENT.toString();
    private static final String DESCRIPTION_DESC_EVENT2 = " " + PREFIX_DESCRIPTION
            + VALID_DESCRIPTION_EVENT2.toString();

    private static final String COMBINATION1 = TITLE_DESC_EVENT + DATE_DESC_EVENT + TIME_DESC_EVENT
            + LOCATION_DESC_EVENT + DESCRIPTION_DESC_EVENT;
    private static final String COMBINATION2 = TITLE_DESC_EVENT2 + DATE_DESC_EVENT2 + TIME_DESC_EVENT2
            + LOCATION_DESC_EVENT2 + DESCRIPTION_DESC_EVENT2;

    private static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE
            + "0000000000000000000000000000000000000000"
            + "0000000000000000000000000000000"; // Title should not be more than 70 characters
    private static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "12-09-1997"; // Date not formatted correctly
    private static final String INVALID_TIME_DESC = " " + PREFIX_TIME + "2450"; // Time not formatted correctly
    private static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION
            + "000000000000000000000"; // Location should not be more than 20 characters
    private static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION
            + "000000000000000000000000000000000000000000000000000000000";
    // Description should not be more than 50 characters

    private AddEventCommandParser parser = new AddEventCommandParser();

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

    @Test
    public void allfieldstag_null_pointer() {
        // Check all field if passed. If all pass, the tag which depends on the combo box gives null exception

        assertThrows(NullPointerException.class, () -> parser.parse(COMBINATION1));

        assertThrows(NullPointerException.class, () -> parser.parse(COMBINATION2));
    }

    @Test
    public void invalid_field_input() {
        // Throws parse exception if the field is invalid and null pointer for optional fields
        assertThrows(ParseException.class, () -> parser.parse(INVALID_TITLE_DESC + DATE_DESC_EVENT
                + TIME_DESC_EVENT
                + LOCATION_DESC_EVENT + DESCRIPTION_DESC_EVENT));

        assertThrows(ParseException.class, () -> parser.parse(TITLE_DESC_EVENT + INVALID_DATE_DESC
                + TIME_DESC_EVENT
                + LOCATION_DESC_EVENT + DESCRIPTION_DESC_EVENT));

        assertThrows(ParseException.class, () -> parser.parse(TITLE_DESC_EVENT + DATE_DESC_EVENT
                + INVALID_TIME_DESC
                + LOCATION_DESC_EVENT + DESCRIPTION_DESC_EVENT));

        assertThrows(NullPointerException.class, () -> parser.parse(TITLE_DESC_EVENT + DATE_DESC_EVENT
                + TIME_DESC_EVENT
                + INVALID_LOCATION_DESC + DESCRIPTION_DESC_EVENT));

        assertThrows(NullPointerException.class, () -> parser.parse(TITLE_DESC_EVENT + DATE_DESC_EVENT
                + TIME_DESC_EVENT
                + LOCATION_DESC_EVENT + INVALID_DESCRIPTION_DESC));

        assertThrows(NullPointerException.class, () -> parser.parse(TITLE_DESC_EVENT + DATE_DESC_EVENT
                + TIME_DESC_EVENT
                + INVALID_LOCATION_DESC + INVALID_DESCRIPTION_DESC));
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
