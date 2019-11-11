package seedu.address.logic.parser.allocate;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.commands.allocate.AutoAllocateCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;


/**
 * Contains unit tests for {@code AutoAllocateCommandParserTest}.
 */
public class AutoAllocateCommandParserTest {
    private static final String TAG_MALE = " " + PREFIX_TAG + "male";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AutoAllocateCommand.MESSAGE_USAGE);

    private AutoAllocateCommandParser parser = new AutoAllocateCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, CommandTestUtil.MANPOWER_COUNT_DESC, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // only tags specified
        assertParseFailure(parser, TAG_MALE, MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + CommandTestUtil.INVALID_MANPOWER_DESC, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, MESSAGE_INVALID_FORMAT); // invalid tag
        assertParseFailure(parser, "1" + CommandTestUtil.INVALID_MANPOWER_DESC + INVALID_TAG_DESC,
                MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1" + CommandTestUtil.VALID_MANPOWER_COUNT_TO_ADD + INVALID_TAG_DESC,
                MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_allFieldsSpecified_success() throws CommandException {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + "";
        AutoAllocateCommand expectedCommand = new AutoAllocateCommand(targetIndex, null,
                new HashSet<>());

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() throws CommandException {
        Index targetIndex = INDEX_SECOND_EVENT;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.MANPOWER_COUNT_DESC;

        AutoAllocateCommand expectedCommand = new AutoAllocateCommand(targetIndex,
                Integer.valueOf(CommandTestUtil.VALID_MANPOWER_COUNT_TO_ADD), new HashSet<Tag>());

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
