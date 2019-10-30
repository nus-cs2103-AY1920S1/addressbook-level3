package seedu.tarence.logic.parser.event;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tarence.logic.parser.ParserTestUtils.INDEX_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.MOD_CODE_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.TUT_NAME_DESC;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_INDEX;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_MOD_CODE;
import static seedu.tarence.logic.parser.ParserTestUtils.VALID_TUT_NAME;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.event.ListEventsCommand;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

class ListEventsCommandParserTest {

    private ListEventsCommandParser parser = new ListEventsCommandParser();

    private SimpleDateFormat dateFormatter = new SimpleDateFormat(Tutorial.DATE_FORMAT);

    @Test
    void parse_validTutNameAndModCode_returnsListCommand() {
        assertParseSuccess(parser, MOD_CODE_DESC + TUT_NAME_DESC,
                new ListEventsCommand(new ModCode(VALID_MOD_CODE), new TutName(VALID_TUT_NAME), null));
    }

    @Test
    void parse_validTutIndex_returnsListCommand() {
        assertParseSuccess(parser, INDEX_DESC,
                new ListEventsCommand(null, null, Index.fromOneBased(VALID_INDEX)));
    }

    @Test
    void parse_missingArgsModCode_throwsParseException() {
        assertParseFailure(parser, TUT_NAME_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ListEventsCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_missingArgsIndex_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ListEventsCommand.MESSAGE_USAGE));
    }
}
