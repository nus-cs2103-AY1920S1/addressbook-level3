package seedu.tarence.logic.parser.assignment;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_INDEX_NON_POSITIVE;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tarence.logic.parser.ParserTestUtils.ASSIGN_NAME_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.END_DATE_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.INDEX_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.INVALID_INDEX_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.INVALID_SCORE_DESC_NEGATIVE;
import static seedu.tarence.logic.parser.ParserTestUtils.INVALID_SCORE_DESC_NON_NUMBER;
import static seedu.tarence.logic.parser.ParserTestUtils.INVALID_START_DATE_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.MOD_CODE_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.SCORE_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.START_DATE_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.TUT_NAME_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_ASSIGN_NAME;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_END_DATE;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_INDEX;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_MOD_CODE;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_SCORE;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_START_DATE;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_TUT_NAME;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.assignment.DeleteAssignmentCommand;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.Assignment;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

public class DeleteAssignmentCommandParserTest {

    private DeleteAssignmentCommandParser parser = new DeleteAssignmentCommandParser();

    private SimpleDateFormat dateFormatter = new SimpleDateFormat(Tutorial.DATE_FORMAT);

    @Test
    void parse_validTutorialIndex_returnsAddCommand() throws ParseException {
        assertParseSuccess(parser, INDEX_DESC + ASSIGN_NAME_DESC + SCORE_DESC + START_DATE_DESC + END_DATE_DESC,
                new DeleteAssignmentCommand(null, null, Index.fromOneBased(VALID_INDEX),
                        null, VALID_ASSIGN_NAME, VALID_SCORE,
                        dateFormatter.parse(VALID_START_DATE), dateFormatter.parse(VALID_END_DATE)));
    }

    @Test
    void parse_validTutNameAndModCode_returnsAddCommand() throws ParseException {
        assertParseSuccess(parser,
                TUT_NAME_DESC + MOD_CODE_DESC + ASSIGN_NAME_DESC + SCORE_DESC + START_DATE_DESC + END_DATE_DESC,
                new DeleteAssignmentCommand(new ModCode(VALID_MOD_CODE), new TutName(VALID_TUT_NAME), null,
                        null, VALID_ASSIGN_NAME, VALID_SCORE,
                        dateFormatter.parse(VALID_START_DATE), dateFormatter.parse(VALID_END_DATE)));
    }

    @Test
    void parse_validAssignIndex_returnsAddCommand() {
        assertParseSuccess(parser,
                INDEX_DESC + INDEX_DESC,
                new DeleteAssignmentCommand(null, null, Index.fromOneBased(VALID_INDEX),
                        Index.fromOneBased(VALID_INDEX), null, null, null, null));
    }

    @Test
    void parse_invalidTutIndex_throwsParseException() {
        assertParseFailure(parser, INVALID_INDEX_DESC + INDEX_DESC,
                MESSAGE_INVALID_INDEX_NON_POSITIVE);
    }

    @Test
    void parse_invalidAssignIndex_throwsParseException() {
        assertParseFailure(parser, INDEX_DESC + INVALID_INDEX_DESC,
                MESSAGE_INVALID_INDEX_NON_POSITIVE);
    }

    @Test
    void parse_invalidDateFormat_throwsParseException() {
        assertParseFailure(parser, TUT_NAME_DESC + MOD_CODE_DESC + ASSIGN_NAME_DESC + SCORE_DESC
                + INVALID_START_DATE_DESC + END_DATE_DESC,
                Assignment.MESSAGE_CONSTRAINTS_START_END_DATE);
    }

    @Test
    void parse_negativeScore_throwsParseException() {
        assertParseFailure(parser, TUT_NAME_DESC + MOD_CODE_DESC + ASSIGN_NAME_DESC + INVALID_SCORE_DESC_NEGATIVE
                        + START_DATE_DESC + END_DATE_DESC,
                Assignment.MESSAGE_CONSTRAINTS_MAX_SCORE);
    }

    @Test
    void parse_nonNumberScore_throwsParseException() {
        assertParseFailure(parser, TUT_NAME_DESC + MOD_CODE_DESC + ASSIGN_NAME_DESC + INVALID_SCORE_DESC_NON_NUMBER
                        + START_DATE_DESC + END_DATE_DESC,
                Assignment.MESSAGE_CONSTRAINTS_MAX_SCORE);
    }

    @Test
    void parse_missingArgs_throwsParseException() {
        assertParseFailure(parser, INDEX_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAssignmentCommand.MESSAGE_USAGE));
    }


}
