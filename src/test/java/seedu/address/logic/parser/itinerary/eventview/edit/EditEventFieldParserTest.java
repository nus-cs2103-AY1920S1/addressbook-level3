package seedu.address.logic.parser.itinerary.eventview.edit;

import static seedu.address.logic.commands.itinerary.events.edit.EditEventFieldCommand.COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserDateUtil.MESSAGE_INVALID_FORMAT;
import static seedu.address.logic.parser.ParserDateUtil.TIME_FORMAT;
import static seedu.address.model.itinerary.Location.MESSAGE_CONSTRAINTS;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.itinerary.events.edit.EditEventFieldCommand;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Description;
import seedu.address.model.itinerary.Location;


class EditEventFieldParserTest {
    private EditEventFieldParser parser = new EditEventFieldParser();

    @Test
    public void parseValidStartTime_success() {
        EditEventFieldCommand.EditEventDescriptor editEventDescriptor =
                new EditEventFieldCommand.EditEventDescriptor();
        editEventDescriptor.setStartTime(LocalTime.of(12, 10));
        assertParseSuccess(parser, COMMAND_WORD + " " + PREFIX_DATE_START + "1210",
                new EditEventFieldCommand(editEventDescriptor));
    }

    @Test
    public void parseValidEndTime_success() {
        EditEventFieldCommand.EditEventDescriptor editEventDescriptor =
                new EditEventFieldCommand.EditEventDescriptor();
        editEventDescriptor.setEndTime(LocalTime.of(12, 20));
        assertParseSuccess(parser, COMMAND_WORD + " " + PREFIX_DATE_END + "1220",
                new EditEventFieldCommand(editEventDescriptor));
    }

    @Test
    public void parseValidBudget_success() {
        EditEventFieldCommand.EditEventDescriptor editEventDescriptor =
                new EditEventFieldCommand.EditEventDescriptor();
        editEventDescriptor.setBudget(new Budget(123));
        assertParseSuccess(parser, COMMAND_WORD + " " + PREFIX_BUDGET + "123",
                new EditEventFieldCommand(editEventDescriptor));
    }

    @Test
    public void parseValidDestination_success() {
        EditEventFieldCommand.EditEventDescriptor editEventDescriptor =
                new EditEventFieldCommand.EditEventDescriptor();
        editEventDescriptor.setDestination(new Location("Address"));
        assertParseSuccess(parser, COMMAND_WORD + " " + PREFIX_LOCATION + "Address",
                new EditEventFieldCommand(editEventDescriptor));
    }

    @Test
    public void parseValidDescriptionNonBlank_success() {
        EditEventFieldCommand.EditEventDescriptor editEventDescriptor =
                new EditEventFieldCommand.EditEventDescriptor();
        editEventDescriptor.setDescription(new Description("China"));
        assertParseSuccess(parser, COMMAND_WORD + " " + PREFIX_DESCRIPTION + "China",
                new EditEventFieldCommand(editEventDescriptor));
    }

    @Test
    public void parseValidDescriptionBlank_success() {
        EditEventFieldCommand.EditEventDescriptor editEventDescriptor =
                new EditEventFieldCommand.EditEventDescriptor();
        editEventDescriptor.setDescription(new Description(""));
        assertParseSuccess(parser, COMMAND_WORD + " " + PREFIX_DESCRIPTION + "",
                new EditEventFieldCommand(editEventDescriptor));
    }

    @Test
    public void parseInvalidStartTime_failure() {
        EditEventFieldCommand.EditEventDescriptor editEventDescriptor =
                new EditEventFieldCommand.EditEventDescriptor();
        editEventDescriptor.setStartTime(LocalTime.of(12, 20));
        assertParseFailure(parser, COMMAND_WORD + " " + PREFIX_DATE_END + "12/10/2019 1220",
                String.format(MESSAGE_INVALID_FORMAT, "time", TIME_FORMAT));
    }

    @Test
    public void parseInvalidEndTime_failure() {
        EditEventFieldCommand.EditEventDescriptor editEventDescriptor =
                new EditEventFieldCommand.EditEventDescriptor();
        assertParseFailure(parser, COMMAND_WORD + " " + PREFIX_DATE_END + "12/10/2019 1220",
                String.format(MESSAGE_INVALID_FORMAT, "time", TIME_FORMAT));
    }

    @Test
    public void parseInvalidBudget_failure() {
        EditEventFieldCommand.EditEventDescriptor editDayDescriptor =
                new EditEventFieldCommand.EditEventDescriptor();
        assertParseFailure(parser, COMMAND_WORD + " " + PREFIX_BUDGET + "asdf",
                Budget.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parseInvalidDestination_failure() {
        EditEventFieldCommand.EditEventDescriptor editDayDescriptor =
                new EditEventFieldCommand.EditEventDescriptor();
        assertParseFailure(parser, COMMAND_WORD + " " + PREFIX_LOCATION + "",
                MESSAGE_CONSTRAINTS);
    }
}
