package seedu.address.logic.parser.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.storage.EditTitleCommand;

/**
 * Test class for {@code EditStudyPlanTitleParser}.
 */
public class EditTitleCommandParserTest {

    private EditStudyPlanTitleParser parser = new EditStudyPlanTitleParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditTitleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyArg_returnsEditTitleCommand() {
        String validTitle = "non-empty";
        EditTitleCommand expectedEditTitleCommand = new EditTitleCommand(validTitle);
        assertParseSuccess(parser, validTitle, expectedEditTitleCommand);
    }

}
