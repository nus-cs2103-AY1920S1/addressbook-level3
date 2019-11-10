package seedu.address.logic.parser.itinerary.dayview.edit;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.itinerary.days.edit.EditDayFieldCommand.COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATA_FILE_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.itinerary.Location.MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.itinerary.days.edit.EditDayFieldCommand;
import seedu.address.model.common.Photo;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Description;

class EditDayFieldParserTest {
    private EditDayFieldParser parser = new EditDayFieldParser();

    @Test
    public void parseValidBudget_success() {
        EditDayFieldCommand.EditDayDescriptor editDayDescriptor =
                new EditDayFieldCommand.EditDayDescriptor();
        editDayDescriptor.setBudget(new Budget(123));
        assertParseSuccess(parser, COMMAND_WORD + " " + PREFIX_BUDGET + "123",
                new EditDayFieldCommand(editDayDescriptor));
    }

    @Test
    public void parseValidDestination_success() {
        EditDayFieldCommand.EditDayDescriptor editDayDescriptor =
                new EditDayFieldCommand.EditDayDescriptor();
        editDayDescriptor.setDescription(new Description("Hello"));
        assertParseSuccess(parser, COMMAND_WORD + " " + PREFIX_DESCRIPTION + "Hello",
                new EditDayFieldCommand(editDayDescriptor));
    }

    @Test
    public void parseValidDescriptionNonBlank_success() {
        EditDayFieldCommand.EditDayDescriptor editDayDescriptor =
                new EditDayFieldCommand.EditDayDescriptor();
        editDayDescriptor.setDescription(new Description("China"));
        assertParseSuccess(parser, COMMAND_WORD + " " + PREFIX_DESCRIPTION + "China",
                new EditDayFieldCommand(editDayDescriptor));
    }

    @Test
    public void parseValidDescriptionBlank_success() {
        EditDayFieldCommand.EditDayDescriptor editDayDescriptor =
                new EditDayFieldCommand.EditDayDescriptor();
        editDayDescriptor.setDescription(new Description(""));
        assertParseSuccess(parser, COMMAND_WORD + " " + PREFIX_DESCRIPTION + "",
                new EditDayFieldCommand(editDayDescriptor));
    }

    // No actual file
    @Test
    public void parseInvalidPhoto_failure() {
        EditDayFieldCommand.EditDayDescriptor editDayDescriptor =
                new EditDayFieldCommand.EditDayDescriptor();
        assertParseFailure(parser, COMMAND_WORD + " " + PREFIX_DATA_FILE_PATH + "C:\\a.jpg",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Photo.MESSAGE_PATH_CONSTRAINTS));
    }

    @Test
    public void parseInvalidBudget_failure() {
        EditDayFieldCommand.EditDayDescriptor editDayDescriptor =
                new EditDayFieldCommand.EditDayDescriptor();
        assertParseFailure(parser, COMMAND_WORD + " " + PREFIX_BUDGET + "asdf",
                Budget.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parseInvalidDestination_failure() {
        EditDayFieldCommand.EditDayDescriptor editDayDescriptor =
                new EditDayFieldCommand.EditDayDescriptor();
        assertParseFailure(parser, COMMAND_WORD + " " + PREFIX_LOCATION + "",
                MESSAGE_CONSTRAINTS);
    }

}
