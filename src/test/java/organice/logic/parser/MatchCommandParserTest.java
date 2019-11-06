package organice.logic.parser;

import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.commands.CommandTestUtil.INVALID_MATCHCOMMAND_ALL;
import static organice.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static organice.logic.commands.CommandTestUtil.NRIC_DESC_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.NRIC_DESC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static organice.logic.commands.CommandTestUtil.VALID_MATCHCOMMAND_ALL;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_IRENE;
import static organice.logic.parser.CommandParserTestUtil.assertParseFailure;
import static organice.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import organice.logic.commands.MatchCommand;


public class MatchCommandParserTest {
    private MatchCommandParser parser = new MatchCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble -- nric
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NRIC_DESC_PATIENT_IRENE,
                        new MatchCommand(VALID_NRIC_PATIENT_IRENE));

        // white space only preamble -- all
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_MATCHCOMMAND_ALL,
                new MatchCommand(MatchCommandParser.ALL));
    }

    @Test
    public void parse_nricFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE);

        // missing nric prefix -- nric input
        assertParseFailure(parser, VALID_NRIC_PATIENT_IRENE, expectedMessage);

        // missing nric prefix -- all input
        assertParseFailure(parser, MatchCommandParser.ALL, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // wrongly typed nric
        assertParseFailure(parser, INVALID_NRIC_DESC, MatchCommandParser.MESSAGE_INVALID_INPUTS);

        // wrongly typed all
        assertParseFailure(parser, INVALID_MATCHCOMMAND_ALL, MatchCommandParser.MESSAGE_INVALID_INPUTS);
    }

    @Test
    public void parse_multipleValues_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE);

        // multiple nrics -- throws error
        assertParseFailure(parser, NRIC_DESC_DOCTOR_AMY + NRIC_DESC_PATIENT_IRENE,
                expectedMessage);

        // multiple 'all' inputs -- throws error
        assertParseFailure(parser, VALID_MATCHCOMMAND_ALL + VALID_MATCHCOMMAND_ALL,
                expectedMessage);
    }
}
