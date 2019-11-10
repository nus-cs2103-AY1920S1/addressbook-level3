package seedu.address.logic.parser.flashcard;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.flashcard.StartTimeTrialCommand;
import seedu.address.model.flashcard.FlashcardContainsTagPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TagUtil;

public class StartTimeTrialCommandParserTest {
    private StartTimeTrialCommandParser parser = new StartTimeTrialCommandParser();
    private String tagString = "one";
    private HashSet<Tag> tagSet = TagUtil.generateTagSetFromStrings(tagString);
    private String[] keywordArray = TagUtil.generateKeywordList(tagString);

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                StartTimeTrialCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsStartTimeTrialCommand() {
        // no leading and trailing whitespaces
        StartTimeTrialCommand expectedStartTimeTrialCommand =
                new StartTimeTrialCommand(new FlashcardContainsTagPredicate(tagSet), keywordArray);
        assertParseSuccess(parser, tagString, expectedStartTimeTrialCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + tagString + " \n \t", expectedStartTimeTrialCommand);
    }
}
