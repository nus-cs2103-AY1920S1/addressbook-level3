package seedu.tarence.logic.parser.assignment;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_INDEX_NON_POSITIVE;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tarence.logic.parser.ParserTestUtils.INDEX_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.INVALID_INDEX_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.INVALID_SCORE_DESC_NEGATIVE;
import static seedu.tarence.logic.parser.ParserTestUtils.INVALID_SCORE_DESC_NON_NUMBER;
import static seedu.tarence.logic.parser.ParserTestUtils.MOD_CODE_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.SCORE_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.TUT_NAME_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_INDEX;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_MOD_CODE;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_SCORE;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_TUT_NAME;

import org.junit.jupiter.api.Test;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.assignment.SetAssignmentScoreCommand;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.Assignment;
import seedu.tarence.model.tutorial.TutName;

public class SetAssignmentScoreCommandParserTest {

    private SetAssignmentScoreCommandParser parser = new SetAssignmentScoreCommandParser();

    @Test
    void parse_validTutorialAndAssignmentIndex_returnsAddCommand() {
        assertParseSuccess(parser, INDEX_DESC + INDEX_DESC + INDEX_DESC + SCORE_DESC,
                new SetAssignmentScoreCommand(null, null, Index.fromOneBased(VALID_INDEX),
                        Index.fromOneBased(VALID_INDEX), Index.fromOneBased(VALID_INDEX),
                        VALID_SCORE));
    }

    @Test
    void parse_validTutNameAndModCode_returnsSetCommand() {
        assertParseSuccess(parser,
                MOD_CODE_DESC + TUT_NAME_DESC + INDEX_DESC + INDEX_DESC + SCORE_DESC,
                new SetAssignmentScoreCommand(new ModCode(VALID_MOD_CODE), new TutName(VALID_TUT_NAME), null,
                        Index.fromOneBased(VALID_INDEX), Index.fromOneBased(VALID_INDEX),
                        VALID_SCORE));
    }

    @Test
    void parse_invalidTutIndex_throwsParseException() {
        assertParseFailure(parser, INVALID_INDEX_DESC + INDEX_DESC + INDEX_DESC + SCORE_DESC,
                MESSAGE_INVALID_INDEX_NON_POSITIVE);
    }

    @Test
    void parse_negativeScore_throwsParseException() {
        assertParseFailure(parser, MOD_CODE_DESC + TUT_NAME_DESC + INDEX_DESC + INDEX_DESC
                        + INVALID_SCORE_DESC_NEGATIVE,
                Assignment.MESSAGE_CONSTRAINTS_SCORE);
    }

    @Test
    void parse_nonNumberScore_throwsParseException() {
        assertParseFailure(parser, TUT_NAME_DESC + MOD_CODE_DESC + INDEX_DESC + INDEX_DESC
                        + INVALID_SCORE_DESC_NON_NUMBER,
                Assignment.MESSAGE_CONSTRAINTS_SCORE);
    }

    @Test
    void parse_missingArgsModCode_throwsParseException() {
        assertParseFailure(parser, TUT_NAME_DESC + INDEX_DESC + INDEX_DESC + SCORE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetAssignmentScoreCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_missingArgsIndexes_throwsParseException() {
        assertParseFailure(parser, INDEX_DESC + INDEX_DESC + SCORE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetAssignmentScoreCommand.MESSAGE_USAGE));
    }

}
