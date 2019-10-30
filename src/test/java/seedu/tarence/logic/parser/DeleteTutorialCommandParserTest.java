package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_FIRST_IN_LIST;

import org.junit.jupiter.api.Test;

import seedu.tarence.logic.commands.DeleteTutorialCommand;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.TutName;

public class DeleteTutorialCommandParserTest {

    public static final String VALID_TUT_NAME = "Tut 99";
    public static final String VALID_MOD_CODE = "AZ1234";
    private DeleteTutorialCommandParser parser = new DeleteTutorialCommandParser();

    @Test
    void parse_validIndex_returnDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteTutorialCommand(INDEX_FIRST_IN_LIST));
    }

    @Test
    void parse_validTutName_returnDeleteCommand() {
        assertParseSuccess(parser, " tn/" + VALID_TUT_NAME, new DeleteTutorialCommand(new TutName(VALID_TUT_NAME)));
    }

    @Test
    void parse_validTutNameAndModCode_returnDeleteCommand() {
        assertParseSuccess(parser, " tn/" + VALID_TUT_NAME + " m/" + VALID_MOD_CODE,
                new DeleteTutorialCommand(new ModCode(VALID_MOD_CODE), new TutName(VALID_TUT_NAME)));
    }

    @Test
    void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTutorialCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTutorialCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_onlyModCodeGiven_throwsParseException() {
        assertParseFailure(parser, " m/" + VALID_MOD_CODE, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTutorialCommand.MESSAGE_USAGE));
    }
}
