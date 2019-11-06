package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_IRRELEVANT_PREFIXES;
import static seedu.address.logic.commands.CommandTestUtil.AVAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.DISTRICT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AVAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DISTRICT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VTYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAILABILITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISTRICT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VTYPE;
import static seedu.address.logic.commands.CommandTestUtil.VTYPE_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTRICT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTITY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTITY;
import static seedu.address.testutil.VehicleBuilder.DEFAULT_DISTRICT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditVehicleCommand;
import seedu.address.logic.commands.EditVehicleCommand.EditVehicle;
import seedu.address.model.vehicle.Availability;
import seedu.address.model.vehicle.District;
import seedu.address.testutil.EditVehicleBuilder;

public class EditVehicleCommandParserTest {
    private static final String INVALID_PREFIX_DESC = " qwe/hello";

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditVehicleCommand.MESSAGE_USAGE);

    private EditVehicleCommandParser parser = new EditVehicleCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, EditVehicleCommand.COMMAND_WORD, (MESSAGE_INVALID_INDEX + "\n"
                + MESSAGE_INVALID_FORMAT));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, EditVehicleCommand.COMMAND_WORD + "-5",
                MESSAGE_INVALID_INDEX + "\n" + MESSAGE_INVALID_FORMAT);

        //zero index
        assertParseFailure(parser, EditVehicleCommand.COMMAND_WORD + "0",
                MESSAGE_INVALID_INDEX + "\n" + MESSAGE_INVALID_FORMAT);

        //invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string",
                MESSAGE_INVALID_INDEX + "\n" + MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string",
                MESSAGE_IRRELEVANT_PREFIXES + "\n" + MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ENTITY;

        String userInput = targetIndex.getOneBased() + DISTRICT_DESC + VTYPE_DESC + AVAIL_DESC;

        EditVehicle editor = new EditVehicleBuilder().withDistrict(VALID_DISTRICT)
                .withVType(VALID_VTYPE)
                .withAvail(VALID_AVAILABILITY).build();

        EditVehicleCommand expectedCommand = new EditVehicleCommand(targetIndex, editor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ENTITY;
        String userInput = targetIndex.getOneBased() + DISTRICT_DESC + VTYPE_DESC;

        EditVehicle editor = new EditVehicleBuilder().withDistrict(VALID_DISTRICT)
                .withVType(VALID_VTYPE).build();

        EditVehicleCommand expectedCommand = new EditVehicleCommand(targetIndex, editor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        //District
        Index targetIndex = INDEX_SECOND_ENTITY;
        String userInput = targetIndex.getOneBased() + DISTRICT_DESC;
        EditVehicle editor = new EditVehicleBuilder().withDistrict(VALID_DISTRICT).build();
        EditVehicleCommand expectedCommand = new EditVehicleCommand(targetIndex, editor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //VType
        userInput = targetIndex.getOneBased() + VTYPE_DESC;
        editor = new EditVehicleBuilder().withVType(VALID_VTYPE).build();
        expectedCommand = new EditVehicleCommand(targetIndex, editor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //Availability
        userInput = targetIndex.getOneBased() + AVAIL_DESC;
        editor = new EditVehicleBuilder().withAvail(VALID_AVAILABILITY).build();
        expectedCommand = new EditVehicleCommand(targetIndex, editor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidPrefix_failure() {
        //invalid prefix behind a valid prefix
        assertParseFailure(parser, "1" + VTYPE_DESC + INVALID_PREFIX_DESC, MESSAGE_IRRELEVANT_PREFIXES);

        //invalid prefix as the first prefix: error thrown when parsing index
        assertParseFailure(parser, "1" + INVALID_PREFIX_DESC, MESSAGE_IRRELEVANT_PREFIXES
                + "\n" + MESSAGE_INVALID_FORMAT);

        //invalid prefix as first prefix followed by valid prefix: error thrown when parsing index
        assertParseFailure(parser, "1" + INVALID_PREFIX_DESC + DISTRICT_DESC, MESSAGE_IRRELEVANT_PREFIXES
                + "\n" + MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid district
        assertParseFailure(parser, " 1" + INVALID_DISTRICT_DESC, District.MESSAGE_CONSTRAINTS);

        // invalid VType
        assertParseFailure(parser, " 1" + INVALID_VTYPE_DESC, Messages.MESSAGE_NO_SUCH_VTYPE);

        //invalid Availability
        assertParseFailure(parser, " 1" + INVALID_AVAIL_DESC, Availability.MESSAGE_CONSTRAINTS);

        // invalid VType followed by valid district
        assertParseFailure(parser, "1" + INVALID_VTYPE_DESC + " " + PREFIX_DISTRICT + DEFAULT_DISTRICT,
                Messages.MESSAGE_NO_SUCH_VTYPE);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_DISTRICT_DESC + INVALID_AVAIL_DESC,
                District.MESSAGE_CONSTRAINTS);
    }
}


