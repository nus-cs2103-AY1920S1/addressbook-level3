package seedu.jarvis.logic.parser.cca;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.EQUIPMENT_DESC_BOAT;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.EQUIPMENT_DESC_PADDLE;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.INVALID_EQUIPMENT_DESC;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.INVALID_NAME_DESC;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.INVALID_TYPE_DESC;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.NAME_DESC_CANOEING;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.NAME_DESC_GUITAR_ENSEMBLE;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.TYPE_DESC_CANOEING;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.TYPE_DESC_GUITAR_ENSEMBLE;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.VALID_EQUIPMENT_PADDLE;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.VALID_NAME_CANOEING;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.VALID_NAME_TYPE_SPORT;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jarvis.testutil.cca.TypicalCcas.CANOEING;
import static seedu.jarvis.testutil.cca.TypicalEquipmentList.CANOEING_EQUIPMENT_LIST_WITH_TWO_EQUIPMENT;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.cca.AddCcaCommand;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.CcaName;
import seedu.jarvis.model.cca.CcaType;
import seedu.jarvis.model.cca.Equipment;
import seedu.jarvis.testutil.cca.CcaBuilder;

public class AddCcaCommandParserTest {
    private AddCcaCommandParser parser = new AddCcaCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Cca expectedCca = new CcaBuilder().build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_CANOEING + TYPE_DESC_CANOEING
                + EQUIPMENT_DESC_PADDLE , new AddCcaCommand(expectedCca));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_GUITAR_ENSEMBLE + NAME_DESC_CANOEING + TYPE_DESC_CANOEING
                + EQUIPMENT_DESC_PADDLE , new AddCcaCommand(expectedCca));

        // multiple phones - last type accepted
        assertParseSuccess(parser, NAME_DESC_GUITAR_ENSEMBLE + NAME_DESC_CANOEING + TYPE_DESC_GUITAR_ENSEMBLE
                + TYPE_DESC_CANOEING + EQUIPMENT_DESC_PADDLE , new AddCcaCommand(expectedCca));

        // multiple tags - all accepted
        Cca expectedCcaMultipleEquipment = new CcaBuilder(CANOEING)
                .withEquipmentList(CANOEING_EQUIPMENT_LIST_WITH_TWO_EQUIPMENT)
                .build();

        assertParseSuccess(parser, NAME_DESC_CANOEING + TYPE_DESC_CANOEING
                + EQUIPMENT_DESC_PADDLE + EQUIPMENT_DESC_BOAT, new AddCcaCommand(expectedCcaMultipleEquipment));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero equipment
        Cca expectedCca = new CcaBuilder(CANOEING).withEquipmentList().build();
        assertParseSuccess(parser, NAME_DESC_CANOEING + TYPE_DESC_CANOEING, new AddCcaCommand(expectedCca));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCcaCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_CANOEING + TYPE_DESC_CANOEING + EQUIPMENT_DESC_PADDLE,
                expectedMessage);

        // missing type prefix
        assertParseFailure(parser, NAME_DESC_CANOEING + VALID_NAME_TYPE_SPORT + EQUIPMENT_DESC_PADDLE,
                expectedMessage);

        // missing equipment prefix
        assertParseFailure(parser, NAME_DESC_CANOEING + TYPE_DESC_CANOEING + VALID_EQUIPMENT_PADDLE,
                CcaType.MESSAGE_CONSTRAINTS);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_CANOEING + VALID_NAME_TYPE_SPORT + VALID_EQUIPMENT_PADDLE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + TYPE_DESC_CANOEING + EQUIPMENT_DESC_PADDLE,
                CcaName.MESSAGE_CONSTRAINTS);

        // invalid type
        assertParseFailure(parser, NAME_DESC_CANOEING + INVALID_TYPE_DESC + EQUIPMENT_DESC_PADDLE,
                CcaType.MESSAGE_CONSTRAINTS);

        // invalid equipment
        assertParseFailure(parser, NAME_DESC_CANOEING + TYPE_DESC_CANOEING + INVALID_EQUIPMENT_DESC,
                Equipment.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_TYPE_DESC + EQUIPMENT_DESC_PADDLE,
                CcaName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_CANOEING + TYPE_DESC_CANOEING
                        + EQUIPMENT_DESC_PADDLE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCcaCommand.MESSAGE_USAGE));
    }
}
