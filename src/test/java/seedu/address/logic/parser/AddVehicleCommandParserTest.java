package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_IRRELEVANT_PREFIXES;
import static seedu.address.logic.commands.AddVehicleCommand.MESSAGE_USAGE;
import static seedu.address.logic.commands.CommandTestUtil.AVAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.DISTRICT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAILABILITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISTRICT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VNUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VTYPE;
import static seedu.address.logic.commands.CommandTestUtil.VNUM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VTYPE_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddVehicleCommand;
import seedu.address.model.vehicle.Vehicle;
import seedu.address.testutil.VehicleBuilder;

public class AddVehicleCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);
    private static final String INVALID_PREFIX_DESC = " qwe/hello";

    private AddVehicleCommandParser parser = new AddVehicleCommandParser();

    @Test
    public void parse_allFieldsValid_success() {
        String userInput = DISTRICT_DESC + VNUM_DESC + VTYPE_DESC + AVAIL_DESC;
        Vehicle toAdd = new VehicleBuilder().withVNum(VALID_VNUM).withDistrict(VALID_DISTRICT).withVType(VALID_VTYPE)
                .withAvail(VALID_AVAILABILITY).build();
        AddVehicleCommand expectedCommand = new AddVehicleCommand(toAdd);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_notAllFieldsPresent_failure() {
        String userInput = DISTRICT_DESC + VNUM_DESC + VTYPE_DESC;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_noFieldsSpecified_failure() {
        String userInput = "";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_irrelevantPrefix_failure() {

        String userInput = DISTRICT_DESC + INVALID_PREFIX_DESC + VNUM_DESC + VTYPE_DESC + AVAIL_DESC;
        assertParseFailure(parser, userInput, MESSAGE_IRRELEVANT_PREFIXES);
    }

}
