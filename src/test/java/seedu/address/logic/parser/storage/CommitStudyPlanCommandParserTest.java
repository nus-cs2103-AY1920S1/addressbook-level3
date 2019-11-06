package seedu.address.logic.parser.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.storage.CommitStudyPlanCommand;

/**
 * Test class for {@code CommitStudyPlanCommandParser}.
 */
public class CommitStudyPlanCommandParserTest {

    private CommitStudyPlanCommandParser parser = new CommitStudyPlanCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CommitStudyPlanCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyArg_returnsEditTitleCommand() {
        String validCommitMessage = "non-empty";
        CommitStudyPlanCommand expectedCommitStudyPlanCommand = new CommitStudyPlanCommand(validCommitMessage);
        assertParseSuccess(parser, validCommitMessage, expectedCommitStudyPlanCommand);
    }

}
