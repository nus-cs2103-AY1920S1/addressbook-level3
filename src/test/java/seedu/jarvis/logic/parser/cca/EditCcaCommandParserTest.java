package seedu.jarvis.logic.parser.cca;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.EQUIPMENT_DESC_PADDLE;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.INVALID_EQUIPMENT_DESC;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.INVALID_NAME_DESC;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.INVALID_TYPE_DESC;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.TYPE_DESC_CANOEING;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.VALID_NAME_CANOEING;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.cca.EditCcaCommand;
import seedu.jarvis.model.cca.CcaName;
import seedu.jarvis.model.cca.CcaType;
import seedu.jarvis.model.cca.Equipment;

public class EditCcaCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCcaCommand.MESSAGE_USAGE);

    private EditCcaCommandParser parser = new EditCcaCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_CANOEING, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCcaCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_NAME_CANOEING, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_NAME_CANOEING, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, CcaName.MESSAGE_CONSTRAINTS); // invalid cca name
        assertParseFailure(parser, "1" + INVALID_TYPE_DESC, CcaType.MESSAGE_CONSTRAINTS); // invalid cca type
        assertParseFailure(parser, "1" + INVALID_EQUIPMENT_DESC,
                Equipment.MESSAGE_CONSTRAINTS); // invalid equipment

        // invalid type followed by valid equipment
        assertParseFailure(parser, "1" + INVALID_TYPE_DESC + EQUIPMENT_DESC_PADDLE, CcaType.MESSAGE_CONSTRAINTS);

        // valid type followed by invalid type. The test case for invalid type followed by valid type
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + TYPE_DESC_CANOEING + INVALID_TYPE_DESC, CcaType.MESSAGE_CONSTRAINTS);
    }
}
