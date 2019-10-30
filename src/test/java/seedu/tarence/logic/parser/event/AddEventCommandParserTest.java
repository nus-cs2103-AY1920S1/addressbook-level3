package seedu.tarence.logic.parser.event;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tarence.logic.parser.ParserTestUtils.END_DATE_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.EVENT_NAME_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.INDEX_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.INVALID_START_DATE_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.MOD_CODE_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.START_DATE_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.TUT_NAME_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_END_DATE;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_EVENT_NAME;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_INDEX;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_MOD_CODE;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_START_DATE;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_TUT_NAME;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.event.AddEventCommand;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.Event;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

public class AddEventCommandParserTest {

    private AddEventCommandParser parser = new AddEventCommandParser();

    private SimpleDateFormat dateFormatter = new SimpleDateFormat(Tutorial.DATE_FORMAT);

    @Test
    void parse_validTutNameAndModCode_returnsAddCommand() throws ParseException {
        assertParseSuccess(parser, MOD_CODE_DESC + TUT_NAME_DESC + EVENT_NAME_DESC + START_DATE_DESC + END_DATE_DESC,
                new AddEventCommand(new ModCode(VALID_MOD_CODE), new TutName(VALID_TUT_NAME), null,
                        VALID_EVENT_NAME, dateFormatter.parse(VALID_START_DATE), dateFormatter.parse(VALID_END_DATE)));
    }

    @Test
    void parse_validTutIndex_returnsAddCommand() throws ParseException {
        assertParseSuccess(parser, INDEX_DESC + EVENT_NAME_DESC + START_DATE_DESC + END_DATE_DESC,
                new AddEventCommand(null, null, Index.fromOneBased(VALID_INDEX),
                        VALID_EVENT_NAME, dateFormatter.parse(VALID_START_DATE), dateFormatter.parse(VALID_END_DATE)));
    }

    @Test
    void parse_invalidDateFormat_throwsParseException() {
        assertParseFailure(parser, INDEX_DESC + EVENT_NAME_DESC + INVALID_START_DATE_DESC + END_DATE_DESC,
                Event.MESSAGE_CONSTRAINTS_START_END_TIME);
    }

    @Test
    void parse_missingArgsModCode_throwsParseException() {
        assertParseFailure(parser, TUT_NAME_DESC + EVENT_NAME_DESC + START_DATE_DESC + END_DATE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddEventCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_missingArgsIndex_throwsParseException() {
        assertParseFailure(parser, EVENT_NAME_DESC + START_DATE_DESC + END_DATE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddEventCommand.MESSAGE_USAGE));
    }
}
